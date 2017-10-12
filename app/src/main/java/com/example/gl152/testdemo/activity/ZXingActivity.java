package com.example.gl152.testdemo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.bean.IntentString;
import com.google.zxing.WriterException;
import com.zxing.activity.CaptureActivity;
import com.zxing.encoding.EncodingHandler;

import butterknife.BindView;
import butterknife.OnClick;


public class ZXingActivity extends BaseActivity {

    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.but_ivcode)
    Button butIvcode;
    @BindView(R.id.but_docode)
    Button butDocode;
    @BindView(R.id.ivcode)
    ImageView ivcode;

    public static final int ZXING_CODE = 200;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zxing;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.but_ivcode, R.id.but_docode})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.but_ivcode:
                String codestring = et.getText().toString().trim();
                try {
                    if (!TextUtils.isEmpty(codestring)) {
                        Bitmap bitmap = EncodingHandler.createQRCode(codestring, 400);
                        ivcode.setImageBitmap(bitmap);
                    } else {
                        showToast("二维码字符串不能为空");
                    }

                } catch (WriterException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.but_docode:
                Intent intent = new Intent(ZXingActivity.this, CaptureActivity.class);
                startActivityForResult(intent, ZXING_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ZXING_CODE) {
            if (data != null) {
                String url = data.getStringExtra("result");
                if (!TextUtils.isEmpty(url)) {
                    showToast(url);
                    Intent intent = new Intent(ZXingActivity.this, WebViewActivity.class);
                    intent.putExtra(IntentString.URL, url);
                    startActivity(intent);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
