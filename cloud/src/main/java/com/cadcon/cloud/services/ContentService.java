package com.cadcon.cloud.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadcon.cloud.controllers.node.TestResponse;
import com.cadcon.cloud.controllers.node.TestSubmitRequest;
import com.cadcon.cloud.controllers.plan.CapsuleResponse;
import com.cadcon.cloud.controllers.plan.NodeDetailResponse;
import com.cadcon.cloud.controllers.plan.NodeResponse;
import com.cadcon.cloud.dao.ContentDao;
import com.cadcon.cloud.dao.PlanDao;
import com.cadcon.cloud.models.Media;
import com.cadcon.cloud.models.MediaProgress;
import com.cadcon.cloud.models.Node;
import com.cadcon.cloud.models.Option;
import com.cadcon.cloud.models.OptionDetails;
import com.cadcon.cloud.models.Plan;
import com.cadcon.cloud.models.Question;
import com.cadcon.cloud.models.QuestionDetail;
import com.cadcon.cloud.models.Solution;
import com.cadcon.cloud.models.SolutionDetail;
import com.cadcon.cloud.models.SubjectCapsule;
import com.cadcon.cloud.models.Test;
import com.cadcon.cloud.models.TestProgress;

@Service
public class ContentService {

    @Autowired
    ContentDao contentDao;
    @Autowired
    PlanDao planDao;

    private Logger logger = LoggerFactory.getLogger(ContentService.class);

    public List<CapsuleResponse> getSubjectCapsuleOfPlan(long studentId, long planId) {

        logger.debug("retrieving subject capsules of student {} with plan {}", studentId, planId);

        List<SubjectCapsule> subjectList = planDao.getSubjectCapsuleOfPlan(planId);

        logger.debug("retrieve subject list {}", subjectList);

        List<CapsuleResponse> capsuleResponseList = new ArrayList<>();

        for (SubjectCapsule capsule : subjectList) {
            CapsuleResponse response = new CapsuleResponse();
            response.setId(capsule.getCapsuleId());
            response.setName(capsule.getName());
            response.setSubject(capsule.getSubjectId());

            Node node = contentDao.getNodeOfCapsule(capsule.getNodeRoot());

            logger.debug("node of capsule {} is {}", capsule.getName(), node.getName());
            NodeResponse nodeResponse = getNodeResponseFromNode(studentId, node);
            response.setNode(nodeResponse);
            capsuleResponseList.add(response);
        }
        return capsuleResponseList;
    }

    private NodeResponse getNodeResponseFromNode(long studentId, Node node) {

        NodeResponse nodeResponse = new NodeResponse(node);

        nodeResponse.setHasChildren(contentDao.doesNodeHasChildren(node.getId()));
        logger.debug("Retrieving node response for {} on {}. Does not has child -> {} ", studentId, node.getId(),
                nodeResponse.isHasChildren());

        nodeResponse.setMediaProgress(getLearnProgress(node.getId(), studentId, nodeResponse.isHasChildren()));
        logger.debug("media progress {}", nodeResponse.getMediaProgress());

        logger.debug("getting test progress for node {} for student {}", node.getId(), studentId);

        nodeResponse.setTestProgress(getTestProgress(node.getId(), studentId));

        return nodeResponse;
    }

    private double getTestProgress(long id, long studentId) {
        List<Test> nodeTests = contentDao.getNodeTests(id);
        if (nodeTests == null || nodeTests.size() == 0) {
            return 0;
        }

        long attendedTest = contentDao.getTestProgressFor(id, studentId);
        logger.info("Total tests attended by student : {} - {}", studentId, attendedTest);

        return (double) attendedTest / nodeTests.size();
    }

    private List<TestProgress> getTestDetailsForNode(long id, long studentId) {
        List<TestProgress> testProgressList = new ArrayList<>();

        List<Test> nodeTests = contentDao.getNodeTests(id);
        logger.info("Test taken in the node {} -> {}", id, nodeTests.size());

        if (nodeTests == null || nodeTests.size() == 0) {
            return testProgressList;
        }

        for (Test test : nodeTests) {
            List<TestProgress> progressList = contentDao.getTestProgressDetails(test.getId(), studentId);
            if (progressList.size() == 0) {
                // If no tests has been taken then create a dummy entry and send it.
                TestProgress progress = new TestProgress();
                Test testNode = contentDao.getMockTestDetails(test.getId());
                progress.setTestId((int) testNode.getId());
                progress.setTestName(test.getName());
                testProgressList.add(progress);
            } else {
                testProgressList.addAll(progressList);
            }
        }
        return testProgressList;
    }

    private double getLearnProgress(long id, long studentId, boolean hasChildren) {
        if (hasChildren) {
            List<Node> childNodes = contentDao.getChildNodes(id);
            double learnSum = 0;
            for (Node child : childNodes) {
                learnSum += getLearnProgress(child.getId(), studentId, contentDao.doesNodeHasChildren(child.getId()));
            }
            return learnSum / childNodes.size();
        } else {
            List<Media> nodeMedia = contentDao.getNodeMedia(id);
            double totalProgress = 0;

            if (nodeMedia == null || nodeMedia.size() == 0) {
                return 0;
            }

            for (Media media : nodeMedia) {
                MediaProgress progress = contentDao.getMediaProgressFor(media.getId(), studentId);
                if (progress != null) {
                    int totalDuration = (int) progress.getVideoDuration();
                    int watchedDuration = (int) progress.getWatchedDuration();
                    totalProgress += (watchedDuration / totalDuration) * 100;
                }
            }
            return totalProgress / nodeMedia.size();
        }
    }

    public NodeDetailResponse getChildrenNodes(long studentId, long nodeId) {
        NodeDetailResponse nodeDetailResponse = new NodeDetailResponse();
        List<Node> childNodes = contentDao.getChildNodes(nodeId);
        List<NodeResponse> nodeResponseList = new ArrayList<>();
        logger.info("Fetching child nodes for student {} on node {}", studentId, nodeId);
        for (Node node : childNodes) {
            NodeResponse response = getNodeResponseFromNode(studentId, node);
            nodeResponseList.add(response);
        }
        List<TestProgress> testResponseNode = getTestDetailsForNode(nodeId, studentId);
        nodeDetailResponse.setChildNodeList(nodeResponseList);
        nodeDetailResponse.setTestResponse(testResponseNode);
        return nodeDetailResponse;
    }

    public List<Media> getNodeMedia(long nodeId) {
        return contentDao.getNodeMedia(nodeId);
    }

    public TestResponse getMockTest(long studentId, long nodeId) {
        Test test = contentDao.getUnAttemptedtTest(studentId, nodeId);
        TestResponse testResponse = prepareTestResponse(test, getMockTestQuestions(test.getId()));
        return testResponse;
    }

    private TestResponse prepareTestResponse(Test test, List<Question> questionsForTest) {
        TestResponse testResponse = new TestResponse();
        testResponse.setId(test.getId());
        testResponse.setName(test.getName());
        List<QuestionDetail> questionDetails = new ArrayList<>();
        for (Question question : questionsForTest) {
            Media media = contentDao.getMediaDetails(question.getMedia());
            SolutionDetail solutionDetail = getSolutionDetailsFromSolution(question.getSolution());
            List<OptionDetails> options = getQuestionOptions(question.getId());
            QuestionDetail questionDetail = new QuestionDetail();
            questionDetail.setId(question.getId());
            questionDetail.setMedia(media);
            questionDetail.setText(question.getText());
            questionDetail.setSolutionDetail(solutionDetail);
            questionDetail.setOptions(options);
            questionDetails.add(questionDetail);
        }
        testResponse.setQuestionDetailList(questionDetails);
        return testResponse;
    }

    public List<Question> getMockTestQuestions(long testId) {
        return contentDao.getTestQuestions(testId);
    }

    private List<OptionDetails> getQuestionOptions(long questionId) {

        List<Option> options = contentDao.getOptionsForQuestion(questionId);
        List<OptionDetails> optionDetailList = new ArrayList<>();

        for (Option option : options) {
            OptionDetails optionDetails = new OptionDetails();
            optionDetails.setId(option.getId());
            optionDetails.setCorrect(option.isCorrect());
            optionDetails.setMarksAssociated(option.getMarks());
            optionDetails.setSerial(option.getSerial());
            optionDetails.setText(option.getText());

            Media media = contentDao.getMediaDetails(option.getMedia());
            optionDetails.setMedia(media);
            optionDetailList.add(optionDetails);
        }

        return optionDetailList;
    }

    private SolutionDetail getSolutionDetailsFromSolution(long solId) {
        Solution solution = contentDao.getSolutionDetails(solId);
        Media solutionMedia = contentDao.getMediaDetails(solution.getMedia());
        SolutionDetail solutionDetail = new SolutionDetail();
        solutionDetail.setMedia(solutionMedia);
        solutionDetail.setId(solution.getId());
        solutionDetail.setText(solution.getText());
        return solutionDetail;
    }

    public TestResponse getPracticeTest(long studentId, long mediaId) throws Exception {
        final List<Test> testList = contentDao.getUnAttemptedPracticeTest(studentId, mediaId);
        if (testList.size() == 0) {
            logger.error("No tests available for studetnt - {} , media - {}", studentId, mediaId);
            throw new Exception("No test available");
        }
        Test test = testList.get(((int) Math.ceil(Math.random() * 100)) % testList.size());
        TestResponse response = prepareTestResponse(test, getPracticeTestQuestionList(test.getId()));
        return response;
    }

    private List<Question> getPracticeTestQuestionList(long testId) {
        return contentDao.getPracticeTestQuestions(testId);
    }

    public void submitMockTest(long studentId, long testId, TestSubmitRequest submitRequest) {
        Test mockTestDetails = contentDao.getMockTestDetails(testId);
        TestResponse testResponse = prepareTestResponse(mockTestDetails, getMockTestQuestions(testId));
        Optional<Integer> maxMarksOptional =
                testResponse.questionDetailList.stream().flatMap(questionDetail -> questionDetail.getOptions().stream())
                        .map(OptionDetails::getMarksAssociated).reduce(Integer::sum);
        int maxMarks = 0;
        if (maxMarksOptional.isPresent()) {
            maxMarks = maxMarksOptional.get();
        }
        QuestionMarks questionMarks = new QuestionMarks(testResponse);
        Optional<Integer> studentmarksOptional = submitRequest.getSubmissionRequestList().stream()
                .map(testSubmissionRequest -> questionMarks.getMarks(testSubmissionRequest.getQuestionId(),
                        (long) testSubmissionRequest.getOptionSerial()))
                .reduce(Integer::sum);
        int studentMarks = 0;
        if (studentmarksOptional.isPresent()) {
            studentMarks = studentmarksOptional.get();
        }
        logger.info("Submitting the unit test {} by student {}. Secured marks : {} Max marks : {}", testId, studentId,
                studentMarks, maxMarks);
        contentDao.saveStudentMockTestMarks(studentId, testId, studentMarks, maxMarks, submitRequest.getTimeTaken());
    }

    public void submitPracticeTest(long studentId, long testId, TestSubmitRequest submitRequest) {
        Test practiceTestDetails = contentDao.getPracticeTestDetails(testId);
        TestResponse testResponse = prepareTestResponse(practiceTestDetails, getPracticeTestQuestionList(testId));
        Optional<Integer> maxMarksOptional =
                testResponse.questionDetailList.stream().flatMap(questionDetail -> questionDetail.getOptions().stream())
                        .map(OptionDetails::getMarksAssociated).reduce(Integer::sum);
        int maxMarks = 0;
        if (maxMarksOptional.isPresent()) {
            maxMarks = maxMarksOptional.get();
        }
        QuestionMarks questionMarks = new QuestionMarks(testResponse);
        Optional<Integer> studentmarksOptional = submitRequest.getSubmissionRequestList().stream()
                .map(testSubmissionRequest -> questionMarks.getMarks(testSubmissionRequest.getQuestionId(),
                        (long) testSubmissionRequest.getOptionSerial()))
                .reduce(Integer::sum);
        int studentMarks = 0;
        if (studentmarksOptional.isPresent()) {
            studentMarks = studentmarksOptional.get();
        }
        logger.info("Submitting the practice test {} by student {}. Secured marks : {} Max marks : {}", testId,
                studentId, studentMarks, maxMarks);
        contentDao.saveStudentPracticeTestMarks(studentId, testId, studentMarks, maxMarks,
                submitRequest.getTimeTaken());
    }

    private class QuestionMarks {

        Map<Long, Long> rightOptions = new HashMap<>();
        Map<Long, Integer> marks = new HashMap<>();

        public QuestionMarks(TestResponse testResponse) {
            for (QuestionDetail detail : testResponse.questionDetailList) {
                for (OptionDetails option : detail.getOptions()) {
                    if (option.isCorrect()) {
                        rightOptions.put(detail.getId(), option.getId());
                        marks.put(detail.getId(), option.getMarksAssociated());
                        break;
                    }
                }
            }
        }

        public int getMarks(Long questId, Long optionId) {
            if (rightOptions.get(questId) == optionId) {
                return marks.get(questId);
            } else {
                return 0;
            }
        }
    }

    public TestResponse getMockTestById(long testId) {
        Test test = contentDao.getMockTestDetails(testId);
        TestResponse testResponse = prepareTestResponse(test, getMockTestQuestions(test.getId()));
        return testResponse;
    }

    public List<Plan> getAllPlans() {
        return contentDao.getAllPlans();
    }
}
