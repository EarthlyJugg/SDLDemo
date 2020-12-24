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
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sdldemo.R;
import com.example.sdldemo.example.widget.media.AndroidMediaController;
import com.example.sdldemo.example.widget.media.IjkVideoView2;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoActivity2 extends AppCompatActivity {
    private static final String TAG = "VideoActivity";

    private String mVideoPath;
    private Uri mVideoUri;

    private boolean mBackPressed;

    private AndroidMediaController mMediaController;
    private IjkVideoView2 mVideoView;
    private IjkVideoView2 mVideoView2;
    private IjkVideoView2 mVideoView3;
    private IjkVideoView2 mVideoView4;


    public static Intent newIntent(Context context, String videoPath, String videoTitle) {
        Intent intent = new Intent(context, VideoActivity2.class);
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
        setContentView(R.layout.activity_player2);


        // handle arguments
        mVideoPath = getIntent().getStringExtra("videoPath");

        Intent intent = getIntent();

        findViewById(R.id.backText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");


        mVideoView = (IjkVideoView2) findViewById(R.id.video_view);
        mVideoView2 = (IjkVideoView2) findViewById(R.id.video_view2);
//        mVideoView3 = (IjkVideoView2) findViewById(R.id.video_view3);
//        mVideoView4 = (IjkVideoView2) findViewById(R.id.video_view4);


        if (mVideoPath != null) {
            mVideoView.setVideoPath(mVideoPath);
            mVideoView2.setVideoPath(mVideoPath);
//            mVideoView3.setVideoPath(mVideoPath);
//            mVideoView4.setVideoPath(mVideoPath);
        } else if (mVideoUri != null) {
            mVideoView.setVideoURI(mVideoUri);
            mVideoView2.setVideoURI(mVideoUri);
//            mVideoView3.setVideoURI(mVideoUri);
//            mVideoView4.setVideoURI(mVideoUri);
        } else {
            Log.e(TAG, "Null Data Source\n");
            finish();
            return;
        }

        mVideoView.start();
        mVideoView2.start();
//        mVideoView3.start();
//        mVideoView4.start();

    }


    @Override
    public void onBackPressed() {
        mBackPressed = true;
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBackPressed ||
                !mVideoView.isBackgroundPlayEnabled()
                || !mVideoView2.isBackgroundPlayEnabled()
//                || !mVideoView3.isBackgroundPlayEnabled()
//                || !mVideoView4.isBackgroundPlayEnabled()
        ) {
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopBackgroundPlay();

            mVideoView2.stopPlayback();
            mVideoView2.release(true);
            mVideoView2.stopBackgroundPlay();

//            mVideoView3.stopPlayback();
//            mVideoView3.release(true);
//            mVideoView3.stopBackgroundPlay();
//
//            mVideoView4.stopPlayback();
//            mVideoView4.release(true);
//            mVideoView4.stopBackgroundPlay();

        } else {
            mVideoView.enterBackground();
            mVideoView2.enterBackground();
//            mVideoView3.enterBackground();
//            mVideoView4.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }


}
