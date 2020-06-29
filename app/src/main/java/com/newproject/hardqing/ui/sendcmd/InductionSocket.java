package com.newproject.hardqing.ui.sendcmd;

import com.google.gson.annotations.SerializedName;
import com.newproject.hardqing.constant.WebSocketConst;
import com.newproject.hardqing.ui.LoginUserManager;

public class InductionSocket {
    /**
     * {
     *     "cmd":"open_room_introduction", //方法名
     *     "token":"2zu3mmrijq68cws8c8kwksksk", //当前用户token
     *     "room_id": "297" //当前房间id
     * }
     */

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("token")
    private String token;

    @SerializedName("room_id")
    private String room_id;

    public InductionSocket(String room_id){
        this.cmd = WebSocketConst.open_room_introduction;
        this.token = LoginUserManager.getToken();
        this.room_id = room_id;
    }
}
