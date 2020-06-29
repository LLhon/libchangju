package com.newproject.hardqing.ui.receivecmd;

import com.google.gson.annotations.SerializedName;

/**
 * 用于主播端娱乐系统传递的广播的事件类
 */
public class EntertainEntity {
    /**
     * {
     *     "cmd":"open_turntable", //方法名
     *     "activity_id":"1", //请求接口获取到的当前活动id
     * }
     */
    @SerializedName("cmd")
    private String cmd;

    @SerializedName("activity_id")
    private String activity_id;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    @Override
    public String toString() {
        return "EntertainEvent{" +
                "cmd='" + cmd + '\'' +
                ", activity_id='" + activity_id + '\'' +
                '}';
    }
}
