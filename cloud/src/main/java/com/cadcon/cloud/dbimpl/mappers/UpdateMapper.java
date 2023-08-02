package com.cadcon.cloud.dbimpl.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cadcon.cloud.controllers.update.UpdateResponse;
import com.cadcon.cloud.models.SubjectCapsule;

public class UpdateMapper implements RowMapper<UpdateResponse>{
	
	private JdbcTemplate template;

    private static final String UPGRADE_DETAIL = "select" +
            " serial_no, " +
            " current_version, " +
            " force_upgrade, " +
            " recommended_upgrade " +
            " from app_version_details where current_version > ?";

    public UpdateMapper(JdbcTemplate template) {
        this.template = template;
    }
	    

	@Override
	public UpdateResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
		String currentVersion = rs.getString("current_version");
        boolean forceUpgrade = rs.getBoolean("force_upgrade");
        boolean recommendUpgrade = rs.getBoolean("recommended_upgrade");
        UpdateResponse updateResponse = new UpdateResponse(currentVersion,forceUpgrade,recommendUpgrade);
        return updateResponse;
	}
	
	public List<UpdateResponse> getUpdateInfo(String currentVersion) {
        return template.query(UPGRADE_DETAIL, Arrays.asList(currentVersion).toArray(),this);
    }

}
