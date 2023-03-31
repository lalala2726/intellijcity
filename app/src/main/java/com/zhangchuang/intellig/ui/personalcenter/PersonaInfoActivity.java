package com.zhangchuang.intellig.ui.personalcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhangchuang.intellig.R;
import com.zhangchuang.intellig.network.NetworkUtils;
import com.zhangchuang.intellig.utils.ConfigUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonaInfoActivity extends AppCompatActivity {

    private ImageView userHeadImage;
    private TextView userNickName;
    private TextView userName;
    private TextView userAccount;
    private TextView userGender;
    private TextView userPhoneNumber;
    private TextView userEmail;

    private ConfigUtils configUtils;

    private NetworkUtils networkUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personainfo);
        initView();
        getUserInfoByNetwork();
    }

    public void initView() {
        setTitle("个人信息");
        userHeadImage = findViewById(R.id.info_user_head_image);
        userNickName = findViewById(R.id.info_user_nick_name);
        userName = findViewById(R.id.info_username);
        userAccount = findViewById(R.id.info_user_account);
        userGender = findViewById(R.id.info_user_gender);
        userPhoneNumber = findViewById(R.id.info_user_phonenumber);
        userEmail = findViewById(R.id.info_user_email);
        configUtils = new ConfigUtils(getApplicationContext());
        networkUtils = new NetworkUtils(getApplicationContext());

    }


    /**
     * 通过网络获取用户信息
     */
    public void getUserInfoByNetwork() {
        String token = configUtils.readTokenInfo();
        networkUtils.getUserInfo(token, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("SUCCESS","GET回调成功--->"+response.toString());
                try {
                    String string = response.body().string();
                    JSONObject jsonObject = new JSONObject(string);
                    if (jsonObject.getInt("code") == 200){
                        String user = jsonObject.getString("user");
                        displayInfo(user);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    /**
     * 展示信息
     * @param json
     */
    public void displayInfo(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        String userName1 = jsonObject.getString("userName");
        userName.setText(userName1);

        String userId = jsonObject.getString("userId");
        userAccount.setText(userId);

        int sex = jsonObject.getInt("sex");
        if (sex == 1){
            userGender.setText("女");
        }

        String email = jsonObject.getString("email");
        if (!"".equals(email)){
            userEmail.setText(email);
        }

        String phonenumber = jsonObject.getString("phonenumber");
        userPhoneNumber.setText(phonenumber);

        userNickName.setText(userName1);

        String avatar = jsonObject.getString("avatar");
        Glide.with(getApplicationContext()).load(configUtils.readNetworkInfo() + avatar).into(userHeadImage);


    }

}