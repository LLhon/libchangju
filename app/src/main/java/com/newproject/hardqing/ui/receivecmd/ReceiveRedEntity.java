package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class ReceiveRedEntity {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("msg_id")
    private String msg_id;

    @SerializedName("room_id")
    private String room_id;

    @SerializedName("code")
    private String code;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("content")
    private String content;

    @SerializedName("currency")
    private String currency;

    @SerializedName("money")
    private String money;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
