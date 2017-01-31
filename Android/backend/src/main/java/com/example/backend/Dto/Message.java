package com.example.backend.Dto;

import java.util.Date;

public class Message {

    private String content;

    private String sender;

    private Date dateTime;

    public Message() {}

    public Message(String content, String sender, Date dateTime) {
        this.content = content;
        this.sender = sender;
        this.dateTime = dateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
