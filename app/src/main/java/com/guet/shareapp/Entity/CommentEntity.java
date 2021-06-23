package com.guet.shareapp.Entity;

import java.io.Serializable;

public class CommentEntity implements Serializable {
    private String avatar_url;
    private int comment_id;
    private String content;
    private String date;
    private String nickName;

    public CommentEntity(String avatar_url, int comment_id, String content, String date, String nickName) {
        this.avatar_url = avatar_url;
        this.comment_id = comment_id;
        this.content = content;
        this.date = date;
        this.nickName = nickName;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
