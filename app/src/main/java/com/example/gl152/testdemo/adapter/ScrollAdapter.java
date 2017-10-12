package com.example.gl152.testdemo.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.gl152.testdemo.R;

import java.util.List;

/**
 * Created by gl152 on 2017/7/4.
 */

public class ScrollAdapter extends CommonAdapter<String> {


    public ScrollAdapter(Context context, List<String> list) {
        super(context, list, R.layout.scrollitem);
    }

    @Override
    public void convert(ViewHolder holder, String item) {
        TextView tv = holder.getView(R.id.tvScroll);
        tv.setText(item);
    }
}
