package com.cadcon.cloud.controllers.login;

public class AuthenticationRequest {

    private String userName;
    private String userSecret;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String userName, String userSecret) {
        this.userName = userName;
        this.userSecret = userSecret;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSecret() {
        return userSecret;
    }

    public void setUserSecret(String userSecret) {
        this.userSecret = userSecret;
    }
}
