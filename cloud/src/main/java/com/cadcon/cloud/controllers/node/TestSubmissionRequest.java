package com.cadcon.cloud.controllers.node;

public class TestSubmissionRequest {
    private Long questionId;
    private int optionSerial;

    public TestSubmissionRequest(Long questionId, int optionSerial) {
        this.questionId = questionId;
        this.optionSerial = optionSerial;
    }

    public TestSubmissionRequest() {
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public int getOptionSerial() {
        return optionSerial;
    }

    public void setOptionSerial(int optionSerial) {
        this.optionSerial = optionSerial;
    }
}
