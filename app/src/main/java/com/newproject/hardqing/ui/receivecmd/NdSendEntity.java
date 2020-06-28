package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

//禁言
public class NdSendEntity {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("username")
    private String username;

    @SerializedName("content")
    private String content;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
