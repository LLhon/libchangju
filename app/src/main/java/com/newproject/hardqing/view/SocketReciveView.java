package com.newproject.hardqing.view;


import com.newproject.hardqing.ui.receivecmd.BaScreenEntity;
import com.newproject.hardqing.ui.receivecmd.ChangeLiveStatusEntity;
import com.newproject.hardqing.ui.receivecmd.CloseAllEntity;
import com.newproject.hardqing.ui.receivecmd.CloseEntity;
import com.newproject.hardqing.ui.receivecmd.CurrencyEntity;
import com.newproject.hardqing.ui.receivecmd.ErrorEntity;
import com.newproject.hardqing.ui.receivecmd.GiftEntity;
import com.newproject.hardqing.ui.receivecmd.HardwareFailureEntity;
import com.newproject.hardqing.ui.receivecmd.InRoomEntity;
import com.newproject.hardqing.ui.receivecmd.InviteAgreedEntity;
import com.newproject.hardqing.ui.receivecmd.InviteEntity;
import com.newproject.hardqing.ui.receivecmd.InviteNoticeEntity;
import com.newproject.hardqing.ui.receivecmd.InviteRefusedEntity;
import com.newproject.hardqing.ui.receivecmd.KickEntity;
import com.newproject.hardqing.ui.receivecmd.ManageEntity;
import com.newproject.hardqing.ui.receivecmd.MultiRoomLianMaiEntity;
import com.newproject.hardqing.ui.receivecmd.MultiRoomMessageEntity;
import com.newproject.hardqing.ui.receivecmd.OutRoomEntity;
import com.newproject.hardqing.ui.receivecmd.PlayBillEntity;
import com.newproject.hardqing.ui.receivecmd.ReceiveLeadRedEntity;
import com.newproject.hardqing.ui.receivecmd.ReceiveRedEntity;
import com.newproject.hardqing.ui.receivecmd.RedCurrencyEntity;
import com.newproject.hardqing.ui.receivecmd.RedEntity;
import com.newproject.hardqing.ui.receivecmd.SeeTemplateEntity;
import com.newproject.hardqing.ui.receivecmd.SendChatMsgEntity;
import com.newproject.hardqing.ui.receivecmd.SendMsgEntity;
import com.newproject.hardqing.ui.receivecmd.StartLiveEntity;
import com.newproject.hardqing.ui.receivecmd.StopLiveEntity;
import com.newproject.hardqing.ui.receivecmd.TourInEntity;
import com.newproject.hardqing.ui.receivecmd.UpdateRoomEntity;

public interface SocketReciveView {

    void joinSuccess(SendMsgEntity sendMsgEntity);

    void inRoom(InRoomEntity inRoomEntity);

    void outRoom(OutRoomEntity outRoomEntity);

    void sendMsg(SendMsgEntity sendMsgEntity);

    void sendGift(GiftEntity giftEntity);

    void currency(CurrencyEntity currencyEntity);

    void bannedLive(ManageEntity manageEntity);

    void kickLive(KickEntity kickEntity);

    void startLive(StartLiveEntity startLiveEntity);

    void stopLive(StopLiveEntity stopLiveEntity);

    //邀请连麦
    void invite9r(InviteEntity msg);

    //连麦同意
    void inviteAgree(InviteAgreedEntity inviteAgreedEntity);

    //连麦拒绝
    void inviteRefused(InviteRefusedEntity inviteRefusedEntity);

    //连麦推流成功回调返回   通知全直播间的人
    void inviteNotice(InviteNoticeEntity inviteNoticeEntity);

    //关闭一个连麦
    void closeInvite(CloseEntity closeEntity);

    //关闭全部连麦
    void closeAllInvite(CloseAllEntity closeAllEntity);

    //错误返回
    void err(ErrorEntity errorEntity);

    void addMsg(SendMsgEntity sendMsgEntity);

    void sendChatMsg(SendChatMsgEntity sendchatMsgEntity);

    //显示全部蜡烛
    void showLight();

    //清屏
    void clearAnim();

    //隐藏树
    void hiddenTree();

    //吹灭蜡烛
    void hiddenLight();

    //显示许愿树
    void showTree();

    //显示蛋糕
    void showCake();

    //点亮一根蜡烛
    void candle(String candleId);


    //点亮全部蜡烛
//    void candleAll();

    //虚化蛋糕 出现许愿树：
//    void vow();

    //实化蛋糕 消失许愿树：
//    void newCake();

    //收到红包
    void red(RedEntity redEntity);

    //发完红包返回余额
    void redCurrrency(RedCurrencyEntity currencyEntity);

    //领取红包返回
    void receiveRed(ReceiveRedEntity receiveRedEntity);

    //主播红包返回
    void receiveleadRed(ReceiveLeadRedEntity receiveLeadRedEntity);

    //30秒到了，可以发起连麦
    void canMai();

    //60秒到了，停止红包雨动画
    void stopRain();





    //显示模板
    void showTemplate(SeeTemplateEntity seeTemplateEntity);
    //停止展示模板
    void quitTemplate();
    //游客进入
    void tourIn(TourInEntity tourInEntity);
    //游客收到就退出
    void hardFailure(HardwareFailureEntity tourInEntity);
    //霸屏
    void bapin(BaScreenEntity baScreenEntity);
    //停止霸屏
    void stopBapin();

    void  ChangeLiveStatus(ChangeLiveStatusEntity entity);

    void showStar();

    void showVisibleCake();

    void stopLight();

    void hiddenTags(boolean isMe);

    void showTags(String pid);

    //主播离开，回到二维码页面
    void stopUp();

    void updateRoom(UpdateRoomEntity entity);

    void showPlayBill(PlayBillEntity entity);

    void messageByMultiRoom(MultiRoomMessageEntity entity);

}
