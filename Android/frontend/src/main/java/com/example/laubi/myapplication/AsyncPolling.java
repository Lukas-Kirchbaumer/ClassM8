package com.example.laubi.myapplication;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.backend.Database;
import com.example.backend.Dto.Message;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Anwender on 25.01.2017.
 */

public class AsyncPolling extends AsyncTask<Context, ArrayList<Message>, Void> {


    WeakReference<Activity> mWeakActivity;

    public AsyncPolling(Activity activity) {
        mWeakActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected Void doInBackground(Context... params) {
        while(true){
            try {
                Thread.sleep(5000);

                if (isNetworkAvailable(params[0])) {
                    //Todo get new messages

                    ArrayList<Message> al = new ArrayList<Message>();

                    publishProgress(al);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onProgressUpdate(ArrayList<Message>... values){

        Activity ac = mWeakActivity.get();

        ListView lvMessages = (ListView) ac.findViewById(R.id.lvMessages);
        lvMessages.setAdapter(null);
        ArrayAdapter messagesArrayAdapter = new ArrayAdapter(mWeakActivity.get(),
                android.R.layout.simple_list_item_1, values[0]);
        lvMessages.setAdapter(messagesArrayAdapter);
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
