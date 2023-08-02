package com.cadcon.cloud.dbimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cadcon.cloud.controllers.update.UpdateResponse;
import com.cadcon.cloud.dao.UpdateDao;
import com.cadcon.cloud.dbimpl.mappers.UpdateMapper;

@Repository
public class UpdateDaoImpl implements UpdateDao{
	
	 @Autowired 
	 JdbcTemplate template;

	@Override
	public List<UpdateResponse> getUpdateVersionInfo(String currentVersion) {
		return new UpdateMapper(template).getUpdateInfo(currentVersion);
		
	}

}
