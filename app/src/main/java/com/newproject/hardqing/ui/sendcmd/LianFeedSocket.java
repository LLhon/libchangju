package com.newproject.hardqing.ui.sendcmd;

import com.google.gson.annotations.SerializedName;
import com.newproject.hardqing.constant.WebSocketConst;
import com.newproject.hardqing.ui.LoginUserManager;

public class LianFeedSocket {
    /**
     * {
     *     "cmd":"self_introduction", //方法名
     *     "token":"2zu3mmrijq68cws8c8kwksksk", //当前用户token
     *     "user_id":"184", //被抽取到的用户id
     *     "room_id":"193", //直播间id
     *     "activity_id":"2", //请求接口获取到的当前活动id
     *     "anchor_id":"201" //当前直播间主播ip
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

    @SerializedName("activity_id")
    private String activity_id;

    @SerializedName("anchor_id")
    private String anchor_id;

    public LianFeedSocket(String user_id, String room_id, String activity_id, String anchor_id) {
        this.cmd = WebSocketConst.self_introduction;
        this.token = LoginUserManager.getToken();
        this.user_id = user_id;
        this.room_id = room_id;
        this.activity_id = activity_id;
        this.anchor_id = anchor_id;
    }
}
