package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class InviteRefusedEntity {
    @SerializedName("cmd")
    private String cmd;

    @SerializedName("name")
    private String name;

    @SerializedName("content")
    private String content;

    @SerializedName("status")
    private String status;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
