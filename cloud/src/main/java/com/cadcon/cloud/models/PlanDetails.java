package com.cadcon.cloud.models;

import java.util.Date;

public class PlanDetails extends Plan {

    private long id;
    private Date boughtOn;
    private Date firstDay;
    private Date lastDay;

    public PlanDetails(long id, String name, String desc, int validity, String language, long planId, boolean active,
            Date boughtOn, Date firstDay, Date lastDay) {
        super(planId, name, desc, validity, language, active);
        this.id = id;
        this.boughtOn = boughtOn;
        this.firstDay = firstDay;
        this.lastDay = lastDay;

    }

    public PlanDetails() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getBoughtOn() {
        return boughtOn;
    }

    public void setBoughtOn(Date boughtOn) {
        this.boughtOn = boughtOn;
    }

    public Date getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(Date firstDay) {
        this.firstDay = firstDay;
    }

    public Date getLastDay() {
        return lastDay;
    }

    public void setLastDay(Date lastDay) {
        this.lastDay = lastDay;
    }
}
