package com.cadcon.cloud.models;

public class Plan {

    private String name;
    private String desc;
    private int validity;
    private String language;
    private long planId;
    private boolean active;

    public Plan(long planId, String name, String desc, int validity, String language, boolean active) {
        super();
        this.name = name;
        this.desc = desc;
        this.validity = validity;
        this.language = language;
        this.planId = planId;
        this.active = active;
    }

    public Plan() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getPlanId() {
        return planId;
    }

    public void setPlanId(long planId) {
        this.planId = planId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
