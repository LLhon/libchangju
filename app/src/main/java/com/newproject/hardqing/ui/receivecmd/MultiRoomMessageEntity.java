package com.newproject.hardqing.ui.receivecmd;

/**
 * Created by LLhon
 */
public class MultiRoomMessageEntity {

    private String cmd;
    private String user_id;
    private String auser_id;
    private String room_id;
    private String aroom_id;
    private String token;
    private int type;
    private long lrc_progress;
    private long song_duration;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAuser_id() {
        return auser_id;
    }

    public void setAuser_id(String auser_id) {
        this.auser_id = auser_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getAroom_id() {
        return aroom_id;
    }

    public void setAroom_id(String aroom_id) {
        this.aroom_id = aroom_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getLrc_progress() {
        return lrc_progress;
    }

    public void setLrc_progress(long lrc_progress) {
        this.lrc_progress = lrc_progress;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getSong_duration() {
        return song_duration;
    }

    public void setSong_duration(long song_duration) {
        this.song_duration = song_duration;
    }

    @Override public String toString() {
        return "MultiRoomMessageEntity{" +
            "cmd='" + cmd + '\'' +
            ", user_id='" + user_id + '\'' +
            ", auser_id='" + auser_id + '\'' +
            ", room_id='" + room_id + '\'' +
            ", aroom_id='" + aroom_id + '\'' +
            ", token='" + token + '\'' +
            ", type=" + type +
            ", lrc_progress=" + lrc_progress +
            ", song_duration=" + song_duration +
            '}';
    }
}
