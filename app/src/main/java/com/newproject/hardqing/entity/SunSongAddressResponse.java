package com.newproject.hardqing.entity;

/**
 * Created by LLhon
 */
public class SunSongAddressResponse {
    /**
     * code : 0
     * type : success
     * description : 请求成功
     * region : C1
     * result : {"mp3":"http://ks3.score.joyk.com.cn/shineAccompani/153238-yc.mp3?AccessKeyId=nXLz3axA9cpFXPsrs0fS&Expires=1589453387&Signature=Ef%2B2dN5ilnbq0ND2OE/UtyO5q8o%3D&","mp3bc":"http://ks3.score.joyk.com.cn/shineAccompani/153238-bc.mp3?AccessKeyId=nXLz3axA9cpFXPsrs0fS&Expires=1589453387&Signature=mtdRWOXB25jnJAl2hIuAbFc06w8%3D&","lrc":"http://ks3.score.joyk.com.cn/shineAccompani/153238.lrc?AccessKeyId=nXLz3axA9cpFXPsrs0fS&Expires=1589453387&Signature=TL8oOEQhw6OREJBdo8MXIGi6T3I%3D&"}
     */

    private String code;
    private String type;
    private String description;
    private String region;
    private SunSongResult result;

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

    public SunSongResult getResult() {
        return result;
    }

    public void setResult(SunSongResult result) {
        this.result = result;
    }

    @Override public String toString() {
        return "SunSongAddressResponse{" +
            "code='" + code + '\'' +
            ", type='" + type + '\'' +
            ", description='" + description + '\'' +
            ", region='" + region + '\'' +
            ", result=" + result +
            '}';
    }
}
