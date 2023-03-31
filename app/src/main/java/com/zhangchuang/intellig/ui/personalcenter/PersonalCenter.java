package com.zhangchuang.intellig.ui.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.zhangchuang.intellig.R;
import com.zhangchuang.intellig.network.NetworkUtils;
import com.zhangchuang.intellig.ui.login.LoginActivity;
import com.zhangchuang.intellig.utils.ConfigUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalCenter extends Fragment {
    private TextView userNickName;
    private ImageView headImage;

    private ConfigUtils configUtils;

    private NetworkUtils networkUtils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        initView(view);
        getUserInfoByNetwork();
        return view;
    }

    public void initView(View v) {
        //跳转个人信息页面
        v.findViewById(R.id.personal_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),PersonaInfoActivity.class));
            }
        });
        //跳转到钱包页面
        v.findViewById(R.id.wallet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        v.findViewById(R.id.order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //跳转到修改密码
        v.findViewById(R.id.update_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //跳转到意见反馈
        v.findViewById(R.id.feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //退出登录
        v.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        headImage = v.findViewById(R.id.user_head_image);
        userNickName = v.findViewById(R.id.user_nick_name);
        configUtils = new ConfigUtils(getContext());
        networkUtils = new NetworkUtils(getContext());
    }

    /**
     * 通过网络获取用户信息
     */
    public void getUserInfoByNetwork() {
        String token = configUtils.readTokenInfo();
        networkUtils.getUserInfo(token, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("SUCCESS", "GET回调成功--->" + response);
                try {
                    String string = response.body().string();
                    JSONObject jsonObject = new JSONObject(string);
                    if (jsonObject.getInt("code") == 200) {
                        String user = jsonObject.getString("user");
                        collateData(user);
                    }
                } catch (IOException e) {
                    Log.w("IOException", e.toString());
                } catch (JSONException e) {
                    Log.w("JSONException", e.toString());
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.w("WARNING", "GET回调失败--->" + throwable);
            }
        });
    }


    /**
     * 整理数据
     *
     * @param json
     */
    public void collateData(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String userName = jsonObject.getString("userName");
        String avatar = jsonObject.getString("avatar");

        userNickName.setText(userName);
        Glide.with(getContext()).load(configUtils.readNetworkInfo() + avatar).into(headImage);
    }


}