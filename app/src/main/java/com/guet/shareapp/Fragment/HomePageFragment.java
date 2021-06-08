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


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomePageFragment extends Fragment {
    ViewPager mViewPager;
    SlidingTabLayout mSlidingTab;
    private List<Fragment> fragments = new ArrayList<>();

    public HomePageFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        mViewPager = view.findViewById(R.id.view_pager);
        mSlidingTab = view.findViewById(R.id.sliding_tabs);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragments.add(new DiscoverFragment());
        fragments.add(new MessageFragment());
        initViewPager();
    }

    private void initViewPager() {
        HomePagerAdapter mHomeAdapter = new HomePagerAdapter(getChildFragmentManager(),fragments);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(mHomeAdapter);
        mSlidingTab.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }



}