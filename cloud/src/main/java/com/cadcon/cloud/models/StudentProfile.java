package com.cadcon.cloud.models;

import java.util.List;

public class StudentProfile {

    private long id;
    private long studentId;
    private String firstName;
    private String lastName;
    private List<Email> primaryEmail;
    private Address primaryAddress;
    private List<Phone> phoneNumbers;
    private School school;
    private PlanDetails planDetails;

    public StudentProfile() {
    }

    public StudentProfile(long id, long studentId, String firstName, String lastName, List<Email> primaryEmail,
            Address primaryAddress, List<Phone> phoneNumbers, School school, PlanDetails planDetails) {
        this.id = id;
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.primaryEmail = primaryEmail;
        this.primaryAddress = primaryAddress;
        this.phoneNumbers = phoneNumbers;
        this.school = school;
        this.planDetails = planDetails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Email> getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(List<Email> primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public List<Phone> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<Phone> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public PlanDetails getPlanDetails() {
        return planDetails;
    }

    public void setPlanDetails(PlanDetails planDetails) {
        this.planDetails = planDetails;
    }

    @Override
    public String toString() {
        return "StudentProfile [id=" + id + ", studentId=" + studentId + ", firstName=" + firstName + ", lastName="
                + lastName + ", primaryEmail=" + primaryEmail + ", primaryAddress=" + primaryAddress + ", phoneNumbers="
                + phoneNumbers + ", school=" + school + ", planDetails=" + planDetails + "]";
    }

}
