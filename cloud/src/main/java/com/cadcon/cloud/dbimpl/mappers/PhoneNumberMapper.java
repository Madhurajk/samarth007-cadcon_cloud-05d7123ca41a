package com.cadcon.cloud.dbimpl.mappers;

import com.cadcon.cloud.models.Phone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class PhoneNumberMapper implements RowMapper<Phone> {

    private JdbcTemplate template;

    public PhoneNumberMapper(JdbcTemplate template) {
        this.template = template;
    }

    private static final String PERSON_PHONE = "select " +
            "p.number as number, " +
            "p.code as code, " +
            "p.isPrimary as isPrimary, " +
            "p.id as id " +
            "from person_phone pp join Phone p on pp.phone = p.id where pp.person = ?";

    private static final String SCHOOL_PHONE = "select " +
            "p.number as number, " +
            "p.code as code, " +
            "p.isPrimary as isPrimary, " +
            "p.id as id" +
            " from school_phone sp join Phone p on sp.phone = p.id where sp.school = ?";

    @Override
    public Phone mapRow(ResultSet resultSet, int i) throws SQLException {
        String number = resultSet.getString("number");
        String code = resultSet.getString("code");
        boolean isPrimary = resultSet.getBoolean("isPrimary");
        long id = resultSet.getLong("id");
        return new Phone(number,code,isPrimary,id);
    }

    public List<Phone> getUserPhone(long userId){
        return template.query(PERSON_PHONE, Arrays.asList(userId).toArray(),this);
    }

    public List<Phone> getSchoolPhone(long schoolId) {
        return template.query(SCHOOL_PHONE, Arrays.asList(schoolId).toArray(), this);
    }
}
