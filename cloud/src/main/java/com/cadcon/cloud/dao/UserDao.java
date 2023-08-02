package com.cadcon.cloud.dao;

import java.util.List;

import com.cadcon.cloud.controllers.user.CarousalData;
import com.cadcon.cloud.models.Media;
import com.cadcon.cloud.models.Person;
import com.cadcon.cloud.models.StudentProfile;

public interface UserDao {

    public List<Person> getUserByPhoneNumber(String phoneNumber);

    void addLoginAttempt(long id, String loginMode, String deviceIdentifier);

    Person getUserByUsername(String username);

    boolean doesUserExists(String userName, String userSecret);

    StudentProfile getStudentProfile(long userId);

    long getStudentIdByUserId(long userId);

    List<CarousalData> getCarousalForStudent(long studentId);

    Media getSponserInfo(long studentId);

    void updateUserFirstName(StudentProfile newStudent);

    void updateUserLastName(StudentProfile newStudent);

    void updateUserEmail(long emailId, String newEmail);

    public String signupUser(StudentProfile student);

    public String updateUserSecret(Person person);
}
