package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class ReceiveLeadRedEntity {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("msg_id")
    private String msg_id;

    @SerializedName("room_id")
    private String room_id;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("username")
    private String username;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("amount")
    private String amount;

    @SerializedName("create_time")
    private String create_time;
    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
