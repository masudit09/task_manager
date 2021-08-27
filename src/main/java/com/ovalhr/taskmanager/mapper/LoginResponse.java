package com.ovalhr.taskmanager.mapper;

/**
 * Created by rana on 8/28/21.
 */
public class LoginResponse {

    private String username;

    private String fullName;

    private String token;

    private String type = "Bearer";

    public LoginResponse(String username, String fullName, String token) {
        this.username = username;
        this.fullName = fullName;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
