package com.cadcon.cloud.controllers.plan;

import java.util.List;

import com.cadcon.cloud.models.TestProgress;

public class NodeDetailResponse {
    List<NodeResponse> childNodeList;
    List<TestProgress> testResponse;

    public List<NodeResponse> getChildNodeList() {
        return childNodeList;
    }

    public void setChildNodeList(List<NodeResponse> childNodeList) {
        this.childNodeList = childNodeList;
    }

    public List<TestProgress> getTestResponse() {
        return testResponse;
    }

    public void setTestResponse(List<TestProgress> testResponse) {
        this.testResponse = testResponse;
    }
}
