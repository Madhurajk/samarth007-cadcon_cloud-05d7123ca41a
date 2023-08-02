package com.cadcon.cloud.dao;

import com.cadcon.cloud.models.School;

public interface SchoolDao {

    public long insertSchool(String schoolName, String code, long schoolAddress);
    public long linkSchoolPhone(long schoolRow, long phoneValue);

    public School getSchoolOfUser(long userId);
}
