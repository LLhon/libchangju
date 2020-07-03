package com.newproject.hardqing.ui.sendcmd;

import com.google.gson.annotations.SerializedName;
import com.newproject.hardqing.constant.WebSocketConst;
import com.newproject.hardqing.ui.LoginUserManager;
import com.newproject.hardqing.ui.receivecmd.RandomLuckyEntity;

import java.util.List;

public class LuckyFiveAudienceSocket {
    /**
     * {
     *     "cmd": "self_introduction_random_users", //方法名
     *     "token": "2zu3mmrijq68cws8c8kwksksk", //当前用户token
     *     "anchor_id": "201", //当前直播间主播id
     *     "users": [{
     *         "user_id": 201, //被抽到的用户id
     *         "username": "王大锤", //被抽到的用户昵称
     *         "status": 0 //连麦状态标记，填0即可
     *     }, {
     *         "user_id": 184,
     *         "username": "单纯猫",
     *         "status": 0
     *     }, {
     *         "user_id": 178,
     *         "username": "淑丽",
     *         "status": 0
     *     }],
     *     "room_id": "297", //当前房间id
     *     "activity_id": "2" //当前活动id
     * }
     */

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("token")
    private String token;

    @SerializedName("anchor_id")
    private String anchor_id;

    @SerializedName("users")
    private List<RandomLuckyEntity.UsersBean> users;

    @SerializedName("room_id")
    private String room_id;

    @SerializedName("activity_id")
    private String activity_id;

    public LuckyFiveAudienceSocket(String anchor_id, List<RandomLuckyEntity.UsersBean> users, String room_id, String activity_id){
        this.cmd = WebSocketConst.self_introduction_random_users;
        this.token = LoginUserManager.getToken();
        this.anchor_id = anchor_id;
        this.users = users;
        this.room_id = room_id;
        this.activity_id = activity_id;
    }

}
