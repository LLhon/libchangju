package com.newproject.hardqing.ui.sendcmd;

import com.google.gson.annotations.SerializedName;
import com.newproject.hardqing.constant.WebSocketConst;

/**
 * Created by LLhon
 */
public class MultiRoomMessageSocket {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("auser_id")
    private String auser_id;

    @SerializedName("room_id")
    private String room_id;

    @SerializedName("aroom_id")
    private String aroom_id;

    @SerializedName("token")
    private String token;

    @SerializedName("type")
    private int type;

    @SerializedName("lrc_progress")
    private long lrc_progress;

    @SerializedName("song_duration")
    private long song_duration;

    public MultiRoomMessageSocket(String user_id, String auser_id, String room_id,
        String aroom_id, int type) {
        this.cmd = WebSocketConst.multiroom_public_message;
        this.user_id = user_id;
        this.auser_id = auser_id;
        this.room_id = room_id;
        this.aroom_id = aroom_id;
        this.type = type;
    }

    public void setLrcProgress(long lrcProgress) {
        this.lrc_progress = lrcProgress;
    }

    public void setSongDuration(long songDuration) {
        this.song_duration = songDuration;
    }
}
