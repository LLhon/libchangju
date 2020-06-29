package com.newproject.hardqing.ui.receivecmd;

import java.util.List;

public class RandomLuckyEntity {
    /**
     * cmd : self_introduction_random_users
     * users : [{"user_id":201,"username":"王大锤","status":0},{"user_id":184,"username":"单纯猫","status":0},{"user_id":178,"username":"淑丽","status":0}]
     */

    private String cmd;
    private List<UsersBean> users;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class UsersBean {
        /**
         * user_id : 201
         * username : 王大锤
         * status : 0
         */

        private int user_id;
        private String username;
        private int status;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public UsersBean(int user_id, String username, int status) {
            this.user_id = user_id;
            this.username = username;
            this.status = status;
        }

        @Override
        public String toString() {
            return "UsersBean{" +
                    "user_id=" + user_id +
                    ", username='" + username + '\'' +
                    ", status=" + status +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RandomLuckyEntity{" +
                "cmd='" + cmd + '\'' +
                ", users=" + users +
                '}';
    }
}
