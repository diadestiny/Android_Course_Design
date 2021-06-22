package com.guet.shareapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.guet.shareapp.Interface.OnItemLongListener;
import com.guet.shareapp.R;

import java.util.HashMap;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.Album_ViewHolder> {
    Context context;
    OnItemLongListener onItemLongListener;
    private List<String> list; // Uri地址
    HashMap<String, Integer> mp = new HashMap<>();

    public AlbumAdapter(Context context, List<String> list)
    {
        this.context = context;
        this.list = list;
    }

    public void setOnItemLongListener(OnItemLongListener onItemLongListener) {
        this.onItemLongListener = onItemLongListener;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AlbumAdapter.Album_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false);
        final AlbumAdapter.Album_ViewHolder holder = new AlbumAdapter.Album_ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.Album_ViewHolder itemViewHolder, int i) {
        Glide.with(context)
                .load(list.get(i))
                .error(R.drawable.ic_load)
                .fitCenter()
                .into(itemViewHolder.mItemIcon);
        itemViewHolder.mItemIcon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int pos = itemViewHolder.getLayoutPosition();
                onItemLongListener.onItemLongClick(itemViewHolder.itemView,pos);
                return true;
            }
        });
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
