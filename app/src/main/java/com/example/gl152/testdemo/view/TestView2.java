package com.example.gl152.testdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.utils.CommonUtils;

/**
 * Created by gl152 on 2017/4/13.
 */

public class TestView2 extends View {
    private int radius;
    private Bitmap bitmap, fitbitmap;
    private int textsize;
    private Paint paint;
    private String text;
    private int imagewidth, imageheight;
    private static final String TAG = "TestView2";

    public TestView2(Context context) {
        super(context);
    }

    public TestView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TestView2);
        radius = ta.getInteger(R.styleable.TestView2_radius, 100);
        textsize = ta.getDimensionPixelSize(R.styleable.TestView2_textsize, 100);
        int resId = ta.getResourceId(R.styleable.TestView2_image, R.mipmap.ic_launcher);
        bitmap = BitmapFactory.decodeResource(getResources(), resId);
        text = ta.getString(R.styleable.TestView2_text);
        imagewidth = ta.getDimensionPixelSize(R.styleable.TestView2_imagewidth, 0);
        imageheight = ta.getDimensionPixelSize(R.styleable.TestView2_imageheight, 0);
        if (imagewidth != 0 || imageheight != 0) {
            int bw = CommonUtils.dip2px(imagewidth);
            int bh = CommonUtils.dip2px(imageheight);
            fitbitmap = Bitmap.createScaledBitmap(bitmap, bw, bh, true);
            bitmap = fitbitmap;
        }
        ta.recycle();
    }

    /**
     * 1.当默认情况下，自定义控件的宽高，不管设置的是match_parent还是wrap_content，最终显示的宽高都会是填充父控件的宽高（简单来说就是满屏的宽高）
     * 2.如果自定义控件的宽高的宽高设置的为100dp这种精确数字时，最终显示的宽高就是指定的数字宽高
     * 3.如果想要让自定义控件宽高的特点与系统控件一致，需要进行自定义控件的宽高处理
     * 所谓的跟系统控件一致，
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wmode = MeasureSpec.getMode(widthMeasureSpec);
        int wsize = MeasureSpec.getSize(widthMeasureSpec);
        switch (wmode) {
            case MeasureSpec.EXACTLY:
                Log.i(TAG, "onMeasure: 当前模式为精确模式");
                widthMeasureSpec = wsize;
                break;
            case MeasureSpec.AT_MOST:
                Log.i(TAG, "onMeasure: 当前模式为最大值模式");
                //根据内容宽度像素值以及测算模式计算后得到对应的测算值
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(bitmap.getWidth() + radius, MeasureSpec.EXACTLY);
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.i(TAG, "onMeasure: 当前模式为未知模式");
                break;
        }

        int hmode = MeasureSpec.getMode(heightMeasureSpec);
        int hsize = MeasureSpec.getSize(heightMeasureSpec);
        switch (hmode) {
            case MeasureSpec.EXACTLY:
                heightMeasureSpec = hsize;
                break;
            case MeasureSpec.AT_MOST:
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(bitmap.getHeight() + radius, MeasureSpec.EXACTLY);
                break;
        }

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: " + widthMeasureSpec + "  " + heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawBitmap(bitmap, 0, radius, null);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

        canvas.drawCircle(bitmap.getWidth(), radius, radius, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(textsize);

        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int tw = rect.width();
        int th = rect.height();
        canvas.drawText(text, bitmap.getWidth() - tw / 2, radius + th / 2, paint);


    }


}
