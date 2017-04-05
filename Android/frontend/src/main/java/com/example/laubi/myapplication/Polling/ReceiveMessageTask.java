package com.example.laubi.myapplication.Polling;

import android.app.Activity;
import android.content.Context;

import com.example.backend.Database.Database;
import com.example.backend.Interfaces.DataReader;
import com.example.laubi.myapplication.Activities.HomeActivity;
import com.example.laubi.myapplication.Adapters.ChatArrayAdapter;

import java.util.TimerTask;

/**
 * Created by laubi on 2/1/2017.
 */

    public class ReceiveMessageTask extends TimerTask {
        private Context context;
    private HomeActivity activity;
        public ReceiveMessageTask(Context applicationContext, HomeActivity activity) {
            context = applicationContext;
            this.activity = activity;
        }

    @Override
    public void run() {
        try {
           activity.runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    ((ChatArrayAdapter)HomeActivity.getLvMessages().getAdapter()).add(DataReader.getInstance().receiveMessage(context));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
