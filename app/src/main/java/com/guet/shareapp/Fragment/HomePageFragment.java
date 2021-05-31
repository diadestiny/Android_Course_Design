package com.guet.shareapp.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.guet.shareapp.Adapter.HomePagerAdapter;
import com.guet.shareapp.R;

import butterknife.BindView;


public class HomePageFragment extends Fragment {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTab;


    public HomePageFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initViewPager();
    }

//    private void initViewPager() {
//        HomePagerAdapter mHomeAdapter = new HomePagerAdapter(getChildFragmentManager(), getApplicationContext());
//        mViewPager.setOffscreenPageLimit(5);
//        mViewPager.setAdapter(mHomeAdapter);
//        mSlidingTab.setViewPager(mViewPager);
//        mViewPager.setCurrentItem(1);
//    }

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }
}