package com.kitsuno.exception;

import java.util.List;

public class ErrorResponse {

    private int status;
    private String message;
    private String timeStamp;
    private String details;

    public ErrorResponse() {
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getDetails() {
        return details;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
