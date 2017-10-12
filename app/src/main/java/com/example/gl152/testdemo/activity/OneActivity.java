package com.example.gl152.testdemo.activity;

import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gl152.testdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

public class OneActivity extends BaseActivity {
    Dialog dialog;

    @BindView(R.id.dialogbutton)
    Button dialogbutton;
    TextView tvone;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_one;
    }

    @Override
    protected void init() {

    }

    @OnClick(R.id.dialogbutton)
    public void showdialog() {
        dialog = new Dialog(OneActivity.this, R.style.OneDialog);
        View view = LayoutInflater.from(OneActivity.this).inflate(R.layout.dialog_one, null);
        tvone = (TextView) view.findViewById(R.id.tvone);
        tvone.setText("OneActivity");
        dialog.setContentView(view);
        dialog.setCancelable(true);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        dialog.show();

    }

}
