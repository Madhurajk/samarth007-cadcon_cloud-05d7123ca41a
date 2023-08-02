package com.cadcon.cloud.dbimpl.mappers;

import com.cadcon.cloud.models.Address;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class AddressMapper implements RowMapper<Address> {

    private JdbcTemplate template;

    public AddressMapper(JdbcTemplate template) {
        this.template = template;
    }

    private static final String USER_ADDRESS = "select A.id as id," +
            " A.pincode as pincode," +
            " A.state as state," +
            " A.district as district," +
            " A.taluk as taluk," +
            " A.post as post," +
            " A.address_line_1 as adr1," +
            " A.address_line_2 as adr2" +
            " from Person P join Address A on P.address = A.id where P.id = ?";

    private static final String SCHOOL_ADDRESS = "select" +
            " A.id as id," +
            " A.pincode as pincode," +
            " A.state as state," +
            " A.district as district," +
            " A.taluk as taluk," +
            " A.post as post," +
            " A.address_line_1 as adr1," +
            " A.address_line_2 as adr2" +
            " from School s join Address A on s.Address = A.id where s.id = ?";


    @Override
    public Address mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String pincode = resultSet.getString("pincode");
        String state = resultSet.getString("state");
        String district = resultSet.getString("district");
        String taluk = resultSet.getString("taluk");
        String post = resultSet.getString("post");
        String adr1 = resultSet.getString("adr1");
        String adr2 = resultSet.getString("adr2");
        return new Address(id,pincode,state,district,taluk,post,adr1,adr2);
    }

    public Address fetchUserAddress(long userId){
        return template.queryForObject(USER_ADDRESS, Arrays.asList(userId).toArray(),this);
    }

    public Address fetchSchoolAddress(long schoolId){
        return template.queryForObject(SCHOOL_ADDRESS,Arrays.asList(schoolId).toArray(),this);
    }
}
