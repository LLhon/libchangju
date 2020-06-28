package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class SendChatMsgEntity  extends  SendMsgEntity
{
    @SerializedName("type")
    private int type;

    @SerializedName("currency")
    private double currency;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getCurrency() {
        return currency;
    }

    public void setCurrency(double type) {
        this.currency = type;
    }

    public SendChatMsgEntity(String userId, String username, String avatar, String content, String is_manage,int type,double currency)
    {
        super(userId,username,avatar,content,is_manage);

        this.currency=currency;
        this.type=type;
    }
}
