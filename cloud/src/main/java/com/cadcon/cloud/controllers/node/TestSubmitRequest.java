package com.cadcon.cloud.controllers.node;

import java.util.List;

public class TestSubmitRequest {

    private List<TestSubmissionRequest> submissionRequestList;
    private long timeTaken;

    public List<TestSubmissionRequest> getSubmissionRequestList() {
        return submissionRequestList;
    }

    public void setSubmissionRequestList(List<TestSubmissionRequest> submissionRequestList) {
        this.submissionRequestList = submissionRequestList;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

}
