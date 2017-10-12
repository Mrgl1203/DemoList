package com.example.gl152.testdemo.activity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gl152.testdemo.R;

import butterknife.ButterKnife;
import common.GlApplication;

public abstract class BaseActivity extends AppCompatActivity {
    private Toast toast;
    private RelativeLayout top;
    private TextView tvBack, tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initWindow();
        init();
    }

    protected abstract int getLayoutId();

    protected abstract void init();

    //只支持4.4版本以上
    protected void initWindow() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }

    //4.4版本以下的调整title高度
    protected void initTitle(String Title) {
        top = (RelativeLayout) findViewById(R.id.top);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.top_title_heigh));
            top.setLayoutParams(lp);
        }

        tvBack = (TextView) findViewById(R.id.back);
        tvTitle = (TextView) findViewById(R.id.title);
        if (Title == null) {
            Title = "";
        }
        tvTitle.setText(Title);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public GlApplication getGlApplication() {
        return (GlApplication) getApplication();
    }

    public void showToast(String toastmsg) {
        if (toast == null) {
            toast = Toast.makeText(this, toastmsg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(toastmsg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public void showAlert(String Msg, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this).setMessage(Msg)
                .setNegativeButton("OK", listener)
                .setCancelable(false)
                .show();
    }

    public void showAlert(String Msg) {
        new AlertDialog.Builder(this).setMessage(Msg)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }
}
