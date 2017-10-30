package com.example.exit.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by EXIT on 10/16/2017.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "Messages.db";
    private static int VERSION_NUM = 1;

    public static final String TABLE_NAME = "entry";
    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "message";

//    public static String[] MESSAGE_FIELDS = new String[] {
//            KEY_ID,
//            KEY_MESSAGE,
//    };


    private static String SQL_CREATE_TABLE =
            "CREATE TABLE" + TABLE_NAME + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_MESSAGE + " TEXT" + ")";

    public ChatDatabaseHelper (Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_TABLE);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
        Log.i("ChatDatabaseHelper","Calling onUpgrade, oldVersion=" + oldVer + "newVersion=" +newVer);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(SQL_CREATE_TABLE);
        Log.i("ChatDatabaseHelper","Calling onDowngrade, oldVersion=" + oldVer + "newVersion=" +newVer);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.i("Database ", "onOpen was called");
    }

}
