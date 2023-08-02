package com.cadcon.cloud.controllers.login;

public class CreateUserByUsernameRequest extends CreateUserRequest {
    private String userName;
    private String userSecret;

    public CreateUserByUsernameRequest(String loginMode, String device_identifier, String userName, String userSecret) {
        super(loginMode, device_identifier);
        this.userName = userName;
        this.userSecret = userSecret;
    }

    public CreateUserByUsernameRequest() {              
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
