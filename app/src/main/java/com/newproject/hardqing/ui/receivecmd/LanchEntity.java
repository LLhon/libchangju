package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class LanchEntity {
    @SerializedName("cmd")
    private String cmd;

    @SerializedName("room_id")
    private String room_id;

    @SerializedName("room_no")
    private String roomNo;

    @SerializedName("category_id")
    private int category_id;

    @SerializedName("category_uri")
     private String category_uri;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int value) {
        this.category_id = value;
    }

    public String getCategory_uri() {
        return category_uri;
    }

    public void setCategory_uri(String value) {
        this.category_uri = value;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String fd) {
        this.room_id = room_id;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }
}
