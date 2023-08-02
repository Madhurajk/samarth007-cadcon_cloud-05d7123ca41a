package com.cadcon.cloud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadcon.cloud.controllers.update.UpdateResponse;
import com.cadcon.cloud.dao.UpdateDao;

@Service
public class UpdateService {
	
	@Autowired
    private UpdateDao updateDao;
	
	public UpdateResponse getUpdateResponse(String currentVersion) {
		List<UpdateResponse> updateResponseList = updateDao.getUpdateVersionInfo(currentVersion);
		UpdateResponse resultResponse = new UpdateResponse(currentVersion, false, false);
		for(UpdateResponse updateResp : updateResponseList) {
			if(updateResp.isForceUpgrade()) {
				resultResponse.setForceUpgrade(true);
				break;
			}
			if(updateResp.isRecommendUpgrade()) {
				resultResponse.setRecommendUpgrade(true);
			}
		}
		return resultResponse;
	}

}
