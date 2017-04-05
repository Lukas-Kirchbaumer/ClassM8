package com.example.laubi.myapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.backend.Database.Database;
import com.example.backend.Dto.Emote;
import com.example.backend.Dto.Message;
import com.example.laubi.myapplication.Activities.HomeActivity;
import com.example.laubi.myapplication.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
                (format.format(itemsArrayList.get(position).getDateTime()));
        String currUser = Database.getInstance().getCurrentMate().getFirstname() + " " + Database.getInstance().getCurrentMate().getLastname();
        if (itemsArrayList.get(position).getSender().equals(currUser)) {
            info = "You   " + format.format(itemsArrayList.get(position).getDateTime());
        }

        labelView.setText(info);

        SpannableStringBuilder builtString = buildMessage(itemsArrayList.get(position).getContent());

        valueView.setText(builtString);

        //   valueView.setText(itemsArrayList.get(position).getContent());
        try {
            this.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 5. retrn rowView
        return rowView;
    }

    private SpannableStringBuilder buildMessage(String content) {

        SpannableStringBuilder builder = new SpannableStringBuilder();
        System.out.println(content);
        if (content.contains("§")) {
            int firstindex = content.indexOf("§");

            String partTillFirst = content.substring(0, firstindex);
            builder.append(partTillFirst);

            System.out.println("first part:" + partTillFirst);

            while (firstindex != -1) {
                System.out.println(firstindex);
                int secondindex = content.indexOf("§", firstindex + 1) + 1;

                System.out.println(secondindex);

                if (secondindex != -1) {

                    boolean found = false;

                    String curcont = content.substring(firstindex + 1, secondindex - 1);
                    System.out.println("cur part:" + curcont);
                    for (Emote e : Database.getInstance().getEmojis()) {
                        if (e.getShortString().equals(curcont)) {
                            System.out.println("found emote");
                            curcont = e.getFileName();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        builder.append(content.substring(firstindex, secondindex));
                    } else {
                        builder.append(" ");
                        java.io.File nf = new java.io.File(context.getFilesDir(), curcont);

                        Bitmap bitmap = BitmapFactory.decodeFile(nf.getAbsolutePath());
                        Bitmap changeableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

                        float ratio = Math.min(
                                (float) 32 / changeableBitmap.getWidth(),
                                (float) 32 / changeableBitmap.getHeight());
                        int width = Math.round((float) ratio * changeableBitmap.getWidth());
                        int height = Math.round((float) ratio * changeableBitmap.getHeight());

                        Bitmap newBitmap = Bitmap.createScaledBitmap(changeableBitmap, width,
                                height, true);

                        builder.setSpan(new ImageSpan((Activity) context, newBitmap), builder.length() - 1, builder.length(), 0);
                    }
                    firstindex = content.indexOf("§", secondindex);
                }
            }
        } else {
            builder.append(content);
            System.out.println(content);
        }
        System.out.println(content);
        return builder;
    }

    @Override
    public void notifyDataSetChanged() {
        try {
            HomeActivity.getUpdating().acquire();
            super.notifyDataSetChanged();
            HomeActivity.getUpdating().release();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void add(ArrayList<Message> object) {
        itemsArrayList.addAll(object);
        this.notifyDataSetChanged();
    }
}
