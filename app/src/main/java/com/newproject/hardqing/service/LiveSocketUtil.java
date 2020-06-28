package com.newproject.hardqing.service;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import com.newproject.hardqing.constant.WebSocketConst;
import com.newproject.hardqing.ui.sendcmd.ChorusRequest;
import com.newproject.hardqing.ui.sendcmd.MultiRoomMessageSocket;
import com.newproject.hardqing.ui.sendcmd.PublicMessageSocket;
import com.newproject.hardqing.util.GsonConverter;

/**
 * @description 直播间Socket消息
 * @author LLhon
 * @date 2019/8/26 11:17
 */
public class LiveSocketUtil {

    private static final String LIVE = "Live";
    private static final String VERSION = "1.0";

    //发广播，socket service中发消息；
    public static void sendMsg(Context context, String msg) {
        Intent intent = new Intent();
        intent.setAction(LiveService.ACTION_SEND_MSG);
        intent.putExtra(LiveService.SEND_MSG, msg);
        context.sendBroadcast(intent);
    }

    //合唱下载歌曲成功 Type = 4；
    public static void sendChorusDownLoadSuccess(Context context, String userId, String userName,
        String songcode, String lrcUrl, String roomId) {
        ChorusRequest musicSocket = new ChorusRequest(userId, userName, 4, songcode, lrcUrl, roomId);
        sendMsg(context, new Gson().toJson(musicSocket));
    }

    /**
     * 通知连麦观众端取消合唱
     * @param context
     * @param userId
     * @param userName
     * @param songcode
     * @param lrcUrl
     * @param roomId
     */
    public static void sendCancelChorus(Context context, String userId, String userName,
        String songcode, String lrcUrl, String roomId) {
        ChorusRequest musicSocket = new ChorusRequest(userId, userName, 5, songcode, lrcUrl, roomId);
        sendMsg(context, new Gson().toJson(musicSocket));
    }

    /**
     * 发送消息到直播间内的所有观众
     * @param context
     * @param socket
     */
    public static void sendPublicMessage(Context context, PublicMessageSocket socket) {
        sendMsg(context, new Gson().toJson(socket));
    }

    /*******************************   跨房间消息   *****************************/

    /**
     * 跨房间发送公共消息，对方房间内所有人都能收到
     * @param context
     */
    public static void sendMessageByMultiRoom(Context context, MultiRoomMessageSocket socket) {
        sendMsg(context, GsonConverter.toJson(socket));
    }
}
