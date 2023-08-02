package com.cadcon.cloud.models;

import java.util.List;

public class QuestionDetail {

    private long id;
    private String text;
    private Media media;
    private SolutionDetail solutionDetail;
    private List<OptionDetails> options;

    public QuestionDetail() {
    }

    public QuestionDetail(long id, String text, Media media, SolutionDetail solutionDetail, List<OptionDetails> options) {
        this.id = id;
        this.text = text;
        this.media = media;
        this.solutionDetail = solutionDetail;
        this.options = options;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public SolutionDetail getSolutionDetail() {
        return solutionDetail;
    }

    public void setSolutionDetail(SolutionDetail solutionDetail) {
        this.solutionDetail = solutionDetail;
    }

    public List<OptionDetails> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDetails> options) {
        this.options = options;
    }
}
