package com.newproject.hardqing.entity;

import com.google.gson.annotations.SerializedName;

public class QrcodeBean {

    @SerializedName("type")
    private String type;
    @SerializedName("content")
    private String content;
    @SerializedName("fd")
    private String fd;
    @SerializedName("channel_id")
    private int channelId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFd() {
        return fd;
    }

    public void setFd(String fd) {
        this.fd = fd;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return "QrcodeBean{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", fd='" + fd + '\'' +
                ", channelId=" + channelId +
                '}';
    }
}
