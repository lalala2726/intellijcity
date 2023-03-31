package com.zhangchuang.intellig.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.zhangchuang.intellig.R;
import com.zhangchuang.intellig.utils.ConfigUtils;

public class NewsInfoActivity extends AppCompatActivity {
    private WebView webView;
    private ConfigUtils configUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);
        initView();
    }

    /**
     * 初始化视图
     */
    public void initView() {
        configUtils = new ConfigUtils(getApplicationContext());
        webView = findViewById(R.id.web_view);
        //接收数据
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        setTitle(title);
        content = content.replace("src=\"", "src=\"" + configUtils.readNetworkInfo());
        Log.e("SUCCESS", "传递的信息--->" + title);
        Log.e("SUCCESS", "传递的信息--->" + content);
        //WebView加载本地富文本
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
    }
}