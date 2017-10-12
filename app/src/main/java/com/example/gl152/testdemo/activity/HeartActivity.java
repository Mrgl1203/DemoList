package com.example.gl152.testdemo.activity;

import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.view.VerticalTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HeartActivity extends BaseActivity {
    private static final String TAG = "HeartActivity";
    @BindView(R.id.but)
    Button but;
    @BindView(R.id.relmain)
    RelativeLayout relmain;
    int[] location = new int[2];
    int screenheight, screenwidth;
    @BindView(R.id.ts)
    TextSwitcher ts;
    private String[] message = new String[]{"18*****1501  中500元\n18*****1501  中500元\n18*****1501  中500元",
            "18*****1501  中400元\n18*****1501  中400元\n18*****1501  中400元",
            "18*****1501  中300元\n18*****1501  中300元\n18*****1501  中300元"};

    Handler handler = new Handler();
    int index;
    List<String> list = new ArrayList<>();

    @BindView(R.id.vts)
    VerticalTextView mSeckillHistory;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_heart;
    }

    @Override
    protected void init() {
        screenwidth = getWindowManager().getDefaultDisplay().getWidth();
        screenheight = getWindowManager().getDefaultDisplay().getHeight();
        Log.e(TAG, "init: screenwidth=" + screenwidth + "--------------screenheight=" + screenheight);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            if (i % 3 == 0) {
                stringBuilder.delete(0, stringBuilder.length());
                stringBuilder.append("xiaoming\t\t188****000" + i);
            } else {
                stringBuilder.append("\nxiaoming\t\t188****000" + i);
                if (i % 3 == 2) {
                    list.add(stringBuilder.toString());
                }
                if (i == 11 - 1) {
                    list.add(stringBuilder.toString());
                }
            }
        }


        ts.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(HeartActivity.this);
                textView.setTextSize(22);
                textView.setMaxLines(4);

                textView.setTextColor(Color.parseColor("#000000"));
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER_VERTICAL;
                textView.setLayoutParams(params);


                return textView;
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                index++;
                ts.setText(list.get(index % message.length));
                handler.postDelayed(this, 4000);
            }
        }, 0);

        tryVerticalTextView();

    }

    private void tryVerticalTextView() {
        mSeckillHistory.setText(13, 0, getResources().getColor(R.color.cWhite));//设置属性
        mSeckillHistory.setTextStillTime(3000);//设置停留时长间隔
        mSeckillHistory.setAnimTime(4000);//设置进入和退出的时间间隔
        mSeckillHistory.setMaxLines(4);
        mSeckillHistory.setTextList((ArrayList<String>) list);
//        mSeckillHistory.next();
        mSeckillHistory.startAutoScroll();
    }

    /**
     * 10-05 16:20:36.473 17602-17602/com.example.gl152.testdemo E/HeartActivity: init: screenwidth=1440--------------screenheight=2560
     * 10-05 16:20:38.797 17602-17602/com.example.gl152.testdemo E/HeartActivity: init: location[0]=513--------------location[1]=2368
     * 10-05 16:20:38.797 17602-17602/com.example.gl152.testdemo E/HeartActivity: init: butwidth=414--------------butheight=192
     * 计算得出结论getLocationOnScreen获取view的左上角相对于屏幕的位置坐标
     */
    @OnClick(R.id.but)
    public void sendHeart(View view) {
        but.getLocationOnScreen(location);
        Log.e(TAG, "init: location[0]=" + location[0] + "--------------location[1]=" + location[1]);
        Log.e(TAG, "init: butwidth=" + but.getWidth() + "--------------butheight=" + but.getHeight());
        final ImageView iv = new ImageView(HeartActivity.this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        iv.setLayoutParams(layoutParams);
        iv.setImageResource(R.mipmap.icon_heart_red);
        relmain.addView(iv);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.heart_anim);
        iv.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv.clearAnimation();
                relmain.removeView(iv);
                iv.setImageDrawable(null);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


}
