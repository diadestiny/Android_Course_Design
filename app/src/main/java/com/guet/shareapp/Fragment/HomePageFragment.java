package com.guet.shareapp.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.tablayout.SlidingTabLayout;
import com.guet.shareapp.Adapter.HomePagerAdapter;
import com.guet.shareapp.Common.LoginActivity;
import com.guet.shareapp.Common.MainActivity;
import com.guet.shareapp.R;


import java.util.ArrayList;
import java.util.List;


public class HomePageFragment extends Fragment {
    ViewPager mViewPager;
    SlidingTabLayout mSlidingTab;
    LinearLayout navigation_layout;
    TextView user_name;
    ImageView head;
    private List<Fragment> fragments = new ArrayList<>();
    private int refresh_num = 0;
    public HomePageFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        mViewPager = view.findViewById(R.id.view_pager);
        mSlidingTab = view.findViewById(R.id.sliding_tabs);
        navigation_layout = view.findViewById(R.id.navigation_layout);
        user_name = view.findViewById(R.id.home_user_name);
        head = view.findViewById(R.id.toolbar_user_avatar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragments.add(new DiscoverFragment());
        fragments.add(new TypeFragment());
        fragments.add(new RecommendFragment());
        initViewPager();
        Glide.with(this)
                .load("https://www.2020agc.site/user/show_avatar/"+LoginActivity.user_name)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(head);
        user_name.setText(LoginActivity.user_name);
        navigation_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                if (activity instanceof MainActivity) {
                    ((MainActivity) activity).toggleDrawer();
                }
            }
        });
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

    @Override
    public void onResume() {
        super.onResume();
        if(refresh_num<=3){
            Glide.with(getContext())
                    .load("https://www.2020agc.site/user/show_avatar/"+LoginActivity.user_name)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(head);
        }
        refresh_num++;
    }
}