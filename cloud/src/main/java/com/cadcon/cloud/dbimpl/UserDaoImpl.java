package com.cadcon.cloud.dbimpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cadcon.cloud.controllers.user.CarousalData;
import com.cadcon.cloud.dao.AddressDao;
import com.cadcon.cloud.dao.EmailDao;
import com.cadcon.cloud.dao.PhoneDao;
import com.cadcon.cloud.dao.SchoolDao;
import com.cadcon.cloud.dao.UserDao;
import com.cadcon.cloud.dbimpl.insertions.UserDetailsInsertions;
import com.cadcon.cloud.dbimpl.mappers.CarousalReader;
import com.cadcon.cloud.dbimpl.mappers.MediaMapper;
import com.cadcon.cloud.dbimpl.mappers.PersonMapper;
import com.cadcon.cloud.dbimpl.mappers.PlanDetailsMapper;
import com.cadcon.cloud.dbimpl.mappers.ProfileMapper;
import com.cadcon.cloud.dbimpl.mappers.SponserMapper;
import com.cadcon.cloud.dbimpl.mappers.StudentIdRetriever;
import com.cadcon.cloud.models.Media;
import com.cadcon.cloud.models.Person;
import com.cadcon.cloud.models.StudentProfile;

@Repository
public class UserDaoImpl implements UserDao {

    private Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private SchoolDao schoolDao;
    @Autowired
    private PhoneDao phoneDao;
    @Autowired
    private EmailDao emailDao;
    @Autowired
    private UserDetailsInsertions userDetailsInsertions;

    @Override
    public List<Person> getUserByPhoneNumber(String phoneNumber) {
        List<Person> query = this.jdbcTemplate.query(PersonMapper.USER_BY_PHONE_NO, new Object[] { phoneNumber },
                new PersonMapper());
        logger.debug("users against {} {}", phoneNumber, query);
        return query;
    }

    @Override
    public void addLoginAttempt(long id, String loginMode, String deviceIdentifier) {
        // todo
    }

    @Override
    public Person getUserByUsername(String username) {
        return jdbcTemplate.queryForObject(PersonMapper.USER_BY_USER_NAME, new Object[] { username },
                new PersonMapper());
    }

    @Override
    public boolean doesUserExists(String userName, String userSecret) {
        return false;
    }

    @Override
    public StudentProfile getStudentProfile(long userId) {
        StudentProfile profile = new ProfileMapper(jdbcTemplate).getStudentProfilebyStudentId(userId);
        logger.debug("retrieved profile details");
        profile.setSchool(schoolDao.getSchoolOfUser(userId));
        logger.debug("school information retrieved");
        profile.setPhoneNumbers(phoneDao.getUserPhone(userId));
        logger.debug("phone numbers retrieved");
        profile.setPrimaryEmail(emailDao.getUserEmails(userId));
        logger.debug("email info retrieved");
        profile.setPrimaryAddress(addressDao.getAddressOfUser(userId));
        logger.debug("address details retrieved");
        profile.setPlanDetails(new PlanDetailsMapper(jdbcTemplate).getPlanOfProfile(profile.getStudentId()));
        logger.debug("plan details retrieved");
        return profile;
    }

    @Override
    public long getStudentIdByUserId(long userId) {
        return new StudentIdRetriever(jdbcTemplate).getStudentIdFromUserId(userId);
    }

    @Override
    public List<CarousalData> getCarousalForStudent(long studentId) {
        return new CarousalReader(jdbcTemplate, new MediaMapper(jdbcTemplate)).readCarousalFor(studentId);
    }

    @Override
    public Media getSponserInfo(long studentId) {
        List<Media> mediaList =
                new SponserMapper(jdbcTemplate, new MediaMapper(jdbcTemplate)).readSponserInfo(studentId);
        if (mediaList.size() > 0)
            return mediaList.get(0);
        else
            return null;
    }

    @Override
    public void updateUserFirstName(StudentProfile newStudent) {
        userDetailsInsertions.updateFName(newStudent.getId(), newStudent.getFirstName());
    }

    @Override
    public void updateUserLastName(StudentProfile newStudent) {
        userDetailsInsertions.updateLastName(newStudent.getId(), newStudent.getLastName());
    }

    @Override
    public void updateUserEmail(long emailID, String newEmail) {
        userDetailsInsertions.updateUserDetails(emailID, newEmail);
    }

    @Override
    public String signupUser(StudentProfile student) {
        return userDetailsInsertions.signupUser(student);
    }

    @Override
    public String updateUserSecret(Person person) {
        return userDetailsInsertions.updateUserSecret(person);
    }
}
