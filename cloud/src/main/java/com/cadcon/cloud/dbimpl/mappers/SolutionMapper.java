package com.cadcon.cloud.dbimpl.mappers;

import com.cadcon.cloud.models.Solution;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class SolutionMapper implements RowMapper<Solution> {

    private JdbcTemplate template;

    private static final String SOLUTION_DETAIL = "select" +
            " id, " +
            " solution_text, " +
            " solution_media " +
            " from Solution where id = ?";

    public SolutionMapper(JdbcTemplate template) {
        this.template = template;
    }


    @Override
    public Solution mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String text = resultSet.getString("solution_text");
        long media = resultSet.getLong("solution_media");
        return new Solution(id,text,media);
    }

    public Solution getSolutionDetails(long solution) {
        return template.queryForObject(SOLUTION_DETAIL, Arrays.asList(solution).toArray(),this);
    }
}
