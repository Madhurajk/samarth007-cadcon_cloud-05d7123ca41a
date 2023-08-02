package com.cadcon.cloud.dbimpl.mappers;

import com.cadcon.cloud.models.StudentProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class ProfileMapper implements RowMapper<StudentProfile> {

    private static final String STUDENT_PROFILE = "select " +
            "p.id as id, " +
            "sp.student_id as studentId, " +
            "p.first_name as first_name, " +
            "p.last_name as last_name" +
            " from Student_Profile sp join Person p on sp.person = p.id where p.id = ? and sp.is_active = ?";

    private JdbcTemplate template;

    public ProfileMapper(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public StudentProfile mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        long studentId = resultSet.getLong("studentId");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");

        StudentProfile profile = new StudentProfile();
        profile.setId(id);
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setStudentId(studentId);
        return profile;
    }

    public StudentProfile getStudentProfilebyStudentId(long studentId){
        return template.queryForObject(STUDENT_PROFILE, Arrays.asList(studentId,true).toArray(),this);
    }
}
