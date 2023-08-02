package com.cadcon.cloud.models;

public class Email {
    private String email;
    private long id;
    private boolean isPrimary;

    public Email(String email, long id, boolean isPrimary) {
        this.email = email;
        this.id = id;
        this.isPrimary = isPrimary;
    }

    public Email() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

	@Override
	public String toString() {
		return "Email [email=" + email + ", id=" + id + ", isPrimary=" + isPrimary + "]";
	}
    
    
}
