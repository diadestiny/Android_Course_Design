package com.guet.shareapp.Entity;

import java.io.Serializable;
import java.util.List;

public class HotTag implements Serializable{
    private int code;
    private String seid;
    private String message;
    private int timestamp;
    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String keyword;
        private String status;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public HotTag(int code, String seid, String message, int timestamp, List<ListBean> list) {
        this.code = code;
        this.seid = seid;
        this.message = message;
        this.timestamp = timestamp;
        this.list = list;
    }
}
