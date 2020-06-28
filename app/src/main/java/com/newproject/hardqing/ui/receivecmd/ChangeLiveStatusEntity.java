package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class ChangeLiveStatusEntity {
    @SerializedName("room_id")
    private int room_id;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("status")
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int cmd) {
        this.status = cmd;
    }


    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int cmd) {
        this.room_id = cmd;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String cmd) {
        this.user_id = cmd;
    }

    @Override
    public String toString() {
        return "ChangeLiveStatusEntity{" +
                "room_id=" + room_id +
                ", user_id='" + user_id + '\'' +
                ", status=" + status +
                '}';
    }
}
