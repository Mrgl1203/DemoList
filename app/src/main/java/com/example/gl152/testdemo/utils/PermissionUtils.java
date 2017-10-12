package com.example.gl152.testdemo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.gl152.testdemo.bean.PermissionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gl152 on 2017/5/16.
 */

public class PermissionUtils {
    private Activity context;

    public PermissionUtils(Activity activity) {
        this.context = activity;
    }

    PermissionModel[] permissonmodels = new PermissionModel[]{new PermissionModel(Manifest.permission.WRITE_EXTERNAL_STORAGE, "需要sd卡权限方便我们写入缓存文件", "sd卡写入", RITE_EXTERNAL_STORAGE_CODE),
            new PermissionModel(Manifest.permission.CAMERA, "需要摄像头权限方便我们进行媒体操作", "摄像头", CAMERA_CODE), new PermissionModel(Manifest.permission.READ_CONTACTS, "需要通讯录权限方便我们进行朋友圈设置", "通讯录", READ_CONTACTS_CODE),
            new PermissionModel(Manifest.permission.RECORD_AUDIO, "方便我们录制视频", "录像", RECORD_AUDIO)
    };

    public static final int RITE_EXTERNAL_STORAGE_CODE = 100;
    public static final int CAMERA_CODE = 101;
    public static final int READ_CONTACTS_CODE = 102;
    public static final int PERMISSIONCODE = 104;
    public static final int BACK_SETTING = 103;
    public static final int RECORD_AUDIO = 105;

    private static final String TAG = "PermissionUtils";

    public List<PermissionModel> findDeniedPermissions() {
        List<PermissionModel> denylist = new ArrayList<>();
        for (PermissionModel permissionModel : permissonmodels) {
            if (ContextCompat.checkSelfPermission(context, permissionModel.getPermission()) != PackageManager.PERMISSION_GRANTED) {
                denylist.add(permissionModel);
            }
        }
        return denylist;
    }

    public boolean hasPermission() {
        List<PermissionModel> denylist = findDeniedPermissions();
        if (denylist.size() > 0) {
            for (PermissionModel permissionModel : denylist) {
                Log.e(TAG, "hasPermission: ----------" + permissionModel.getPermission());
                ActivityCompat.requestPermissions(context, new String[]{permissionModel.getPermission()}, permissionModel.getPermissioncode());
            }
            return false;
        } else {
            return true;
        }
    }

    public String findPermissionsReason(String permission) {
        for (PermissionModel permissionModel : permissonmodels) {
            if (permission.equals(permissionModel.getPermission())) {
                return permissionModel.getRequestreason();
            }
        }
        return null;
    }

    public String findPermissionsName(String permission) {
        for (PermissionModel permissionModel : permissonmodels) {
            if (permissionModel.getPermission().equals(permission)) {
                return permissionModel.getPermissionname();
            }
        }
        return null;
    }
}
