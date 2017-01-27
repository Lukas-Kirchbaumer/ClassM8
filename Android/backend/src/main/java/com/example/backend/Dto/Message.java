package com.example.backend.Dto;


import java.util.Date;

/**
 * Created by laubi on 1/18/2017.
 */
public class Message {
    private int id;
    private String sender;
    private String message;
    private Date Timestamp;

    public Message(int id, String sender, String message, Date timestamp) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        Timestamp = timestamp;
    }

    public Message(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Date timestamp) {
        Timestamp = timestamp;
    }
}
