package com.zhangchuang.intellig.ui.function;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangchuang.intellig.R;
import com.zhangchuang.intellig.network.api.SystemService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChatActivity extends AppCompatActivity {

    private Retrofit mRetrofit;

    private TextView chatText;

    private EditText editText;
    private static final String SERVER_ADDRESS = "http://192.168.3.52:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        findViewById(R.id.button_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = editText.getText().toString();
                editText.setText("");
                Toast.makeText(ChatActivity.this, "已发送", Toast.LENGTH_SHORT).show();
                chatText.setText("请稍后~~~");
                sendInfoByNetwork(info);
            }
        });
    }

    public void initView() {
        setTitle("ChatGTP");
        chatText = findViewById(R.id.chat_text);
        editText = findViewById(R.id.edit_text);
    }

    public void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(SERVER_ADDRESS)
                .build();
    }


    public void sendInfoByNetwork(String content) {
        Log.d("SUCCESS", "执行一次网络请求");
        initRetrofit();
        SystemService systemService = mRetrofit.create(SystemService.class);
        Call<ResponseBody> chat = systemService.chat(content);
        chat.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("POST回调成功", response.toString());
                try {
                    String string = response.body().string();
                    Log.i("SUCCESS", "返回的信息--->" + string);
                    displayInfo(string);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.w("POST回调失败", throwable.toString());
            }
        });
    }

    public void displayInfo(String info) {
        chatText.setText(info);
    }

}