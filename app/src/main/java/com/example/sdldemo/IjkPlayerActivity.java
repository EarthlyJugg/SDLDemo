package com.example.sdldemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class IjkPlayerActivity extends AppCompatActivity implements VideoListener {

    private VideoPlayer videoPlayer;
    private SeekBar seekBar;

    private static String TAG = "IjkPlayerActivity_log";
    private long duration;


    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {

            switch (msg.what) {
                case 0:
                    if (hasMessages(1)) {
                        removeMessages(1);
                    }
                    int toIndex = msg.arg1;
                    Message obtain = Message.obtain();
                    obtain.what = 1;
                    obtain.arg1 = toIndex;
                    sendMessageDelayed(obtain, 500);
                    break;
                case 1:
                    int indx = msg.arg1;
                    long duration = videoPlayer.getDuration();
                    long l = (duration * indx) / 100;
                    videoPlayer.seekTo(l);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        videoPlayer = findViewById(R.id.video);
        seekBar = ((SeekBar) findViewById(R.id.SeekBar));
        

        videoPlayer = ((VideoPlayer) findViewById(R.id.video));
        videoPlayer.setVideoListener(this);
        videoPlayer.setPath("http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4");//37:02
        try {
            videoPlayer.load();
        } catch (IOException e) {
            Toast.makeText(this, "播放失败", Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d(TAG, "onProgressChanged: " + i);
                Message msg = Message.obtain();
                msg.what = 0;
                msg.arg1 = i;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStartTrackingTouch: ");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch: ");
            }
        });

    }

    @Override
    public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
        Log.d(TAG, "onBufferingUpdate: ");
    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        Log.d(TAG, "onCompletion: ");
    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        videoPlayer.start();
        duration = videoPlayer.getDuration();
        Log.d(TAG, "onPrepared: " + duration);
    }

    @Override
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {
        Log.d(TAG, "onSeekComplete: ");
    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {
        Log.d(TAG, "onVideoSizeChanged: ");
    }

    public void onPre(View view) {

        long currentPosition = videoPlayer.getCurrentPosition();
        currentPosition -= 3 * 1000;
        videoPlayer.seekTo(currentPosition);
    }

    public void onNext(View view) {

        long currentPosition = videoPlayer.getCurrentPosition();
        currentPosition += 5 * 1000;
        videoPlayer.seekTo(currentPosition);

    }
}
