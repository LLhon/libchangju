package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

//禁言/取消禁言
public class ManageEntity {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("room_id")
    private String room_id;

    @SerializedName("auser_id")
    private String auser_id;

    @SerializedName("username")
    private String username;

    @SerializedName("content")
    private String content;

    @SerializedName("type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getAuser_id() {
        return auser_id;
    }

    public void setAuser_id(String auser_id) {
        this.auser_id = auser_id;
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
