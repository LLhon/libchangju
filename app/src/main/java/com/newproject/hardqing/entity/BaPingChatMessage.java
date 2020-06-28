package com.newproject.hardqing.entity;

import com.google.gson.annotations.SerializedName;

import com.newproject.hardqing.ui.receivecmd.BaScreenEntity;
import com.newproject.hardqing.ui.receivecmd.GiftEntity;

public class BaPingChatMessage {

    @SerializedName("type")
    private int type;
    @SerializedName("uri_type")
    private String uriType;
    @SerializedName("uri")
    private String uri;
    @SerializedName("time")
    private String time;
    @SerializedName("text")
    private String text;
    @SerializedName("music_name")
    private String musicName;
    @SerializedName("sound_effect")
    private int soundEffect;
    @SerializedName("voice_control_progress")
    private int voiceControlProgress;
    @SerializedName("message_value")
    private String messageValue;
    @SerializedName("stream_id")
    private String streamID;
    @SerializedName("aroom_id")
    private String aroom_id;

    @SerializedName("gift_id")
    private String giftId;
    @SerializedName("gif_img1")
    private String gifImg1;
    @SerializedName("gif_img2")
    private String gifImg2;

    public String getMessageValue() {
        return messageValue;
    }

    public void setMessageValue(String messageValue) {
        this.messageValue = messageValue;
    }

    public int getVoiceControlProgress() {
        return voiceControlProgress;
    }

    public void setVoiceControlProgress(int voiceControlProgress) {
        this.voiceControlProgress = voiceControlProgress;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUriType() {
        return uriType;
    }

    public void setUriType(String uriType) {
        this.uriType = uriType;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public int getSoundEffect() {
        return soundEffect;
    }

    public void setSoundEffect(int soundEffect) {
        this.soundEffect = soundEffect;
    }

    public BaScreenEntity getBaScreenEntity() {
        BaScreenEntity baScreenEntity = new BaScreenEntity();
        baScreenEntity.setType(uriType);
        baScreenEntity.setTime(time);
        baScreenEntity.setUri(uri);
        baScreenEntity.setClickShowBaPing(true);
        return baScreenEntity;
    }

    public String getStreamID() {
        return streamID;
    }

    public void setStreamID(String streamID) {
        this.streamID = streamID;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getGifImg1() {
        return gifImg1;
    }

    public void setGifImg1(String gifImg1) {
        this.gifImg1 = gifImg1;
    }

    public String getGifImg2() {
        return gifImg2;
    }

    public void setGifImg2(String gifImg2) {
        this.gifImg2 = gifImg2;
    }

    public String getAroom_id() {
        return aroom_id;
    }

    public void setAroom_id(String aroom_id) {
        this.aroom_id = aroom_id;
    }

    @Override
    public String toString() {
        return "BaPingChatMessage{" +
                "type=" + type +
                ", uriType='" + uriType + '\'' +
                ", uri='" + uri + '\'' +
                ", time='" + time + '\'' +
                ", text='" + text + '\'' +
                ", musicName='" + musicName + '\'' +
                ", soundEffect=" + soundEffect +
                ", voiceControlProgress=" + voiceControlProgress +
                ", messageValue='" + messageValue + '\'' +
                ", streamID='" + streamID + '\'' +
                ", giftId='" + giftId + '\'' +
                ", gifImg1='" + gifImg1 + '\'' +
                ", gifImg2='" + gifImg2 + '\'' +
                '}';
    }

    public GiftEntity getGiftEntity() {
        GiftEntity giftEntity = new GiftEntity();
        giftEntity.setGiftSamll(gifImg1);
        giftEntity.setGiftImg(gifImg2);
        giftEntity.setGiftId(giftId);
        giftEntity.setGiftSound("");
        giftEntity.setGiftStyle("1");
        giftEntity.setGiftType("1");
        giftEntity.setGiftNum("1");
        giftEntity.setGiftName("入场欢迎");
        return giftEntity;
    }
}
