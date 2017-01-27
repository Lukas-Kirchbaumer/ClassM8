package com.example.backend.Dto;

import java.util.Date;

/**
 * Created by laubi on 1/18/2017.
 */
public class Message {
    private String sender;
    private String content;
    private Date datetime;

    public Message(String sender, String content, Date datetime) {
        this.sender = sender;
        this.content = content;
        this.datetime = datetime;
    }

    public Message() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (sender != null ? !sender.equals(message.sender) : message.sender != null) return false;
        if (content != null ? !content.equals(message.content) : message.content != null)
            return false;
        return datetime != null ? datetime.equals(message.datetime) : message.datetime == null;

    }

    @Override
    public int hashCode() {
        int result = sender != null ? sender.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        return result;
    }
}
