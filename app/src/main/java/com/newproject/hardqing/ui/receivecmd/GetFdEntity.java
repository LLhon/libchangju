package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class GetFdEntity {
    @SerializedName("cmd")
    private String cmd;

    @SerializedName("fd")
    private String fd;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getFd() {
        return fd;
    }

    public void setFd(String fd) {
        this.fd = fd;
    }
}
