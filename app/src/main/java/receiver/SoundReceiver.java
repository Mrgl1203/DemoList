package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

import com.example.gl152.testdemo.activity.SoundActivity;
import com.example.gl152.testdemo.imp.SoundInterface;

/**
 * Created by gl152 on 2017/4/12.
 */

public class SoundReceiver extends BroadcastReceiver {
    private SoundInterface soundInterface = SoundActivity.soundInterface;
    private static final String TAG = "SoundReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        // 监听一下音量
        if ("android.media.VOLUME_CHANGED_ACTION".equals(action)) {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int progress = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
            Log.i(TAG, "onReceive:--- " + progress);

            soundInterface.soundchange(progress);


        }


    }
}
