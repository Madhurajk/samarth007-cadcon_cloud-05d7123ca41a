package com.cadcon.cloud.models;

public class Media {
    private long id;
    private String link;
    private String memeType;
    private String label;
    private String description;

    public Media(long id, String link, String memeType, String label, String description) {
        this.id = id;
        this.link = link;
        this.memeType = memeType;
        this.label = label;
        this.description = description;
    }

    public Media() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMemeType() {
        return memeType;
    }

    public void setMemeType(String memeType) {
        this.memeType = memeType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
