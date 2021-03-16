package com.zeus.inc.afinams.message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Message {

    private String request;
    private String message;
    private String date;

    public Message(String request, String message) {
        this.request = request;
        this.message = message;
        this.date = new Date(System.currentTimeMillis()).toString();
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
