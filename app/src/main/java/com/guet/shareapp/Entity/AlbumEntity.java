package com.guet.shareapp.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AlbumEntity implements Serializable {

    private String comment_url;
    private String created;
    private int    downloadSum;
    private int    pictureIntro;
    private String pictureName;
    private String starSum;
    private String thumbnailPath;
    private String visible;
    private String picture_url;

    public AlbumEntity(String comment_url, String created, int downloadSum, int pictureIntro, String pictureName, String starSum, String thumbnailPath, String visible) {
        this.comment_url = comment_url;
        this.created = created;
        this.downloadSum = downloadSum;
        this.pictureIntro = pictureIntro;
        this.pictureName = pictureName;
        this.starSum = starSum;
        this.thumbnailPath = thumbnailPath;
        this.visible = visible;
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

    public int getPictureIntro() {
        return pictureIntro;
    }

    public void setPictureIntro(int pictureIntro) {
        this.pictureIntro = pictureIntro;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getStarSum() {
        return starSum;
    }

    public void setStarSum(String starSum) {
        this.starSum = starSum;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

}
