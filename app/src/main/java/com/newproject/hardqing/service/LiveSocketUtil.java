package com.newproject.hardqing.service;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import com.newproject.hardqing.constant.WebSocketConst;
import com.newproject.hardqing.ui.receivecmd.RandomLuckyEntity;
import com.newproject.hardqing.ui.sendcmd.ChorusRequest;
import com.newproject.hardqing.ui.sendcmd.CloseOrInductionSocket;
import com.newproject.hardqing.ui.sendcmd.EntertainSocket;
import com.newproject.hardqing.ui.sendcmd.ExtractAudienceSocket;
import com.newproject.hardqing.ui.sendcmd.InductionSocket;
import com.newproject.hardqing.ui.sendcmd.LianFeedSocket;
import com.newproject.hardqing.ui.sendcmd.LuckyAudienceFeedbackSocket;
import com.newproject.hardqing.ui.sendcmd.LuckyFiveAudienceSocket;
import com.newproject.hardqing.ui.sendcmd.MultiRoomMessageSocket;
import com.newproject.hardqing.ui.sendcmd.PublicMessageSocket;
import com.newproject.hardqing.util.GsonConverter;

import java.util.List;

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

    //显示转盘
    public static void dEntertain(Context context,String activity_id,String room_id){
        EntertainSocket entertainSocket = new EntertainSocket(activity_id,room_id);
        sendMsg(context,new Gson().toJson(entertainSocket));
    }

    //显示自我介绍
    public static void showInduction(Context context,String room_id){
        InductionSocket inductionSocket = new InductionSocket(room_id);
        sendMsg(context,new Gson().toJson(inductionSocket));
    }

    //自我介绍随机抽几名幸运观众
    public static void showFiveAudience(Context context, String anchor_id, List<RandomLuckyEntity.UsersBean> users, String room_id, String activity_id){
        LuckyFiveAudienceSocket luckyFiveAudienceSocket = new LuckyFiveAudienceSocket(anchor_id,users,room_id,activity_id);
        sendMsg(context,new Gson().toJson(luckyFiveAudienceSocket));
    }

    //被抽到的幸运观众
    public static void extractAudience(Context context,String user_id,String anchor_id,String username,String room_id){
        ExtractAudienceSocket extractAudienceSocket = new ExtractAudienceSocket(user_id,anchor_id,username,room_id);
        sendMsg(context,new Gson().toJson(extractAudienceSocket));
    }

    public static void LianFeedBack(Context context,String user_id,String room_id,String activity_id, String anchor_id){
        LianFeedSocket lianFeedSocket = new LianFeedSocket(user_id,room_id,activity_id,anchor_id);
        sendMsg(context,new Gson().toJson(lianFeedSocket));
    }

    /**
     * @param context
     * @param user_id  当前主播id
     */
    //关闭转盘或者自我介绍
    public static void closeInduction(Context context,String user_id,String room_id,String anchor_id){
        CloseOrInductionSocket closeOrInductionSocket = new CloseOrInductionSocket(user_id,room_id,anchor_id);
        sendMsg(context,new Gson().toJson(closeOrInductionSocket));
    }

    //被抽到的幸运观众反馈
    public static void luckyAudienceFeedback(Context context,String user_id, String username, String anchor_id, String room_id, String content){
        LuckyAudienceFeedbackSocket luckyAudienceFeedbackSocket = new LuckyAudienceFeedbackSocket(user_id,username,anchor_id,room_id,content);
        sendMsg(context,new Gson().toJson(luckyAudienceFeedbackSocket));
    }
}
