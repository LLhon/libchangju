package com.newproject.hardqing.entity;


import com.newproject.hardqing.ui.receivecmd.SendMsgEntity;

/**
 * Created by Administrator on 2018/1/17.
 * 横屏直播间的消息通知fragment
 */

public class LiveChatMessage {

    //code 200
    private int code;
    private SendMsgEntity entity;
    private boolean isScroll;

    public boolean isScroll() {
        return isScroll;
    }

    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }

    public SendMsgEntity getEntity() {
        return entity;
    }

    public void setEntity(SendMsgEntity entity) {
        this.entity = entity;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
