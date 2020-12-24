package com.example.sdldemo.example.widget.media;

import android.os.Environment;

/**
 * Created by Sammie on 2017/2/28.
 */

public class Config {
    //--------------------------------------------服务配置---------------------------------------------------------
    //接口链接地址
//    public final static String ServiceIp = "http://sap.dyajb.com:9105/sap-ajb/app/req";//阿里云
    public final static String ServiceIp = "http://saptest1.dyajb.com:9005/sap-ajb/app/req";//公司云 218.107.12.70
//     public final static String ServiceIp = "http://sapaws.dyajb.com:9205/sap-ajb/app/req";//亚马逊

    //版本更新请求地址
//    public final static String UpdateHost = "http://sap.dyajb.com:6639";// 阿里云/亚马逊
    public final static String UpdateHost = "http://saptest1.dyajb.com:9007";//公司云

    //是否是亚马逊版本
    public final static String AppUpdateTag = "anjuxiaobao__mirror";//其他 anjuxiaobao__mirror 亚马逊 anjuxiaobao__mirror_aws
//    public final static String AppUpdateTag = "anjuxiaobao__mirror_aws";//其他 anjuxiaobao__mirror 亚马逊 anjuxiaobao__mirror_aws

    public final static String UserAgreementUri="http://sap.dyajb.com/protocols/license.txt"; // 用户协议
    public final static String AjbAccountgreementUri= "http://sap.dyajb.com/protocols/ajb_license.txt";// 安居小宝账号用户协议
    public final static String SecretUri="http://sap.dyajb.com/protocols/privacy_policy.txt";// --隐私政策
    public final static String AjbSecretUri="http://sap.dyajb.com/protocols/ajb_privacy_policy.txt"; //--安居小宝隐私政策

    public final static boolean IsAws = false;
    public final static boolean IsAli = false;

    public static final String Default_AreaCode = "86";
    public final static int nMaxRetryTimes = 1;
    public final static int nRetryIntervalMilliseconds = 200;

    public final static double MoveMaxWidth = 512.0;
    public final static double MoveMaxHeight = 288.0;
    public final static String CHANNEL_ID = "ajb_id";//配置8.0通知栏ID;

    //云台的每点一次的坐标.
    public final static int xPositionLeft = 0;
    public final static int yPositionLeft = 144;
    public final static int xPositionUp = 256;
    public final static int yPositionUp = 0;
    public final static int xPositionRight = 511;
    public final static int yPositionRight = 144;
    public final static int xPositionDown = 256;
    public final static int yPositionDown = 287;

    //---------------------------------------------路径配置---------------------------------------------------------
    public static final String SdRootPath = Environment.getExternalStorageDirectory().getPath() + "/123laji";
    public static final String RQCode_IMG_PATH = SdRootPath + "/images/qrcode/";//图片缓存路径
    public static final String CACHE_IMG_PATH = SdRootPath + "/images/";//图片缓存路径
    public static final String APK_PATH = SdRootPath + "/apk/";//升级文件保存路径
    public static final String SHARE_LOGO = SdRootPath + "/share/logo/";//分享功能资源路径
    public static final String IpConfigPath = SdRootPath + "/config/";//配置文件路径
    public static final String LOG_PATH = SdRootPath + "/log/";//log保存路径
    public final static String FilePath = SdRootPath + "/%s/";
    public final static String VideoSavePath = SdRootPath + "/%s/video/";
    public final static String AlermVideoPath = SdRootPath + "/%s/alermVideo/";
    public final static String PhotoSavePath = SdRootPath + "/%s/photo/";
    public final static String VideoLastPhotoSavePath = SdRootPath + "/%s/video/.tmp/";
    public static final String VideoLastViewSavePath = SdRootPath + "/%s/video/.thumbnail/";
    public static final String DownloadVideoSavePath = SdRootPath + "/%s/video/%s/%s/";
    public static final String Serialize_AdInfo = SdRootPath + "/AdInfo.obj";
}