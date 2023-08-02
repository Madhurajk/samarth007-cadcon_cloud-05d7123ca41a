package com.cadcon.cloud.dbimpl.mappers;

import com.cadcon.cloud.models.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class SchoolMapper implements RowMapper<School> {

    private JdbcTemplate template;

    public SchoolMapper(JdbcTemplate template) {
        this.template = template;
    }

    private static final String USER_SCHOOL = "select " +
            "s.id as id," +
            "s.Name as name," +
            "s.code as code" +
            " from Student_Profile sp join School s on sp.school = s.id where sp.person = ? and is_active = true";

    @Override
    public School mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String code = resultSet.getString("code");
        School school = new School();
        school.setId(id);
        school.setCode(name);
        school.setCode(code);
        return school;
    }

    public School getSchoolOfUser(long userId){
        return template.queryForObject(USER_SCHOOL, Arrays.asList(userId).toArray(),this);
    }
}
