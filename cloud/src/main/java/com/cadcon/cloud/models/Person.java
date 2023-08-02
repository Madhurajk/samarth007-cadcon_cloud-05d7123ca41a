package com.cadcon.cloud.models;

public class Person {
    private long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String userSecret;

    public Person(long id, String firstName, String lastName, String userName, String userSecret) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.userSecret = userSecret;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSecret() {
        return userSecret;
    }

    public void setUserSecret(String userSecret) {
        this.userSecret = userSecret;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
                + ", userName='" + userName + '\'' + ", userSecret='" + userSecret + '\'' + '}';
    }
}
