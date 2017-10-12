package com.example.gl152.testdemo.activity;

import android.os.Bundle;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.view.TestView2;
import com.example.gl152.testdemo.view.TestView4;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DrawViewActivity extends BaseActivity {


    @BindView(R.id.view2)
    TestView2 view2;
    @BindView(R.id.view4)
    TestView4 view4;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_draw_view;
    }

    @Override
    protected void init() {

    }

    @OnClick(R.id.view2)
    public void clearView4() {
        view4.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
