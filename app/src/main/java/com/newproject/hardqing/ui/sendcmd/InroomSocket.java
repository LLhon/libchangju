package com.newproject.hardqing.ui.sendcmd;

import com.google.gson.annotations.SerializedName;
import com.newproject.hardqing.constant.WebSocketConst;

//socket 退出
public class InroomSocket {
    @SerializedName("cmd")
    private String cmd;

    @SerializedName("room_id")
    private String room_id;

    public InroomSocket(String room_id) {
        this.cmd = WebSocketConst.tourist_in;
        this.room_id = room_id;
    }
}
