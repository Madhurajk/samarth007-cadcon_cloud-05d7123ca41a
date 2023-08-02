package com.cadcon.cloud.dbimpl.mappers;

import com.cadcon.cloud.models.SubjectCapsule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CapsuleMapper implements RowMapper<SubjectCapsule> {

    private static final String PLAN_CAPSULE = "select " +
            "sc.id as id, " +
            "sc.name as name, " +
            "sc.subject as subject, " +
            "sc.learn_root as learn " +
            " from Plan p join Plan_subjects ps on p.id = ps.plan " +
            "join Subject_Capsule sc on ps.subject_capsule = sc.id " +
            "where p.id = ?";

    private JdbcTemplate template;

    public CapsuleMapper(JdbcTemplate template) {
        this.template = template;
    }

    public List<SubjectCapsule> getCapsuleOfPlan(long planId){
        return template.query(PLAN_CAPSULE, Arrays.asList(planId).toArray(),this);
    }

    @Override
    public SubjectCapsule mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        long subject = resultSet.getLong("subject");
        long learn = resultSet.getLong("learn");
        SubjectCapsule capsule = new SubjectCapsule(id,subject,name,learn);
        return capsule;
    }
}
