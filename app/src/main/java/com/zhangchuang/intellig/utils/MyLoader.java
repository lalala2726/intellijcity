package com.zhangchuang.intellig.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * @Author:Zhangchuang
 * @Date: 2023/3/22 22:02
 */
public class MyLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object o, ImageView imageView) {
        Glide.with(context).load(o).into(imageView);
    }
}
