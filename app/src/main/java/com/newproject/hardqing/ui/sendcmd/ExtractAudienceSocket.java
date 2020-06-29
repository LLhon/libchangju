package com.newproject.hardqing.ui.sendcmd;

import com.google.gson.annotations.SerializedName;
import com.newproject.hardqing.constant.WebSocketConst;
import com.newproject.hardqing.ui.LoginUserManager;

public class ExtractAudienceSocket {
    /***
     * {
     *     "cmd":"lucky_audience", //方法名
     *     "token":"2zu3mmrijq68cws8c8kwksksk", //当前用户token
     *     "user_id":"201", //被抽取到的用户id
     *     "anchor_id": "201", //当前直播间主播id
     *     "username":"王多鱼", //被抽取到的幸运用户昵称
     *     "room_id":"193", //直播间id
     * }
     */

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("token")
    private String token;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("anchor_id")
    private String anchor_id;

    @SerializedName("username")
    private String username;

    @SerializedName("room_id")
    private String room_id;

    public ExtractAudienceSocket(String user_id, String anchor_id, String username, String room_id){
        this.cmd = WebSocketConst.extractAudience;
        this.token = LoginUserManager.getToken();
        this.user_id = user_id;
        this.username = username;
        this.room_id = room_id;
        this.anchor_id = anchor_id;
    }
}
