package com.cadcon.cloud.util;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("custom")
public class CustomProperties {

    // Default plan for the student sign up
    private Integer planId;

    // Default School id for the students who hasn't provided the school details
    private Long schoolId;

    // Default language option for the student while signing up
    private Integer languageId;

    private Map<String, String> plans;

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public Map<String, String> getPlans() {
        return plans;
    }

    public void setPlans(Map<String, String> plans) {
        this.plans = plans;
    }

    @Override
    public String toString() {
        return "CustomProperties [planId=" + planId + ", schoolId=" + schoolId + ", languageId=" + languageId
                + ", plans=" + plans + "]";
    }

}
