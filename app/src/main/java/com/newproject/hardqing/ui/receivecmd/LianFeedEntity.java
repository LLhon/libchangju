package com.newproject.hardqing.ui.receivecmd;

public class LianFeedEntity {
    /**
     * {
     *     "cmd":"self_introduction", //方法名
     *     "user_id":"197", //被抽取到的用户id
     *     "anchor_id": "201" //当前直播间主播id
     *      "type": 1 //1:未连麦，2:连麦中，3，连麦完成，4，拒绝连麦 5、中途离开
     * }
     */

    private String cmd;
    private String user_id;
    private String anchor_id;
    private int type;

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

    public String getAnchor_id() {
        return anchor_id;
    }

    public void setAnchor_id(String anchor_id) {
        this.anchor_id = anchor_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LianFeedEntity{" +
                "cmd='" + cmd + '\'' +
                ", user_id='" + user_id + '\'' +
                ", anchor_id='" + anchor_id + '\'' +
                ", type=" + type +
                '}';
    }
}
