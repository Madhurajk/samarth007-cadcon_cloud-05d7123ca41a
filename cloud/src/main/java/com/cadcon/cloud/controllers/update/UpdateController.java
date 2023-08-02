package com.cadcon.cloud.controllers.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cadcon.cloud.services.ContentService;
import com.cadcon.cloud.services.UpdateService;

@RestController
@RequestMapping("api/v1/update")
public class UpdateController {

    @Autowired
    UpdateService updateService;
    
    private Logger logger = LoggerFactory.getLogger(UpdateController.class);


    @GetMapping("/check/{currentVersion}")
    public UpdateResponse getAvailableUpdate(@PathVariable("currentVersion") String currentVersion){
    	UpdateResponse updateResponse = new UpdateResponse();
    	try {
    		logger.info("Current App version : " + currentVersion);
    		updateResponse = updateService.getUpdateResponse(currentVersion);
    	} catch (Exception ex) {
    		logger.error("Error while getting update response : " + ex);
    		updateResponse.setCurrentVersion(currentVersion);
    		updateResponse.setForceUpgrade(false);
    		updateResponse.setRecommendUpgrade(false);
    	}
    	return updateResponse;
    }
}
