package com.example.gl152.testdemo.activity;

import android.view.View;
import android.widget.Button;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.utils.SdCardHelper;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class SDCardActivity extends BaseActivity {

    @BindView(R.id.but_sd)
    Button butSd;
    @BindView(R.id.but_save)
    Button butSave;
    @BindView(R.id.but_load)
    Button butLoad;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sdcard;
    }

    @Override
    protected void init() {
    }

    @OnClick({R.id.but_sd, R.id.but_save, R.id.but_load})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.but_sd:
                boolean hasSd = SdCardHelper.isSDCardMounted();
                long allsize = SdCardHelper.getSDCardSize();
                long freesize = SdCardHelper.getSDCardFreeSize();
                showToast("hasSd=" + hasSd + "----allsize=" + allsize + "-----freesize=" + freesize);
                break;
            case R.id.but_save:
                String text = "这是一个文件存储测试";
                try {
                    SdCardHelper.saveFile(text.getBytes(), "test1.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                showToast("点击了save---");
                break;
            case R.id.but_load:

                break;
        }
    }
}
