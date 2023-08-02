package com.cadcon.cloud.models;

import java.util.List;

public class School {

    private long id;
    private String name;
    private String code;
    private Address address;
    private List<Phone> schoolPhone;
    private List<Email> schoolEmails;

    public School() {
    }

    public School(long id, String name, String code, Address address, List<Phone> schoolPhone, List<Email> schoolEmails) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.address = address;
        this.schoolPhone = schoolPhone;
        this.schoolEmails = schoolEmails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getSchoolPhone() {
        return schoolPhone;
    }

    public void setSchoolPhone(List<Phone> schoolPhone) {
        this.schoolPhone = schoolPhone;
    }

    public List<Email> getSchoolEmails() {
        return schoolEmails;
    }

    public void setSchoolEmails(List<Email> schoolEmails) {
        this.schoolEmails = schoolEmails;
    }
}
