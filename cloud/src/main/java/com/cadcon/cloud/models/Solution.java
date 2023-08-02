package com.cadcon.cloud.models;

public class Solution {

    private long id;
    private String text;
    private long media;

    public Solution(long id, String text, long media) {
        this.id = id;
        this.text = text;
        this.media = media;
    }

    public Solution() {
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
}
