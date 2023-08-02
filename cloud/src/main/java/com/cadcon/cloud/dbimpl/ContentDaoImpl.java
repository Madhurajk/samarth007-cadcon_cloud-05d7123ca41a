package com.cadcon.cloud.dbimpl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cadcon.cloud.dao.ContentDao;
import com.cadcon.cloud.dbimpl.insertions.MockTestResultInsertions;
import com.cadcon.cloud.dbimpl.insertions.PracticeTestResultInsertions;
import com.cadcon.cloud.dbimpl.mappers.MediaMapper;
import com.cadcon.cloud.dbimpl.mappers.MediaProgressMapper;
import com.cadcon.cloud.dbimpl.mappers.NodeMapper;
import com.cadcon.cloud.dbimpl.mappers.OptionMapper;
import com.cadcon.cloud.dbimpl.mappers.PlanMapper;
import com.cadcon.cloud.dbimpl.mappers.QuestionMapper;
import com.cadcon.cloud.dbimpl.mappers.SolutionMapper;
import com.cadcon.cloud.dbimpl.mappers.TestMapper;
import com.cadcon.cloud.dbimpl.mappers.TestProgressMapper;
import com.cadcon.cloud.models.Media;
import com.cadcon.cloud.models.MediaProgress;
import com.cadcon.cloud.models.Node;
import com.cadcon.cloud.models.Option;
import com.cadcon.cloud.models.Plan;
import com.cadcon.cloud.models.Question;
import com.cadcon.cloud.models.Solution;
import com.cadcon.cloud.models.Test;
import com.cadcon.cloud.models.TestProgress;

@Repository
public class ContentDaoImpl implements ContentDao {

    private static final String HAS_CHILDREN =
            "select exists(select 1 from LearningNode where parent_node = ?) as hasChildren";

    private static final String STUDENT_TEST_PROGRESS =
            "select count (distinct stp.test) as attended_test from unit_test ut join student_test_progress stp on ut.id = stp.test where stp.student = ? and ut.for_node = ?;";

    @Autowired
    JdbcTemplate template;

    @Override
    public Node getNodeOfCapsule(long nodeId) {
        return new NodeMapper(template).getNode(nodeId);
    }

    @Override
    public Boolean doesNodeHasChildren(long nodeId) {
        return template.queryForObject(HAS_CHILDREN, Arrays.asList(nodeId).toArray(),
                (resultSet, i) -> resultSet.getBoolean("hasChildren"));
    }

    @Override
    public List<Node> getChildNodes(long nodeId) {
        return new NodeMapper(template).getChildren(nodeId);
    }

    @Override
    public List<Media> getNodeMedia(long id) {
        return new MediaMapper(template).getMediaForNode(id);
    }

    @Override
    public List<Test> getNodeTests(long nodeId) {
        return new TestMapper(template).getTestsForNode(nodeId);
    }

    @Override
    public Test getUnAttemptedtTest(long studentId, long nodeId) {
        return new TestMapper(template).getRandomUnAttemptedTest(studentId, nodeId);
    }

    @Override
    public Test getPracticeTestDetails(long testId) {
        return new TestMapper(template).getPracticeTestDetails(testId);
    }

    @Override
    public Test getMockTestDetails(long testId) {
        return new TestMapper(template).getMockTestDetails(testId);
    }

    @Override
    public MediaProgress getMediaProgressFor(long mediaId, long studentId) {
        return new MediaProgressMapper(template).getMediaProgress(mediaId, studentId);
    }

    @Override
    public Long getTestProgressFor(long nodeId, long studentId) {
        return template.queryForObject(STUDENT_TEST_PROGRESS, Arrays.asList(studentId, nodeId).toArray(),
                (resultSet, i) -> resultSet.getLong("attended_test"));
    }

    @Override
    public List<TestProgress> getTestProgressDetails(long testId, long studentId) {
        return new TestProgressMapper(template).getTestProgressDetails(testId, studentId);
    }

    @Override
    public List<Question> getTestQuestions(long id) {
        return new QuestionMapper(template).getTestQuestions(id);
    }

    @Override
    public Media getMediaDetails(long mediaId) {
        return new MediaMapper(template).getMediaDetails(mediaId);
    }

    @Override
    public Solution getSolutionDetails(long solution) {
        return new SolutionMapper(template).getSolutionDetails(solution);
    }

    @Override
    public List<Option> getOptionsForQuestion(long questionId) {
        return new OptionMapper(template).getOptionsForQuestion(questionId);
    }

    @Override
    public List<Test> getUnAttemptedPracticeTest(long studentId, long mediaId) {
        return new TestMapper(template).getRandomUnAtemptedPracticeTest(studentId, mediaId);
    }

    @Override
    public List<Question> getPracticeTestQuestions(long testId) {
        return new QuestionMapper(template).getPracticeTestQuestions(testId);
    }

    @Override
    public void saveStudentMockTestMarks(long studentId, long testId, int studentMarks, int maxMarks, long timeTaken) {
        new MockTestResultInsertions(studentId, testId, studentMarks, maxMarks, timeTaken, template).insert();
    }

    @Override
    public List<Plan> getAllPlans() {
        return new PlanMapper(template).getAllActivePlans();
    }

    @Override
    public void saveStudentPracticeTestMarks(long studentId, long testId, int studentMarks, int maxMarks,
            long timeTaken) {
        new PracticeTestResultInsertions(studentId, testId, studentMarks, maxMarks, timeTaken, template).insert();
    }

}
