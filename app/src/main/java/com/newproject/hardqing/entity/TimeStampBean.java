package com.newproject.hardqing.entity;

/**
 * @author LLhon
 * @description
 * @date 2019/8/17 11:11
 */
public class TimeStampBean {

    /**
     * code : 0
     * type : success
     * description : 请求成功
     * result : 1565963101
     */

    private int code;
    private String type;
    private String description;
    private String result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
