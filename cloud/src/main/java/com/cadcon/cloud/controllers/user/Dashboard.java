package com.cadcon.cloud.controllers.user;

import com.cadcon.cloud.controllers.plan.CapsuleResponse;
import com.cadcon.cloud.models.Media;
import com.cadcon.cloud.models.SubjectCapsule;

import java.util.List;

public class Dashboard {
    private List<CarousalData> userCarousalData;
    private Media userSponserData;
    private List<CapsuleResponse> userSubjectData;

    public Dashboard(List<CarousalData> userCarousalData, Media userSponserData, List<CapsuleResponse> userSubjectData) {
        this.userCarousalData = userCarousalData;
        this.userSponserData = userSponserData;
        this.userSubjectData = userSubjectData;
    }

    public Dashboard() {
    }

    public List<CarousalData> getUserCarousalData() {
        return userCarousalData;
    }

    public void setUserCarousalData(List<CarousalData> userCarousalData) {
        this.userCarousalData = userCarousalData;
    }

    public Media getUserSponserData() {
        return userSponserData;
    }

    public void setUserSponserData(Media userSponserData) {
        this.userSponserData = userSponserData;
    }

    public List<CapsuleResponse> getUserSubjectData() {
        return userSubjectData;
    }

    public void setUserSubjectData(List<CapsuleResponse> userSubjectData) {
        this.userSubjectData = userSubjectData;
    }
}
