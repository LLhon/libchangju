package com.newproject.hardqing.ui.receivecmd;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.newproject.hardqing.entity.BaPingChatMessage;
import com.newproject.hardqing.util.GsonConverter;

public class SendMsgEntity {

    @SerializedName("cmd")
    private String cmd;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("username")
    private String username;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("content")
    private String content;

    @SerializedName("is_manage")
    private String is_manage;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsManage() {
        return is_manage;
    }

    public void setIs_manage(String is_manage) {
        this.is_manage = is_manage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public SendMsgEntity(String userId, String username, String avatar, String content, String is_manage) {
        this.userId = userId;
        this.username = username;
        this.avatar = avatar;
        this.content = content;
        this.is_manage = is_manage;
    }

    public boolean isClickGiftMessage() {
        if (!TextUtils.isEmpty(content) && GsonConverter.isJson(content)) {
            BaPingChatMessage baPingChatMessage = GsonConverter.fromJson(content, BaPingChatMessage.class);
            if (baPingChatMessage != null && baPingChatMessage.getType() == 5) {
                return true;
            }
        }
        return false;
    }

    @Override public String toString() {
        return "SendMsgEntity{" +
            "cmd='" + cmd + '\'' +
            ", userId='" + userId + '\'' +
            ", username='" + username + '\'' +
            ", avatar='" + avatar + '\'' +
            ", content='" + content + '\'' +
            ", is_manage='" + is_manage + '\'' +
            '}';
    }
}
