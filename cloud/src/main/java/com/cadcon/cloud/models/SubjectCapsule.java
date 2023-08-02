package com.cadcon.cloud.models;

public class SubjectCapsule {
    private long capsuleId;
    private long subjectId;
    private String name;
    private long nodeRoot;

    public SubjectCapsule(long capsuleId, long subjectId, String name, long nodeRoot) {
        this.capsuleId = capsuleId;
        this.subjectId = subjectId;
        this.name = name;
        this.nodeRoot = nodeRoot;
    }

    public SubjectCapsule() {
    }

    public long getCapsuleId() {
        return capsuleId;
    }

    public void setCapsuleId(long capsuleId) {
        this.capsuleId = capsuleId;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNodeRoot() {
        return nodeRoot;
    }

    public void setNodeRoot(long nodeRoot) {
        this.nodeRoot = nodeRoot;
    }
}
