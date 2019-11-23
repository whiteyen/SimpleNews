package com.bilibili.news.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bilibili.news.fragment.CaijingFragment;
import com.bilibili.news.fragment.EntertainmentFragment;
import com.bilibili.news.fragment.JunshiFragment;
import com.bilibili.news.fragment.KejiFragment;
import com.bilibili.news.fragment.ShehuiFragment;
import com.bilibili.news.fragment.TiyuFragment;
import com.bilibili.news.fragment.ToutiaoFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"头条","社会","娱乐","体育","军事","科技"};
    public MainPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int i) {
        if(i==0){
            return new ToutiaoFragment();
        }
        else if(i==1){
            return new ShehuiFragment();
        }
        else if(i==2){
            return new EntertainmentFragment();
        }
        else if(i==3){
            return new TiyuFragment();
        }
        else if(i==4){
            return new JunshiFragment();
        }
        else if(i==5){
            return new KejiFragment();
        }
        else {
            return new CaijingFragment();
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
