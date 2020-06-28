package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

//进入房间
public class InRoomEntity {
    @SerializedName("cmd")
    private String cmd;

    //上次 0没强退 1强退
    @SerializedName("is_out")
    private String isOut;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("username")
    private String username;

    @SerializedName("avatar")
    private String avatar;
    //1 禁言 2 正常
    @SerializedName("nd_send")
    private String ndSend;

    //1 是 2 否
    @SerializedName("kick")
    private String kick;

    // 1 管理 2 否
    @SerializedName("is_manage")
    private String is_manage;


    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getIsOut() {
        return isOut;
    }

    public void setIsOut(String isOut) {
        this.isOut = isOut;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNdSend() {
        return ndSend;
    }

    public void setNdSend(String ndSend) {
        this.ndSend = ndSend;
    }

    public String getKick() {
        return kick;
    }

    public void setKick(String kick) {
        this.kick = kick;
    }

    public String getIs_manage() {
        return is_manage;
    }

    public void setIs_manage(String is_manage) {
        this.is_manage = is_manage;
    }
}
