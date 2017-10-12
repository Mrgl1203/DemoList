package com.example.gl152.testdemo.utils;

import common.GlApplication;

/**
 * Created by gl152 on 2017/4/13.
 */

public class CommonUtils {
    public static int dip2px(float dpValue) {
        float scale = GlApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
