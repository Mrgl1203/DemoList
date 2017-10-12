package com.example.gl152.testdemo.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by gl152 on 2017/6/5.
 */

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private Camera mCamera;
    private SurfaceHolder holder;
    public static final int BackFacing = Camera.CameraInfo.CAMERA_FACING_BACK;//后置摄像头
    public static final int FrontFacing = Camera.CameraInfo.CAMERA_FACING_FRONT;//前置摄像头
    private static final String TAG = "CameraSurfaceView";
    int cameraId = -1;

    public CameraSurfaceView(Context context) {
        super(context);
        init();
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        holder = getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.TRANSPARENT);
        holder.setKeepScreenOn(true);     // 设置该组件不会让屏幕自动关闭
        if (mCamera == null) {
            if (Camera.getNumberOfCameras() == 2) {//如果有两个摄像头开后置
                mCamera = Camera.open(FrontFacing);
                cameraId = FrontFacing;
            } else {
                mCamera = Camera.open(BackFacing);
                cameraId = BackFacing;
            }//开启相机获取对象，可以传cameraId0,1
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(TAG, "surfaceCreated: --------------------");

        try {
            mCamera.setPreviewDisplay(holder);//整个程序的核心，相机预览的内容放在 holder
            mCamera.startPreview();//该方法只有相机开启后才能调用
            mCamera.setDisplayOrientation(90);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "surfaceChanged: ---------------------------");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "surfaceDestroyed: -------------------------------");
        if (mCamera != null) {
            holder.removeCallback(this);
            mCamera.setPreviewCallback(null);
            mCamera.release();//释放相机资源
            mCamera = null;
        }
    }

    public Camera changeCamera() {
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
        if (cameraId == BackFacing) {
            mCamera = Camera.open(FrontFacing);
            cameraId = FrontFacing;
        } else if (cameraId == FrontFacing) {
            mCamera = Camera.open(BackFacing);
            cameraId = BackFacing;
        }
        try {
            if (mCamera != null) {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
                mCamera.setDisplayOrientation(90);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mCamera;
    }

    public Camera getCamera() {
        return mCamera;
    }

    public int getCameraFaceing() {
        return cameraId;
    }

}
