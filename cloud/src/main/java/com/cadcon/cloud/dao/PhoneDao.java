package com.cadcon.cloud.dao;

import com.cadcon.cloud.models.Phone;

import java.util.List;

public interface PhoneDao {
    long insertPhoneNumber(String code, String number, boolean isPrimary);
    List<Phone> getUserPhone(long userId);

    List<Phone> getSchoolPhone(long id);
}
