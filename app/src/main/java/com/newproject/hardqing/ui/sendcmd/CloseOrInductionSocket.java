package com.newproject.hardqing.ui.sendcmd;

import com.google.gson.annotations.SerializedName;
import com.newproject.hardqing.constant.WebSocketConst;
import com.newproject.hardqing.ui.LoginUserManager;

public class CloseOrInductionSocket {
    /**
     * {
     * "cmd":"close_room_activity", //方法名
     * "token":"2zu3mmrijq68cws8c8kwksksk", //当前用户token
     * "user_id":"298", //当前主播id
     *"room_id": 198, //当前房间id
     *     "anchor_id": 201 //当前主播id
     * <p>
     * }
     */

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("token")
    private String token;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("room_id")
    private String room_id;

    @SerializedName("anchor_id")
    private String anchor_id;

    public CloseOrInductionSocket(String user_id, String room_id, String anchor_id) {
        this.cmd = WebSocketConst.close_induction;
        this.token = LoginUserManager.getToken();
        this.user_id = user_id;
        this.room_id = room_id;
        this.anchor_id = anchor_id;

    }
}
