package com.zhangchuang.intellig.entity;

/**
 * 用户登录实体类用于登录
 *
 * @Author:Zhangchuang
 * @Date: 2023/3/27 16:24
 */
public class LoginAccount {
    private String username;
    private String password;

    public LoginAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
