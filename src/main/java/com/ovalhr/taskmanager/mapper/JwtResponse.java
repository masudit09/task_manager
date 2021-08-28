package com.ovalhr.taskmanager.mapper;

import java.util.List;

/**
 * Created by rana on 8/27/21.
 */
public class JwtResponse {

    private String message;

    private boolean status;

    private Object data;

    public JwtResponse(String token, String username,String fullName) {
        this.message = "Success";
        this.status = true;
        this.data = new LoginResponse(username, fullName, token);
    }

    public JwtResponse(boolean status) {
        this.message = "Failed";
        this.status = false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
