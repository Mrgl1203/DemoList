package com.example.gl152.testdemo.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gl152 on 2017/3/21.
 * 通用ViewHolder
 */

public class ViewHolder {
    private Context mContext;
    private View mConverView;
    private final SparseArray<View> mViews;//安卓专属，类似map集合，效率更高但是key值只能存储int
    private int position;

    private ViewHolder(Context context, ViewGroup parent, int layout) {
        mContext = context;
        mViews = new SparseArray<>();
        mConverView = LayoutInflater.from(context).inflate(layout, parent, false);
        mConverView.setTag(this);
    }

    public static ViewHolder getInstance(Context context, View converView, ViewGroup parent, int layout) {
        if (converView == null) {
            return new ViewHolder(context, parent, layout);
        } else {
            return (ViewHolder) converView.getTag();
        }

    }

    public View getConverView() {
        return mConverView;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public <T extends View> T getView(int viewID) {
        View view = mViews.get(viewID);
        if (view == null) {
            view = mConverView.findViewById(viewID);
            mViews.put(viewID, view);
        }
        return (T) view;
    }
}
