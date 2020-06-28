package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class RedCurrencyEntity {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("currency")
    private String currency;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
