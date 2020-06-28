package com.newproject.hardqing.entity;

import com.google.gson.annotations.SerializedName;

public class SunSongRequest {

    @SerializedName("platcode")
    private String platcode;
    @SerializedName("startpage")
    private int startpage;
    @SerializedName("pagesize")
    private int pagesize;
    @SerializedName("search")
    private String search;
    @SerializedName("timestamp")
    private long timestamp;
    @SerializedName("noncestr")
    private String noncestr;
    @SerializedName("sign")
    private String sign;
    @SerializedName("theme")
    private int theme;
    @SerializedName("region")
    private String region;

    public String getPlatcode() {
        return platcode;
    }

    public void setPlatcode(String platcode) {
        this.platcode = platcode;
    }

    public int getStartpage() {
        return startpage;
    }

    public void setStartpage(int startpage) {
        this.startpage = startpage;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "SunSongRequest{" +
                "platcode='" + platcode + '\'' +
                ", startpage=" + startpage +
                ", pagesize=" + pagesize +
                ", search='" + search + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", sign='" + sign + '\'' +
                ", theme=" + theme +
                ", region='" + region + '\'' +
                '}';
    }
}
