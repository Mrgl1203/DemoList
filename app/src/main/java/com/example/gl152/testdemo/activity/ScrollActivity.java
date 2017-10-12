package com.example.gl152.testdemo.activity;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.adapter.ScrollAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ScrollActivity extends BaseActivity {


    @BindView(R.id.lv)
    ListView lv;
    View listHead;

    List<String> data = new ArrayList<>();
    @BindView(R.id.tvTxt)
    TextView tvTxt;
    private ScrollAdapter scrollAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scroll;
    }

    @Override
    protected void init() {
//        listHead = LayoutInflater.from(this).inflate(R.layout.scrollhead, null);
//        lv.addHeaderView(listHead);
        initData();
        scrollAdapter = new ScrollAdapter(this, data);
        lv.setAdapter(scrollAdapter);
        tvTxt.setText("daasdsada\n");
        tvTxt.append("GL");
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            data.add("第" + i + "条数据");
        }
    }


}
