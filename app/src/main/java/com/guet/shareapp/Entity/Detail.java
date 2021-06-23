package com.guet.shareapp.Entity;

import java.io.Serializable;

public class Detail implements Serializable {
    private String avatar_url;
    private String comment_url;
    private String created;
    private int downloadSum;
    private String nickName;
    private String pictureIntro;
    private String pictureName;
    private String picture_url;
    private int starSum;
    private String thumbnailPath;
    private boolean visible;

    public Detail(String avatar_url, String comment_url, String created, int downloadSum, String nickName, String pictureIntro, String pictureName, String picture_url, int starSum, String thumbnailPath, boolean visible) {
        this.avatar_url = avatar_url;
        this.comment_url = comment_url;
        this.created = created;
        this.downloadSum = downloadSum;
        this.nickName = nickName;
        this.pictureIntro = pictureIntro;
        this.pictureName = pictureName;
        this.picture_url = picture_url;
        this.starSum = starSum;
        this.thumbnailPath = thumbnailPath;
        this.visible = visible;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getComment_url() {
        return comment_url;
    }

    public void setComment_url(String comment_url) {
        this.comment_url = comment_url;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getDownloadSum() {
        return downloadSum;
    }

    public void setDownloadSum(int downloadSum) {
        this.downloadSum = downloadSum;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPictureIntro() {
        return pictureIntro;
    }

    public void setPictureIntro(String pictureIntro) {
        this.pictureIntro = pictureIntro;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public int getStarSum() {
        return starSum;
    }

    public void setStarSum(int starSum) {
        this.starSum = starSum;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
