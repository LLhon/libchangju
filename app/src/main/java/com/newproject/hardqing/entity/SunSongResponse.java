package com.newproject.hardqing.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SunSongResponse {

    /**
     * code : 0
     * type : success
     * description : 请求成功
     * region : B
     * totalCount : 80052
     * result : [{"id":"886356","name":"Accessories","jianpin":"A","language":"1","length":1,"singerid":"12003644","singer":"莫文蔚","quality":0,"movie":"","times":0},{"id":"255117","name":"Afflatus","jianpin":"A","language":"1","length":1,"singerid":"13001667","singer":"鬼否乐队","quality":0,"movie":"","times":0},{"id":"966817","name":"Aitai","jianpin":"A","language":"1","length":1,"singerid":"13000735","singer":"蜜雪薇琪","quality":0,"movie":"","times":0},{"id":"975891","name":"ALL","jianpin":"A","language":"1","length":1,"singerid":"11006098","singer":"3秒乐团","quality":0,"movie":"","times":0},{"id":"153622","name":"Alone","jianpin":"A","language":"1","length":1,"singerid":"11006785","singer":"黄子韬","quality":0,"movie":"","times":0},{"id":"661233","name":"Alright","jianpin":"A","language":"1","length":1,"singerid":"13000735","singer":"蜜雪薇琪","quality":0,"movie":"","times":0},{"id":"378047","name":"Always","jianpin":"A","language":"1","length":1,"singerid":"11001463","singer":"陈杰瑞/潘嘉丽","quality":0,"movie":"","times":0},{"id":"846587","name":"Amen","jianpin":"A","language":"1","length":1,"singerid":"12001086","singer":"戴佩妮","quality":0,"movie":"","times":0},{"id":"146346","name":"Amour","jianpin":"A","language":"1","length":1,"singerid":"12003998","singer":"梁咏琪","quality":0,"movie":"","times":0},{"id":"900997","name":"Andy","jianpin":"A","language":"1","length":1,"singerid":"11001748","singer":"阿杜","quality":0,"movie":"","times":0},{"id":"143132","name":"Angel","jianpin":"A","language":"1","length":1,"singerid":"13000871","singer":"T-rush","quality":0,"movie":"","times":0},{"id":"140951","name":"Angel","jianpin":"A","language":"1","length":1,"singerid":"11001397","singer":"陈冠希","quality":0,"movie":"","times":0},{"id":"848094","name":"Angel","jianpin":"A","language":"1","length":1,"singerid":"11004188","singer":"欢子","quality":0,"movie":"","times":0},{"id":"976303","name":"Angel","jianpin":"A","language":"1","length":1,"singerid":"13000173","singer":"谎言留声机","quality":0,"movie":"","times":0},{"id":"834343","name":"Angel","jianpin":"A","language":"1","length":1,"singerid":"11004272","singer":"李圣杰","quality":0,"movie":"","times":0},{"id":"801852","name":"Angel","jianpin":"A","language":"1","length":1,"singerid":"11005383","singer":"陶喆","quality":0,"movie":"","times":0},{"id":"402888","name":"ANGEL","jianpin":"A","language":"1","length":1,"singerid":"11005383","singer":"陶喆","quality":0,"movie":"","times":0},{"id":"146878","name":"Angel","jianpin":"A","language":"1","length":1,"singerid":"12002398","singer":"赵咏华","quality":0,"movie":"","times":0},{"id":"140037","name":"Angeline","jianpin":"A","language":"1","length":1,"singerid":"11005383","singer":"陶喆","quality":0,"movie":"","times":0},{"id":"873058","name":"Angella","jianpin":"A","language":"1","length":1,"singerid":"11003923","singer":"蓝雨","quality":0,"movie":"","times":0}]
     */
    @SerializedName("code")
    private String code;
    @SerializedName("type")
    private String type;
    @SerializedName("description")
    private String description;
    @SerializedName("region")
    private String region;
    @SerializedName("totalCount")
    private int totalCount;
    @SerializedName("result")
    private List<SunSongResult> songResultList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<SunSongResult> getSongResultList() {
        return songResultList;
    }

    public void setSongResultList(List<SunSongResult> songResultList) {
        this.songResultList = songResultList;
    }

    @Override
    public String toString() {
        return "SunSongResult{" +
                "code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", region='" + region + '\'' +
                ", totalCount=" + totalCount +
                ", songResultList=" + songResultList +
                '}';
    }

}
