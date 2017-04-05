package com.example.backend.Dto;

import com.example.backend.Dto.Message;

import java.io.Serializable;
import java.util.List;

public class SchoolclassChat implements Serializable {

    private static final long serialVersionUID = 8413523108574250877L;

    private long id;

    private List<Message> messages = null;

    public SchoolclassChat() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        if(messages == null)
            return null;
        else
            return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
