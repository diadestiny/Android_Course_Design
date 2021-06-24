package com.guet.shareapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.guet.shareapp.R;
import com.guet.shareapp.Utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import static com.guet.shareapp.Utils.Download.savePicture;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    private List<String> list;
    private Context context;
    private String search_name;

    public SearchAdapter(Context context, List<String>List,String search_name)
    {
        this.list = List;
        this.context = context;
        this.search_name = search_name;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position)
    {
        //展示的图片
        Glide.with(context)
                .load(list.get(position))
                .error(R.drawable.ic_load)
                .fitCenter()
                .into(holder.ImgView);

    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.download.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = holder.getAdapterPosition();
                //下载
               savePicture(search_name+position+".jpg",list.get(position),context);

            }
        });


        return holder;
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View itemView;
        ImageView ImgView;
        TextView item_title;
        Button download;

        public ViewHolder(View v)
        {
            super(v);
            itemView = v;
            ImgView = v.findViewById(R.id.item_img);
            item_title = v.findViewById(R.id.item_title);
            download = v.findViewById(R.id.item_btn);
        }
    }



}
