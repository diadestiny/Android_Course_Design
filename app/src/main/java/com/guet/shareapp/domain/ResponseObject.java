package com.guet.shareapp.domain;

import java.io.Serializable;

public class ResponseObject<T> implements Serializable {

    /**
     * code : 200
     * massage : 登录成功
     */

    private int code;
    private String message;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
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

    @Override
    public String toString() {
        return "ResponeObject{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
