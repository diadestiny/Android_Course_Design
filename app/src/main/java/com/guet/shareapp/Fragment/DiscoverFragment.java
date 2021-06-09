package com.guet.shareapp.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guet.shareapp.Adapter.DiscoverAdapter;
import com.guet.shareapp.Entity.ImageEntity;
import com.guet.shareapp.R;

import java.util.ArrayList;

public class DiscoverFragment extends Fragment {
    DiscoverAdapter adapter;
    View view;
    RecyclerView recyclerView;
    ArrayList<ImageEntity> discoverList;
    public static DiscoverFragment newIntance() {
        return new DiscoverFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_discover, container, false);
        initView();
        initData();
        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recycle);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }

    private void initData() {
        discoverList = new ArrayList<>();
        ImageEntity img = new ImageEntity();

        discoverList.add(img);
        adapter = new DiscoverAdapter(getContext(), discoverList);
        recyclerView.setAdapter(adapter);
    }


}
