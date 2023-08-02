package com.cadcon.cloud.dbimpl.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cadcon.cloud.models.Question;

public class QuestionMapper implements RowMapper<Question> {

    private JdbcTemplate template;

    private Logger logger = LoggerFactory.getLogger(QuestionMapper.class);

    private static final String TEST_QUESTIONS = "select" + " q.id as id, " + " q.ques_text as text, "
            + " q.ques_media as media, " + " q.solution as solution"
            + " from Question q join test_questions t on q.id = t.test_question where t.test = ?";

    private static final String PRACTICE_TEST_QUESTIONS = "select" + " q.id as id, " + " q.ques_text as text, "
            + " q.ques_media as media, " + " q.solution as solution"
            + " from Question q join Practice_Questions t on q.id = t.question where t.practice_test = ?";

    public QuestionMapper(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Question mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String text = resultSet.getString("text");
        long media = resultSet.getLong("media");
        long solution = resultSet.getLong("solution");

        logger.debug("id -> {}, text -> {}, media -> {}, solution -> {}", id, text, media, solution);

        return new Question(id, text, media, solution);
    }

    public List<Question> getTestQuestions(long id) {
        return template.query(TEST_QUESTIONS, Arrays.asList(id).toArray(), this);
    }

    public List<Question> getPracticeTestQuestions(long testId) {
        return template.query(PRACTICE_TEST_QUESTIONS, Arrays.asList(testId).toArray(), this);
    }
}
