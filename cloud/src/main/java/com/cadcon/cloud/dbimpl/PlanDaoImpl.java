package com.cadcon.cloud.dbimpl;

import com.cadcon.cloud.dao.PlanDao;
import com.cadcon.cloud.dbimpl.mappers.CapsuleMapper;
import com.cadcon.cloud.models.SubjectCapsule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlanDaoImpl implements PlanDao {

    @Autowired JdbcTemplate template;

    @Override
    public List<SubjectCapsule> getSubjectCapsuleOfPlan(long planId) {
        return new CapsuleMapper(template).getCapsuleOfPlan(planId);
    }
}
