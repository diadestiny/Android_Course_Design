package com.guet.shareapp.domain;

public class SimpleResponse {

    /**
     * code : 200
     * massage : 登录成功
     */

    private int code;
    private String message;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
