package com.cadcon.cloud.models;

public class Node {
    private String name;
    private long id;
    private String color;
    private String description;
    private String image;
    private long parent;
    private String tag;
    private int sequence;
    private boolean isActive;

    public Node(String name, long id, String color, String description, String image, long parent, String tag, int sequence, boolean isActive) {
        this.name = name;
        this.id = id;
        this.color = color;
        this.description = description;
        this.image = image;
        this.parent = parent;
        this.tag = tag;
        this.sequence = sequence;
        this.isActive = isActive;
    }

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
