package com.cadcon.cloud.controllers.login;

public class TokenResponse {

    private String jwt;

    public TokenResponse(String jwt) {
        this.jwt = jwt;
    }

    public TokenResponse() {
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
