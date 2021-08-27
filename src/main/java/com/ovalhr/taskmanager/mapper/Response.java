package com.ovalhr.taskmanager.mapper;

/**
 * Created by rana on 8/22/21.
 */
public class Response {
    private String message;
    private boolean status;
    private Object data;


    public Response(Object object) {
        this.status = true;
        this.message =  "Success";
        this.data = object;
    }

    public Response(String message, Object data) {
        this.status = false;
        this.message = message;
        this.data = data;
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
