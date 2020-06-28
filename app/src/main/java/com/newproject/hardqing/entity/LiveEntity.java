package com.newproject.hardqing.entity;

import java.util.List;

public class LiveEntity {


    /**
     * code : 200
     * msg : 成功
     * extras : {"id":"17","room_no":"5752997","user_id":"11",
     * "title":"阿狸","category_id":"1",
     * "cover":"/uploads/live/20190104/6b7f52ceec1bb1f62fc75e4ac3eef245460.png",
     * "watch":"-105","watch_total":"-105","status":"2",
     * "create_time":"1542595977","start_time":"1546608687",
     * "stop_time":"1546608723",
     * "push_rtmp":"rtmp://video-center.alivecdn.com/5752997/2df16db35129f1b546da8a073bae6316?vhost=lara.hhekj.com&auth_key=1546608728-0-0-65673c9dd599e0c994c2a7b3ebb1ea8e",
     * "main_rtmp":"rtmp://lara.hhekj.com/5752997/2df16db35129f1b546da8a073bae6316?auth_key=1546608728-0-0-65673c9dd599e0c994c2a7b3ebb1ea8e",
     * "play_rtmp":"rtmp://lara.hhekj.com/5752997/2df16db35129f1b546da8a073bae6316?auth_key=1546608728-0-0-65673c9dd599e0c994c2a7b3ebb1ea8e","code":"2df16db35129f1b546da8a073bae6316",
     * "is_record":"2","pid":"2","type":"0","is_bap":"0","bap_time":"1546568819",
     * "username":"方圆","avatar":"/uploads/avatar/20181230/4551a16275146b46902793ba0cb4786c980.jpg",
     * "islikes":"0","num":"2","kick":"2","order_no":"2019010443133","mbid":"31","yq_name":"","yq_describe":"","yq_uri":"","yq_type":""}
     * data : []
     */

    private int code;
    private String msg;
    private ExtrasBean extras;
    private List<?> data;

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

    public ExtrasBean getExtras() {
        return extras;
    }

    public void setExtras(ExtrasBean extras) {
        this.extras = extras;
    }

    @Override
    public String toString() {
        return "LiveEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", extras=" + extras +
                ", data=" + data +
                '}';
    }

    public static class ExtrasBean {
        /**
         * id : 17
         * room_no : 5752997
         * user_id : 11
         * title : 阿狸
         * category_id : 1
         * cover : /uploads/live/20190104/6b7f52ceec1bb1f62fc75e4ac3eef245460.png
         * watch : -105
         * watch_total : -105
         * status : 2
         * create_time : 1542595977
         * start_time : 1546608687
         * stop_time : 1546608723
         * push_rtmp : rtmp://video-center.alivecdn.com/5752997/2df16db35129f1b546da8a073bae6316?vhost=lara.hhekj.com&auth_key=1546608728-0-0-65673c9dd599e0c994c2a7b3ebb1ea8e
         * main_rtmp : rtmp://lara.hhekj.com/5752997/2df16db35129f1b546da8a073bae6316?auth_key=1546608728-0-0-65673c9dd599e0c994c2a7b3ebb1ea8e
         * play_rtmp : rtmp://lara.hhekj.com/5752997/2df16db35129f1b546da8a073bae6316?auth_key=1546608728-0-0-65673c9dd599e0c994c2a7b3ebb1ea8e
         * code : 2df16db35129f1b546da8a073bae6316
         * is_record : 2
         * pid : 2
         * type : 0
         * is_bap : 0
         * bap_time : 1546568819
         * username : 方圆
         * avatar : /uploads/avatar/20181230/4551a16275146b46902793ba0cb4786c980.jpg
         * islikes : 0
         * num : 2
         * kick : 2
         * order_no : 2019010443133
         * mbid : 31
         * yq_name :
         * yq_describe :
         * yq_uri :
         * yq_type :
         */

        private String id;
        private String room_no;
        private String user_id;
        private String title;
        private String category_id;
        private String category_name;
        private String cover;
        private String watch;
        private String watch_total;
        private String status;
        private String create_time;
        private String start_time;
        private String stop_time;
        private String push_rtmp;
        private String main_rtmp;
        private String play_rtmp;
        private String code;
        private String is_record;
        private String pid;
        private String type;
        private String is_bap;
        private String bap_time;
        private String username;
        private String avatar;
        private String islikes;
        private String num;
        private String kick;
        private String order_no;
        private String mbid;
        private String yq_name;
        private String yq_describe;
        private String yq_uri;
        private String yq_type;
        private String yq_cover;

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getYq_cover() {
            return yq_cover;
        }

        public void setYq_cover(String yq_cover) {
            this.yq_cover = yq_cover;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRoom_no() {
            return room_no;
        }

        public void setRoom_no(String room_no) {
            this.room_no = room_no;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getWatch() {
            return watch;
        }

        public void setWatch(String watch) {
            this.watch = watch;
        }

        public String getWatch_total() {
            return watch_total;
        }

        public void setWatch_total(String watch_total) {
            this.watch_total = watch_total;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getStop_time() {
            return stop_time;
        }

        public void setStop_time(String stop_time) {
            this.stop_time = stop_time;
        }

        public String getPush_rtmp() {
            return push_rtmp;
        }

        public void setPush_rtmp(String push_rtmp) {
            this.push_rtmp = push_rtmp;
        }

        public String getMain_rtmp() {
            return main_rtmp;
        }

        public void setMain_rtmp(String main_rtmp) {
            this.main_rtmp = main_rtmp;
        }

        public String getPlay_rtmp() {
            return play_rtmp;
        }

        public void setPlay_rtmp(String play_rtmp) {
            this.play_rtmp = play_rtmp;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getIs_record() {
            return is_record;
        }

        public void setIs_record(String is_record) {
            this.is_record = is_record;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIs_bap() {
            return is_bap;
        }

        public void setIs_bap(String is_bap) {
            this.is_bap = is_bap;
        }

        public String getBap_time() {
            return bap_time;
        }

        public void setBap_time(String bap_time) {
            this.bap_time = bap_time;
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

        public String getIslikes() {
            return islikes;
        }

        public void setIslikes(String islikes) {
            this.islikes = islikes;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getKick() {
            return kick;
        }

        public void setKick(String kick) {
            this.kick = kick;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getMbid() {
            return mbid;
        }

        public void setMbid(String mbid) {
            this.mbid = mbid;
        }

        public String getYq_name() {
            return yq_name;
        }

        public void setYq_name(String yq_name) {
            this.yq_name = yq_name;
        }

        public String getYq_describe() {
            return yq_describe;
        }

        public void setYq_describe(String yq_describe) {
            this.yq_describe = yq_describe;
        }

        public String getYq_uri() {
            return yq_uri;
        }

        public void setYq_uri(String yq_uri) {
            this.yq_uri = yq_uri;
        }

        public String getYq_type() {
            return yq_type;
        }

        public void setYq_type(String yq_type) {
            this.yq_type = yq_type;
        }

        @Override
        public String toString() {
            return "ExtrasBean{" +
                    "id='" + id + '\'' +
                    ", room_no='" + room_no + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", title='" + title + '\'' +
                    ", category_id='" + category_id + '\'' +
                    ", cover='" + cover + '\'' +
                    ", watch='" + watch + '\'' +
                    ", watch_total='" + watch_total + '\'' +
                    ", status='" + status + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", start_time='" + start_time + '\'' +
                    ", stop_time='" + stop_time + '\'' +
                    ", push_rtmp='" + push_rtmp + '\'' +
                    ", main_rtmp='" + main_rtmp + '\'' +
                    ", play_rtmp='" + play_rtmp + '\'' +
                    ", code='" + code + '\'' +
                    ", is_record='" + is_record + '\'' +
                    ", pid='" + pid + '\'' +
                    ", type='" + type + '\'' +
                    ", is_bap='" + is_bap + '\'' +
                    ", bap_time='" + bap_time + '\'' +
                    ", username='" + username + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", islikes='" + islikes + '\'' +
                    ", num='" + num + '\'' +
                    ", kick='" + kick + '\'' +
                    ", order_no='" + order_no + '\'' +
                    ", mbid='" + mbid + '\'' +
                    ", yq_name='" + yq_name + '\'' +
                    ", yq_describe='" + yq_describe + '\'' +
                    ", yq_uri='" + yq_uri + '\'' +
                    ", yq_type='" + yq_type + '\'' +
                    ", yq_cover='" + yq_cover + '\'' +
                    '}';
        }
    }
}
