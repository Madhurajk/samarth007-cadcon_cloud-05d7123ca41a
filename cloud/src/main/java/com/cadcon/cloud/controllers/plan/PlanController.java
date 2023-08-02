package com.cadcon.cloud.controllers.plan;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cadcon.cloud.models.Person;
import com.cadcon.cloud.models.Plan;
import com.cadcon.cloud.services.ContentService;
import com.cadcon.cloud.services.UserService;

@RestController
@RequestMapping("api/v1/plan/")
public class PlanController {

    @Autowired
    private UserService userService;
    @Autowired
    private ContentService contentService;

    private Logger logger = LoggerFactory.getLogger(PlanController.class);

    @GetMapping()
    public List<Plan> getPlans() {
        List<Plan> planList = contentService.getAllPlans();
        return planList;
    }

    @GetMapping("{planId}/subjects")
    public List<CapsuleResponse> getSubjectsForUser(@PathVariable("planId") long planId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.debug("retrieved principle -> {}", principal);
        Person person = (Person) principal;
        long studentId = userService.studentIdByUserId(person.getId());
        logger.debug("student id -> {}", studentId);
        List<CapsuleResponse> subjectCapsuleOfPlan = contentService.getSubjectCapsuleOfPlan(studentId, planId);
        logger.debug("retrieved subjects -> {}", subjectCapsuleOfPlan);
        return subjectCapsuleOfPlan;
    }
}
