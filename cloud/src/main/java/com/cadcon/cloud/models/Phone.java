package com.cadcon.cloud.models;

public class Phone {

    private String number;
    private String code;
    private boolean isPrimary;
    private long id;

    public Phone(String number, String code, boolean isPrimary, long id) {
        this.number = number;
        this.code = code;
        this.isPrimary = isPrimary;
        this.id = id;
    }

    public Phone() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

	@Override
	public String toString() {
		return "Phone [number=" + number + ", code=" + code + ", isPrimary=" + isPrimary + ", id=" + id + "]";
	}
    
    
}
