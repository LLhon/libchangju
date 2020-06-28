package com.newproject.hardqing.ui.sendcmd;

import com.google.gson.annotations.SerializedName;
import com.newproject.hardqing.constant.WebSocketFeedConst;

public class ChorusRequest {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("sender_id")
    private String senderId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("sender_user_name")
    private String senderUserName;

    @SerializedName("type")
    private int type;

    @SerializedName("songcode")
    private String songcode;

    @SerializedName("lrc_url")
    private String lrcUrl;

    @SerializedName("room_id")
    private String roomId;

    public ChorusRequest(String userId, String userName, int type, String songcode, String lrcUrl,
        String roomId) {
        this.cmd = WebSocketFeedConst.private_message;
        this.userId = userId;
        this.type = type;
        this.userName = userName;
        this.songcode = songcode;
        this.lrcUrl = lrcUrl;
        this.roomId = roomId;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSongcode() {
        return songcode;
    }

    public void setSongcode(String songcode) {
        this.songcode = songcode;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    public String getLrcUrl() {
        return lrcUrl;
    }

    public void setLrcUrl(String lrcUrl) {
        this.lrcUrl = lrcUrl;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "ChorusRequest{" +
                "cmd='" + cmd + '\'' +
                ", userId='" + userId + '\'' +
                ", senderId='" + senderId + '\'' +
                ", userName='" + userName + '\'' +
                ", type=" + type +
                ", songcode='" + songcode + '\'' +
                '}';
    }
}
