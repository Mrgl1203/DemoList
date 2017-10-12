package com.example.gl152.testdemo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.bean.IntentString;
import com.example.gl152.testdemo.view.CameraSurfaceView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecoderVideoActivity extends BaseActivity {

    @BindView(R.id.chronometer)
    Chronometer chronometer;
    @BindView(R.id.relrec)
    RelativeLayout relrec;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.ivRec)
    ImageView ivRec;
    @BindView(R.id.tvCancel)
    TextView tvCancel;
    @BindView(R.id.surface)
    CameraSurfaceView surface;
    MediaRecorder mRecorder;
    Camera mCamera;
    String path;
    int progress;
    boolean isRec = false;
    //    Timer progressTimer;
//    TimerTask progressTimerTask;
    private static final String TAG = "RecoderVideoActivity";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    stopRec();
                    break;
                case 2:
                    progress += 2;
                    progressbar.setProgress(progress);
                    break;
            }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(2);
            Log.i(TAG, "run: ----------------------" + progress);
            if (progress >= progressbar.getMax()) {
                handler.sendEmptyMessage(1);
            }
            handler.postDelayed(this, 10);
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_recoder_video;
    }

    @Override
    protected void init() {
        mCamera = surface.getCamera();
        progressbar.setMax(1000);
        chronometer.setFormat("计时：%s");
    }


    private void startRec() {

        showToast("开始录制视频！");
        startprogress();
        if (mRecorder == null) {
            mRecorder = new MediaRecorder(); // 创建MediaRecorder
        }

        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.unlock();
            mRecorder.setCamera(mCamera);
        }
        try {
            // 设置音频采集方式
            mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
            //设置视频的采集方式
            mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            //设置文件的输出格式
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//aac_adif， aac_adts， output_format_rtp_avp， output_format_mpeg2ts ，webm
            //设置audio的编码格式
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            //设置video的编码格式
            mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            //设置录制的视频编码比特率
            mRecorder.setVideoEncodingBitRate(1024 * 1024);
            //设置录制的视频帧率,注意文档的说明:
            mRecorder.setVideoFrameRate(30);
            //设置要捕获的视频的宽度和高度
            mRecorder.setVideoSize(320, 240);//最高只能设置640x480
            //设置记录会话的最大持续时间（毫秒）
//            mRecorder.setMaxDuration(60 * 1000);

            if (surface.getCameraFaceing()==CameraSurfaceView.BackFacing){
                mRecorder.setOrientationHint(90);
            }else {
                mRecorder.setOrientationHint(270);
            }
            path = getExternalCacheDir().getPath();
            if (path != null) {
                File dir = new File(path + "/videos");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                path = dir + "/" + System.currentTimeMillis() + ".mp4";
                //设置输出文件的路径
                mRecorder.setOutputFile(path);
                //准备录制
                mRecorder.prepare();
                //开始录制
                mRecorder.start();
                isRec = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void startprogress() {
//        progressTimerTask = new TimerTask() {
//            @Override
//            public void run() {
//                progress += 10;
//                progressbar.setProgress(progress);
//                Log.i(TAG, "run: ----------------------" + progress);
//                if (progress >= progressbar.getMax() - 10) {
//                    stopRec();
//                }
//            }
//        };
//        progressTimer = new Timer();
//        /**第二个参数表示第一次调用task要延迟多久
//         * 第三个参数表示第一次调动之后，从第二次开始每隔多久调用一次task*/
//        progressTimer.schedule(progressTimerTask, 10, 10);
        chronometer.start();
        handler.postDelayed(runnable, 0);
    }

    private void stopProgress() {
//        progressTimer.cancel();
//        progressTimerTask.cancel();
//        progressTimer = null;
//        progressTimerTask = null;
//        progress = 0;
        chronometer.stop();
        handler.removeCallbacks(runnable);
    }

    private void stopRec() {

        stopProgress();
        try {
            //停止录制
            mRecorder.stop();
            //重置
            mRecorder.reset();
            showAlert("视频录制完成！", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    progress = 0;
                    progressbar.setProgress(0);
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    Intent intent = new Intent(RecoderVideoActivity.this, VideoPlayActivity.class);
                    intent.putExtra(IntentString.FiLEPATH, path);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        isRec = false;
    }


    @OnClick({R.id.ivRec, R.id.tvCancel, R.id.surface})
    public void recClick(View view) {
        switch (view.getId()) {
            case R.id.ivRec:

                if (!isRec) {
                    startRec();
                } else {
                    stopRec();
                }
                break;
            case R.id.tvCancel:

                break;
            case R.id.surface:
                mCamera = surface.changeCamera();
                break;
        }
    }

    /**
     * 释放MediaRecorder
     */
    private void releaseMediaRecorder() {
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
    }

    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        try {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.setPreviewCallback(null);
                mCamera.unlock();
                mCamera.release();
            }
        } catch (RuntimeException e) {
        } finally {
            mCamera = null;
        }
    }

    @Override
    protected void onDestroy() {
        releaseCamera();
        releaseMediaRecorder();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /** 4.）录制视频质量

     通过上面的例子就可以完成视频录制了，但是通过自己配置的参数有时录制的视频质量不高，所以我们可以通过配置文件替代上面的参数配置
     复制代码

     CamcorderProfile profile;

     if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_480P)) {
     profile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
     } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_720P)) {
     profile = CamcorderProfile.get(CamcorderProfile.QUALITY_720P);
     } else {
     profile = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
     }

     mediaRecorder.setProfile(profile);

     复制代码
     5.）录制音频简单示例
     复制代码

     try {
     if (mRecorder == null) {
     mRecorder = new MediaRecorder();
     mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置音频采集方式
     mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);//设置音频输出格式
     mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//设置音频编码方式
     }
     mRecorder.setOutputFile(filePath);//设置录音文件输出路径
     mRecorder.prepare();
     mRecorder.start();
     } catch (Exception e) {
     }*/
}
