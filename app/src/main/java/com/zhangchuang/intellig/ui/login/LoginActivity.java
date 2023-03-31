package com.zhangchuang.intellig.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.zhangchuang.intellig.MainActivity;
import com.zhangchuang.intellig.R;
import com.zhangchuang.intellig.entity.Data;
import com.zhangchuang.intellig.entity.LoginAccount;
import com.zhangchuang.intellig.network.NetworkUtils;
import com.zhangchuang.intellig.utils.ConfigUtils;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextAccount;
    private EditText editTextPassword;


    private NetworkUtils network;

    private ConfigUtils configUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        configUtils = new ConfigUtils(getApplicationContext());
        network = new NetworkUtils(getApplicationContext());
        setTitle("登录");
        editTextAccount = findViewById(R.id.edit_account);
        editTextPassword = findViewById(R.id.edit_password);
        findViewById(R.id.button_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextAccount.getText().toString();
                String password = editTextPassword.getText().toString();
                if ("".equals(username) || "".equals(password)) {
                    Toast.makeText(LoginActivity.this, "请填写完整!", Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginAccount loginAccount = new LoginAccount(username, password);
                networkLogin(new Gson().toJson(loginAccount));

            }
        });
    }


    /**
     * 网络登录
     */
    public void networkLogin(String json) {
        Log.i("INFO", "转换的JSON信息--->" + json);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        network.userLogin(body, new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {
                    Data data = response.body();
                    if (data == null) return;
                    configUtils.saveTokenInfo(data.getToken());
                    Toast.makeText(LoginActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    configUtils.saveLoginInfo(true);
                    return;
                }
                Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable throwable) {
                Log.e("ERROR", "POST回调失败--->" + throwable.getMessage());
            }
        });

    }

}