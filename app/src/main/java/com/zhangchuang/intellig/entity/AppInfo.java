package com.zhangchuang.intellig.entity;

/**
 * @Author:Zhangchuang
 * @Date: 2023/3/29 9:54
 */
public class AppInfo {
    private final String name;

    private final int icon;

    public AppInfo(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }
}
