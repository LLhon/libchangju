package com.newproject.hardqing.service;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.newproject.hardqing.ui.sendcmd.GetIdSocket;
import com.newproject.hardqing.ui.sendcmd.InroomSocket;
import com.newproject.hardqing.ui.sendcmd.OutRoomSocket;
import com.newproject.hardqing.ui.sendcmd.PendanHiddenSocket;

public class CommonUtil {
    //发广播，socket service中发消息；
    public static void sendMsg(Context context, String msg) {
        Intent intent = new Intent();
        intent.setAction(LiveService.ACTION_SEND_MSG);
        intent.putExtra(LiveService.SEND_MSG, msg);
        context.sendBroadcast(intent);
    }

    //获取fd；
    public static void socketGetId(Context context) {
        GetIdSocket getIdSocket = new GetIdSocket();
        sendMsg(context, new Gson().toJson(getIdSocket));
    }

    //进入直播间
    public static void inroom(Context context, String roomId) {
        InroomSocket inRoomSocket = new InroomSocket(roomId);
        sendMsg(context, new Gson().toJson(inRoomSocket));
    }

    //退出直播间
    public static void outroom(Context context, String roomId,String uid) {
        OutRoomSocket outRoomSocket = new OutRoomSocket(roomId,uid);
        sendMsg(context, new Gson().toJson(outRoomSocket));
    }

    //隐藏挂件
    public static void hiddenTag(Context context, String room_id) {
        PendanHiddenSocket pendanHiddenSocket = new PendanHiddenSocket(room_id);
        sendMsg(context, new Gson().toJson(pendanHiddenSocket));
    }


}
