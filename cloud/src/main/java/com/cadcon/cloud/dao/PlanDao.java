package com.cadcon.cloud.dao;

import com.cadcon.cloud.models.SubjectCapsule;

import java.util.List;

public interface PlanDao {
    public List<SubjectCapsule> getSubjectCapsuleOfPlan(long planId);
}
