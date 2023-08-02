package com.cadcon.cloud.models;

public class Test {

    private long id;
    private String name;
    private long node;

    public Test(long id, String name, long node) {
        this.id = id;
        this.name = name;
        this.node = node;
    }

    public Test() {
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

    public long getNode() {
        return node;
    }

    public void setNode(long node) {
        this.node = node;
    }
}
