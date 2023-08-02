package com.cadcon.cloud.services;

import com.cadcon.cloud.dao.AddressDao;
import com.cadcon.cloud.dao.PhoneDao;
import com.cadcon.cloud.dao.SchoolDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoDataUploadService {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private SchoolDao schoolDao;

    public long uploadAddress(String adLine1, String adLine2, String post, String taluk, String district, String state, String pincode){
        return addressDao.insertAddress(adLine1,adLine2, post, taluk, district,state,pincode);
    }

    public long uploadPhoneNumber(String code, String number, boolean isPrimary) {
        return phoneDao.insertPhoneNumber(code, number, isPrimary);
    }

    public long uploadSchool(String schoolName, String schoolCode, Long address) {
        return schoolDao.insertSchool(schoolName,schoolCode,address);
    }

    public long  linkSchoolWithPhone(long schoolRow, long phoneValue) {
        return schoolDao.linkSchoolPhone(schoolRow,phoneValue);
    }
}
