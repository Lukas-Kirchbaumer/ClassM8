package com.example.laubi.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.backend.Dto.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Anwender on 26.01.2017.
 */

public class ChatArrayAdapter extends ArrayAdapter<Message> {

    private final Context context;
    private final ArrayList<Message> itemsArrayList;

    public ChatArrayAdapter(Context context, ArrayList<Message> itemsArrayList) {

        super(context, R.layout.row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.row, parent, false);

        // 3. Get the two text view from the rowView
        String pattern = "HH:mm";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        TextView labelView = (TextView) rowView.findViewById(R.id.header);
        TextView valueView = (TextView) rowView.findViewById(R.id.content);
        String info = itemsArrayList.get(position).getSender() + "   " +
                      format.format(itemsArrayList.get(position).getTimestamp());
        labelView.setText(info);
        valueView.setText(itemsArrayList.get(position).getMessage());

        // 5. retrn rowView
        return rowView;
    }

}
