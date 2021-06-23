package com.guet.shareapp.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.guet.shareapp.Entity.CommentEntity;
import com.guet.shareapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{


    private Context context;
    private List<CommentEntity> commentEntities = new ArrayList<>();

    public CommentAdapter(Context context, List<CommentEntity> commentEntities)
    {
        this.context = context;
        this.commentEntities = commentEntities;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position)
    {
        //展示评论头像
        Glide.with(context)
                .load(commentEntities.get(position).getAvatar_url())
                .error(R.drawable.head)
                .fitCenter()
                .into(holder.iv_author);
        holder.tv_name_item.setText(commentEntities.get(position).getNickName());
        holder.tv_comment.setText(commentEntities.get(position).getContent());
        holder.tv_time.setText(commentEntities.get(position).getDate());

    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount()
    {
        return commentEntities.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View itemView;
        ImageView iv_author;
        TextView tv_name_item,tv_comment,tv_time;

        public ViewHolder(View v)
        {
            super(v);
            itemView = v;
            iv_author = v.findViewById(R.id.iv_author);
            tv_name_item = v.findViewById(R.id.tv_name_item);
            tv_comment = v.findViewById(R.id.tv_comment);
            tv_time =  v.findViewById(R.id.tv_time);
        }
    }


}
