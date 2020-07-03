package com.newproject.hardqing.ui.receivecmd;

public class ExtractAudienceEntity {
    /**
     * {
     *     "cmd":"lucky_audience", //方法名
     *     "user_id":"201", //被抽取到的用户id
     *     "username":"王多鱼", //被抽取到的幸运用户昵称
     * }
     */
    private String cmd;
    private String user_id;
    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ExtractAudienceEntity{" +
                "cmd='" + cmd + '\'' +
                ", user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
