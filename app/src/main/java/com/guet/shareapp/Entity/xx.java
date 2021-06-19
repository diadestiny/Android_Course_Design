package com.guet.shareapp.Entity;

public class xx {//信息类
    public String text;//信息内容
    public int img;//头像的图片id
    public boolean zuo = true;//控制信息显示在左边还有右边，默认左边

    public xx(String s, int i) {
        text = s;
        img = i;
    }

    public xx(String s, int i, boolean b) {
        text = s;
        img = i;
        zuo = b;
    }
}