package com.example.backend.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.backend.Dto.Message;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laubi on 4/1/2017.
 */

public class EmoteDbOperations{
    public static final String LOGTAG = "MSG_MNGMNT_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {


    };

    public EmoteDbOperations(Context context){
        dbhandler = new EmoteDbHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();


    }
    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    public List<Message> getAllMessagesOfUser(int userid) {
/*
        Cursor cursor = database.query(EmoteDbHandler.TABLE_MESSAGES,allColumns,EmoteDbHandler.COLUMN_USER + "=" + userid,null,null, null, null);

        List<Message> messages = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Message message = new Message();
                Timestamp t = Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(EmoteDbHandler.COLUMN_DATETIME)));
                message.setDateTime(t);
                message.setContent(cursor.getString(cursor.getColumnIndex(EmoteDbHandler.COLUMN_CONTENT)));
                message.setSender(cursor.getString(cursor.getColumnIndex(EmoteDbHandler.COLUMN_SENDER)));
                messages.add(message);
            }
        }

        return null;
    }

    try {
        ContentValues values = new ContentValues();
        Timestamp timestamp = new Timestamp(message.getDateTime().getTime());

        System.out.println(timestamp);

        values.put(EmoteDbHandler.COLUMN_DATETIME, timestamp.toString());
        values.put(EmoteDbHandler.COLUMN_CONTENT, message.getContent());
        values.put(EmoteDbHandler.COLUMN_SENDER, message.getSender());
        values.put(EmoteDbHandler.COLUMN_USER, userid);
        database.insert(EmoteDbHandler.TABLE_MESSAGES, null, values);
    }catch (Exception e)
    {
        e.printStackTrace();
    }
    */
        return null;
    }
}
