package com.zhangchuang.intellig.ui.start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zhangchuang.intellig.MainActivity;
import com.zhangchuang.intellig.R;
import com.zhangchuang.intellig.entity.NetworkInfo;
import com.zhangchuang.intellig.ui.login.LoginActivity;
import com.zhangchuang.intellig.utils.ConfigUtils;

public class StartActivity extends AppCompatActivity {

    private ConfigUtils configUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();
        initView();
    }


    public void initView() {
        configUtils = new ConfigUtils(getApplicationContext());
        Log.i("TAG", "配置信息--->" + configUtils.readConfigInfo() + "\t登录信息--->" + configUtils.readLoginInfo());
        new Thread(() -> {
            try {
                Thread.sleep(10 * 100 * 1);
                //判断是否配置过信息
                if (configUtils.readConfigInfo()) {
                    //判断是否登录过
                    if (configUtils.readLoginInfo()) {
                        startActivity(new Intent(StartActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(StartActivity.this, LoginActivity.class));
                    }
                } else {
                    startActivity(new Intent(StartActivity.this, GuideActivity.class));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}