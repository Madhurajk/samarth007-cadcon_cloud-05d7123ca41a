package com.cadcon.cloud.models;

public class SolutionDetail {
    private long id;
    private String text;
    private Media media;

    public SolutionDetail(long id, String text, Media media) {
        this.id = id;
        this.text = text;
        this.media = media;
    }

    public SolutionDetail() {
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
}
