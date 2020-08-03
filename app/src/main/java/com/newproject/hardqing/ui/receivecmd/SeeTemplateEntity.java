package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class SeeTemplateEntity {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("type")
    private String type;

    @SerializedName("music")
    private String music;

    @SerializedName("uri")
    private String uri;

    @SerializedName("cover")
    private String cover;

    @SerializedName("lenth")
    private String lenth;

    @SerializedName("time")
    private String time;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLenth() {
        return lenth;
    }

    public void setLenth(String lenth) {
        this.lenth = lenth;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override public String toString() {
        return "SeeTemplateEntity{" +
            "cmd='" + cmd + '\'' +
            ", type='" + type + '\'' +
            ", music='" + music + '\'' +
            ", uri='" + uri + '\'' +
            ", cover='" + cover + '\'' +
            ", lenth='" + lenth + '\'' +
            ", time='" + time + '\'' +
            '}';
    }
}
