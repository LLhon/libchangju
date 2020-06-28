package com.newproject.hardqing.entity;

import com.google.gson.annotations.SerializedName;

public class SwitchCamera {

    @SerializedName("stream_id")
    private String streamId;

    @SerializedName("is_camera_close")
    private boolean isCameraClose;

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public boolean isCameraClose() {
        return isCameraClose;
    }

    public void setCameraClose(boolean cameraClose) {
        isCameraClose = cameraClose;
    }

    @Override
    public String toString() {
        return "SwitchCamera{" +
                "streamId='" + streamId + '\'' +
                ", isCameraClose=" + isCameraClose +
                '}';
    }
}
