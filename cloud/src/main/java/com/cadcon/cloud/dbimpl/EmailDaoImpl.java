package com.cadcon.cloud.dbimpl;

import com.cadcon.cloud.dao.EmailDao;
import com.cadcon.cloud.dbimpl.mappers.EmailMapper;
import com.cadcon.cloud.models.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmailDaoImpl implements EmailDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Email> getUserEmails(long userId) {
        return new EmailMapper(template).getUserEmail(userId);
    }

    @Override
    public List<Email> getSchoolEmails(long schoolId) {
        return new EmailMapper(template).getSchoolEmail(schoolId);
    }
}
