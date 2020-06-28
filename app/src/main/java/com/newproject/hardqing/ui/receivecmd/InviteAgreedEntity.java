package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class InviteAgreedEntity {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("status")
    private String status;

    @SerializedName("push_rtmp")
    private String push_rtmp;

    @SerializedName("lmid")
    private String lmid;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPush_rtmp() {
        return push_rtmp;
    }

    public void setPush_rtmp(String push_rtmp) {
        this.push_rtmp = push_rtmp;
    }

    public String getLmid() {
        return lmid;
    }

    public void setLmid(String lmid) {
        this.lmid = lmid;
    }
}
