package com.example.gl152.testdemo.activity;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.adapter.VideoAdapter;
import com.example.gl152.testdemo.bean.VideoBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.activity_video)
    RelativeLayout activityVideo;
    VideoAdapter adapter;
    List<VideoBean> data = new ArrayList<>();
    private static final String TAG = "SearchActivity";
    @BindView(R.id.searchView)
    SearchView searchView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void init() {
        initData();
        Class<?> c = searchView.getClass();
        try {
            Field f = c.getDeclaredField("mSearchPlate");//通过反射，获得类对象的一个属性对象
            f.setAccessible(true);//设置此私有属性是可访问的
            View v = (View) f.get(searchView);//获得属性的值
            v.setBackgroundResource(android.R.color.transparent);//设置此view的背景
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter = new VideoAdapter(this);
        adapter.resetData(data);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showToast(data.get(position).getName());
                VideoBean videoBean = data.get(position);
                Log.i(TAG, "onItemSelected: ----" + videoBean.isChecked());
                if (videoBean.isChecked()) {
                    videoBean.setChecked(false);
                } else {
                    videoBean.setChecked(true);
                }
                adapter.notifyDataSetChanged();
            }
        });

        lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initSearch();

    }

    private void initSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    List list = new ArrayList();
                    for (VideoBean videobean : data) {
                        if (videobean.getName().toLowerCase().contains(query)) {
                            list.add(videobean);
                        }
                    }
                    if (list.size() > 0) {
                        adapter.resetData(list);
                    } else {

                    }

                }else {
                    adapter.resetData(data);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    List list = new ArrayList();
                    for (VideoBean videobean : data) {
                        if (videobean.getName().toLowerCase().contains(newText)) {
                            list.add(videobean);
                        }
                    }
                    if (list.size() > 0) {
                        adapter.resetData(list);
                    } else {
                        list.add(new VideoBean("搜索无结果", false));
                        adapter.resetData(list);
                    }
                }else {
                    adapter.resetData(data);
                }
                return false;
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            VideoBean videoBean = new VideoBean(i + "", false);
            data.add(videoBean);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
