package com.cadcon.cloud.dbimpl.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cadcon.cloud.models.TestProgress;

public class TestProgressMapper implements RowMapper<TestProgress> {

    private JdbcTemplate template;

    public TestProgressMapper(JdbcTemplate template) {
        this.template = template;
    }

    private static final String STUDENT_TEST_PROGRESS_DETAILS =
            "select ut.id as id, ut.name as testName, stp.marks as obtained, "
                    + "stp.max as total, stp.created_at as submitTime"
                    + " from student_test_progress stp join unit_test ut on stp.test = ut.id where ut.id = ? and stp.student = ? order by stp.created_at desc limit 1;";

    private Logger logger = LoggerFactory.getLogger(TestProgressMapper.class);

    @Override
    public TestProgress mapRow(ResultSet resultSet, int i) throws SQLException {
        int testId = resultSet.getInt("id");
        String testName = resultSet.getString("testName");
        int totalMarks = resultSet.getInt("total");
        int obtained = resultSet.getInt("obtained");
        long submitTime = resultSet.getLong("submitTime");
        return new TestProgress(testId, testName, obtained, totalMarks, submitTime);
    }

    public List<TestProgress> getTestProgressDetails(long testId, long studentId) {
        try {
            return template.query(STUDENT_TEST_PROGRESS_DETAILS, Arrays.asList(testId, studentId).toArray(), this);
        } catch (DataAccessException e) {
            logger.error("no test progress available for {} and {}", testId, studentId, e);
            return Collections.emptyList();
        }
    }

}
