package com.cadcon.cloud.controllers.plan;

public class CapsuleResponse {
    private long id;
    private String name;
    private long subject;
    private NodeResponse node;

    public CapsuleResponse(long id, String name, long subject, NodeResponse node) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.node = node;
    }

    public CapsuleResponse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSubject() {
        return subject;
    }

    public void setSubject(long subject) {
        this.subject = subject;
    }

    public NodeResponse getNode() {
        return node;
    }

    public void setNode(NodeResponse node) {
        this.node = node;
    }
}