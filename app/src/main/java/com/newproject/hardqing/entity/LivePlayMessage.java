package com.newproject.hardqing.entity;

/**
 * Created by Administrator on 2018/2/1.
 */
//观看直播时的相关消息：进入直播间，发弹幕，
public class LivePlayMessage {

    //200 直播间socket 100 直播间关注主播 300 直播间生日许愿
    private int code;

    //第几个许愿过程
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    //0 没有 1 关注
    private String isLike;

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    @Override
    public String toString() {
        return "LivePlayMessage{" +
                "code=" + code +
                ", position=" + position +
                ", content='" + content + '\'' +
                ", isLike='" + isLike + '\'' +
                '}';
    }
}
