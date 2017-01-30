package com.example.backend.Dto;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.Collection;
import java.util.List;

/**
 * Created by laubi on 1/27/2017.
 */

public class Chat {

    private static Chat instance = null;

    public static Chat getInstance() {
        if (instance == null) {
            instance = new Chat();
        }
        return instance;
    }

    private ObservableArrayList<Message> messages;

    public Chat() {
        messages = new ObservableArrayList<>();
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
