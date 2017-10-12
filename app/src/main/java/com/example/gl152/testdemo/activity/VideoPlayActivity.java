package com.example.gl152.testdemo.activity;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;

import com.example.gl152.testdemo.R;
import com.example.gl152.testdemo.bean.IntentString;
import com.example.gl152.testdemo.view.FullVideoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * VideoView的API
 * void start()：开始播放
 * void stopPlayback()：停止播放
 * void pause()：暂停
 * void resume()：重新播放
 * void seekTo(int msec)：从第几毫秒开始播放
 * int getCurrentPosition()：获取当前播放的位置
 * int getDuration()：获取当前播放视频的总长度
 * boolean isPlaying()：当前VideoView是否在播放视频
 * void setVideoPath(String path)：以文件路径的方式设置VideoView播放的视频源。
 * void setVideoURI(Uri uri)：以Uri的方式设置视频源，可以是网络Uri或本地Uri。
 * setMediaController(MediaController controller)：设置MediaController控制器。
 * setOnCompletionListener(MediaPlayer.onCompletionListener l)：监听播放完成的事件。
 * setOnErrorListener(MediaPlayer.OnErrorListener l)：监听播放发生错误时候的事件。
 * setOnPreparedListener(MediaPlayer.OnPreparedListener l)：：监听视频装载完成的事件。
 */

public class VideoPlayActivity extends BaseActivity {

    @BindView(R.id.videoview)
    FullVideoView videoview;
    MediaController mediaController;
    String videopath;
    @BindView(R.id.ivStart)
    ImageView ivStart;
    @BindView(R.id.ivFirstImg)
    ImageView ivFirstImg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void init() {
        videopath = getIntent().getStringExtra(IntentString.FiLEPATH);
        mediaController = new MediaController(this);

        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videopath);
        Bitmap bitmap = media.getFrameAtTime(0);//获取视频第一帧
        ivFirstImg.setImageBitmap(bitmap);

        videoview.setOnCompletionListener(onCompletionListener);
    }

    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            ivStart.setVisibility(View.VISIBLE);
            ivFirstImg.setVisibility(View.VISIBLE);
        }
    };

    @OnClick({R.id.ivStart})
    public void startVideo() {
        ivStart.setVisibility(View.GONE);
        ivFirstImg.setVisibility(View.GONE);
        videoview.setVideoPath(videopath);
        videoview.setMediaController(mediaController);
        videoview.start();
        videoview.requestFocus();

    }

    @OnClick(R.id.ivFirstImg)
    public void clickImg(){
        mediaController.hide();
    }

}
