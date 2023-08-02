package com.cadcon.cloud.controllers.node;

import com.cadcon.cloud.models.QuestionDetail;

import java.util.List;

public class TestResponse {
    public long id;
    public String name;
    public List<QuestionDetail> questionDetailList;

    public TestResponse() {
    }

    public TestResponse(long id, String name, List<QuestionDetail> questionDetailList) {
        this.id = id;
        this.name = name;
        this.questionDetailList = questionDetailList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QuestionDetail> getQuestionDetailList() {
        return questionDetailList;
    }

    public void setQuestionDetailList(List<QuestionDetail> questionDetailList) {
        this.questionDetailList = questionDetailList;
    }
}
