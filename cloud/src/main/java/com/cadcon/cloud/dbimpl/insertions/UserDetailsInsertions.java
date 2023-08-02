package com.cadcon.cloud.dbimpl.insertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cadcon.cloud.models.Address;
import com.cadcon.cloud.models.Person;
import com.cadcon.cloud.models.Phone;
import com.cadcon.cloud.models.School;
import com.cadcon.cloud.models.StudentProfile;
import com.cadcon.cloud.util.CustomProperties;

@Repository
public class UserDetailsInsertions {

    private Logger logger = LoggerFactory.getLogger(UserDetailsInsertions.class);

    public static final String UPDATE_FNAME = "update Person set first_name = ? where id = ?";
    public static final String UPDATE_LNAME = "update Person set last_name = ? where id = ?";

    public static final String UPDATE_EMAIL = "update email set email_string = ? where id = ?";

    public static final String GET_SCHOOL_BY_NAME = "select id, name, code from School where name = ?";

    /* Insert Statements for sign up user starts */

    // Insert Address
    public static final String INSERT_ADDRESS =
            "insert into Address(pincode, state, district, taluk, post, address_line_1, address_line_2) values(?,?,?,?,?,?,?) returning id";

    // Insert Person
    public static final String INSERT_PERSON =
            "insert into Person(address, first_name, last_name) values (?,?,?) returning id";

    // Insert Phone and Person_Phone
    public static final String INSERT_PHONE = "insert into Phone(number, code, isPrimary) values(?,?,?) returning id";
    public static final String INSERT_PERSON_PHONE = "insert into Person_Phone(person,phone) values(?,?)";

    // Insert Email and Person_Email
    public static final String INSERT_EMAIL = "insert into Email(email_string,isprimary) values (?,?) returning id";
    public static final String INSERT_PERSON_EMAIL = "insert into Person_Email(email, person) values(?,?)";

    // Insert Student_School_Info
    public static final String INSERT_STUDENT_SCHOOL_INFO =
            "insert into Student_School_Info(student_id, school_name, year_of_passing) values(?,?,?) returning id";

    // Insert Student Profile
    public static final String INSERT_STUDENT_PROFILE =
            "insert into Student_Profile(person, school,is_active, language) values (?,?,?,?) returning student_id";

    // Insert Subscribed_Plan
    public static final String INSERT_SUBSCRIBED_PLAN =
            "insert into Subscribed_Plan(plan,student_profile,bought_on,first_day,last_day) values (?,?,?,?,?)";

    // Insert Authentication
    public static final String INSERT_AUTHENTICATION =
            "insert into Authentication(person,secret,user_name) values(?,?,?)";

    private static final String UPDATE_USER_SECRET = "update authentication set secret = ? where person = ?";

    /* Insert Statements for sign up user ends */

    private JdbcTemplate template;

    /*
     * customProperties has all the properties required for the signing up of a user
     */
    @Autowired
    CustomProperties customProperties;

    public UserDetailsInsertions(@Autowired JdbcTemplate template) {
        this.template = template;
    }

    public void updateFName(long userId, String firstName) {
        template.update(UPDATE_FNAME, Arrays.asList(firstName, userId).toArray());
    }

    public void updateLastName(long userId, String lastName) {
        template.update(UPDATE_LNAME, Arrays.asList(lastName, userId).toArray());
    }

    public void updateUserDetails(long emailId, String email) {
        template.update(UPDATE_EMAIL, Arrays.asList(email, emailId).toArray());
    }

    /**
     * Signup a user needs to insert data into a multiple table in a single transaction. So setting auto commit false
     * for the connection and using the same connection object for inserting data to all the tables. Committing the
     * transactions in the end. If any issues persists then rolling back the entire transaction. No stale entry remains
     * in th table.
     * 
     * @param student
     * @return
     */
    public String signupUser(StudentProfile student) {

        logger.info("Mandatory Info for sign-up from properties file : " + customProperties.toString());
        int personId = -1;
        Connection con = null;
        try {
            con = template.getDataSource().getConnection();

            /*
             * Set auto commit false for the entire connection object Commit all the insert at once in the end. Do
             * rollback in case of any issues in the db transaction
             */
            con.setAutoCommit(false);

            // Its a map of PlanId to SchoolId
            Map<Long, Long> planIdToSchoolIdMap = customProperties.getPlans().entrySet().stream().collect(Collectors
                    .toMap(entry -> Long.parseLong(entry.getKey()), entry -> Long.parseLong(entry.getValue())));

            long planId = student.getPlanDetails() != null ? student.getPlanDetails().getPlanId()
                    : customProperties.getPlanId();

            long schoolId = planIdToSchoolIdMap.containsKey(planId) ? planIdToSchoolIdMap.get(planId)
                    : customProperties.getSchoolId();

            // Insert Address
            Address studentAddress = student.getPrimaryAddress();
            int addressId = insertStudentAddress(con, studentAddress);
            logger.debug("Address inserted. addressId : " + addressId);

            // Insert Person
            personId = insertPerson(con, addressId, student);
            logger.debug("Person inserted. personId : " + personId);

            // Insert Phone and Person_Phone
            int phoneId = insertPhone(con, student.getPhoneNumbers().get(0));
            logger.debug("Phone inserted. phoneId : " + phoneId);
            insertPersonPhone(con, phoneId, personId);

            // Insert Email and Person_Email
            if (student.getPrimaryEmail() != null && student.getPrimaryEmail().size() > 0) {
                int emailId = insertEmail(con, student.getPrimaryEmail().get(0).getEmail());
                logger.debug("Email inserted. emailId : " + emailId);
                insertPersonEmail(con, emailId, personId);
            }

            // Insert Student_Profile
            /*
             * 1. Check if the payload contains school name 1a. if yes, check if the school is already in the db 1aa. if
             * yes, create a student profile using the existing school id. 1ab. if no, create a student profile with
             * default school id. 1b. If no, use the default school id from the config file.
             */
            // Check if the student has entered the school details.
            int studentProfileId = 0;
            // Step 1 - Check if the payload contains school name
            if (student.getSchool() != null) {
                // Step 1a - check if the school is already in the db
                School school = getSchoolByName(con, student.getSchool().getName());
                if (school != null) {
                    // Step 1aa - create a student profile using the existing school id.
                    studentProfileId =
                            insertStudentProfile(con, personId, school.getId(), customProperties.getLanguageId());
                    logger.info("Student_Profile inserted. studentProfileId : " + studentProfileId);
                }
                // Step 1ab - student profile with the default school id.
                else {
                    // create the student profile with newly generated school id.
                    studentProfileId = insertStudentProfile(con, personId, schoolId, customProperties.getLanguageId());
                    logger.info("Inserting the default school id : " + schoolId);
                    logger.info("Student_Profile inserted. studentProfileId : " + studentProfileId);
                }

                // Create a entry in the Student_School_Info
                // year of passing information is coming from the student.getSchool().getCode() variable for now.
                int studentSchoolInfoId = insertStudentSchoolInfo(con, personId, student.getSchool().getName(),
                        student.getSchool().getCode());
                logger.info("Data inserted into Student_School_Info. studentSchoolInfoId : " + studentSchoolInfoId);
            }
            // Step 1b - use the default school id from the config file.
            else {
                // Create a entry in the Student_School_Info
                int studentSchoolInfoId = insertStudentSchoolInfo(con, personId, null, null);
                logger.info("Data inserted into Student_School_Info. studentSchoolInfoId : " + studentSchoolInfoId);

                studentProfileId = insertStudentProfile(con, personId, schoolId, customProperties.getLanguageId());
                logger.info("Inserting the default school id : " + schoolId);
                logger.info("Student_Profile inserted. studentProfileId : " + studentProfileId);
            }

            // Insert Subscribed_Plan
            insertSubscribedPlan(con, planId, studentProfileId);

            // Insert Authentication
            insertAuthentication(con, personId, student.getPhoneNumbers().get(0).getNumber());

            // Commit once all the transactions are done.
            con.commit();

            logger.info("User has been saved with phone number : " + student.getPhoneNumbers().get(0).getNumber());

        } catch (Exception e) {
            logger.error("Error while signing up the user : ");
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e1) {
                logger.error("Error while rolling back the transaction : " + e.getMessage());
            }
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                logger.error("Error while closing the connection : " + e.getMessage());
            }
        }
        return student.getPhoneNumbers().get(0).getNumber();
    }

    /**
     * Private method to insert data to Address table.
     * 
     * @param con
     * @param studentAddress
     * @return
     * @throws SQLException
     */
    private int insertStudentAddress(Connection con, Address studentAddress) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(INSERT_ADDRESS.toString());

            stmt.setString(1, studentAddress.getPinCode());
            stmt.setString(2, studentAddress.getState());
            stmt.setString(3, studentAddress.getDistrict());
            stmt.setString(4, studentAddress.getTaluk());
            stmt.setString(5, studentAddress.getPost());
            stmt.setString(6, studentAddress.getAddressLine1());
            stmt.setString(7, studentAddress.getAddressLine2());

            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } finally {

        }

    }

    /**
     * Private method to insert data to Person table.
     * 
     * @param con
     * @param addressId
     * @param student
     * @return
     * @throws SQLException
     */
    private int insertPerson(Connection con, int addressId, StudentProfile student) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(INSERT_PERSON.toString());
            stmt.setInt(1, addressId);
            stmt.setString(2, student.getFirstName());
            stmt.setString(3, student.getLastName());

            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } finally {
            stmt.close();
            rs.close();
        }
    }

    /**
     * Private method to insert data to Phone table.
     * 
     * @param con
     * @param phone
     * @return
     * @throws SQLException
     */
    private int insertPhone(Connection con, Phone phone) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(INSERT_PHONE.toString());
            stmt.setString(1, phone.getNumber());
            stmt.setString(2, phone.getCode());
            stmt.setBoolean(3, true);

            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

            return -1;
        } finally {
            stmt.close();
            rs.close();
        }
    }

    /**
     * Private method to insert data to Person_Phone method.
     * 
     * @param con
     * @param phoneId
     * @param personId
     * @throws SQLException
     */
    private void insertPersonPhone(Connection con, int phoneId, int personId) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(INSERT_PERSON_PHONE.toString());
            stmt.setInt(1, personId);
            stmt.setInt(2, phoneId);

            stmt.executeUpdate();

        } finally {
            stmt.close();
        }
    }

    /**
     * Private method to insert data to Student_Profile table.
     * 
     * @param con
     * @param personId
     * @param schoolId
     * @param languageId
     * @return
     * @throws SQLException
     */
    private int insertStudentProfile(Connection con, int personId, long schoolId, int languageId) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(INSERT_STUDENT_PROFILE.toString());
            stmt.setInt(1, personId);
            stmt.setLong(2, schoolId);
            stmt.setBoolean(3, true);
            stmt.setInt(4, languageId);

            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

            return -1;

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
        }
    }

    /**
     * Private method to insert data to Subscribed_Plan table.
     * 
     * @param con
     * @param planId
     * @param studentProfileId
     * @throws SQLException
     */
    private void insertSubscribedPlan(Connection con, long planId, int studentProfileId) throws SQLException {
        PreparedStatement stmt = null;
        try {
            LocalDate localDate = LocalDate.now();
            stmt = con.prepareStatement(INSERT_SUBSCRIBED_PLAN.toString());
            stmt.setLong(1, planId);
            stmt.setInt(2, studentProfileId);
            stmt.setObject(3, localDate);
            stmt.setObject(4, localDate);
            stmt.setObject(5, localDate.plusYears(1));

            stmt.executeUpdate();

        } finally {
            stmt.close();
        }
    }

    /**
     * Private method to insert data to Email table.
     * 
     * @param con
     * @param emailString
     * @return
     * @throws SQLException
     */
    private int insertEmail(Connection con, String emailString) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(INSERT_EMAIL.toString());
            stmt.setString(1, emailString);
            stmt.setBoolean(2, true);

            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

            return -1;

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
        }
    }

    /**
     * Private method to insert data to Person_Email table.
     * 
     * @param con
     * @param emailId
     * @param personId
     * @throws SQLException
     */
    private void insertPersonEmail(Connection con, int emailId, int personId) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(INSERT_PERSON_EMAIL.toString());
            stmt.setInt(1, emailId);
            stmt.setInt(2, personId);

            stmt.executeUpdate();

        } finally {
            stmt.close();
        }

    }

    /**
     * Private method to insert data to Authentication table.
     * 
     * @param con
     * @param personId
     * @param userName
     * @throws SQLException
     */
    private void insertAuthentication(Connection con, int personId, String userName) throws SQLException {
        PreparedStatement stmt = null;
        try {
            // Create a random UUID for user secret
            UUID secret = UUID.randomUUID();
            stmt = con.prepareStatement(INSERT_AUTHENTICATION.toString());
            stmt.setInt(1, personId);
            stmt.setString(2, secret.toString());
            stmt.setString(3, userName);

            stmt.executeUpdate();

        } finally {
            stmt.close();
        }
    }

    /**
     * This method is to get the school object for the given school name
     * 
     * @param con
     * @param schoolName
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("null")
    private School getSchoolByName(Connection con, String schoolName) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        School school = new School();
        try {
            stmt = con.prepareStatement(GET_SCHOOL_BY_NAME.toString());
            stmt.setString(1, schoolName);

            rs = stmt.executeQuery();
            if (rs.next()) {
                school.setId(rs.getInt("id"));
                school.setName(rs.getString("name"));
                school.setCode(rs.getString("code"));
                return school;
            }
            return null;

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    /**
     * This method insert the new school object to the database
     * 
     * @param con
     * @param studentId
     * @param schoolName
     * @param yearOfPassing
     * @return
     * @throws SQLException
     */
    private int insertStudentSchoolInfo(Connection con, long studentId, String schoolName, String yearOfPassing)
            throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(INSERT_STUDENT_SCHOOL_INFO.toString());
            stmt.setLong(1, studentId);
            stmt.setString(2, schoolName);
            stmt.setString(3, yearOfPassing);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            return -1;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    public String updateUserSecret(Person person) {
        UUID secret = UUID.randomUUID();
        template.update(UPDATE_USER_SECRET, Arrays.asList(secret, person.getId()).toArray());
        return secret.toString();
    }

}
