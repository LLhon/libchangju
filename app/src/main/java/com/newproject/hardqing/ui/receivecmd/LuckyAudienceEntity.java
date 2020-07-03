package com.newproject.hardqing.ui.receivecmd;

public class LuckyAudienceEntity {
    /**
     * {
     *     "cmd":"lucky_audience_feedback", //方法名
     *     "token":"2zu3mmrijq68cws8c8kwksksk", //当前用户token
     *     "user_id": "197", //用户id
     *     "username": "王大锤", //用户昵称
     *     "anchor_id": "201", //当前直播间主播id
     *     "content": "喝一杯" //中奖内容
     * }
     */
    private String cmd;
    private String token;
    private String user_id;
    private String username;
    private String anchor_id;
    private String content;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAnchor_id() {
        return anchor_id;
    }

    public void setAnchor_id(String anchor_id) {
        this.anchor_id = anchor_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "LuckyAudienceEntity{" +
                "cmd='" + cmd + '\'' +
                ", token='" + token + '\'' +
                ", user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", anchor_id='" + anchor_id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
