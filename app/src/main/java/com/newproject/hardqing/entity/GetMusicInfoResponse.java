package com.newproject.hardqing.entity;

import com.google.gson.annotations.SerializedName;

public class GetMusicInfoResponse {

    /**
     * code : 200
     * msg : 成功
     * extras :
     * data : {"id":"5","title":"小苹果","singer":"筷子兄弟","length":"212","lyrics":"/static/upload/lyrics/20181212/2c14a73aab60896da4b8483636897816163.lrc","downloadurl":"/static/upload/musicFiles/20181212/02d0a56517688318a2e5ef0f08713bb2551.mp3","bitrate":"128kbit"}
     */

    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("extras")
    private String extras;
    @SerializedName("data")
    private DataBean data;

    public int getCode() { return code;}

    public void setCode(int code) { this.code = code;}

    public String getMsg() { return msg;}

    public void setMsg(String msg) { this.msg = msg;}

    public String getExtras() { return extras;}

    public void setExtras(String extras) { this.extras = extras;}

    public DataBean getData() { return data;}

    public void setData(DataBean data) { this.data = data;}

    @Override
    public String toString() {
        return "GetMusicInfoResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", extras='" + extras + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * id : 5
         * title : 小苹果
         * singer : 筷子兄弟
         * length : 212
         * lyrics : /static/upload/lyrics/20181212/2c14a73aab60896da4b8483636897816163.lrc
         * downloadurl : /static/upload/musicFiles/20181212/02d0a56517688318a2e5ef0f08713bb2551.mp3
         * bitrate : 128kbit
         */

        @SerializedName("id")
        private String id;
        @SerializedName("title")
        private String title;
        @SerializedName("singer")
        private String singer;
        @SerializedName("length")
        private String length;
        @SerializedName("lyrics")
        private String lyrics;
        @SerializedName("downloadurl")
        private String downloadurl;
        @SerializedName("bitrate")
        private String bitrate;

        public String getId() { return id;}

        public void setId(String id) { this.id = id;}

        public String getTitle() { return title;}

        public void setTitle(String title) { this.title = title;}

        public String getSinger() { return singer;}

        public void setSinger(String singer) { this.singer = singer;}

        public String getLength() { return length;}

        public void setLength(String length) { this.length = length;}

        public String getLyrics() { return lyrics;}

        public void setLyrics(String lyrics) { this.lyrics = lyrics;}

        public String getDownloadurl() { return downloadurl;}

        public void setDownloadurl(String downloadurl) { this.downloadurl = downloadurl;}

        public String getBitrate() { return bitrate;}

        public void setBitrate(String bitrate) { this.bitrate = bitrate;}

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", singer='" + singer + '\'' +
                    ", length='" + length + '\'' +
                    ", lyrics='" + lyrics + '\'' +
                    ", downloadurl='" + downloadurl + '\'' +
                    ", bitrate='" + bitrate + '\'' +
                    '}';
        }
    }
}
