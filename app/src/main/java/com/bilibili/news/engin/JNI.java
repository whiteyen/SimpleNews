package com.bilibili.news.engin;

public class JNI {
    static {
        System.loadLibrary("native-lib");
    }

    public static native String getAppKey();
}
