package com.cadcon.cloud.dbimpl;

import com.cadcon.cloud.dao.AddressDao;
import com.cadcon.cloud.dao.EmailDao;
import com.cadcon.cloud.dao.PhoneDao;
import com.cadcon.cloud.dao.SchoolDao;
import com.cadcon.cloud.dbimpl.mappers.SchoolMapper;
import com.cadcon.cloud.models.School;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SchoolDaoImpl implements SchoolDao {

    private static final String INSERT_SCHOOL = "insert into School(Name, code, Address) values (?, ?, ?)";
    private static final String INSERT_SCHOOL_PHONE = "insert into school_phone(phone, school) values(?, ?)";

    @Autowired private JdbcTemplate template;
    @Autowired private AddressDao addressDao;
    @Autowired private PhoneDao phoneDao;
    @Autowired private EmailDao emailDao;

    Logger logger = LoggerFactory.getLogger(SchoolDaoImpl.class);

    public long insertSchool(String schoolName, String code, long schoolAddress){
        return template.update(INSERT_SCHOOL, schoolName, code, schoolAddress);
    }

    public long linkSchoolPhone(long schoolRow, long phoneValue) {
        return template.update(INSERT_SCHOOL_PHONE, phoneValue, schoolRow);
    }

    @Override
    public School getSchoolOfUser(long userId) {
        School school = new SchoolMapper(template).getSchoolOfUser(userId);
        logger.debug("retrieved school info");
        school.setAddress(addressDao.getAddressOfSchool(school.getId()));
        logger.debug("retrieved address info");
        school.setSchoolPhone(phoneDao.getSchoolPhone(school.getId()));
        logger.debug("retrieved phone info");
        school.setSchoolEmails(emailDao.getSchoolEmails(school.getId()));
        logger.debug("retrieved email details");
        return school;
    }
}
