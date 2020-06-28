package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

/**
 * 游客进入
 */
public class TourInEntity {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("tourist_id")
    private String tourist_id;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getTourist_id() {
        return tourist_id;
    }

    public void setTourist_id(String tourist_id) {
        this.tourist_id = tourist_id;
    }
}
