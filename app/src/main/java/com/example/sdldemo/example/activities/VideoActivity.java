/*
 * Copyright (C) 2015 Bilibili
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sdldemo.example.activities;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sdldemo.R;
import com.example.sdldemo.example.fragments.TracksFragment;
import com.example.sdldemo.example.widget.media.AndroidMediaController;
import com.example.sdldemo.example.widget.media.Config;
import com.example.sdldemo.example.widget.media.IjkVideoView;
import com.example.sdldemo.example.widget.media.IjkVideoView2;
import com.example.sdldemo.example.widget.media.MeasureHelper;
import com.example.sdldemo.example.widget.media.StringHelper;
import com.example.sdldemo.util.DateTimeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.misc.ITrackInfo;

public class VideoActivity extends AppCompatActivity implements TracksFragment.ITrackHolder {
    private static final String TAG = "VideoActivity";

    static {
        System.loadLibrary("player");
    }

    private String mVideoPath;
    private Uri mVideoUri;

    private AndroidMediaController mMediaController;
    private IjkVideoView2 mVideoView;
    private TextView mToastTextView;
    private TableLayout mHudView;
    private DrawerLayout mDrawerLayout;
    private ViewGroup mRightDrawer;

    private boolean mBackPressed;
    private ImageView tempImage;


    public static Intent newIntent(Context context, String videoPath, String videoTitle) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("videoPath", videoPath);
        intent.putExtra("videoTitle", videoTitle);
        return intent;
    }

    public static void intentTo(Context context, String videoPath, String videoTitle) {
        context.startActivity(newIntent(context, videoPath, videoTitle));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);


        // handle arguments
        mVideoPath = getIntent().getStringExtra("videoPath");

        Intent intent = getIntent();
        String intentAction = intent.getAction();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        mMediaController = new AndroidMediaController(this, true);
        mMediaController.setSupportActionBar(actionBar);

        mToastTextView = (TextView) findViewById(R.id.toast_text_view);
        mHudView = (TableLayout) findViewById(R.id.hud_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mRightDrawer = (ViewGroup) findViewById(R.id.right_drawer);
        tempImage = ((ImageView) findViewById(R.id.tempImage));

        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        findViewById(R.id.getFrame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String path = screenShot("aliyun", ".jpg");
                boolean isSucceed = mVideoView.getFrameBitmap((path));
//                Bitmap bitmap = mVideoView.getBitmap();
//                saveBitmapByNative(bitmap, 90, path.getAbsolutePath());
                if (isSucceed) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = BitmapFactory.decodeFile(path);
                            tempImage.setImageBitmap(bitmap);
                        }
                    }, 500);
                } else {
                    Toast.makeText(VideoActivity.this, "截图失败", Toast.LENGTH_SHORT).show();
                }


            }
        });
        findViewById(R.id.startVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String parsonFile = String.format(Config.PhotoSavePath, "yulian");
                File file = new File(parsonFile);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String fileName = parsonFile + System.currentTimeMillis() + "_lingtao_" + new Random().nextInt(1000) + ".mp4";
                Log.d(TAG, "onClick: " + fileName);
                int ret = mVideoView.startRecord(fileName);
                Log.d(TAG, "onClick: 开始返回码:" + ret);
            }
        });
        findViewById(R.id.stopVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i1 = mVideoView.stopRecord();
                Log.d(TAG, "onClick: 结束返回码:" + i1);
            }
        });


        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");


        mVideoView = (IjkVideoView2) findViewById(R.id.video_view);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setHudView(mHudView);
        // prefer mVideoPath


        if (mVideoPath != null) {
            mVideoView.setVideoPath(mVideoPath);
        } else if (mVideoUri != null) {
            mVideoView.setVideoURI(mVideoUri);
        } else {
            Log.e(TAG, "Null Data Source\n");
            finish();
            return;
        }

        initListener();
        mVideoView.start();

    }

    public String screenShot(String ipcName, String suffix) {

        try {
            String parsonFile = String.format(Config.PhotoSavePath, "yulian");
            File file = new File(parsonFile);
            if (!file.exists()) {
                file.mkdirs();
            }
            String picPath = System.currentTimeMillis() + "_" + ipcName + "_" + new Random().nextInt(1000) + suffix;
            File file1 = new File(picPath);
            if (!file1.exists()) {
                file1.createNewFile();
            }
            return picPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 打开assets下的音乐mp3文件
     */
    private void openAssetMusics() {

        try {
            //播放 assets/a2.mp3 音乐文件
            AssetFileDescriptor fd = getAssets().openFd("hellolele.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                ITrackInfo[] trackInfo = mVideoView.getTrackInfo();
                for (ITrackInfo info : trackInfo) {
                    Log.d(TAG, "onPrepared: " + info.getInfoInline());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        mBackPressed = true;
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBackPressed || !mVideoView.isBackgroundPlayEnabled()) {
            if (mVideoView.isRecord()) {
                mVideoView.stopRecord();
            }
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopBackgroundPlay();
        } else {
            mVideoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_toggle_ratio) {
            int aspectRatio = mVideoView.toggleAspectRatio();
            String aspectRatioText = MeasureHelper.getAspectRatioText(this, aspectRatio);
            mToastTextView.setText(aspectRatioText);
            mMediaController.showOnce(mToastTextView);
            return true;
        } else if (id == R.id.action_toggle_player) {
            int player = mVideoView.togglePlayer();
            String playerText = IjkVideoView.getPlayerText(this, player);
            mToastTextView.setText(playerText);
            mMediaController.showOnce(mToastTextView);
            return true;
        } else if (id == R.id.action_toggle_render) {
            int render = mVideoView.toggleRender();
            String renderText = IjkVideoView.getRenderText(this, render);
            mToastTextView.setText(renderText);
            mMediaController.showOnce(mToastTextView);
            return true;
        } else if (id == R.id.action_show_info) {
            mVideoView.showMediaInfo();
        } else if (id == R.id.action_show_tracks) {
            if (mDrawerLayout.isDrawerOpen(mRightDrawer)) {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.right_drawer);
                if (f != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.remove(f);
                    transaction.commit();
                }
                mDrawerLayout.closeDrawer(mRightDrawer);
            } else {
                Fragment f = TracksFragment.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.right_drawer, f);
                transaction.commit();
                mDrawerLayout.openDrawer(mRightDrawer);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public ITrackInfo[] getTrackInfo() {
        if (mVideoView == null)
            return null;

        return mVideoView.getTrackInfo();
    }

    @Override
    public void selectTrack(int stream) {
        mVideoView.selectTrack(stream);
    }

    @Override
    public void deselectTrack(int stream) {
        mVideoView.deselectTrack(stream);
    }

    @Override
    public int getSelectedTrack(int trackType) {
        if (mVideoView == null)
            return -1;

        return mVideoView.getSelectedTrack(trackType);
    }

    public void getBitmap() {


    }

    // ===================ScreenShot=====================
    public String screenShot(String ipcName, Bitmap bm) {
        try {
            ipcName = StringHelper.stringToHexString(ipcName);
            String parsonFile = String.format(Config.PhotoSavePath, "lingtao");
            File file = new File(parsonFile);
            if (!file.exists()) {
                file.mkdirs();
            }
            String picPath = parsonFile + System.currentTimeMillis() + "_" + ipcName + "_" + DateTimeUtil.getCurrentDateTimeString
                    ("yyyy-MM-dd HH-mm-ss-") + new Random().nextInt(1000) + ".jpg";
            saveBitmap(picPath, bm);
            return picPath;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(this, "截图失败", Toast.LENGTH_SHORT).show();
        }
        return "";
    }


    /**
     * 保存方法
     */
    public void saveBitmap(String filePath, Bitmap bm) {
        Log.e(TAG, "保存图片");
        File f = new File(filePath);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Log.d(TAG, "saveBitmap: 已经保存");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 使用哈夫曼算法压缩图片
     *
     * @param bitmap     保存图片
     * @param quality    质量
     * @param outPutPath 保存路径
     * @return
     */
    public native int saveBitmapByNative(Bitmap bitmap, int quality, String outPutPath);

    public native void getFrame(boolean b);

    public native int getVideoFrame(String in_url, String out_file);

    public void onFinishClick(View view) {

        finish();
    }

    public void audioAdd(View view) {
        //获取系统的Audio管理者
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //最大音量
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //当前音量

        /**
         *   int streamType  需要调整的音量类型
         *                 （以下常量定义于AudioManager类中）
         *                 STREAM_ALARM 警报
         *                 STREAM_MUSIC 音乐回放即媒体音量
         *                 STREAM_NOTIFICATION 窗口顶部状态栏Notification,
         *                 STREAM_RING 铃声
         *                 STREAM_SYSTEM 系统
         *                 STREAM_VOICE_CALL 通话
         *                 STREAM_DTMF 双音多频,不是很明白什么东西
         *
         *             int direction   调整的方向，加或者减。
         *                 （以下常量定义于AudioManager类中）
         *                 ADJUST_LOWER 降低音量
         *                 ADJUST_RAISE 升高音量
         *                 ADJUST_SAME 保持不变,这个主要用于向用户展示当前的音量
         *
         *             int flags   附加的一些参数
         *                 参数的说明参考API文档：http://www.android-doc.com/reference/android/media/AudioManager.html
         *
         *                 FLAG_PLAY_SOUND 调整音量时播放声音
         *                 FLAG_SHOW_UI 调整时显示音量条,就是按音量键出现的那个
         *                 0表示什么也没有
         */

        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        while (currentVolume < 3) {
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FX_KEY_CLICK);
            currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        }

    }

    public void audioSub(View view) {

        //获取系统的Audio管理者
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //最大音量
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //当前音量

        /**
         *   int streamType  需要调整的音量类型
         *                 （以下常量定义于AudioManager类中）
         *                 STREAM_ALARM 警报
         *                 STREAM_MUSIC 音乐回放即媒体音量
         *                 STREAM_NOTIFICATION 窗口顶部状态栏Notification,
         *                 STREAM_RING 铃声
         *                 STREAM_SYSTEM 系统
         *                 STREAM_VOICE_CALL 通话
         *                 STREAM_DTMF 双音多频,不是很明白什么东西
         *
         *             int direction   调整的方向，加或者减。
         *                 （以下常量定义于AudioManager类中）
         *                 ADJUST_LOWER 降低音量
         *                 ADJUST_RAISE 升高音量
         *                 ADJUST_SAME 保持不变,这个主要用于向用户展示当前的音量
         *
         *             int flags   附加的一些参数
         *                 参数的说明参考API文档：http://www.android-doc.com/reference/android/media/AudioManager.html
         *
         *                 FLAG_PLAY_SOUND 调整音量时播放声音
         *                 FLAG_SHOW_UI 调整时显示音量条,就是按音量键出现的那个
         *                 0表示什么也没有
         */
        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        do {
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FX_KEY_CLICK);
            currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        } while (currentVolume > 0);

    }
}
