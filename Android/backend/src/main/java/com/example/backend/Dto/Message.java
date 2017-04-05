package com.example.backend.Dto;

import java.sql.Timestamp;
import java.util.Date;

public class Message {

    private String content;

    private String sender;

    private Timestamp dateTime;

    public Message() {}

    public Message(String content, String sender, Timestamp dateTime) {
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

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (content != null ? !content.equals(message.content) : message.content != null)
            return false;
        if (sender != null ? !sender.equals(message.sender) : message.sender != null) return false;
        return dateTime != null ? dateTime.equals(message.dateTime) : message.dateTime == null;

    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
