package com.example.backend.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by laubi on 4/1/2017.
 */

public class EmoteDbHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "emotes.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_EMOTES = "emotes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SIGN = "sign";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_FILE = "file";
    public static final String COLUMN_SCHOOLCLASS = "schoolclass";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_EMOTES + " (" +
                    COLUMN_ID + " INTEGER, " +
                    COLUMN_SIGN + " TEXT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_FILE + " BLOB, " +
                    COLUMN_SCHOOLCLASS + " INTEGER" +
                    ")";


    public EmoteDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMOTES);
        db.execSQL(TABLE_CREATE);
    }
}