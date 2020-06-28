package com.newproject.hardqing.entity;

import com.google.gson.annotations.SerializedName;

public class UserDetailBean {

    /**
     * code : 200
     * msg : 成功
     * extras :
     * data : {"id":"13","username":"笑笑","avatar":"/uploads/avatar/20181103/8d9d4600e7ff914ffee02892cc407804840.jpg","autograph":"","gz":"3","fs":"2","islikes":"2","nd_send":"2"}
     */

    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("extras")
    private String extras;
    @SerializedName("data")
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 13
         * username : 笑笑
         * avatar : /uploads/avatar/20181103/8d9d4600e7ff914ffee02892cc407804840.jpg
         * autograph :
         * gz : 3
         * fs : 2
         * islikes : 2
         * nd_send : 2
         */

        @SerializedName("id")
        private String id;
        @SerializedName("username")
        private String username;
        @SerializedName("avatar")
        private String avatar;
        @SerializedName("autograph")
        private String autograph;
        @SerializedName("gz")
        private String gz;
        @SerializedName("fs")
        private String fs;
        @SerializedName("islikes")
        private String islikes;
        @SerializedName("nd_send")
        private String ndSend;
        @SerializedName("kick")
        private String kick;

        public String getKick() {
            return kick;
        }

        public void setKick(String kick) {
            this.kick = kick;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getAutograph() {
            return autograph;
        }

        public void setAutograph(String autograph) {
            this.autograph = autograph;
        }

        public String getGz() {
            return gz;
        }

        public void setGz(String gz) {
            this.gz = gz;
        }

        public String getFs() {
            return fs;
        }

        public void setFs(String fs) {
            this.fs = fs;
        }

        public String getIslikes() {
            return islikes;
        }

        public void setIslikes(String islikes) {
            this.islikes = islikes;
        }

        public String getNdSend() {
            return ndSend;
        }

        public void setNdSend(String ndSend) {
            this.ndSend = ndSend;
        }
    }
}
