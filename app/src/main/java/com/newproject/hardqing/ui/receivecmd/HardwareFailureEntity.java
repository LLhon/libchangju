package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

/**
 * 收到退出直播间
 */
public class HardwareFailureEntity {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("room_id")
    private String room_id;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }
}
