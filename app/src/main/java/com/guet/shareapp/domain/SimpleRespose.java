package com.guet.shareapp.domain;

public class SimpleRespose {

    /**
     * code : 200
     * massage : 登录成功
     */

    private int code;
    private String massage;

    /*
    200: 成功
    400： 失败
     */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
