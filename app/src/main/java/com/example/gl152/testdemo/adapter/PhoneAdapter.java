package com.example.gl152.testdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.bean.PhoneBean;

import java.util.List;

/**
 * Created by gl152 on 2017/5/11.
 */

public class PhoneAdapter extends BaseAdapter {
    private List<PhoneBean> data;
    private Context context;
    private LayoutInflater inflater;

    public PhoneAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<PhoneBean> data) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.phone_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(data.get(position).getDisplayname());
        holder.tvNumber.setText(data.get(position).getPhoneNumber());
        return convertView;
    }

    class ViewHolder {
        TextView tvName;
        TextView tvNumber;

        public ViewHolder(View view) {
            this.tvName = (TextView) view.findViewById(R.id.tvName);
            this.tvNumber = (TextView) view.findViewById(R.id.tvNumber);
        }
    }
}
