package com.zhangchuang.intellig.entity;

/**
 * 通用数据接收
 *
 * @Author:Zhangchuang
 * @Date: 2023/3/28 14:30
 */
public class Data<T> {

    private Integer code;

    private String msg;

    private String token;

    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
