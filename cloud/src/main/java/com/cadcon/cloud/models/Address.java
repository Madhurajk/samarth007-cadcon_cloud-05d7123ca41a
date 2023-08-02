package com.cadcon.cloud.models;

public class Address {
    private long id;
    private String pinCode;
    private String state;
    private String district;
    private String taluk;
    private String post;
    private String addressLine1;
    private String addressLine2;

    public Address(long id, String pinCode, String state, String district, String taluk, String post, String addressLine1, String addressLine2) {
        this.id = id;
        this.pinCode = pinCode;
        this.state = state;
        this.district = district;
        this.taluk = taluk;
        this.post = post;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
    }

    public Address() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTaluk() {
        return taluk;
    }

    public void setTaluk(String taluk) {
        this.taluk = taluk;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

	@Override
	public String toString() {
		return "Address [id=" + id + ", pinCode=" + pinCode + ", state=" + state + ", district=" + district + ", taluk="
				+ taluk + ", post=" + post + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + "]";
	}
    
    
}
