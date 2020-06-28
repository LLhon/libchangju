package com.newproject.hardqing.ui.sendcmd;

import com.google.gson.annotations.SerializedName;
import com.newproject.hardqing.constant.WebSocketConst;

//socket 退出
public class OutRoomSocket {
    @SerializedName("cmd")
    private String cmd;

    @SerializedName("room_id")
    private String room_id;

    @SerializedName("tourist_id")
    private String tourist_id;

    public OutRoomSocket(String room_id,String tourist_id) {
        this.cmd = WebSocketConst.tourist_out;
        this.room_id = room_id;
        this.tourist_id = tourist_id;
    }
}
