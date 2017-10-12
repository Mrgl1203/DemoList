package com.example.gl152.testdemo.activity;

import android.animation.ArgbEvaluator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gl152.testdemo.R;

import butterknife.BindView;


public class ArgActivity extends BaseActivity {
    /**
     * ArgbEvaluator.evaluate(floatfraction, Object startValue, Object endValue);
     * 用于根据一个起始颜色值和一个结束颜色值以及一个偏移量生成一个新的颜色，分分钟实现类似于微信底部栏滑动颜色渐变。
     */
    ArgbEvaluator argbEvaluator;
    @BindView(R.id.chatting_content_iv)
    ImageView chattingContentIv;
    @BindView(R.id.chatting_size_iv)
    TextView chattingSizeIv;
    @BindView(R.id.chatting_length_iv)
    TextView chattingLengthIv;
    @BindView(R.id.chatting_video_data_area)
    LinearLayout chattingVideoDataArea;
    @BindView(R.id.chatting_status_btn)
    ImageView chattingStatusBtn;
    @BindView(R.id.container_status_btn)
    LinearLayout containerStatusBtn;
    @BindView(R.id.chatting_click_area)
    FrameLayout chattingClickArea;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_arg;
    }

    @Override
    protected void init() {
        argbEvaluator = new ArgbEvaluator();
        chattingStatusBtn.setImageResource(R.mipmap.icon_bear);
    }

}
