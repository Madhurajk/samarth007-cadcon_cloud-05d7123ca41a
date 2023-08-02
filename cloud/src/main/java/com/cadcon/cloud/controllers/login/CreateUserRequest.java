package com.cadcon.cloud.controllers.login;

public class CreateUserRequest {

    public static String LOGIN_MODE_PHONE = "login_mode_phone";
    public static String LOGIN_MODE_WEB = "login_mode_web";
    public static String LOGIN_MODE_SCHOOL = "login_mode_school";

    private String loginMode;
    private String deviceIdentifier;

    public CreateUserRequest(String loginMode, String device_identifier) {
        this.loginMode = loginMode;
        this.deviceIdentifier = device_identifier;
    }

    public CreateUserRequest() {
    }

    public String getLoginMode() {
        return loginMode;
    }

    public void setLoginMode(String loginMode) {
        this.loginMode = loginMode;
    }

    public String getDeviceIdentifier() {
        return deviceIdentifier;
    }

    public void setDeviceIdentifier(String deviceIdentifier) {
        this.deviceIdentifier = deviceIdentifier;
    }
}
