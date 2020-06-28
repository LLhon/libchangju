package com.newproject.hardqing.constant;

//与直播相关的常量；
public class WebSocketConst {


    //硬件获取自己的fd：
    public static final String getfd = "getfd";
    //游客进入直播间
    public static final String tourist_in = "tourist_in";
    //游客退出直播间
    public static final String tourist_out = "tourist_out";


    //websocket 会返回的cmd 命令
    //进入直播间
    public static final String inroom = "inroom";
    //退出直播间
    public static final String outroom = "outroom";


    //直播间发消息
    public static final String send9r = "send9r";
    //直播间刷礼物
    public static final String gift9r = "gift9r";
    //踢人
    public static final String getout9r = "getout9r";
    //禁言/取消禁言
    public static final String ndsend9r = "ndsend9r";

    //开始推流
    public static final String start_live = "start_live";
    //停止推流
    public static final String stop_live = "stop_live";


    //邀请连麦
    public static final String invite9r = "invite9r";

    //连麦反馈  连麦反馈
    public static final String invite9f = "invite9f";

    //邀请断开 主播和被断麦者
    public static final String close_invite = "close_invite";

    //直播间发红包
    public static final String red = "red";

    //直播间领取红包
    public static final String receive_red = "receive_red";

    //主播红包
    public static final String lead_red = "lead_red";


    public static final String keep9h = "keep9h";


    //聊天室发消息
    public static final String send9f = "send9f";

    public static final String pendant9f = "pendant9f";

    public static final String pendant9r = "pendant9r";

    /**************************** TV多房间 ***************************/
    //跨房间发送公共消息，对方房间内所有人都能收到
    public static final String multiroom_public_message = "multiroom_public_message";
}
