package com.guet.shareapp.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.guet.shareapp.Fragment.DiscoverFragment;

import com.guet.shareapp.R;

import java.util.List;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES = {"发现","相册","推荐"};
    private List<Fragment> fragments ;
    public HomePagerAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.fragments = list;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
