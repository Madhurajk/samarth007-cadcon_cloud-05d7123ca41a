package com.cadcon.cloud.dbimpl;

import com.cadcon.cloud.dao.PhoneDao;
import com.cadcon.cloud.dbimpl.mappers.PhoneNumberMapper;
import com.cadcon.cloud.models.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PhoneDaoImpl implements PhoneDao {

    private static final String INSERT_PHONE_NUMBER = "insert into Phone(number, code, isPrimary) values (?, ?, ?)";

    @Autowired
    private JdbcTemplate template;

    public long insertPhoneNumber(String code, String number, boolean isPrimary){
        return template.update(INSERT_PHONE_NUMBER, number, code, isPrimary);
    }

    @Override
    public List<Phone> getUserPhone(long userId) {
        return new PhoneNumberMapper(template).getUserPhone(userId);
    }

    @Override
    public List<Phone> getSchoolPhone(long id) {
        return new PhoneNumberMapper(template).getSchoolPhone(id);
    }
}
