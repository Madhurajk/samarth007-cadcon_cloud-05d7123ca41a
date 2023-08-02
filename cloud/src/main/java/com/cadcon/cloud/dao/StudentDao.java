package com.cadcon.cloud.dao;

import com.cadcon.cloud.models.StudentProfile;

public interface StudentDao {

    public StudentProfile getStudentProfile(String personId);
}
