package com.newproject.hardqing.ui.receivecmd;

/**
 * Created by LLhon
 */
public class MultiRoomLianMaiEntity {

    private String cmd;
    private String user_id;
    private String auser_id;
    private String room_id;
    private String token;
    private ContentBean content;
    private String username;
    private String ausername;
    private String avatar;
    private String user;
    private String auser;
    private String lmid;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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

    public String getAuser_id() {
        return auser_id;
    }

    public void setAuser_id(String auser_id) {
        this.auser_id = auser_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAuser() {
        return auser;
    }

    public void setAuser(String auser) {
        this.auser = auser;
    }

    public String getLmid() {
        return lmid;
    }

    public void setLmid(String lmid) {
        this.lmid = lmid;
    }

    public String getAusername() {
        return ausername;
    }

    public void setAusername(String ausername) {
        this.ausername = ausername;
    }

    public static class ContentBean {
        private String room_id;
        private String receiver_id;
        private String sender_id;
        private String stream_id;
        private String lmid;
        private int type;
        private int watch_total;

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getReceiver_id() {
            return receiver_id;
        }

        public void setReceiver_id(String receiver_id) {
            this.receiver_id = receiver_id;
        }

        public String getSender_id() {
            return sender_id;
        }

        public void setSender_id(String sender_id) {
            this.sender_id = sender_id;
        }

        public String getStream_id() {
            return stream_id;
        }

        public void setStream_id(String stream_id) {
            this.stream_id = stream_id;
        }

        public int getWatch_total() {
            return watch_total;
        }

        public void setWatch_total(int watch_total) {
            this.watch_total = watch_total;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getLmid() {
            return lmid;
        }

        public void setLmid(String lmid) {
            this.lmid = lmid;
        }

        @Override public String toString() {
            return "ContentBean{" +
                "room_id='" + room_id + '\'' +
                ", receiver_id='" + receiver_id + '\'' +
                ", sender_id='" + sender_id + '\'' +
                ", stream_id='" + stream_id + '\'' +
                ", lmid='" + lmid + '\'' +
                ", type=" + type +
                ", watch_total=" + watch_total +
                '}';
        }
    }

    @Override public String toString() {
        return "MultiRoomLianMaiEntity{" +
            "cmd='" + cmd + '\'' +
            ", user_id='" + user_id + '\'' +
            ", auser_id='" + auser_id + '\'' +
            ", room_id='" + room_id + '\'' +
            ", token='" + token + '\'' +
            ", content=" + content +
            ", username='" + username + '\'' +
            ", ausername='" + ausername + '\'' +
            ", avatar='" + avatar + '\'' +
            ", user='" + user + '\'' +
            ", auser='" + auser + '\'' +
            ", lmid='" + lmid + '\'' +
            ", msg='" + msg + '\'' +
            '}';
    }
}
