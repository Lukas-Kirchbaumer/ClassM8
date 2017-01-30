package com.example.laubi.myapplication;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.backend.Database;
import com.example.backend.Dto.Chat;
import com.example.backend.Dto.Message;
import com.example.backend.Interfaces.DataReader;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;

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

                //if (isNetworkAvailable(params[0])) {

                    //get new messages


                    Chat.getInstance().addMultipleMessages(DataReader.getInstance().receiveMessage());
                    publishProgress();

               // }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onProgressUpdate(ArrayList<Message>... values){

        Activity ac = mWeakActivity.get();


        ListView lvMessages = (ListView) ac.findViewById(R.id.lvMessages);
        ChatArrayAdapter caa = (ChatArrayAdapter) lvMessages.getAdapter();
        caa.notifyDataSetChanged();
        lvMessages.setSelection(caa.getCount() - 1);

    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
