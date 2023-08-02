package com.cadcon.cloud.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cadcon.cloud.controllers.login.CreateUserByUsernameRequest;
import com.cadcon.cloud.controllers.user.CarousalData;
import com.cadcon.cloud.dao.UserDao;
import com.cadcon.cloud.models.Email;
import com.cadcon.cloud.models.JwtUser;
import com.cadcon.cloud.models.Media;
import com.cadcon.cloud.models.Person;
import com.cadcon.cloud.models.StudentProfile;
import com.cadcon.cloud.util.ValidateUser;

@Service
public class UserService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    ValidateUser validateUser;

    public List<Person> getPersonByPhoneNumber(String phoneNumber) {
        logger.debug("requesting perof for phone number {}", phoneNumber);
        return userDao.getUserByPhoneNumber(phoneNumber);
    }

    public void updateLoginAttempt(long id, String loginMode, String deviceIdentifier) {
        userDao.addLoginAttempt(id, loginMode, deviceIdentifier);
    }

    public Person getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public boolean validateUserLogin(CreateUserByUsernameRequest request) {
        return userDao.doesUserExists(request.getUserName(), request.getUserSecret());
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Person userByUsername = userDao.getUserByUsername(userName);
        return new JwtUser(userByUsername.getUserName(), userByUsername.getUserSecret(),
                Arrays.asList(UserPermissions.USER));
    }

    public StudentProfile getStudentProfileById(long id) {
        return userDao.getStudentProfile(id);
    }

    public long studentIdByUserId(long userId) {
        return userDao.getStudentIdByUserId(userId);
    }

    public List<CarousalData> getCarousalDetails(long studentId) {
        return userDao.getCarousalForStudent(studentId);
    }

    public Media getSponserInfo(long studentId) {
        return userDao.getSponserInfo(studentId);
    }

    @Transactional
    public void updateStudentInformation(StudentProfile newStudent) {
        StudentProfile dbStudent = getStudentProfileById(newStudent.getId());
        if (!dbStudent.getFirstName().equals(newStudent.getFirstName())) {
            userDao.updateUserFirstName(newStudent);
        }

        if (!dbStudent.getLastName().equals(newStudent.getLastName())) {
            userDao.updateUserLastName(newStudent);
        }

        List<Email> dbEmailList =
                dbStudent.getPrimaryEmail().stream().filter(Email::isPrimary).collect(Collectors.toList());

        if (dbEmailList.size() > 0) {
            Email dbEmail = dbEmailList.get(0);
            String newEmail = newStudent.getPrimaryEmail().stream().filter(Email::isPrimary)
                    .collect(Collectors.toList()).get(0).getEmail();

            if (!dbEmailList.equals(newEmail)) {
                userDao.updateUserEmail(dbEmail.getId(), newEmail);
            }
        }
    }

    public static class UserPermissions {
        public static final String ADMIN = "admin";
        public static final String USER = "user";
    }

    public String signupUser(StudentProfile student) throws Exception {
        validateUser.validateUserForSignup(student);
        return userDao.signupUser(student);
    }

    public String updateUserSecret(Person person) {
        return userDao.updateUserSecret(person);
    }
}
