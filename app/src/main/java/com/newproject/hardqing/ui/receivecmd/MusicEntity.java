package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class MusicEntity {

    /**
     * id : 5
     * title : 小苹果
     * singer : 筷子兄弟
     * length : 212
     * lyrics : /static/upload/lyrics/20181212/2c14a73aab60896da4b8483636897816163.lrc
     * downloadurl : /static/upload/musicFiles/20181212/02d0a56517688318a2e5ef0f08713bb2551.mp3
     * bitrate : 128kbit
     */

    @SerializedName("cmd")
    private String cmd;
    @SerializedName("room_id")
    private String roomId;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("sender_id")
    private String senderId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("sender_user_name")
    private String senderUserName;

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("singer")
    private String singer;

    @SerializedName("lyrics")
    private String lyrics;

    @SerializedName("song_url")
    private String songUrl;

    @SerializedName("downloadurl")
    private String downloadurl;

    @SerializedName("is_sound_effects")
    private boolean isSoundEffects;

    @SerializedName("effects_music_id")
    private int effectsMusicId;

    @SerializedName("is_ba_ping")
    private boolean isBaPing;

    @SerializedName("is_stop_ba_ping")
    private boolean isStopBaPing;

    @SerializedName("ba_ping_data")
    private String baPingData;

    @SerializedName("is_down_success")
    private int isDownSuccess;

    @SerializedName("song_id")
    private String songId;

    @SerializedName("chorus_type")
    private int chorusType;

    @SerializedName("mp3_url")
    private String mp3Url;

    @SerializedName("bcMp3_url")
    private String bcMp3Url;

    @SerializedName("lrc_url")
    private String lrcUrl;

    @SerializedName("lrc_timestamp")
    private long lrcTimeStamp;

    public long getLrcTimeStamp() {
        return lrcTimeStamp;
    }

    public void setLrcTimeStamp(long lrcTimeStamp) {
        this.lrcTimeStamp = lrcTimeStamp;
    }

    public int getIsDownSuccess() {
        return isDownSuccess;
    }

    public void setIsDownSuccess(int isDownSuccess) {
        this.isDownSuccess = isDownSuccess;
    }

    public String getMp3Url() {
        return mp3Url;
    }

    public void setMp3Url(String mp3Url) {
        this.mp3Url = mp3Url;
    }

    public String getBcMp3Url() {
        return bcMp3Url;
    }

    public void setBcMp3Url(String bcMp3Url) {
        this.bcMp3Url = bcMp3Url;
    }

    public String getLrcUrl() {
        return lrcUrl;
    }

    public void setLrcUrl(String lrcUrl) {
        this.lrcUrl = lrcUrl;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public int getChorusType() {
        return chorusType;
    }

    public void setChorusType(int chorusType) {
        this.chorusType = chorusType;
    }

    public int isDownSuccess() {
        return isDownSuccess;
    }

    public void setDownSuccess(int downSuccess) {
        isDownSuccess = downSuccess;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title;}

    public String getSinger() { return singer;}

    public void setSinger(String singer) { this.singer = singer;}

    public String getLyrics() { return lyrics;}

    public void setLyrics(String lyrics) { this.lyrics = lyrics;}

    public String getDownloadurl() { return downloadurl;}

    public void setDownloadurl(String downloadurl) { this.downloadurl = downloadurl;}

    public boolean isSoundEffects() {
        return isSoundEffects;
    }

    public void setSoundEffects(boolean soundEffects) {
        isSoundEffects = soundEffects;
    }

    public int getEffectsMusicId() {
        return effectsMusicId;
    }

    public void setEffectsMusicId(int effectsMusicId) {
        this.effectsMusicId = effectsMusicId;
    }

    public boolean isBaPing() {
        return isBaPing;
    }

    public void setBaPing(boolean baPing) {
        isBaPing = baPing;
    }

    public boolean isStopBaPing() {
        return isStopBaPing;
    }

    public void setStopBaPing(boolean stopBaPing) {
        isStopBaPing = stopBaPing;
    }

    public String getBaPingData() {
        return baPingData;
    }

    public void setBaPingData(String baPingData) {
        this.baPingData = baPingData;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    @Override public String toString() {
        return "MusicEntity{" +
            "cmd='" + cmd + '\'' +
            ", roomId='" + roomId + '\'' +
            ", userId='" + userId + '\'' +
            ", senderId='" + senderId + '\'' +
            ", userName='" + userName + '\'' +
            ", senderUserName='" + senderUserName + '\'' +
            ", id='" + id + '\'' +
            ", title='" + title + '\'' +
            ", singer='" + singer + '\'' +
            ", lyrics='" + lyrics + '\'' +
            ", songUrl='" + songUrl + '\'' +
            ", downloadurl='" + downloadurl + '\'' +
            ", isSoundEffects=" + isSoundEffects +
            ", effectsMusicId=" + effectsMusicId +
            ", isBaPing=" + isBaPing +
            ", isStopBaPing=" + isStopBaPing +
            ", baPingData='" + baPingData + '\'' +
            ", isDownSuccess=" + isDownSuccess +
            ", songId='" + songId + '\'' +
            ", chorusType=" + chorusType +
            ", mp3Url='" + mp3Url + '\'' +
            ", bcMp3Url='" + bcMp3Url + '\'' +
            ", lrcUrl='" + lrcUrl + '\'' +
            '}';
    }
}
