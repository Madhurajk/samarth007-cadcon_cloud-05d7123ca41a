package com.cadcon.cloud.dbimpl.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cadcon.cloud.models.Plan;

public class PlanMapper implements RowMapper<Plan> {

    private JdbcTemplate template;

    public PlanMapper(JdbcTemplate template) {
        this.template = template;
    }

    private static final String GET_ALL_ACTIVE_PLANS = "select" + " p.id, " + " p.name, " + " p.description, "
            + " p.active, " + "p.validity, " + " l.name as language "
            + " from Plan p join language l on p.language = l.id where p.active = 'true'";

    @Override
    public Plan mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        int validity = resultSet.getInt("validity");
        String language = resultSet.getString("language");
        boolean active = resultSet.getBoolean("active");

        return new Plan(id, name, description, validity, language, active);
    }

    public List<Plan> getAllActivePlans() {
        return template.query(GET_ALL_ACTIVE_PLANS, this);
    }

}
