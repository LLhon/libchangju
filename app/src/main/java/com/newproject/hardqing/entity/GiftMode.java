package com.newproject.hardqing.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GiftMode {


    /**
     * code : 200
     * msg : 成功
     * extras :
     * data : [{"id":"9","title":"前途无量","currency":"0","type":"0","img1":"uploads/gift/20181214/4c6cda11910ebadcadea840c0d314b9d769.png","img2":"uploads/gift/20181214/93526332bc1ca967eeb50def1a94286f133.svga"},{"id":"8","title":"爱如潮水","currency":"1","type":"0","img1":"uploads/gift/20181214/6e8c6cd377f052e6cc28b95b5a66b689199.png","img2":"uploads/gift/20181214/b382c760ff99d496db0adf0373215e0e406.svga"},{"id":"7","title":"新年快乐","currency":"1","type":"0","img1":"uploads/gift/20181214/812b62e2e91b4e46121d693994219d9d960.png","img2":"uploads/gift/20181214/e6802029a1a3e705f91c8b208bfe23e7705.svga"},{"id":"3","title":"金币","currency":"10","type":"0","img1":"uploads/gift/20181123/3ddaddd8b0a6291d98b7a97acd2e4961296.png","img2":"uploads/gift/20181123/e4cbe178d4f8d6b037778f810a9d81fd626.png"},{"id":"2","title":"皇冠","currency":"888","type":"0","img1":"uploads/gift/20181031/bad9390721bd7f168f7066b01c803c59240.png","img2":"uploads/gift/20181031/18befc0daf315b69ae71671178c187b4378.png"},{"id":"1","title":"钻戒","currency":"0","type":"0","img1":"uploads/gift/20181031/71f80a5faee421d3ffee4e25ceb794d322.png","img2":"uploads/gift/20181031/5ff3ed1de70bad95a70e2fe8e2012f3f780.png"}]
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

        public DataBean(String id, String title, String type, String img1, int pic) {
            this.id = id;
            this.title = title;
            this.type = type;
            this.img1 = img1;
            this.pic = pic;
        }

        public DataBean() {
        }

        /**
         * id : 9
         * title : 前途无量
         * currency : 0
         * type : 0
         * img1 : uploads/gift/20181214/4c6cda11910ebadcadea840c0d314b9d769.png
         * img2 : uploads/gift/20181214/93526332bc1ca967eeb50def1a94286f133.svga
         */

        @SerializedName("id")
        private String id;
        @SerializedName("title")
        private String title;
        @SerializedName("currency")
        private String currency;
        @SerializedName("type")
        private String type;
        @SerializedName("img1")
        private String img1;
        @SerializedName("img2")
        private String img2;
        @SerializedName("pic")
        private int pic;

        public int getPic() {
            return pic;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImg1() {
            return img1;
        }

        public void setImg1(String img1) {
            this.img1 = img1;
        }

        public String getImg2() {
            return img2;
        }

        public void setImg2(String img2) {
            this.img2 = img2;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", currency='" + currency + '\'' +
                    ", type='" + type + '\'' +
                    ", img1='" + img1 + '\'' +
                    ", img2='" + img2 + '\'' +
                    ", pic=" + pic +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GiftMode{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", extras='" + extras + '\'' +
                ", data=" + data +
                '}';
    }
}