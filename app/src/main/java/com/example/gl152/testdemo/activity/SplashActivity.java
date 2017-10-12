package com.example.gl152.testdemo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.utils.PermissionUtils;

public class SplashActivity extends BaseActivity {
    Handler handler = new Handler();
    PermissionUtils permissionUtils;
    private static final String TAG = "SplashActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        permissionUtils = new PermissionUtils(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                completePermission();
            }
        }, 1000);
    }

    public void completePermission() {
        if (permissionUtils.hasPermission()) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.CAMERA_CODE:
            case PermissionUtils.READ_CONTACTS_CODE:
            case PermissionUtils.RITE_EXTERNAL_STORAGE_CODE:
            case PermissionUtils.RECORD_AUDIO:
                // 如果用户不允许，我们视情况发起二次请求或者引导用户到应用页面手动打开
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        Log.e(TAG, "onRequestPermissionsResult: ----------不同意");
                        // 二次请求，表现为：以前请求过这个权限，但是用户拒接了
                        // 在二次请求的时候，会有一个“不再提示的”checkbox
                        // 因此这里需要给用户解释一下我们为什么需要这个权限，否则用户可能会永久不在激活这个申请
                        // 方便用户理解我们为什么需要这个权限
                        if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissions[i])) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                            builder.setMessage(permissionUtils.findPermissionsReason(permissions[i])).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    SplashActivity.this.finish();
                                }
                            });
                            builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    permissionUtils.hasPermission();
                                }
                            });
                            builder.setCancelable(false);
                            builder.show();
                        }
                        // 到这里就表示已经是第3+次请求，而且此时用户已经永久拒绝了，这个时候，我们引导用户到应用权限页面，让用户自己手动打开
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                            builder.setMessage("请去设置中打开" + permissionUtils.findPermissionsName(permissions[i]) + "权限");
                            builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent =
                                            new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    startActivityForResult(intent, PermissionUtils.BACK_SETTING);
                                }
                            });
                            builder.setCancelable(false);
                            builder.show();
                        }
                    } else {
                        Log.e(TAG, "onRequestPermissionsResult: ----------同意");
                        completePermission();
                    }
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PermissionUtils.BACK_SETTING:
                completePermission();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
