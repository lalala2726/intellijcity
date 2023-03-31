package com.zhangchuang.intellig.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * 配置文件信息
 *
 * @Author:Zhangchuang
 * @Date: 2023/3/27 14:23
 */
public class ConfigUtils {
    private Context context;

    public ConfigUtils(Context context) {
        this.context = context;
    }

    /**
     * 保存网络信息
     *
     * @param ipAddress IP地址
     * @param portInfo  端口
     */
    public void saveNetworkInfo(String ipAddress, String portInfo) {
        SharedPreferences application = context.getSharedPreferences("application", MODE_PRIVATE);
        SharedPreferences.Editor edit = application.edit();
        edit.putString("ip", ipAddress);
        edit.putString("port", portInfo);
        edit.commit();
    }


    /**
     * 读取网络配置信息
     *
     * @return 返回网络配置的网络信息
     */
    public String readNetworkInfo() {
        SharedPreferences application = context.getSharedPreferences("application", MODE_PRIVATE);
        String ip = application.getString("ip", null);
        String port = application.getString("port", null);
        String networkInfo = "http://" + ip + ":" + port;
        Log.i("INFO", "网络配置信息--->" + networkInfo);
        return networkInfo;
    }

    /**
     * true代表登录过，false代表未登录
     *
     * @param info
     */
    public void saveLoginInfo(boolean info) {
        SharedPreferences application = context.getSharedPreferences("application", MODE_PRIVATE);
        SharedPreferences.Editor edit = application.edit();
        edit.putBoolean("login", info);
        edit.commit();
    }


    /**
     * 读取登录状态
     *
     * @return true代表登陆过，false代表未登录
     */
    public boolean readLoginInfo() {
        SharedPreferences application = context.getSharedPreferences("application", MODE_PRIVATE);
        boolean info = application.getBoolean("login", false);
        return info;
    }

    /**
     * 保存第一次软件配置
     *
     * @param info true代表不是第一次，false代表是第一次
     */
    public void saveConfigInfo(boolean info) {
        SharedPreferences application = context.getSharedPreferences("application", MODE_PRIVATE);
        SharedPreferences.Editor edit = application.edit();
        edit.putBoolean("config", info);
        edit.commit();
    }

    /**
     * 读取配置信息
     *
     * @return true代表不是第一次，false代表是第一次
     */
    public boolean readConfigInfo() {
        SharedPreferences application = context.getSharedPreferences("application", MODE_PRIVATE);
        boolean config = application.getBoolean("config", false);
        return config;
    }

    /**
     * 保存token信息
     *
     * @param token
     */
    public void saveTokenInfo(String token) {
        SharedPreferences application = context.getSharedPreferences("application", MODE_PRIVATE);
        SharedPreferences.Editor edit = application.edit();
        edit.putString("Token", token);
        edit.commit();
    }


    /**
     * 读取token信息
     */
    public String readTokenInfo() {
        SharedPreferences application = context.getSharedPreferences("application", MODE_PRIVATE);
        String token = application.getString("Token", null);
        return token;
    }

}
