package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class StartLiveEntity {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("play_url")
    private String play_url;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getPlay_url() {
        return play_url;
    }

    public void setPlay_url(String play_url) {
        this.play_url = play_url;
    }
}
