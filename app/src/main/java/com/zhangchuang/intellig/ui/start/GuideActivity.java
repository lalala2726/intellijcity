package com.zhangchuang.intellig.ui.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.zhangchuang.intellig.R;
import com.zhangchuang.intellig.ui.login.EditConfigActivity;
import com.zhangchuang.intellig.utils.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private LinearLayout mLayoutDots;
    private ImageView[] mDots;

    private List<View> mViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        getSupportActionBar().hide();
        initView();
        initDots();
    }

    private void initView() {
        mViewList = new ArrayList<>();
        mViewPager = findViewById(R.id.view_pager);
        LayoutInflater inflater = LayoutInflater.from(GuideActivity.this);
        View view1 = inflater.inflate(R.layout.activity_one, null);
        View view2 = inflater.inflate(R.layout.activity_two, null);
        View view3 = inflater.inflate(R.layout.activity_three, null);
        View view4 = inflater.inflate(R.layout.activity_four, null);
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);
        view4.findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditConfigActivity.class));
            }
        });
        mViewPager.setAdapter(new MyPagerAdapter(mViewList));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //初始化圆点指示器
    private void initDots() {
        mLayoutDots = findViewById(R.id.dot_layout);
        mDots = new ImageView[mViewList.size()];
        for (int i = 0; i < mViewList.size(); i++) {
            mDots[i] = new ImageView(this);
            mDots[i].setImageResource(R.drawable.circle1);
            mDots[i].setLayoutParams(new LinearLayout.LayoutParams(dpToPx(30), dpToPx(30)));
            mDots[i].setEnabled(true);
            mLayoutDots.addView(mDots[i]);
        }
        mDots[0].setEnabled(false);
    }

    //设置选中状态的圆点
    private void selectDot(int position) {
        for (int i = 0; i < mViewList.size(); i++) {
            if (i == position) {
                mDots[i].setImageResource(R.drawable.circle);
                mDots[i].setLayoutParams(new LinearLayout.LayoutParams(dpToPx(30), dpToPx(30)));
                mDots[i].setEnabled(false);
            } else {
                mDots[i].setImageResource(R.drawable.circle1);
                mDots[i].setLayoutParams(new LinearLayout.LayoutParams(dpToPx(30), dpToPx(30)));
                mDots[i].setEnabled(true);
            }
        }
    }

    //将dp值转换为px值
    private int dpToPx(int dpValue) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }
}
