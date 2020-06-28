package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class CandleEntity {


    @SerializedName("cmd")
    private String cmd;

    @SerializedName("candle_id")
    private String candleId;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getCandleId() {
        return candleId;
    }

    public void setCandleId(String candleId) {
        this.candleId = candleId;
    }
}
