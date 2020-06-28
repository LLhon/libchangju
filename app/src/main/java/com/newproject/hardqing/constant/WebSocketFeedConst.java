package com.newproject.hardqing.constant;

public class WebSocketFeedConst {


    //websocket 会返回的cmd 命令

    public static final String getfd = "getfd";
    //返回给硬件  收到此socket进入直播间socket
    public static final String hardwareLaunch = "hardwareLaunch";
    //游客进入直播间
    public static final String tourist_in = "tourist_in";
    //游客退出直播间
    public static final String tourist_out = "tourist_out";
    //直播间关闭，退出直播间
    public static final String hardwareFailure = "hardwareFailure";

    //观众霸屏
    public static final String baping = "baping";

    //进入直播间
    public static final String inroom = "inroom";
    //退出直播间
    public static final String outroom = "outroom";


    //直播间发消息
    public static final String send9r = "send9r";

    public static final String room_chat = "room_chat";//新的发消息

    //直播间刷礼物
    public static final String gift9r = "gift9r";
    //余额 (反馈信息)
    public static final String currency = "currency";

    //禁言操作 貌似没有管理 (反馈信息)
    public static final String manage_s = "manage_s";

    //禁言提示
    public static final String ndsend9r = "ndsend9r";

    //踢人
    public static final String getout9r = "getout9r";

    //开始推流
    public static final String start_live = "start_live";
    //停止推流
    public static final String stop_live = "stop_live";
    //主播离开
    public static final String stop_up = "stop_up";


    //邀请连麦
    public static final String invite9r = "invite9r";


    //连麦反馈  连麦反馈 的 (反馈信息)
    public static final String invite9f = "invite9f";

    //发送 连麦反馈 的 (反馈信息)
    public static final String refused_link = "refused_link";

    //连麦推流成功回调返回   全直播间的人
    public static final String invite9c = "invite9c";


    //邀请断开 主播和被断麦者
    public static final String close_invite = "close_invite";

    // 邀请断开 当连麦一个人都没有返回 (反馈信息)
    public static final String close_invite_all = "close_invite_all";

    //错误返回 (反馈信息)
    public static final String error = "error";

    //返回 发红包者
    public static final String red_currency = "red_currency";

    //领取红包返回
    public static final String receive_red = "receive_red";

    //主播红包返回
    public static final String lead_red = "lead_red";

    //返回 全直播间的人
    public static final String red = "red";


    //礼物特效
    //蛋糕出现
    public static final String cake = "cake";
    //点亮单根蜡烛
    public static final String candle = "candle";
    //点亮全部蜡烛
    public static final String candle_all = "candle_all";
    //虚化蛋糕  出现许愿树：
    public static final String vow = "vow";
    //实话蛋糕
    public static final String new_cake = "new_cake";
    //吹灭蜡烛
    public static final String blow_out = "blow_out";
    //关闭所有蛋糕特效动画
    public static final String all_out = "all_out";


    //模板显示某一页：
    public static final String see_template = "see_template";

    //取消模板显示或者切回直播
    public static final String quit_template = "quit_template";

    //挂件出现
    public static final String pendant9f = "pendant9f";

    //挂件消失
    public static final String pendant9r = "pendant9r";


    public static final String switch_video_source = "switch_video_source";

    public static final String change_live_status = "change_live_status";

    //APP同步歌曲到TV
    public static final String hardware_room_command = "hardware_room_command";

    //一对一消息透传
    public static final String private_message = "private_message";
    //一对多消息透传
    public static final String all_message = "all_message";

    //修改房间主题和封面
    public static final String updateRoom = "update_room";

    //节目单
    public static final String playbill = "playbill";



    /**************************** TV多房间 ***************************/
    //跨房间发送公共消息，对方房间内所有人都能收到
    public static final String multiroom_public_message = "multiroom_public_message";

}
