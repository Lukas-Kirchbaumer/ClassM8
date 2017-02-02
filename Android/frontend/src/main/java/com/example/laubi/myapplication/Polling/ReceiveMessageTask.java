package com.example.laubi.myapplication.Polling;

import com.example.backend.Dto.MappedChat;
import com.example.backend.Interfaces.DataReader;
import com.example.laubi.myapplication.Activities.TestHomeActivity;

import java.util.TimerTask;

/**
 * Created by laubi on 2/1/2017.
 */

public class ReceiveMessageTask extends TimerTask {
    @Override
    public void run() {
        try {
            TestHomeActivity.chatSema.acquire();
            MappedChat.getInstance().addMultipleMessages(DataReader.getInstance().receiveMessage());
            TestHomeActivity.chatSema.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
