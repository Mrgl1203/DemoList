package com.example.gl152.testdemo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.bean.VideoBean;

/**
 * Created by gl152 on 2017/3/21.
 */

public class VideoAdapter extends CommonAdapter<VideoBean> {

    public VideoAdapter(Context context) {
        super(context, null, R.layout.item_video);
    }

    @Override
    public void convert(ViewHolder holder, VideoBean item) {
        TextView tvVideo = holder.getView(R.id.tvVideo);
        ImageView ivVideo = holder.getView(R.id.ivVideo);
        tvVideo.setText(item.getName());
        if (item.isChecked()) {
            ivVideo.setVisibility(View.VISIBLE);
        } else {
            ivVideo.setVisibility(View.GONE);
        }
    }
}
