package com.cadcon.cloud.models;

public class OptionDetails {
    private long id;
    private int serial;
    private String text;
    private Media media;
    private boolean isCorrect;
    private int marksAssociated;

    public OptionDetails() {
    }

    public OptionDetails(long id, int serial, String text, Media media, boolean isCorrect, int marks) {
        this.id = id;
        this.serial = serial;
        this.text = text;
        this.media = media;
        this.isCorrect = isCorrect;
        this.marksAssociated = marks;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
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

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getMarksAssociated() {
        return marksAssociated;
    }

    public void setMarksAssociated(int marksAssociated) {
        this.marksAssociated = marksAssociated;
    }
}
