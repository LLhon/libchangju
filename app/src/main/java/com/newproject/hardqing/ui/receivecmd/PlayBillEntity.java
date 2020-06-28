package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by LLhon
 */
public class PlayBillEntity {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("room_id")
    private String room_id;

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("images")
    private List<String> images;

    @SerializedName("effect_type")
    private String effect_type;

    @SerializedName("time")
    private String time;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getEffect_type() {
        return effect_type;
    }

    public void setEffect_type(String effect_type) {
        this.effect_type = effect_type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
