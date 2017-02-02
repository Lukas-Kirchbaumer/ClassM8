package com.example.laubi.myapplication.Polling;

import android.annotation.TargetApi;
import android.app.AlarmManager;

import com.example.backend.Dto.MappedChat;
import com.example.backend.Interfaces.DataReader;

/**
 * Created by laubi on 2/1/2017.
 */

@TargetApi(24)
public class AlarmListener implements AlarmManager.OnAlarmListener {
    @Override
    public void onAlarm() {
        try {
            MappedChat.getInstance().addMultipleMessages(DataReader.getInstance().receiveMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
