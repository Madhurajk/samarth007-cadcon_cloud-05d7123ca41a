package com.cadcon.cloud.dao;

import java.util.List;

import com.cadcon.cloud.controllers.update.UpdateResponse;

public interface UpdateDao {
	
	public List<UpdateResponse> getUpdateVersionInfo(String currentVersion);
		

}
