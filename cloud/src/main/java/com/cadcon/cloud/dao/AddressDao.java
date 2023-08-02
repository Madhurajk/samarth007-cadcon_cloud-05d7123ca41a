package com.cadcon.cloud.dao;

import com.cadcon.cloud.models.Address;

public interface AddressDao {

    long insertAddress(String adLine1, String adLine2, String post, String taluk, String district, String state, String pincode);

    Address getAddressOfUser(long userId);

    Address getAddressOfSchool(long id);
}
