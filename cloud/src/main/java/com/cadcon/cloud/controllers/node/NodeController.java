package com.cadcon.cloud.controllers.node;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cadcon.cloud.controllers.plan.NodeDetailResponse;
import com.cadcon.cloud.models.Media;
import com.cadcon.cloud.models.Person;
import com.cadcon.cloud.services.ContentService;
import com.cadcon.cloud.services.UserService;

@RestController
@RequestMapping("api/v1/learn")
public class NodeController {

    @Autowired
    ContentService contentService;
    @Autowired
    UserService userService;

    @GetMapping("{nodeId}/children")
    public NodeDetailResponse getNodeChildren(@PathVariable("nodeId") long nodeId) {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long studentId = userService.studentIdByUserId(person.getId());
        return contentService.getChildrenNodes(studentId, nodeId);
    }

    @GetMapping("{nodeId}/media")
    public List<Media> getNodeMedia(@PathVariable("nodeId") long nodeId) {
        return contentService.getNodeMedia(nodeId);
    }

    @GetMapping("{nodeId}/test")
    public TestResponse getMockTest(@PathVariable("nodeId") long nodeId) {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long studentId = userService.studentIdByUserId(person.getId());
        return contentService.getMockTest(studentId, nodeId);
    }

    @GetMapping("test/{testId}")
    public TestResponse getMockTestById(@PathVariable("testId") long testId) {
        return contentService.getMockTestById(testId);
    }

    @GetMapping("{nodeId}/media/{mediaId}/practice")
    public TestResponse getPracticeTest(@PathVariable("nodeId") long nodeId, @PathVariable("mediaId") long mediaId)
            throws Exception {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long studentId = userService.studentIdByUserId(person.getId());
        return contentService.getPracticeTest(studentId, mediaId);
    }

    @PostMapping("test/{testId}")
    public void submitMockTest(@PathVariable("testId") long testId, @RequestBody TestSubmitRequest submissionRequests) {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long studentId = userService.studentIdByUserId(person.getId());
        contentService.submitMockTest(studentId, testId, submissionRequests);
    }

    @PostMapping("practice/{testId}")
    public void submitPracticeTest(@PathVariable("testId") long testId,
            @RequestBody TestSubmitRequest submissionRequests) {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long studentId = userService.studentIdByUserId(person.getId());
        contentService.submitPracticeTest(studentId, testId, submissionRequests);
    }

}
