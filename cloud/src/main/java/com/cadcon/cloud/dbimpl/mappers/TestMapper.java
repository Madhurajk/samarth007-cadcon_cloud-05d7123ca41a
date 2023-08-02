package com.cadcon.cloud.dbimpl.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cadcon.cloud.models.Test;

public class TestMapper implements RowMapper<Test> {

    private static final String NODE_TEST = "select id, name, for_node from unit_test where for_node = ?";
    private static final String UNATTENDED_TEST = "select" + " id," + " name," + " for_node" + " from unit_test ut"
            + " where ut.for_node = ?" + " and ut.id not in " + "(select" + " test" + " from student_test_progress "
            + "where student = ?" + ")" + " limit 1";

    private static final String UNATTEMPTED_PRACTICE_TEST =
            "select" + " pt.id as id, " + "pt.name as name, " + "pt.for_media as for_node "
                    + "from PracticeTest pt join node_media nm on pt.for_media = nm.id where nm.media = ?";

    private static final String PRACTICE_TEST_DETAILS = "select" + " pt.id as id, " + "pt.name as name, "
            + "pt.for_media as for_node " + "from PracticeTest pt where pt.id = ?";

    private static final String MOCK_TEST_DETAILS = "select id, name, for_node from unit_test where id = ?";

    private JdbcTemplate template;

    public TestMapper(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Test mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        long node = resultSet.getLong("for_node");
        return new Test(id, name, node);
    }

    public List<Test> getTestsForNode(long nodeId) {
        return template.query(NODE_TEST, Collections.singletonList(nodeId).toArray(), this);
    }

    public Test getRandomUnAttemptedTest(long studentId, long nodeId) {
        return template.queryForObject(UNATTENDED_TEST, Arrays.asList(nodeId, studentId).toArray(), this);
    }

    public List<Test> getRandomUnAtemptedPracticeTest(long studentId, long mediaId) {
        return template.query(UNATTEMPTED_PRACTICE_TEST, Arrays.asList(mediaId).toArray(), this);
    }

    public Test getPracticeTestDetails(long testId) {
        return template.queryForObject(PRACTICE_TEST_DETAILS, Arrays.asList(testId).toArray(), this);
    }

    public Test getMockTestDetails(long testId) {
        return template.queryForObject(MOCK_TEST_DETAILS, Arrays.asList(testId).toArray(), this);
    }

}
