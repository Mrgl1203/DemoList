package com.example.gl152.testdemo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.asynctask.PicDownloadAsyncTask;
import com.example.gl152.testdemo.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NextPageActivity extends BaseActivity {

    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.but_load)
    Button butLoad;
    @BindView(R.id.menulv)
    ListView menulv;

    List<String> data = new ArrayList<>();
    ArrayAdapter<String> adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_next_page;
    }

    @Override
    protected void init() {
        initData();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        menulv.setAdapter(adapter);
//         对ListView注册上下文菜单
        registerForContextMenu(menulv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // 创建ContextMenu，将我们在res/menu文件夹下定义的xml文件加载进来
        getMenuInflater().inflate(R.menu.menu_list, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        if (item.getItemId() == R.id.action_settings) {
            showToast(data.get(index)+"---设置按钮被点击");
        } else if (item.getItemId() == R.id.delete) {
            data.remove(index);
            adapter.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            data.add("这是第" + i + "条数据");
        }
    }

    @OnClick({R.id.but_load})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.but_load:
                PicDownloadAsyncTask picDownloadAsyncTask = new PicDownloadAsyncTask(this);
                picDownloadAsyncTask.execute(UrlUtils.picdownload);
                picDownloadAsyncTask.setCompleteDownlaodListener(new PicDownloadAsyncTask.CompleteDownlaod() {
                    @Override
                    public void completeDownload(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        iv.setImageBitmap(bitmap);
                    }
                });
                break;
        }
    }


}
