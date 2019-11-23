package com.bilibili.news.engin;

import com.bilibili.news.BuildConfig;
import com.bilibili.news.domain.Api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    public static Retrofit retrofit;
    public static Retrofit getRetrofit(){
        if(retrofit==null){
            OkHttpClient httpClient;
            OkHttpClient.Builder builder  = new OkHttpClient.Builder();

            if(BuildConfig.DEBUG){
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);
            }
            httpClient = builder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://v.juhe.cn/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return  retrofit;
    }
    public static Api createApi(){
        return Network.getRetrofit().create(Api.class);
    }
}
