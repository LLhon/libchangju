package com.newproject.hardqing.entity;

import com.google.gson.annotations.SerializedName;

public class SunTime {

    /**
     {
     "code": "0",
     "type": "success",
     "description": "请求成功",
     "result": 1587775862
     }
     */
    @SerializedName("code")
    private String code;
    @SerializedName("type")
    private String type;
    @SerializedName("description")
    private String description;
    @SerializedName("result")
    private long result;

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

    public long getResult() {
        return result;
    }

    public void setResult(long result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "TimeStampBean{" +
                "code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", result=" + result +
                '}';
    }
}
