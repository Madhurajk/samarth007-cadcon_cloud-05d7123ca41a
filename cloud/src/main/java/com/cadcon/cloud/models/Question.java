package com.cadcon.cloud.models;

public class Question {
    private long id;
    private String text;
    private long media;
    private long solution;

    public Question(long id, String text, long media, long solution) {
        this.id = id;
        this.text = text;
        this.media = media;
        this.solution = solution;
    }

    public Question() {
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

    public long getMedia() {
        return media;
    }

    public void setMedia(long media) {
        this.media = media;
    }

    public long getSolution() {
        return solution;
    }

    public void setSolution(long solution) {
        this.solution = solution;
    }
}
