package com.example.gl152.testdemo.activity;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.bean.IntentString;

public class WebViewActivity extends BaseActivity {
    private WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void init() {

        String url = getIntent().getStringExtra(IntentString.URL);
//        webView = (WebView) findViewById(R.id.webView);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl(url);
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
        finish();
    }


}
