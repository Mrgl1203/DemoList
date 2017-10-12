package com.example.gl152.testdemo.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gl152.testdemo.bean.PersonBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gl152 on 2017/7/12.
 */

public class DBManager {
    private static MyOpenHelper helper;

    public static MyOpenHelper getHelper(Context context) {
        if (helper == null) {
            synchronized (DBManager.class) {
                if (helper == null) {
                    helper = new MyOpenHelper(context);
                }
            }
        }
        return helper;
    }

    public static Cursor select(SQLiteDatabase db, String sql, String[] selectionArgs) {
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(sql, selectionArgs);
        }
        return cursor;
    }


    public static List<PersonBean> cursorToList(Cursor cursor) {
        List<PersonBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(MyOpenHelper.ID));
            String name = cursor.getString(cursor.getColumnIndex(MyOpenHelper.NAME));
            int age = cursor.getInt(cursor.getColumnIndex(MyOpenHelper.AGE));
            PersonBean personBean = new PersonBean(id, name, age);
            list.add(personBean);
        }
        return list;
    }
}
