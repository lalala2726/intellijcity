package com.zhangchuang.intellig.network.api;

import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author:Zhangchuang
 * @Date: 2023/3/22 22:03
 */
public interface SystemService {

    /**
     * 获取主页轮播广告图
     *
     * @return
     */
    @GET("/prod-api/api/rotation/list")
    Call<ResponseBody> getSysAds();


    /**
     * ChatGTP接口
     */
    @POST("/chat")
    Call<ResponseBody> chat(@Query("content") String content);


    /**
     * 获取新闻列表信息
     *
     * @return
     */
    @GET("/prod-api/press/press/list")
    Call<ResponseBody> getNewsListInfo();
}
