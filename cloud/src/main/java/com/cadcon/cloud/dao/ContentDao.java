package com.cadcon.cloud.dao;

import java.util.List;

import com.cadcon.cloud.models.Media;
import com.cadcon.cloud.models.MediaProgress;
import com.cadcon.cloud.models.Node;
import com.cadcon.cloud.models.Option;
import com.cadcon.cloud.models.Plan;
import com.cadcon.cloud.models.Question;
import com.cadcon.cloud.models.Solution;
import com.cadcon.cloud.models.Test;
import com.cadcon.cloud.models.TestProgress;

public interface ContentDao {

    Node getNodeOfCapsule(long capsuleId);

    Boolean doesNodeHasChildren(long nodeId);

    List<Node> getChildNodes(long nodeId);

    List<Media> getNodeMedia(long id);

    List<Test> getNodeTests(long nodeId);

    Test getUnAttemptedtTest(long studentId, long nodeId);

    Test getPracticeTestDetails(long testId);

    Test getMockTestDetails(long testId);

    MediaProgress getMediaProgressFor(long mediaId, long studentId);

    Long getTestProgressFor(long nodeId, long studentId);

    List<TestProgress> getTestProgressDetails(long testId, long studentId);

    List<Question> getTestQuestions(long id);

    Media getMediaDetails(long mediaId);

    Solution getSolutionDetails(long solution);

    List<Option> getOptionsForQuestion(long questionId);

    List<Test> getUnAttemptedPracticeTest(long studentId, long mediaId);

    List<Question> getPracticeTestQuestions(long testId);

    void saveStudentMockTestMarks(long studentId, long testId, int studentMarks, int maxMarks, long timeTaken);

    List<Plan> getAllPlans();

    void saveStudentPracticeTestMarks(long studentId, long testId, int studentMarks, int maxMarks, long timeTaken);
}
