package com.cadcon.cloud.models;

public class Option {

    private long id;
    private int serial;
    private String text;
    private long media;
    private boolean isCorrect;
    private int marks;

    public Option(long id, int serial, String text, long media, boolean isCorrect, int marks) {
        this.id = id;
        this.serial = serial;
        this.text = text;
        this.media = media;
        this.isCorrect = isCorrect;
        this.marks = marks;
    }

    public Option() {
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

    public long getMedia() {
        return media;
    }

    public void setMedia(long media) {
        this.media = media;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
