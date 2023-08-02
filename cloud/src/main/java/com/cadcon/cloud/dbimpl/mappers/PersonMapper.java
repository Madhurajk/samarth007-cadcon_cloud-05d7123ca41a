package com.cadcon.cloud.dbimpl.mappers;

import com.cadcon.cloud.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {

    public static final String USER_BY_USER_NAME = "select " +
            "P.id, " +
            "P.first_name, " +
            "P.last_name, " +
            "a.user_name, " +
            "a.secret " +
            "from Person P join Authentication a on p.id = a.person where a.user_name = ?";

    public static final String USER_BY_PHONE_NO = "select" +
            " P.id," +
            " P.first_name, " +
            "P.last_name, " +
            "a.user_name, " +
            "a.secret " +
            "from Person P join Authentication a on p.id = a.person " +
            "join person_phone pp on p.id = pp.person " +
            "join Phone ph on pp.phone = ph.id " +
            "where ph.number = ? and isPrimary = true";

    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String fname = resultSet.getString("first_name");
        String sname = resultSet.getString("last_name");
        String username = resultSet.getString("user_name");
        String password = resultSet.getString("secret");
        return new Person(id,fname,sname,username,password);
    }
}
