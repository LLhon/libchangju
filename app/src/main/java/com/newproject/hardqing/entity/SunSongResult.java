package com.newproject.hardqing.entity;

import com.google.gson.annotations.SerializedName;

public class SunSongResult {

    /**
     * id : 886356 name : Accessories jianpin : A language : 1 length : 1 singerid : 12003644 singer
     * : 莫文蔚 quality : 0 movie : times : 0
     */
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("jianpin")
    private String jianpin;
    @SerializedName("language")
    private String language;
    @SerializedName("length")
    private int length;
    @SerializedName("singerid")
    private String singerid;
    @SerializedName("singer")
    private String singer;
    @SerializedName("quality")
    private int quality;
    @SerializedName("movie")
    private String movie;
    @SerializedName("times")
    private int times;
    @SerializedName("sex")
    private String sex;
    @SerializedName("region")
    private int region;
    @SerializedName("headimg")
    private String headimg;
    @SerializedName("mp3")
    private String mp3;
    @SerializedName("mp3bc")
    private String mp3bc;
    @SerializedName("lrc")
    private String lrc;

    public String getMp3() {
        return mp3;
    }

    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }

    public String getMp3bc() {
        return mp3bc;
    }

    public void setMp3bc(String mp3bc) {
        this.mp3bc = mp3bc;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJianpin() {
        return jianpin;
    }

    public void setJianpin(String jianpin) {
        this.jianpin = jianpin;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getSingerid() {
        return singerid;
    }

    public void setSingerid(String singerid) {
        this.singerid = singerid;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    @Override public String toString() {
        return "SunSongResult{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", jianpin='" + jianpin + '\'' +
            ", language='" + language + '\'' +
            ", length=" + length +
            ", singerid='" + singerid + '\'' +
            ", singer='" + singer + '\'' +
            ", quality=" + quality +
            ", movie='" + movie + '\'' +
            ", times=" + times +
            ", sex='" + sex + '\'' +
            ", region=" + region +
            ", headimg='" + headimg + '\'' +
            ", mp3='" + mp3 + '\'' +
            ", mp3bc='" + mp3bc + '\'' +
            ", lrc='" + lrc + '\'' +
            '}';
    }
}
