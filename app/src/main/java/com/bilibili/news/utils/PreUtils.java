package com.bilibili.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreUtils {
    public static final String PREF_NAME = "news";
    public static SharedPreferences sp;
    public  static void putBoolean(Context ctx,String key, boolean value){
        if(sp==null){
            sp= ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).apply();
    }
    public static boolean getBoolean(Context ctx,String key,boolean defvalue){
        if(sp==null){
            sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defvalue);
    }
    public static void putString(Context ctx,String key,String value){
        if(sp==null){
            sp = ctx.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        }
        sp.edit().putString(key,value);
    }
    public static String getString (Context ctx,String key,String defValue){
        if(sp==null){
            sp = ctx.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        }
        return sp.getString(key,defValue);
    }
}
