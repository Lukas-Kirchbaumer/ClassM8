package com.example.laubi.myapplication.Polling;

import android.app.Activity;
import android.databinding.ObservableList;
import android.widget.ListView;

import com.example.laubi.myapplication.Adapters.ChatArrayAdapter;
import com.example.laubi.myapplication.R;
import com.example.laubi.myapplication.Activities.TestHomeActivity;

import java.lang.ref.WeakReference;

/**
 * Created by laubi on 2/1/2017.
 */

public class OnItemChangedListener extends ObservableList.OnListChangedCallback {

    TestHomeActivity activity;

    public OnItemChangedListener(WeakReference<Activity> activity) {
        this.activity = (TestHomeActivity) activity.get();
    }

    @Override
    public void onChanged(ObservableList sender) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    TestHomeActivity.chatSema.acquire();
                    ListView lvMessages = (ListView) activity.findViewById(R.id.lvMessages);
                    ChatArrayAdapter caa = (ChatArrayAdapter) lvMessages.getAdapter();
                    caa.notifyDataSetChanged();
                    lvMessages.setSelection(caa.getCount() - 1);
                    TestHomeActivity.chatSema.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {

    }

    @Override
    public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {

    }

    @Override
    public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {

    }

    @Override
    public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {

    }
}
