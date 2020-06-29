package com.newproject.hardqing.ui.sendcmd;

import com.google.gson.annotations.SerializedName;
import com.newproject.hardqing.constant.WebSocketConst;
import com.newproject.hardqing.ui.LoginUserManager;

public class EntertainSocket {

    /**
     *{
     *     "cmd":"open_room_activity", //方法名
     *     "token":"2zu3mmrijq68cws8c8kwksksk", //当前用户token
     *     "activity_id":"1", //请求接口获取到的当前活动id
     *     "room_id":"193", //直播间id
     * }
     */
    @SerializedName("cmd")
    private String cmd;

    @SerializedName("room_id")
    private String room_id;

    @SerializedName("activity_id")
    private String activity_id;

    @SerializedName("token")
    private String token;

    public EntertainSocket(String activity_id, String room_id){
        this.cmd = WebSocketConst.entertainment;
        this.room_id = room_id;
        this.activity_id = activity_id;
        this.token = LoginUserManager.getToken();
    }

}
