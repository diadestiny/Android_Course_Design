package com.guet.shareapp.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageEntity implements Serializable
{
    @SerializedName("pic_id")
    private String imgID;
    private String authorID;
    @SerializedName("pic_name")
    private String displayImgName;
    @SerializedName("pic_starSum")
    private int    starNum;
    @SerializedName("username")
    private String authorName;
    @SerializedName("avatar_url")
    private String authorProfileImgUrl;
    @SerializedName("is_star")
    private boolean isStared;
    @SerializedName("pic_url")
    private String thumbnailUrl;

    public ImageEntity() {}

    public ImageEntity(String imgID, String authorID, String displayImgName, int starNum,
                       String authorName, String authorProfileImgUrl, boolean isStared,
                       String thumbnailUrl){
        this.imgID = imgID;
        this.authorID = authorID;
        this.displayImgName = displayImgName;
        this.starNum = starNum;
        this.authorName = authorName;
        this.authorProfileImgUrl = authorProfileImgUrl;
        this.isStared = isStared;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getThumbnailUrl()
    {
        return thumbnailUrl;
    }

    public String getAuthorID()
    {
        return authorID;
    }


    public String getImgID()
    {
        return imgID;
    }


    public String getDisplayImgName()
    {
        if (displayImgName.length() > 20)
            return displayImgName.substring(0, 9) + "···";
        return displayImgName;
    }

    public int getStarNum()
    {
        return starNum;
    }

    public String getAuthorName()
    {
        return authorName;
    }

    public String getAuthorProfileImgUrl()
    {
        return authorProfileImgUrl;
    }
    public boolean getStared(){
        return isStared;
    }

    public void setStared(boolean stared)
    {
        isStared = stared;
    }

    public void setStarNum(int starNum)
    {
        this.starNum = starNum;
    }

}
