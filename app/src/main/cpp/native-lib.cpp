#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_bilibili_news_engin_JNI_getAppKey__(
        JNIEnv *env,
        jobject /* this */) {
        //appkey
    std::string appkey="104d399fb53d6a94e01726fbe0ec6def";
    return env->NewStringUTF(appkey.c_str());
}
