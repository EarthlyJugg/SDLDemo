package com.example.sdldemo.example.activities;


import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sdldemo.R;
import com.example.sdldemo.example.adapter.MediaAdapter;
import com.example.sdldemo.example.bean.MediaBean;
import com.example.sdldemo.example.widget.media.Config;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class MediaListActivity extends AppCompatActivity {


    private static final String[] sLocalVideoColumns = {
            MediaStore.Video.Media._ID, // 视频id
            MediaStore.Video.Media.DATA, // 视频路径
            MediaStore.Video.Media.SIZE, // 视频字节大小
            MediaStore.Video.Media.DISPLAY_NAME, // 视频名称 xxx.mp4
            MediaStore.Video.Media.TITLE, // 视频标题
            MediaStore.Video.Media.DATE_ADDED, // 视频添加到MediaProvider的时间
            MediaStore.Video.Media.DATE_MODIFIED, // 上次修改时间，该列用于内部MediaScanner扫描，外部不要修改
            MediaStore.Video.Media.MIME_TYPE, // 视频类型 video/mp4
            MediaStore.Video.Media.DURATION, // 视频时长
            MediaStore.Video.Media.ARTIST, // 艺人名称
            MediaStore.Video.Media.ALBUM, // 艺人专辑名称
            MediaStore.Video.Media.RESOLUTION, // 视频分辨率 X x Y格式
            MediaStore.Video.Media.DESCRIPTION, // 视频描述
            MediaStore.Video.Media.IS_PRIVATE,
            MediaStore.Video.Media.TAGS,
            MediaStore.Video.Media.CATEGORY, // YouTube类别
            MediaStore.Video.Media.LANGUAGE, // 视频使用语言
            MediaStore.Video.Media.LATITUDE, // 拍下该视频时的纬度
            MediaStore.Video.Media.LONGITUDE, // 拍下该视频时的经度
            MediaStore.Video.Media.DATE_TAKEN,
            MediaStore.Video.Media.MINI_THUMB_MAGIC,
            MediaStore.Video.Media.BUCKET_ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.BOOKMARK // 上次视频播放的位置
    };
    private static final String[] sLocalVideoThumbnailColumns = {
            MediaStore.Video.Thumbnails.DATA, // 视频缩略图路径
            MediaStore.Video.Thumbnails.VIDEO_ID, // 视频id
            MediaStore.Video.Thumbnails.KIND,
            MediaStore.Video.Thumbnails.WIDTH, // 视频缩略图宽度
            MediaStore.Video.Thumbnails.HEIGHT // 视频缩略图高度
    };

    private ListView listView;
    List<MediaBean> list = new LinkedList<>();
    private MediaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_list);
        listView = ((ListView) findViewById(R.id.listview));

        adapter = new MediaAdapter(this, list);
        listView.setAdapter(adapter);

        adapter.setOnClickListener(new MediaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, MediaBean bean) {
//                Intent intent = new Intent(MediaListActivity.this, MediaPlayerActivity.class);
//                intent.putExtra("path", bean.data);
//                startActivity(intent);

//                MainActivity.start(MediaListActivity.this, bean.data);
                VideoActivity.intentTo(MediaListActivity.this, bean.data, "视频" + position);
            }
        });

        initVideoData();
        list.add(new MediaBean("http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4"));

    }

    private void initVideoData() {

        Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, sLocalVideoColumns,
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                MediaBean videoInfo = new MediaBean();
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                videoInfo.data = data;
                list.add(videoInfo);
            } while (cursor.moveToNext());

            cursor.close();
        }


        String parsonFile = String.format(Config.PhotoSavePath, "yulian");
        File file = new File(parsonFile);
        if (!file.exists()) {
            file.mkdirs();
        }

        for (File tempF : file.listFiles()) {
            if (tempF.isFile() && tempF.getName().endsWith(".mp4")) {
                String path = tempF.getAbsolutePath();
                MediaBean videoInfo = new MediaBean();
                videoInfo.data = path;
                videoInfo.name = tempF.getName();
                list.add(videoInfo);
            }
        }

    }

    public void onRefresh(View view) {
        list.clear();
        initVideoData();
        list.add(new MediaBean("http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4"));
        adapter.notifyDataSetChanged();
    }
}