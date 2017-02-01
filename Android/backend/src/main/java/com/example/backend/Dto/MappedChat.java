package com.example.backend.Dto;

import android.app.Activity;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.Collection;
import java.util.List;

/**
 * Created by laubi on 1/27/2017.
 */

public class MappedChat {

    public static Activity mWeakActivity;
    private static MappedChat instance = null;
    private ObservableArrayList<Message> messages;

    public MappedChat() {
        messages = new ObservableArrayList<>();
    }

    public static MappedChat getInstance() {
        if (instance == null) {
            instance = new MappedChat();
        }
        return instance;
    }

    public int addMultipleMessages(Collection<Message> messages) {
        int counter = 0;
        for (Message m : messages) {
            if (!this.messages.contains(m)) {
                this.messages.add(this.messages.size(), m);
                counter++;
            }
        }
        return counter;
    }

    public ObservableArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ObservableArrayList<Message> messages) {
        this.messages = messages;
    }
}
