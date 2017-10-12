package com.example.gl152.testdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gl152.testdemo.R;

import java.util.List;

/**
 * Created by gl152 on 2017/5/4.
 */

public class logGridAdapter extends BaseAdapter {
    private List<String> data;
    private Context context;
    int position;

    public logGridAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.position = position;
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.log_grid_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(data.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView tv;

        public ViewHolder(View view) {
            tv = (TextView) view.findViewById(R.id.gridtext);
        }
    }

    public int getPosition() {
        return position;
    }
}
