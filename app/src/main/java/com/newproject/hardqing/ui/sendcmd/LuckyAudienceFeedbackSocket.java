package com.newproject.hardqing.ui.sendcmd;

import com.google.gson.annotations.SerializedName;
import com.newproject.hardqing.constant.WebSocketConst;
import com.newproject.hardqing.ui.LoginUserManager;

public class LuckyAudienceFeedbackSocket {
    /**
     * {
     * "cmd":"lucky_audience_feedback", //方法名
     * "token":"2zu3mmrijq68cws8c8kwksksk", //当前用户token
     * "user_id": "197", //用户id
     * "username": "王大锤", //用户昵称
     * "anchor_id": "201", //当前直播间主播id
     * "room_id": "102", //当前房间号
     * "content": "喝一杯" //中奖内容
     * }
     */
    @SerializedName("cmd")
    private String cmd;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("token")
    private String token;

    @SerializedName("username")
    private String username;

    @SerializedName("anchor_id")
    private String anchor_id;

    @SerializedName("room_id")
    private String room_id;

    @SerializedName("content")
    private String content;

    public LuckyAudienceFeedbackSocket(String user_id, String username, String anchor_id, String room_id, String content) {
        this.cmd = WebSocketConst.lucky_audience_feedback;
        this.token = LoginUserManager.getToken();
        this.user_id = user_id;
        this.username = username;
        this.anchor_id = anchor_id;
        this.room_id = room_id;
        this.content = content;
    }

}
