package com.riderecycle.payload;

import java.util.Date;

public class ErrorDetails {

    private String message;
    private Date timestamp;
    private String details;

    public ErrorDetails(String message, Date timestamp, String details) {
        this.message = message;
        this.timestamp = timestamp;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getDetails() {
        return details;
    }
}
