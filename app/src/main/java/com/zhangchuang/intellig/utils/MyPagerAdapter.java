package com.zhangchuang.intellig.utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    private List<View> mViewList;

    public MyPagerAdapter(List<View> viewList) {
        mViewList = viewList;
    }

    //返回ViewPager中所有View的数量
    @Override
    public int getCount() {
        return mViewList.size();
    }

    //判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //返回当前View的位置
    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    //初始化ViewPager条目
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViewList.get(position);
        container.addView(view);
        return view;
    }

    //移除ViewPager条目
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }
}
