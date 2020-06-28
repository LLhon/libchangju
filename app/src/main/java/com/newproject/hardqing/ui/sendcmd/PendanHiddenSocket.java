package com.newproject.hardqing.ui.sendcmd;

import com.google.gson.annotations.SerializedName;
import com.newproject.hardqing.constant.WebSocketConst;

public class PendanHiddenSocket {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("room_id")
    private String room_id;

    public PendanHiddenSocket(String room_id) {
        this.cmd = WebSocketConst.pendant9r;
        this.room_id = room_id;
    }
}
