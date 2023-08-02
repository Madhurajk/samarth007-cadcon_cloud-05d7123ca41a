package com.cadcon.cloud.controllers.plan;

import com.cadcon.cloud.models.Node;

public class NodeResponse {
    private String index;
    private String tag;
    private String name;
    private long id;
    private String color;
    private String description;
    private String image;
    private boolean hasChildren;
    private double mediaProgress;
    private double testProgress;
    private boolean active;
    private int sequence;

    public NodeResponse(String index, String tag, String name, long id, String color, String description, String image,
            boolean hasChildren, double mediaProgress, double testProgress, int sequence) {
        this.index = index;
        this.tag = tag;
        this.name = name;
        this.id = id;
        this.color = color;
        this.description = description;
        this.image = image;
        this.hasChildren = hasChildren;
        this.mediaProgress = mediaProgress;
        this.testProgress = testProgress;
        this.sequence = sequence;
    }

    public NodeResponse(Node node) {
        // this.index = node.getIndex()
        this.tag = node.getTag();
        this.name = node.getName();
        this.id = node.getId();
        this.color = node.getColor();
        this.description = node.getDescription();
        this.image = node.getImage();
        this.active = node.isActive();
        this.sequence = node.getSequence();
    }

    public NodeResponse() {
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public double getMediaProgress() {
        return mediaProgress;
    }

    public void setMediaProgress(double mediaProgress) {
        this.mediaProgress = mediaProgress;
    }

    public double getTestProgress() {
        return testProgress;
    }

    public void setTestProgress(double testProgress) {
        this.testProgress = testProgress;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
