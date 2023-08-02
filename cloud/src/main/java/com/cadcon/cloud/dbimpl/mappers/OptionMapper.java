package com.cadcon.cloud.dbimpl.mappers;

import com.cadcon.cloud.models.Option;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class OptionMapper implements RowMapper<Option> {

    private JdbcTemplate template;

    private static final String OPTIONS = "select" +
            " o.option_serial as serial, " +
            " o.option_text as text, " +
            " o.option_media as media, " +
            " o.is_correct as is_correct, " +
            " o.marks_associated as marks, " +
            " o.id as id " +
            " from ques_option o join Question q on q.id = o.question where q.id = ?";

    public OptionMapper(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Option mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        int serial = resultSet.getInt("serial");
        String text = resultSet.getString("text");
        long media = resultSet.getLong("media");
        boolean isCorrect = resultSet.getBoolean("is_correct");
        int marks = resultSet.getInt("marks");
        return new Option(id,serial,text,media,isCorrect,marks);
    }

    public List<Option> getOptionsForQuestion(long questionId) {
        return template.query(OPTIONS, Arrays.asList(questionId).toArray(),this);
    }
}
