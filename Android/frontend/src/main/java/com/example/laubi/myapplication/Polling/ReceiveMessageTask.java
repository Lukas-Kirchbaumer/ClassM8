package com.example.laubi.myapplication.Polling;

import com.example.backend.Dto.MappedChat;
import com.example.backend.Interfaces.DataReader;

import java.util.TimerTask;

/**
 * Created by laubi on 2/1/2017.
 */

public class ReceiveMessageTask extends TimerTask {
    @Override
    public void run() {
        MappedChat.getInstance().addMultipleMessages(DataReader.getInstance().receiveMessage());
    }
}
