package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class CloseAllEntity {
    @SerializedName("cmd")
    private String cmd;


    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

}
