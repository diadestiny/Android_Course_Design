package com.guet.shareapp.Entity;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String avatarPath;
    private String email;
    private String nickname;
    private String phone;
    private String selfIntro;

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSelfIntro() {
        return selfIntro;
    }

    public void setSelfIntro(String selfIntro) {
        this.selfIntro = selfIntro;
    }

    public UserInfo(String avatarPath, String email, String nickname, String phone, String selfIntro) {
        this.avatarPath = avatarPath;
        this.email = email;
        this.nickname = nickname;
        this.phone = phone;
        this.selfIntro = selfIntro;
    }
}
