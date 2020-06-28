package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

/**
 * @author LLhon
 * @description
 * @date 2019/8/27 11:58
 */
public class UpdateRoomEntity {

    //{"cmd":"update_room","broadcast_cmd":"update_room","title":"\u6709\u4f55\u8d35\u5e72\u597d","room_id":1,"cover":"\/uploads\/live\/20190827\/0c183be622cbfc9e65680d971bf33563143.JPG","fd":34200,"device":"web"}

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("broadcast_cmd")
    private String broadcast_cmd;

    @SerializedName("title")
    private String title;

    @SerializedName("room_id")
    private String room_id;

    @SerializedName("cover")
    private String cover;

    @SerializedName("fd")
    private String fd;

    @SerializedName("device")
    private String device;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getBroadcast_cmd() {
        return broadcast_cmd;
    }

    public void setBroadcast_cmd(String broadcast_cmd) {
        this.broadcast_cmd = broadcast_cmd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getFd() {
        return fd;
    }

    public void setFd(String fd) {
        this.fd = fd;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
