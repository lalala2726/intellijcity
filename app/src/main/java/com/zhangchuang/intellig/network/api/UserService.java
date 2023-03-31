package com.zhangchuang.intellig.network.api;

import com.zhangchuang.intellig.entity.Data;
import com.zhangchuang.intellig.entity.Token;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 用户相关服务
 *
 * @Author:Zhangchuang
 * @Date: 2023/3/27 16:20
 */
public interface UserService {

    /**
     * 用户通过网络登录返回token信息
     *
     * @param body JSON请求体
     * @return
     */
    @POST("/prod-api/api/login")
    Call<Data> userLoginByNetwork(@Body RequestBody body);

    /**
     * 获取用户信息
     *
     * @param token 令牌
     * @return
     */
    @GET("/prod-api/api/common/user/getInfo")
    Call<ResponseBody> getUserInfo(@Header("Authorization") String token);
}
