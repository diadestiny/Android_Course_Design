package com.guet.shareapp.Common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guet.shareapp.Fragment.HomePageFragment;
import com.guet.shareapp.R;

import com.guet.shareapp.Utils.ToastUtil;
import com.guet.shareapp.View.CircleImageView;





public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    CircleImageView mUserAvatarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        initFragments();
        initNavigationView();

    }



    private void initNavigationView() {
        
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        mUserAvatarView = (CircleImageView) headerView.findViewById(R.id.user_avatar_view);
        TextView mUserName = (TextView) headerView.findViewById(R.id.user_name);
        //设置头像
        Glide.with(MainActivity.this)
                .load("https://www.2020agc.site/user/show_avatar/"+LoginActivity.user_name)
                .fitCenter()
                .into(mUserAvatarView);
        //设置用户名
        mUserName.setText(LoginActivity.user_name);

    }

    private void initFragments() {
        HomePageFragment mHomePageFragment = HomePageFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, mHomePageFragment)
                .show(mHomePageFragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (menuItem.getItemId()){
            case R.id.item_chat:
                // 悦享聊天
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
                return true;
            case R.id.item_home:
                startActivity(new Intent(MainActivity.this, MyInfoActivity.class));
                return true;
            case R.id.item_about:
                AlertDialog salertDialog = new AlertDialog.Builder(this).setTitle("悦享开发者")
                        .setMessage("1800300925林楷浩\n1800300909陈澳格\n1800301536谢文韬" )
                        .setIcon(R.drawable.logo)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ToastUtil.ShortToast("感谢支持");
                            }
                        }).create();
                salertDialog.show();
        }
        return false;
    }

    public void toggleDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(this)
                .load("https://www.2020agc.site/user/show_avatar/"+LoginActivity.user_name)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(mUserAvatarView);
    }

}