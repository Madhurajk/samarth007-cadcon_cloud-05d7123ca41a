package com.cadcon.cloud.dbimpl.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cadcon.cloud.models.PlanDetails;

public class PlanDetailsMapper implements RowMapper<PlanDetails> {

    private static final String PLAN_DETAILS = "select " + "sp.id as id, " + "p.name as name, "
            + "p.description as description, " + "p.validity as validity, " + "p.language as language, "
            + "p.id as planId, " + "p.active as active, " + "sp.bought_on as bought, " + "sp.first_day as first_day, "
            + "sp.last_day as last_day "
            + " from subscribed_plan sp join Plan p on sp.plan = p.id where sp.student_profile = ?";

    private JdbcTemplate template;

    public PlanDetailsMapper(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public PlanDetails mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        int validity = resultSet.getInt("validity");
        String language = resultSet.getString("language");
        long planId = resultSet.getLong("planId");
        Date boughtOn = resultSet.getDate("bought");
        Date firstDay = resultSet.getDate("first_day");
        Date lastDay = resultSet.getDate("last_day");
        boolean active = resultSet.getBoolean("active");

        return new PlanDetails(id, name, description, validity, language, planId, active, boughtOn, firstDay, lastDay);
    }

    public PlanDetails getPlanOfProfile(long profile) {
        return template.queryForObject(PLAN_DETAILS, Arrays.asList(profile).toArray(), this);
    }

}
