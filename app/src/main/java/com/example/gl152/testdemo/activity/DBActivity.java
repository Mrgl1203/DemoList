package com.example.gl152.testdemo.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Button;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.bean.PersonBean;
import com.example.gl152.testdemo.utils.DBManager;
import com.example.gl152.testdemo.utils.MyOpenHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DBActivity extends BaseActivity {
    @BindView(R.id.insert)
    Button insert;
    @BindView(R.id.query)
    Button query;
    private MyOpenHelper helper;
    private SQLiteDatabase db;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_db;
    }

    @Override
    protected void init() {
        helper = DBManager.getHelper(this);
        db = helper.getWritableDatabase();
    }

    private void createTable() {
        for (int i = 1; i <= 5; i++) {
            String sql = "insert into " + MyOpenHelper.DBNAME + " values(" + null + ",'顾磊" + i + "',27)";
            db.execSQL(sql);
        }
    }

    @OnClick(R.id.insert)
    public void clickInsert() {
        createTable();
    }

    private static final String TAG = "DBActivity";

    @OnClick(R.id.query)
    public void clickrawQuery() {
//        String sql = "select * from " + MyOpenHelper.DBNAME;
//        Cursor cursor = DBManager.select(db, sql, null);
//        List<PersonBean> list = DBManager.cursorToList(cursor);
//        for (PersonBean personBean : list) {
//            Log.i(TAG, "clickQuery: " + personBean.toString());
//        }

        //queryAPI的方法   ?为占位符，之后String数组的参数会取代
        Cursor cursor = db.query(MyOpenHelper.DBNAME, null, MyOpenHelper.ID + ">?", new String[]{"10"}, null, null, MyOpenHelper.ID + " desc");
        List<PersonBean> list = DBManager.cursorToList(cursor);
        for (PersonBean personBean : list) {
            Log.i(TAG, "clickQuery: " + personBean.toString());
        }
    }


}
