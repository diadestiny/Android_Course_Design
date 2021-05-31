package com.guet.shareapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.guet.shareapp.Fragment.HomePageFragment;

public class MainActivity extends AppCompatActivity {

    private HomePageFragment mHomePageFragment;
    private Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();

    }

    private void initFragments() {
        mHomePageFragment = HomePageFragment.newInstance();
        fragments = new Fragment[]{
                mHomePageFragment
        };
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, mHomePageFragment)
                .show(mHomePageFragment).commit();
    }
}