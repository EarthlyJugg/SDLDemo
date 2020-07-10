#include <jni.h>
#include <string>
#include <android/log.h>


#define LOGI(...) __android_log_print(ANDROID_LOG_INFO , "(^_^)", __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR , "(^_^)", __VA_ARGS__)
extern "C"{
#include "SDL.h"
#include "SDL_log.h"
}




extern "C" JNIEXPORT jstring JNICALL
Java_com_example_sdldemo_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


