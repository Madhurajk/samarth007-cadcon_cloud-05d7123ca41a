package com.cadcon.cloud.models;

public class TestProgress {

    private String testName;
    private int obtainedMarks;
    private int totalMarks;
    private long submitTime;
    private int testId;

    public TestProgress(int testId, String testName, int obtainedMarks, int totalMarks, long submitTime) {
        this.setTestId(testId);
        this.testName = testName;
        this.obtainedMarks = obtainedMarks;
        this.totalMarks = totalMarks;
        this.submitTime = submitTime;
    }

    public TestProgress() {
    }

    public int getObtainedMarks() {
        return obtainedMarks;
    }

    public void setObtainedMarks(int obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(long submitTime) {
        this.submitTime = submitTime;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

}
