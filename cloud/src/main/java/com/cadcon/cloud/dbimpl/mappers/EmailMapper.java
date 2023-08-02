package com.cadcon.cloud.dbimpl.mappers;

import com.cadcon.cloud.models.Email;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class EmailMapper implements RowMapper<Email> {

    private static final String PERSON_EMAIL = "select " +
            "e.id as id," +
            " e.email_string as email," +
            " e.isPrimary as isPrimary" +
            " from person_email pe join email e on pe.email = e.id where pe.person = ?";

    private static final String SCHOOL_EMAIL = "select " +
            "e.id as id," +
            " e.email_string as email," +
            " e.isPrimary as isPrimary" +
            " from school_email se join email e on se.email = e.id where se.school = ?";

    private JdbcTemplate template;

    public EmailMapper(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Email mapRow(ResultSet resultSet, int i) throws SQLException {
        String email = resultSet.getString("email");
        long id = resultSet.getLong("id");
        boolean isPrimary = resultSet.getBoolean("isPrimary");
        return new Email(email,id,isPrimary);
    }

    public List<Email> getUserEmail(long userId){
        return template.query(PERSON_EMAIL, Arrays.asList(userId).toArray(),this);
    }

    public List<Email> getSchoolEmail(long schoolId) {
        return template.query(SCHOOL_EMAIL, Arrays.asList(schoolId).toArray(), this);
    }
}
