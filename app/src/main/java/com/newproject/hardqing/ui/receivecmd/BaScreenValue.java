package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

public class BaScreenValue {

    /**
     * type : 1 uri_type : 0 uri : https://resources.xiaoheshuo.com/uploads/lara/9ca822020080216212634416.jpg
     * time : 10 text : ycyvyvyv
     */
    @SerializedName("type")
    private int type;
    @SerializedName("uri_type")
    private String uri_type;
    @SerializedName("uri")
    private String uri;
    @SerializedName("time")
    private String time;
    @SerializedName("text")
    private String text;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUri_type() {
        return uri_type;
    }

    public void setUri_type(String uri_type) {
        this.uri_type = uri_type;
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

    @Override
    public String toString() {
        return "BaScreenValue{" +
            "type=" + type +
            ", uri_type='" + uri_type + '\'' +
            ", uri='" + uri + '\'' +
            ", time='" + time + '\'' +
            ", text='" + text + '\'' +
            '}';
    }
}
