package com.example.backend.Results;

import com.example.backend.Dto.Message;

import java.util.Vector;

/**
 * Created by laubi on 1/18/2017.
 */
public class ChatResult {
    private Vector<Message> content = new Vector<>();

    public ChatResult() {
    }

    public Vector<Message> getContent() {
        return content;
    }

    public void setContent(Vector<Message> content) {
        this.content = content;
    }
}
