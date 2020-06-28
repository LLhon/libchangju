package com.newproject.hardqing.entity;

/**
 * Created by Administrator on 2018/5/23.
 */

public class RefreshToken {
    //200 刷新token
    public int code;
    public String url;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
