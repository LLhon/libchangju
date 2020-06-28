package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class GiftEntity {
//    {"cmd":"gift9r","user_id":"13","username":"笑笑","avatar":"\/uploads\/avatar\/20181103\/8d9d4600e7ff914ffee02892cc407804840.jpg","gift_id":"1","gift_name":"钻戒","gift_img":"uploads\/gift\/20181031\/5ff3ed1de70bad95a70e2fe8e2012f3f780.png","gift_num":"1","currency":"0"}
    @SerializedName("cmd")
    private String cmd;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("username")
    private String username;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("gift_id")
    private String giftId;

    @SerializedName("gift_name")
    private String giftName;

    @SerializedName("gift_img")
    private String giftImg;

    @SerializedName("gift_num")
    private String giftNum;

    @SerializedName("currency")
    private String currency;

    @SerializedName("gift_style")
    private String giftStyle;

    @SerializedName("gift_small")
    private String giftSamll;

    @SerializedName("gift_type")
    private String giftType;

    @SerializedName("gift_sound") private String giftSound;

    public String getGiftSound() {
        return giftSound;
    }

    public void setGiftSound(String giftSound) {
        this.giftSound = giftSound;
    }

    public String getGiftStyle() {
        return giftStyle;
    }

    public void setGiftStyle(String giftStyle) {
        this.giftStyle = giftStyle;
    }

    public String getGiftSamll() {
        return giftSamll;
    }

    public void setGiftSamll(String giftSamll) {
        this.giftSamll = giftSamll;
    }

    public String getGiftType() {
        return giftType;
    }

    public void setGiftType(String giftType) {
        this.giftType = giftType;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftImg() {
        return giftImg;
    }

    public void setGiftImg(String giftImg) {
        this.giftImg = giftImg;
    }

    public String getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(String giftNum) {
        this.giftNum = giftNum;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
