package com.guet.shareapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guet.shareapp.Entity.AlbumEntity;
import com.guet.shareapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Album_detail_Adapter extends RecyclerView.Adapter<Album_detail_Adapter.Album_ViewHolder> {
    Context context;
//    OnItemClickListener onItemClickListener;
    private List<String> list; // Uri地址
    HashMap<String, Integer> mp = new HashMap<>();

    public Album_detail_Adapter(Context context,List<String> list)
    {
        this.context = context;
        this.list = list;
    }

//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Album_detail_Adapter.Album_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false);
        final Album_detail_Adapter.Album_ViewHolder holder = new Album_detail_Adapter.Album_ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Album_detail_Adapter.Album_ViewHolder itemViewHolder, int i) {
        Glide.with(context)
                .load(list.get(i))
                .error(R.drawable.ic_load)
                .fitCenter()
                .into(itemViewHolder.mItemIcon);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Album_ViewHolder extends RecyclerView.ViewHolder
    {
        View itemView;

        ImageView mItemIcon;

        public Album_ViewHolder(View v)
        {
            super(v);
            itemView = v;
            mItemIcon = v.findViewById(R.id.item_img);
        }
    }

}
