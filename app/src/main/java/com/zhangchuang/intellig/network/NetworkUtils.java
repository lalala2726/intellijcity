package com.zhangchuang.intellig.network;


import android.content.Context;

import com.zhangchuang.intellig.entity.Data;
import com.zhangchuang.intellig.network.api.SystemService;
import com.zhangchuang.intellig.network.api.UserService;
import com.zhangchuang.intellig.utils.ConfigUtils;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    private Retrofit retrofit;

    private Context context;


    private ConfigUtils configUtils;

    public NetworkUtils(Context context) {
        this.context = context;
        configUtils = new ConfigUtils(context);
        retrofit = new Retrofit.Builder()
                .baseUrl(configUtils.readNetworkInfo())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    /**
     * 用户登录
     *
     * @param body
     * @param callback
     */
    public void userLogin(RequestBody body, Callback<Data> callback) {
        UserService userService = retrofit.create(UserService.class);
        Call<Data> dataCall = userService.userLoginByNetwork(body);
        dataCall.enqueue(callback);
    }

    /**
     * 获取系统广告
     *
     * @param call
     */
    public void getSysAds(Callback<ResponseBody> call) {
        SystemService systemService = retrofit.create(SystemService.class);
        Call<ResponseBody> sysAds = systemService.getSysAds();
        sysAds.enqueue(call);


    }

    /**
     * 获取新闻列表信息
     */
    public void getNewsListInfo(Callback<ResponseBody> call) {
        SystemService systemService = retrofit.create(SystemService.class);
        Call<ResponseBody> newsListInfo = systemService.getNewsListInfo();
        newsListInfo.enqueue(call);

    }


    public void getUserInfo(String token, Callback<ResponseBody> call) {
        UserService userService = retrofit.create(UserService.class);
        Call<ResponseBody> userInfo = userService.getUserInfo(token);
        userInfo.enqueue(call);
    }

}
