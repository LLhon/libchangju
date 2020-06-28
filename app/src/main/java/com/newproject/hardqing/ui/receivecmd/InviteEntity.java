package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class InviteEntity {


    @SerializedName("cmd")
    private String cmd;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("content")
    private String content;

    @SerializedName("auser_id")
    private String auser_id;

    @SerializedName("lmid")
    private String lmid;

    public String getLmid() {
        return lmid;
    }

    public void setLmid(String lmid) {
        this.lmid = lmid;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuser_id() {
        return auser_id;
    }

    public void setAuser_id(String auser_id) {
        this.auser_id = auser_id;
    }
}
