package com.example.gl152.testdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.example.gl152.testdemo.R;

/**
 * Created by gl152 on 2017/4/13.
 */

public class TestView3 extends View {

    private Bitmap bit;

    private int startX = 0;

    private int pointX;
    private boolean flag = true;

    public TestView3(Context context) {
        super(context);
        init();
    }

    public TestView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private Handler handler = new Handler();

    private void init() {
        bit = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        pointX = getResources().getDisplayMetrics().widthPixels - bit.getWidth();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (flag) {
                    startX -= 1;
                    if (startX <= pointX) {
                        flag = false;
                    }
                } else {
                    startX += 1;
                    if (startX >= 0) {
                        flag = true;
                    }
                }

                postInvalidate();
                handler.postDelayed(this, 10);

            }
        }, 1000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bit, startX, 0, null);
    }
}
