package com.newproject.hardqing.ui.receivecmd;

public class CloseInductionEntity {
    /**
     * {
     *     "cmd":"close_room_activity", //方法名
     *     "token":"2zu3mmrijq68cws8c8kwksksk", //当前用户token
     *     "user_id":"298", //当前主播id
     * }
     */
    private String cmd;
    private String token;
    private String user_id;

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

    @Override
    public String toString() {
        return "CloseInductionEntity{" +
                "cmd='" + cmd + '\'' +
                ", token='" + token + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
