package com.example.gl152.testdemo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gl152 on 2017/7/12.
 */

public class MyOpenHelper extends SQLiteOpenHelper {


    public static final String DBNAME = "test_db";
    public static final int VERSION = 1;
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String AGE = "age";

    public MyOpenHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + DBNAME + "(" + ID + " Integer primary key autoincrement," + NAME + " varchar," + AGE + " Integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
