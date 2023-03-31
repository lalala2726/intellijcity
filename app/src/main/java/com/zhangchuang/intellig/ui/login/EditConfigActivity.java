package com.zhangchuang.intellig.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zhangchuang.intellig.R;
import com.zhangchuang.intellig.ui.start.StartActivity;
import com.zhangchuang.intellig.utils.ConfigUtils;

public class EditConfigActivity extends AppCompatActivity {

    private EditText editTextIPAddress;
    private EditText editTextPort;

    private ConfigUtils configUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editconfig);
        initView();
        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipAddress = editTextIPAddress.getText().toString();
                String port = editTextPort.getText().toString();
                if ("".equals(ipAddress) || "".equals(port)) {
                    Toast.makeText(EditConfigActivity.this, "请填写完整！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i("TAG", "IP地址和端口: " + ipAddress + " " + port);
                configUtils.saveNetworkInfo(ipAddress, port);
                configUtils.saveConfigInfo(true);
                Toast.makeText(EditConfigActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), StartActivity.class));
            }
        });
    }


    /**
     * 初始化视图
     */
    public void initView() {
        configUtils = new ConfigUtils(getApplicationContext());
        editTextIPAddress = findViewById(R.id.edit_ipaddress);
        editTextPort = findViewById(R.id.edit_port);
    }
}