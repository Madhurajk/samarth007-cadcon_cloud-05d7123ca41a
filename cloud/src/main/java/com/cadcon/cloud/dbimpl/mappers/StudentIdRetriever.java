package com.cadcon.cloud.dbimpl.mappers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class StudentIdRetriever implements RowMapper<Long> {

    private static final String ACTIVE_STUDENT_OF_PERSON = "select sp.student_id as id from Person p join Student_Profile sp on p.id = sp.person where p.id = ? and is_active = true";

    private JdbcTemplate template;

    public StudentIdRetriever(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Long mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getLong("id");
    }

    public long getStudentIdFromUserId(long userId){
        return template.queryForObject(ACTIVE_STUDENT_OF_PERSON, Arrays.asList(userId).toArray(),this);
    }
}
