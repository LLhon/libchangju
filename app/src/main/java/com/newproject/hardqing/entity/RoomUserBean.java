package com.newproject.hardqing.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomUserBean {

    /**
     * code : 200
     * msg : 成功
     * extras :
     * data : [{"user_id":"14","avatar":"/uploads/avatar/20181105/1dedf8d2b3eb72088e0f865532ffe01e308.jpg"}]
     */

    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("extras")
    private String extras;
    @SerializedName("data")
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_id : 14
         * avatar : /uploads/avatar/20181105/1dedf8d2b3eb72088e0f865532ffe01e308.jpg
         */

        @SerializedName("user_id")
        private String userId;
        @SerializedName("avatar")
        private String avatar;

        public DataBean() {
        }

        public DataBean(String userId, String avatar) {
            this.userId = userId;
            this.avatar = avatar;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
