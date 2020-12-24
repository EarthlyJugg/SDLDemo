#include <jni.h>
#include <string>
#include <android/log.h>
#include <android/bitmap.h>

extern "C" {
//#include </jpeglib.h>
#include "includes/jpeglib.h"
}

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO , "(^_^)", __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR , "IjkMediaPlayer_log", __VA_ARGS__)



# define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))



extern  void writeJpegToFile(uint8_t *temp, int width, int height, jint quality, const char *outPutPath);


extern "C" JNIEXPORT jstring JNICALL
Java_com_example_sdldemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject thiz) {
    std::string hello = "Hello from C++";
//    env->GetLongField(thiz,)
    return env->NewStringUTF(hello.c_str());
}


extern "C"
JNIEXPORT jint JNICALL
Java_com_example_sdldemo_MainActivity_getBitmmap2(JNIEnv *env, jobject thiz, jstring fileName) {

    LOGE("开始了");
    const char *file_name = env->GetStringUTFChars(fileName, 0);


    if (!file_name || !strlen(file_name)) { // 没有路径
        LOGE("没有路径");
        return -1;
    }


    env->ReleaseStringUTFChars(fileName, file_name);

    return 0;

}


extern "C"
JNIEXPORT jint JNICALL
Java_com_example_sdldemo_example_activities_VideoActivity_saveBitmapByNative(JNIEnv *env,
                                                                             jobject thiz,
                                                                             jobject bitmap,
                                                                             jint quality,
                                                                             jstring outPutPath_) {

    const char *outPutPath = env->GetStringUTFChars(outPutPath_, 0);

    // 从bitmap中获取argb数据
    AndroidBitmapInfo info;
    // 获取里面的信息
    AndroidBitmap_getInfo(env, bitmap, &info);
    // 得到图片中的像素信息
    uint8_t *pixels;// 相当于byte数组  *pixels === byte[]
    AndroidBitmap_lockPixels(env, bitmap, (void **) &pixels);
    // jpge argb去掉他的a ===> rgb
    // 开一块内存用来存储rgb信息

    if (pixels == NULL) {
        //__android_log_print(ANDROID_LOG_ERROR, "libjpeg_lingtao", "pixels 为空");
        LOGE("pixels 为空");
        return -1;
    }
    LOGE("pixels 不为空");
    // 可以用来存放图片所有信息(w*h为所有像素,每个像素有r,g,b三种颜色)
    uint8_t *data = (uint8_t *) malloc(info.width * info.height * 3);

    uint8_t *temp = data;
    uint8_t r, g, b;
    int color;
    for (int i = 0; i < info.height; ++i) {
        for (int j = 0; j < info.width; ++j) {
            //pixels定义的是无符号字符串类型指针，一个字节
            // pixels 强转成int 类型指针，就变成了四字节，刚好rgba 一个像素就是四个字节
            //所以每次移动一个pixels 就是取到一个像素数据，不然就必须每次一定四位来取到rgba 数据
            color = *(int *) pixels; // color 4个字节,argb

            //rgba 的存储顺序 ABGR
            r = (color >> 16) & 0xFF;
            g = (color >> 8) & 0xFF;
            b = color & 0xFF;
            // 存放颜色,jpeg的格式为bgr,倒过来存
            *data = b;
            *(data + 1) = g;
            *(data + 2) = r;
            data += 3;// 这里就去除了alpha通道
            pixels += 4;// 每个像素有argb4个通道,其中alpha通道被我们丢弃掉了,没有存到data里面
        }

    }
    //把得到的新的图片的信息存入一个新文件 中
    writeJpegToFile(temp, info.width, info.height, quality, outPutPath);
//    writeJpegToFile2(temp, info.width, info.height, quality, outPutPath);

    LOGE("压缩完成:%s",outPutPath);
    free(data);// data是通过malloc开辟的,这里需要释放AndroidBitmap_unlockPixels(env, bitmap);

    env->ReleaseStringUTFChars(outPutPath_, outPutPath);
    return 0;
}

 void writeJpegToFile(uint8_t *temp, int width, int height, jint quality, const char *outPutPath) {
//    3.1、创建jpeg压缩对象
    jpeg_compress_struct jcs;
    //错误回调
    jpeg_error_mgr error;
    jcs.err = jpeg_std_error(&error);
    //创建压缩对象
    jpeg_create_compress(&jcs);
//    3.2、指定存储文件  write binary
    FILE *f = fopen(outPutPath, "wb");
    jpeg_stdio_dest(&jcs, f);
//    3.3、设置压缩参数
    jcs.image_width = width;
    jcs.image_height = height;
    //bgr
    jcs.input_components = 3;
    jcs.in_color_space = JCS_RGB;
    jpeg_set_defaults(&jcs);
    //开启哈夫曼功能
    jcs.optimize_coding = true;
    jpeg_set_quality(&jcs, quality, 1);
//    3.4、开始压缩
    jpeg_start_compress(&jcs, 1);
//    3.5、循环写入每一行数据
    int row_stride = width * 3;//一行的字节数
    JSAMPROW row[1];
    while (jcs.next_scanline < jcs.image_height) {
        //取一行数据
        uint8_t *pixels = temp + jcs.next_scanline * row_stride;
        row[0] = pixels;
        jpeg_write_scanlines(&jcs, row, 1);
    }
//    3.6、压缩完成
    jpeg_finish_compress(&jcs);
//    3.7、释放jpeg对象
    fclose(f);
    jpeg_destroy_compress(&jcs);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_sdldemo_example_activities_VideoActivity_getFrame(JNIEnv *env, jobject thiz,
                                                                   jboolean b) {
    // TODO: implement getFrame()
}



extern "C"
JNIEXPORT jint JNICALL
Java_com_example_sdldemo_example_activities_VideoActivity_getVideoFrame(JNIEnv *env, jobject thiz,
                                                                        jstring in_url,
                                                                        jstring out_file) {


}