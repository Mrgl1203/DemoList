package com.example.gl152.testdemo.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.gl152.testdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.etusername)
    EditText etusername;
    @BindView(R.id.etpwd)
    EditText etpwd;
    @BindView(R.id.loginbutton)
    Button loginbutton;
    String username, password;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    Handler handler = new Handler();
    private static final String TAG = "MainActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        if (!TextUtils.isEmpty(getGlApplication().getUsername()) && !TextUtils.isEmpty(getGlApplication().getPassword())) {
            etusername.setText(getGlApplication().getUsername());
            etpwd.setText(getGlApplication().getPassword());
            login();
        }
    }

    @OnClick(R.id.loginbutton)
    public void login() {
        progressbar.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                username = etusername.getText().toString().trim();
                password = etpwd.getText().toString().trim();

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    progressbar.setVisibility(View.GONE);
                    getGlApplication().saveLoginInfo(username, password, false);

                    startActivity(new Intent(MainActivity.this, AfterLoginActivity.class));
                    finish();
                } else {
                    progressbar.setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(false);
                    builder.setNegativeButton("OK", null);
                    builder.setMessage("Username or Password can not be empty");
                    builder.show();
                }
            }
        }, 3000);
    }


}
