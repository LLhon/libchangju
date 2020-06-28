package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class BaScreenEntity {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("text")
    private String text;

    @SerializedName("uri")
    private String uri;

    @SerializedName("time")
    private String time;

    @SerializedName("type")
    private String type;

    @SerializedName("ba_screen_type")
    private int baScreenType;

    @SerializedName("live_title")
    private String liveTitle;

    @SerializedName("live_type")
    private String liveType;

    @SerializedName("topic_url")
    private String topicURL;

    private boolean isClickShowBaPing;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isClickShowBaPing() {
        return isClickShowBaPing;
    }

    public void setClickShowBaPing(boolean clickShowBaPing) {
        isClickShowBaPing = clickShowBaPing;
    }

    public int getBaScreenType() {
        return baScreenType;
    }

    public void setBaScreenType(int baScreenType) {
        this.baScreenType = baScreenType;
    }

    public String getLiveTitle() {
        return liveTitle;
    }

    public void setLiveTitle(String liveTitle) {
        this.liveTitle = liveTitle;
    }

    public String getLiveType() {
        return liveType;
    }

    public void setLiveType(String liveType) {
        this.liveType = liveType;
    }

    public String getTopicURL() {
        return topicURL;
    }

    public void setTopicURL(String topicURL) {
        this.topicURL = topicURL;
    }

    @Override
    public String toString() {
        return "BaScreenEntity{" +
                "cmd='" + cmd + '\'' +
                ", text='" + text + '\'' +
                ", uri='" + uri + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", baScreenType=" + baScreenType +
                ", liveTitle='" + liveTitle + '\'' +
                ", liveType='" + liveType + '\'' +
                ", topicURL='" + topicURL + '\'' +
                ", isClickShowBaPing=" + isClickShowBaPing +
                '}';
    }
}
