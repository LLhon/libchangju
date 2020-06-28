package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class ErrorEntity {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("content")
    private String content;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
