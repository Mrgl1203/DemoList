package com.example.gl152.testdemo.activity;


import android.media.AudioManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.SeekBar;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.imp.SoundInterface;

import receiver.SoundReceiver;

public class SoundActivity extends BaseActivity {
    public static SoundInterface soundInterface;
    private AudioManager audioManager;
    private SeekBar mSeekBar;
    private SoundReceiver soundReceiver;
    private static final String TAG = "SoundActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sound;
    }

    @Override
    protected void init() {
//        soundInterface = this;
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        max = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM); // 最大音量  0-6  max为7
        current = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM); // 当前音量
        Log.i(TAG, "onResume: "+current+"----"+max);
        mSeekBar.setMax(max);// 设置seekBar
        mSeekBar.setProgress(current);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                current = progress;
                seekBar.setProgress(current);
                // 三个参数一次是  模式，值，标志位
                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, current, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                Log.i(TAG, "onProgressChanged: " + progress + "---------------current=" + current);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        // 注册广播，添加Action
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(AudioManager.RINGER_MODE_CHANGED_ACTION);
//        soundReceiver = new SoundReceiver();
//        registerReceiver(soundReceiver, intentFilter);
    }

//    @Override
//    public void soundchange(int progress) {
//        mSeekBar.setProgress(progress);
//    }

    int max;
    int current;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            current--;
            if (current < 0) {
                current = 0;
            }
            mSeekBar.setProgress(current);
            Log.i(TAG, "onKeyDown: " + current+"------"+audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            current++;
            if (current > max) {
                current = max;
            }
            mSeekBar.setProgress(current);
            Log.i(TAG, "onKeyDown: " + current+"------"+audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        // 一定记得注销广播，否则会造成内存泄漏
//        unregisterReceiver(soundReceiver);
    }
}
