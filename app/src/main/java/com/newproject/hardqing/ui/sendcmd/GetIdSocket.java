package com.newproject.hardqing.ui.sendcmd;

import com.google.gson.annotations.SerializedName;
import com.newproject.hardqing.constant.WebSocketConst;

//socket 登录
public class GetIdSocket {

    @SerializedName("cmd")
    private String cmd;

    public GetIdSocket() {
        this.cmd = WebSocketConst.getfd;
    }
}
