package com.example.backend.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by laubi on 3/31/2017.
 */

public class MessageDbHandler  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "messages.db";
    private static final int DATABASE_VERSION = 5;

    public static final String TABLE_MESSAGES = "messages";

    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_SENDER = "sender";
    public static final String COLUMN_DATETIME = "datetime";
    public static final String COLUMN_USER = "user";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_MESSAGES + " (" +
                    COLUMN_CONTENT + " TEXT, " +
                    COLUMN_SENDER + " TEXT, " +
                    COLUMN_DATETIME + " TEXT, " +
                    COLUMN_USER + " INTEGER" +
                    ")";


    public MessageDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL(TABLE_CREATE);
    }

}

