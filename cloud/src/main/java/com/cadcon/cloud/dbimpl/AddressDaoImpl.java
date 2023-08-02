package com.cadcon.cloud.dbimpl;

import com.cadcon.cloud.dao.AddressDao;
import com.cadcon.cloud.dbimpl.mappers.AddressMapper;
import com.cadcon.cloud.models.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDaoImpl implements AddressDao {

    private static final String INSERT_ADDRESS = "insert into Address(pincode, state, district, taluk, post, address_line_1," +
            "address_line_2) values (?, ?, ?, ?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate template;

    public long insertAddress(String adLine1, String adLine2, String post, String taluk, String district, String state, String pincode){
        return template.update(INSERT_ADDRESS, pincode, state, district, taluk, post, adLine1, adLine2);
    }

    public Address getAddressOfUser(long userId){
        return new AddressMapper(template).fetchUserAddress(userId);
    }

    @Override
    public Address getAddressOfSchool(long id) {
        return new AddressMapper(template).fetchSchoolAddress(id);
    }
}
