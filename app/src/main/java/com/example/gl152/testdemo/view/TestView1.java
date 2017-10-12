package com.example.gl152.testdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by gl152 on 2017/4/13.
 */

public class TestView1 extends View {
    public TestView1(Context context) {
        super(context);
    }

    public TestView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.YELLOW);
        //1.2为左上角坐标   3.4为右下角坐标
        canvas.drawRect(new Rect(100, 100, 400, 400), paint);
        //文字的话为左下角坐标
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        canvas.drawText("自定义view", 100, 100, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wmode = MeasureSpec.getMode(widthMeasureSpec);
        int wsize = MeasureSpec.getSize(widthMeasureSpec);
        switch (wmode) {
            case MeasureSpec.EXACTLY:
                widthMeasureSpec = wsize;
                break;
            case MeasureSpec.AT_MOST:

                break;
        }

        int hmode = MeasureSpec.getMode(heightMeasureSpec);
        int hsize = MeasureSpec.getSize(heightMeasureSpec);
        switch (wmode) {
            case MeasureSpec.EXACTLY:
                heightMeasureSpec = hsize;
                break;
            case MeasureSpec.AT_MOST:

                break;
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }
}
