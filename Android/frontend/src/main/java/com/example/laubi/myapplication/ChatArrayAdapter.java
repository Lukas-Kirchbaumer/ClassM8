package com.example.laubi.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.backend.Database.Database;
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
                      (itemsArrayList.get(position).getDateTime());
        String currUser = Database.getInstance().getCurrentMate().getFirstname() + " " + Database.getInstance().getCurrentMate().getLastname();
        if(itemsArrayList.get(position).getSender().equals(currUser)){
            info =  "You   " + format.format(itemsArrayList.get(position).getDateTime());
        }

        labelView.setText(info);
        valueView.setText(itemsArrayList.get(position).getContent());

        // 5. retrn rowView
        return rowView;
    }

}
