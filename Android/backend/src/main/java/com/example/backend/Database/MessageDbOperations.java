package com.example.backend.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.backend.Dto.Message;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laubi on 3/31/2017.
 */

public class MessageDbOperations{
    public static final String LOGTAG = "MSG_MNGMNT_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            MessageDbHandler.COLUMN_DATETIME,
            MessageDbHandler.COLUMN_CONTENT,
            MessageDbHandler.COLUMN_SENDER,
            MessageDbHandler.COLUMN_USER

    };

    public MessageDbOperations(Context context){
        dbhandler = new MessageDbHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();


    }
    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    public Message addMessage(Message message, int userid){
        try {
            ContentValues values = new ContentValues();
            Timestamp timestamp = new Timestamp(message.getDateTime().getTime());

            System.out.println(timestamp);

            values.put(MessageDbHandler.COLUMN_DATETIME, timestamp.toString());
            values.put(MessageDbHandler.COLUMN_CONTENT, message.getContent());
            values.put(MessageDbHandler.COLUMN_SENDER, message.getSender());
            values.put(MessageDbHandler.COLUMN_USER, userid);
            database.insert(MessageDbHandler.TABLE_MESSAGES, null, values);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return message;
    }

    public String getMaxDate(int userid) {
        Timestamp t;
        try {
            Cursor cursor = database.rawQuery("select " + MessageDbHandler.COLUMN_DATETIME + " from " + MessageDbHandler.TABLE_MESSAGES + " where " + MessageDbHandler.COLUMN_USER + " = " + userid + " order by " + MessageDbHandler.COLUMN_DATETIME + " desc limit 1", null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            System.out.println(cursor.getString(0));
            t = Timestamp.valueOf(cursor.getString(0));
        }catch (IllegalArgumentException iae){
            iae.printStackTrace();
            t = new Timestamp(0);
            System.out.println(t.toString());
        } catch (CursorIndexOutOfBoundsException iae){
        iae.printStackTrace();
        t = new Timestamp(0);
        System.out.println(t.toString());
    }
        System.out.println(t.toString());
        System.out.println(t.toString().length());
        String ret = t.toString();
        if(ret.length() < 23)
        {
            if(ret.length()<22){
                ret = ret + "00";
            }else{
                ret = ret+ "0";
            }
        }
        return t.toString();
    }

    public ArrayList<Message> getAllMessagesOfUser(int userid) {

        Cursor cursor = database.query(MessageDbHandler.TABLE_MESSAGES,allColumns,MessageDbHandler.COLUMN_USER + "=" + userid,null,null, null, null);

        ArrayList<Message> messages = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Message message = new Message();
                Timestamp t = Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(MessageDbHandler.COLUMN_DATETIME)));
                message.setDateTime(t);
                message.setContent(cursor.getString(cursor.getColumnIndex(MessageDbHandler.COLUMN_CONTENT)));
                message.setSender(cursor.getString(cursor.getColumnIndex(MessageDbHandler.COLUMN_SENDER)));
                messages.add(message);
            }
        }
        return messages;
    }

    public void size(int id) {
        Cursor cursor = database.rawQuery("select count(*) from " + MessageDbHandler.TABLE_MESSAGES + " where " + MessageDbHandler.COLUMN_USER + " = " + Database.getInstance().getCurrentMate().getId(), null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        System.out.println(cursor.getInt(0));
    }
    public void delete() {
        Cursor cursor = database.rawQuery("delete from " + MessageDbHandler.TABLE_MESSAGES, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        //System.out.println(cursor.getInt(0));
    }

}
