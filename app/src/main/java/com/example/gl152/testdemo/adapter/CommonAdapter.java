package com.example.gl152.testdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gl152 on 2017/3/21.
 * 通用适配器
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected List<T> mData;
    protected LayoutInflater inflater;
    protected final int itemlayout;
    protected Context mContext;

    public CommonAdapter(Context context, List<T> list, int itemlayout) {
        mContext = context;
        this.itemlayout = itemlayout;
        mData = new ArrayList<>();
        if (list != null) mData.addAll(list);
        inflater = inflater.from(mContext);
    }

    /**
     * 刷新数据
     */
    public void resetData(List<T> list) {
        mData.clear();
        if (list != null) {
            mData.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * 最多显示多少数据
     */
    public void resetData(List<T> list, int maxsize) {
        mData.clear();
        if (list != null) {
            int max = list.size() > maxsize ? maxsize : list.size();
            for (int i = 0; i < max; i++) {
                mData.add(list.get(i));
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getInstance(mContext, convertView, parent, itemlayout);
        viewHolder.setPosition(position);
        convert(viewHolder, getItem(position));
        return viewHolder.getConverView();
    }

    public abstract void convert(ViewHolder holder, T item);
}
