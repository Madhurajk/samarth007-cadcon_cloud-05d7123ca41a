package com.cadcon.cloud.controllers.login;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cadcon.cloud.models.Person;
import com.cadcon.cloud.services.UserService;
import com.cadcon.cloud.util.JwtUtil;

@RequestMapping("api/v1/authenticate")
@RestController
public class LoginControllers {

    private Logger logger = LoggerFactory.getLogger(LoginControllers.class);

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public LoginControllers(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(path = "login/otp")
    public List<Person> getUserForPhoneNumber(@RequestParam("phone") String phoneNumber,
            @RequestBody CreateUserRequest request) {
        logger.info("Logging in the user with otp using phone number : {} , loginMode : {}, deviceIdentifier : {} ",
                phoneNumber, request.getLoginMode(), request.getDeviceIdentifier());
        List<Person> usersAgainstPhoneNumber = userService.getPersonByPhoneNumber(phoneNumber);
        if (usersAgainstPhoneNumber.size() == 1) {
            logger.info("Found user for number : {}, updating the secret", phoneNumber);
            String updatedSecrete = userService.updateUserSecret(usersAgainstPhoneNumber.get(0));
            logger.debug("Updated secret value for user : {} ", updatedSecrete);
            usersAgainstPhoneNumber.get(0).setUserSecret(updatedSecrete);
            userService.updateLoginAttempt(usersAgainstPhoneNumber.get(0).getId(), request.getLoginMode(),
                    request.getDeviceIdentifier());
        }
        return usersAgainstPhoneNumber;
    }

    @PostMapping(path = "/login")
    public Person getUserByUsername(@RequestBody CreateUserByUsernameRequest request) {
        logger.info("Logging in with the username : {}, usersecret : {}", request.getUserName(),
                request.getUserSecret());
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getUserSecret()));
        if (authenticate.isAuthenticated()) {
            Person userByUsername = userService.getUserByUsername(request.getUserName());
            userService.updateLoginAttempt(userByUsername.getId(), request.getLoginMode(),
                    request.getDeviceIdentifier());
            return userByUsername;
        } else {
            throw new IllegalStateException("user does not exist");
        }
    }

    @PostMapping()
    public TokenResponse refreshToken(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserName(), request.getUserSecret()));
        } catch (Exception e) {
            throw new IllegalStateException("incorrect username and password");
        }
        Person person = userService.getUserByUsername(request.getUserName());
        final String jwt = jwtUtil.generateToken(person);
        return new TokenResponse(jwt);
    }

}
