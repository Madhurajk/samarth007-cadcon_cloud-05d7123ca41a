package com.cadcon.cloud.dao;

import com.cadcon.cloud.models.Email;

import java.util.List;

public interface EmailDao {
    public List<Email> getUserEmails(long userId);
    public List<Email> getSchoolEmails(long schoolId);
}
