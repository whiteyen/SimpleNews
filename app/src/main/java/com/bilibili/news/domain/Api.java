package com.bilibili.news.domain;

import com.bilibili.news.utils.NewsBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface Api {
    @GET ("toutiao/index")
    Observable<NewsBean> getNews(@Query("key")String key, @Query("type")String type);
}
