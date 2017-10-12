package com.example.gl152.testdemo.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by gl152 on 2017/5/15.
 */

public class SdCardHelper {
    private static final String TAG = "SdCardHelper";


    // 以下操作首先都需要检查SD卡是否挂载
    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    //获取sd卡的绝对路径
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }


    //获取sd卡的容量大小
    public static long getSDCardSize() {
        StatFs statFs = new StatFs(getSDCardPath());
        long size = statFs.getBlockSizeLong();//新方法需要API18以上
        long count = statFs.getBlockCountLong();
        return size * count / 1024 / 1024;//size*count为byte单位，/1024为kb单位，再/1024为mb单位
    }

    //获取sd卡的剩余容量
    public static long getSDCardFreeSize() {
        StatFs statFs = new StatFs(getSDCardPath());
        long size = statFs.getBlockSizeLong();
        long freecount = statFs.getAvailableBlocksLong();
        return size * freecount / 1024 / 1024;
    }

    public static final String TEST_DIR = getSDCardPath() + File.separator + "testdemo" + File.separator;

    public static String getTestDir() {
        File file = new File(TEST_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }
    //创建文件要动态申请权限
    public static File saveFile(byte[] bytes, String filename) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        File file = new File(getTestDir(),filename);
        if (!file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        int len = 0;
        byte[] buff = new byte[1024];
        while ((len = bais.read(buff)) != -1) {
            fos.write(buff, 0, len);
        }
        fos.close();
        bais.close();
        return file;
    }


}
