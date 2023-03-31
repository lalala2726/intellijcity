package com.zhangchuang.intellig.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhangchuang.intellig.R;
import com.zhangchuang.intellig.network.NetworkUtils;
import com.zhangchuang.intellig.utils.AppGridAdapter;
import com.zhangchuang.intellig.utils.ConfigUtils;
import com.zhangchuang.intellig.utils.MyLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private Banner mBanner;

    private NetworkUtils networkUtils;

    private GridView gridView;

    private ArrayList images;

    private ListView listView;
    private ConfigUtils configUtils;

    //以下的新闻列表信息
    private List<String> newsContent;
    private List<String> newsTitle;
    private List<String> newsCover;
    private List<String> newsPublishDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        getNewsListInfo();
        initGridView(view);
        return view;
    }

    /**
     * 初始化系统视图
     *
     * @param v
     */
    public void initView(View v) {
        listView = v.findViewById(R.id.list_view);
        newsCover = new ArrayList<>();
        newsContent = new ArrayList<>();
        newsPublishDate = new ArrayList<>();
        newsTitle = new ArrayList<>();
        configUtils = new ConfigUtils(getContext());
        gridView = v.findViewById(R.id.gridview);
        mBanner = v.findViewById(R.id.view_banner);
        images = new ArrayList<String>();
        networkUtils = new NetworkUtils(getContext());
        getSysAdsByNetwork();
    }

    /**
     * Banner轮播图相关
     */
    public void initBanner() {
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setImageLoader(new MyLoader());
        mBanner.setImages(images);
        mBanner.setBannerAnimation(Transformer.Accordion);
        mBanner.start();
    }


    /**
     * 网络获取轮播图信息
     */
    public void getSysAdsByNetwork() {
        networkUtils.getSysAds(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("SUCCESS", "GET回调成功--->" + response);
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        JSONObject jsonObject = new JSONObject(string);
                        String rows = jsonObject.getString("rows");
                        collateData(rows);
                    } catch (IOException e) {
                        Log.w("IOException", e.toString());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.w("ERROR", "GET回调失败--->" + throwable);
            }
        });
    }

    /**
     * 轮播图相关
     * 整理数据
     *
     * @param rows
     * @throws JSONException
     */

    public void collateData(String rows) throws JSONException {

        List list = new ArrayList<String>();
        JSONArray jsonArray = new JSONArray(rows);
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.get(i));
        }

        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject = (JSONObject) list.get(i);
            images.add(configUtils.readNetworkInfo() + jsonObject.getString("advImg"));
        }

        for (int i = 0; i < images.size(); i++) {
            System.out.println(images);
        }

        initBanner();
    }

    //主页列表图标设置
    public void initGridView(View v) {
        gridView.setAdapter(new AppGridAdapter(getContext()));
    }

    /**
     * 通过网络获取新闻列表详情
     */
    public void getNewsListInfo() {
        networkUtils.getNewsListInfo(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("SUCCESS", "GET回调成功--->" + response);
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        JSONObject jsonObject = new JSONObject(string);
                        String rows = jsonObject.getString("rows");
                        collateNewsList(rows);
                    } catch (IOException e) {
                        Log.e("IOException", "IO异常--->" + e);
                    } catch (JSONException e) {
                        Log.e("JSONException", "JSON解析异常--->" + e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.w("ERROR", "GET回调失败!错误信息--->" + throwable);
            }
        });
    }

    /**
     * 整理新闻列表信息
     */
    public void collateNewsList(String rows) throws JSONException {
        List list = new ArrayList();
        JSONArray jsonArray = new JSONArray(rows);
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.get(i));
        }
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject = (JSONObject) list.get(i);
            newsTitle.add(jsonObject.getString("title"));
            newsContent.add(jsonObject.getString("content"));
            newsCover.add(configUtils.readNetworkInfo() + jsonObject.getString("cover"));
            newsPublishDate.add(jsonObject.getString("publishDate"));

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newsCover.forEach(System.out::println);
        }
        listView.setAdapter(new ListViewAdapter());
        newsListView();

    }

    /**
     * 新闻列表相关信息
     */
    public void newsListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), NewsInfoActivity.class);
                intent.putExtra("title", newsTitle.get(position));
                intent.putExtra("content", newsContent.get(position));
                startActivity(intent);
            }
        });
    }



    private class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newsTitle.size();
        }

        @Override
        public Object getItem(int position) {
            return newsTitle.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //第一种方式直接创建
//            View view = View.inflate(getContext(),R.layout.newslist,null);
            //第二种方式(省内存)
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.newslist, parent, false);
            }

            TextView title = view.findViewById(R.id.news_title);
            title.setText(newsTitle.get(position));

            TextView publishDate = view.findViewById(R.id.news_publish);
            publishDate.setText(newsPublishDate.get(position));

            ImageView image = view.findViewById(R.id.news_images);

            Glide.with(getContext()).load(newsCover.get(position)).into(image);
            return view;

        }
    }
}