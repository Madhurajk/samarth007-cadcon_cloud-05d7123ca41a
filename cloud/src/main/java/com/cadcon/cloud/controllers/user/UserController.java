package com.cadcon.cloud.controllers.user;

import com.cadcon.cloud.controllers.plan.CapsuleResponse;
import com.cadcon.cloud.dbimpl.insertions.UserDetailsInsertions;
import com.cadcon.cloud.models.Media;
import com.cadcon.cloud.models.Person;
import com.cadcon.cloud.models.StudentProfile;
import com.cadcon.cloud.services.ContentService;
import com.cadcon.cloud.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user/")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    UserService userService;

    @Autowired
    ContentService contentService;

    @GetMapping("{userId}")
    public StudentProfile getStudentDetails(@PathVariable("userId")long userId){
        return userService.getStudentProfileById(userId);
    }

    @GetMapping("{userId}/dashboard")
    public Dashboard getUserDashboard(@PathVariable("userId") long userId){
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long studentId = userService.studentIdByUserId(person.getId());
        List<CarousalData> carousalDetails = userService.getCarousalDetails(studentId);
        long userPlan = userService.getStudentProfileById(person.getId()).getPlanDetails().getPlanId();
        List<CapsuleResponse> subjects = contentService.getSubjectCapsuleOfPlan(studentId,userPlan);
        Media sponserMedia = userService.getSponserInfo(studentId);
        return new Dashboard(carousalDetails,sponserMedia,subjects);
    }

    @PostMapping("{userId}/profile/{profileId}")
    public StudentProfile updateUserDetails(@PathVariable("userId") long userId,
                                            @PathVariable("profileId")long profileId,
                                            @RequestBody StudentProfile refreshedStudent) throws IllegalAccessException {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (person.getId()!=userId){
            throw new IllegalAccessException("user can update only his information");
        }
        userService.updateStudentInformation(refreshedStudent);
        return userService.getStudentProfileById(userId);
    }
    
    @PostMapping("signup")
    public List<Person> signupUser(@RequestBody StudentProfile student) throws Exception{
    	logger.info("Signing up a student : " + student.toString());
    	String phoneNumber  = userService.signupUser(student);
    	return userService.getPersonByPhoneNumber(phoneNumber);
    }
    
}
