package com.example.sdldemo;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sdldemo.example.activities.SampleMediaActivity;
import com.example.sdldemo.example.widget.media.RawDataSourceProvider;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.misc.IjkTrackInfo;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("player");
    }

    private static final String SOURCE_URL = "rtsp://192.168.1.106:8554/test.mkv";
    private static final String SOURCE_URL2 = "http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4";
    private IjkMediaPlayer ijkPlayer;
    private String TAG = "IjkMediaPlayer_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method

        Button button = (Button) findViewById(R.id.IjkPlayer);
//        button.setText(stringFromJNI());
//        ijkPlayer = new IjkMediaPlayer();

        findViewById(R.id.singlePlay).setOnClickListener(this);
        findViewById(R.id.doublePlay).setOnClickListener(this);

    }

    public void IjkPlayer(View view) {
        if (true) {
            Log.d(TAG, "IjkPlayer: " + getBitmmap2(""));
            return;
        }
        //实例化播放内核
//获得播放源访问入口
        AssetManager am = getAssets();
        try {
//            AssetFileDescriptor afd = am.openFd("hellolele.mp3");// 注意这里的区别
            AssetFileDescriptor afd = am.openFd("hellolele2.amr");// 注意这里的区别
            //构建IjkPlayer能识别的IMediaDataSource，下面的RawDataSourceProvider实现了 IMediaDataSource 接口
            RawDataSourceProvider sourceProvider = new RawDataSourceProvider(afd);
            //给IjkPlayer设置播放源
            ijkPlayer.setDataSource(sourceProvider);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ijkPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                ijkPlayer.start();
                IjkTrackInfo[] trackInfo = ijkPlayer.getTrackInfo();
                Log.d(TAG, "onPrepared: " + trackInfo.length);
                for (IjkTrackInfo info : trackInfo) {
                    Log.d(TAG, "onPrepared: " + info.getInfoInline());
                }
            }
        });

        //准备播放
        ijkPlayer.prepareAsync();

        Bitmap bitmap = BitmapFactory.decodeFile("");

    }


    public void IjkPlayer2(View view) {
        startActivity(new Intent(this, SampleMediaActivity.class));
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI2(Bitmap bitmap);
    public native int getBitmmap2(String fileName);

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.singlePlay:
                SampleMediaActivity.intentTo(this,1);
                break;
            case R.id.doublePlay:
                SampleMediaActivity.intentTo(this,2);
                break;
        }

    }
}
