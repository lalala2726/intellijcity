package com.zhangchuang.intellig.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangchuang.intellig.R;
import com.zhangchuang.intellig.entity.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Zhangchuang
 * @Date: 2023/3/29 9:47
 */
public class AppGridAdapter extends BaseAdapter {
    private final Context context;
    private final List<AppInfo> mAppList;

    public AppGridAdapter(Context context) {
        this.context = context;
        mAppList = new ArrayList();

        mAppList.add(new AppInfo("生活资讯", R.drawable.life));
        mAppList.add(new AppInfo("智慧巴士", R.drawable.bus));
        mAppList.add(new AppInfo("门诊预约", R.drawable.hospital));
        mAppList.add(new AppInfo("智慧交管", R.drawable.car));
        mAppList.add(new AppInfo("看电影", R.drawable.watchmovie));
        mAppList.add(new AppInfo("找房子", R.drawable.lookinghouse));
        mAppList.add(new AppInfo("找工作", R.drawable.lookingwork));
        mAppList.add(new AppInfo("数据分析", R.drawable.dataanalysis));
    }

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        }

        AppInfo appInfo = mAppList.get(position);

        ImageView image = view.findViewById(R.id.grid_image);
        image.setImageResource(appInfo.getIcon());

        TextView text = view.findViewById(R.id.grid_text);
        text.setText(appInfo.getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //处理点击事件
                Toast.makeText(context, "点击" + appInfo.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }
}
