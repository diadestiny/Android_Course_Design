package com.guet.shareapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guet.shareapp.Adapter.DiscoverAdapter;
import com.guet.shareapp.Adapter.OnItemClickListener;
import com.guet.shareapp.Adapter.PublishAdapter;
import com.guet.shareapp.Adapter.TypeAdapter;
import com.guet.shareapp.R;
import com.guet.shareapp.Utils.ToastUtil;

import java.io.IOException;

public class TypeFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    TypeAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_type, container, false);
        recyclerView = view.findViewById(R.id.recycle1);
        initRecyclerview();
        return view;
    }

    private void initRecyclerview() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        adapter = new TypeAdapter(getContext());
        recyclerView.setAdapter(adapter);
    }
}
