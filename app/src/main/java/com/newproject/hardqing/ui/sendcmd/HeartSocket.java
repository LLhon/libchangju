package com.newproject.hardqing.ui.sendcmd;

import com.google.gson.annotations.SerializedName;
import com.newproject.hardqing.constant.WebSocketConst;

//心跳维持；
public class HeartSocket {


    @SerializedName("cmd")
    private String keep9h;

    public HeartSocket() {
        this.keep9h = WebSocketConst.keep9h;
    }
}
