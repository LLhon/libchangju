package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class InviteNoticeEntity {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("main_rtmp")
    private String main_rtmp;
    @SerializedName("auser_id")
    private String auser_id;

    @SerializedName("lmid")
    private String lmid;

    // 0 非 1 是拉拉星
    @SerializedName("lara")
    private String lara;

    public String getLara() {
        return lara;
    }

    public void setLara(String lara) {
        this.lara = lara;
    }

    public String getAuserid() {
        return auser_id;
    }

    public void setAuserid(String auserid) {
        this.auser_id = auserid;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getMain_rtmp() {
        return main_rtmp;
    }


    public String getLmid() {
        return lmid;
    }

    public void setLmid(String lmid) {
        this.lmid = lmid;
    }

    public void setMain_rtmp(String push_rtmp) {
        this.main_rtmp = main_rtmp;

    }
}
