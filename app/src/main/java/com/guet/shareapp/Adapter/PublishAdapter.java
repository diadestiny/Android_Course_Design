package com.guet.shareapp.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guet.shareapp.Entity.ImageEntity;
import com.guet.shareapp.R;

import java.util.List;

public class PublishAdapter extends RecyclerView.Adapter<PublishAdapter.ViewHolder> {

    List<String> list;
    Context context;
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener monItemClickListener1) {
        this.onItemClickListener = monItemClickListener1;
    }

    public PublishAdapter(Context context, List<String>List)
    {
        this.list = List;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        final PublishAdapter.ViewHolder holder = new PublishAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (list.get(i).equals("add")){
            viewHolder.ImgView.setImageResource(R.mipmap.ic_add);
            viewHolder.ImgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        int pos = viewHolder.getLayoutPosition();
                        onItemClickListener.onItemClick(viewHolder.itemView,pos);
                    }
                }
            });
        }else{
            Glide.with(context)
                    .load(list.get(i))
                    .into(viewHolder.ImgView);
            if(!list.get(i).equals("add")){
                viewHolder.delView.setImageResource(R.mipmap.pic_del);
            }
            viewHolder.delView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        int pos = viewHolder.getLayoutPosition();
                        onItemClickListener.onItemClick(viewHolder.itemView,pos);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() { return list.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ImgView;
        ImageView delView;
        public ViewHolder(View itemView)
        {
            super(itemView);
            ImgView = itemView.findViewById(R.id.ivImage);
            delView = itemView.findViewById(R.id.ivDelete);
        }
    }



}
