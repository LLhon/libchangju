package com.newproject.hardqing.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.newproject.hardqing.R;
import com.newproject.hardqing.constant.WebSocketConst;
import com.newproject.hardqing.constant.WebSocketFeedConst;
import com.newproject.hardqing.ui.receivecmd.BaScreenEntity;
import com.newproject.hardqing.ui.receivecmd.CandleEntity;
import com.newproject.hardqing.ui.receivecmd.ChangeLiveStatusEntity;
import com.newproject.hardqing.ui.receivecmd.CloseAllEntity;
import com.newproject.hardqing.ui.receivecmd.CloseEntity;
import com.newproject.hardqing.ui.receivecmd.CloseInductionEntity;
import com.newproject.hardqing.ui.receivecmd.CurrencyEntity;
import com.newproject.hardqing.ui.receivecmd.EntertainEntity;
import com.newproject.hardqing.ui.receivecmd.ErrorEntity;
import com.newproject.hardqing.ui.receivecmd.ExtractAudienceEntity;
import com.newproject.hardqing.ui.receivecmd.GiftEntity;
import com.newproject.hardqing.ui.receivecmd.HardwareFailureEntity;
import com.newproject.hardqing.ui.receivecmd.InRoomEntity;
import com.newproject.hardqing.ui.receivecmd.InductionEntity;
import com.newproject.hardqing.ui.receivecmd.InviteAgreedEntity;
import com.newproject.hardqing.ui.receivecmd.InviteEntity;
import com.newproject.hardqing.ui.receivecmd.InviteNoticeEntity;
import com.newproject.hardqing.ui.receivecmd.InviteRefusedEntity;
import com.newproject.hardqing.ui.receivecmd.KickEntity;
import com.newproject.hardqing.ui.receivecmd.LianFeedEntity;
import com.newproject.hardqing.ui.receivecmd.LuckyAudienceEntity;
import com.newproject.hardqing.ui.receivecmd.ManageEntity;
import com.newproject.hardqing.ui.receivecmd.MultiRoomLianMaiEntity;
import com.newproject.hardqing.ui.receivecmd.MultiRoomMessageEntity;
import com.newproject.hardqing.ui.receivecmd.NdSendEntity;
import com.newproject.hardqing.ui.receivecmd.OutRoomEntity;
import com.newproject.hardqing.ui.receivecmd.PandanEntity;
import com.newproject.hardqing.ui.receivecmd.PlayBillEntity;
import com.newproject.hardqing.ui.receivecmd.RandomLuckyEntity;
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
import com.newproject.hardqing.util.GsonConverter;
import com.newproject.hardqing.util.LogUtil;
import com.newproject.hardqing.view.SocketReciveView;

import org.json.JSONException;
import org.json.JSONObject;

public class LivePushHandler extends Handler {

    public static final String TAG = "LivePushHandler";
    private static final int LIVE_ROOM_MSG = 0x123;
    public static final int showLight = 100;
    public static final int hiddenLight = 200;
    public static final int clearAnim = 300;
    public static final int hiddenTree = 400;
    public static final int showTree = 450;
    public static final int canMai = 1000;//发起连麦间隔30s
    public static final int stopRain = 2000;//60s后关闭红包雨
    public static final int stopBapin = 3000;//停止霸屏
    public static final int showCake = 4000;
    public static final int stopLight = 5000;
    public static final int hiddenTag = 6000;
    private SocketReciveView reciveView;
    private Context context;
    private boolean isHost;

    public LivePushHandler(SocketReciveView socketReciveView, Context context, boolean isHost) {
        this.reciveView = socketReciveView;
        this.context = context;
        this.isHost = isHost;
    }


    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case showLight:
                reciveView.showLight();
                break;
            case hiddenLight:
                reciveView.hiddenLight();
                break;
            case clearAnim:
                reciveView.clearAnim();
                break;
            case hiddenTree:
                reciveView.hiddenTree();
                break;
            case showTree:
                reciveView.showTree();
                break;
            case canMai:
                reciveView.canMai();
                break;
            case stopRain:
                reciveView.stopRain();
                break;
            case stopBapin:
                reciveView.stopBapin();
                break;
            case stopLight:
                reciveView.stopLight();
                break;
            case showCake:
                reciveView.showVisibleCake();
                break;
            case hiddenTag:
                reciveView.hiddenTags(true);
                break;
            case 500://加入聊天室成功
                String nickname = context.getString(R.string.xitong);
                String commnetsTwo = context.getString(R.string.commmentstwo);
                reciveView.joinSuccess(new SendMsgEntity("", nickname, "", commnetsTwo, "2"));
                break;
            case LIVE_ROOM_MSG:
                String str = msg.obj.toString();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(str);
                    String cmd = jsonObject.getString("cmd");
                    if (cmd.equals(WebSocketFeedConst.tourist_in)) {
                        TourInEntity tourInEntity = new Gson().fromJson(str, TourInEntity.class);
                        reciveView.tourIn(tourInEntity);
                    } else if (cmd.equals(WebSocketFeedConst.change_live_status)) {
                        ChangeLiveStatusEntity entity = new Gson().fromJson(str, ChangeLiveStatusEntity.class);
                        reciveView.ChangeLiveStatus(entity);
                    } else if (cmd.equals(WebSocketFeedConst.hardwareFailure)) {
                        HardwareFailureEntity entity = new Gson().fromJson(str, HardwareFailureEntity.class);
                        reciveView.hardFailure(entity);
                    } else if (cmd.equals(WebSocketFeedConst.baping)) {
                        BaScreenEntity entity = new Gson().fromJson(str, BaScreenEntity.class);
                        reciveView.bapin(entity);
                    } else if (cmd.equals(WebSocketFeedConst.inroom)) {
                        InRoomEntity inRoomEntity = new Gson().fromJson(str, InRoomEntity.class);
                        reciveView.inRoom(inRoomEntity);
                    } else if (cmd.equals(WebSocketFeedConst.outroom)) {
                        OutRoomEntity outRoomEntity = new Gson().fromJson(str, OutRoomEntity.class);
                        reciveView.outRoom(outRoomEntity);
                    } else if (cmd.equals(WebSocketFeedConst.send9r)) {
                        SendMsgEntity sendMsgEntity = new Gson().fromJson(str, SendMsgEntity.class);
                        reciveView.sendMsg(sendMsgEntity);
                    } else if (cmd.equals(WebSocketFeedConst.room_chat)) {
                        SendChatMsgEntity sendMsgEntity = new Gson().fromJson(str, SendChatMsgEntity.class);
                        reciveView.sendChatMsg(sendMsgEntity);
                    } else if (cmd.equals(WebSocketFeedConst.gift9r)) {
                        GiftEntity giftEntity = new Gson().fromJson(str, GiftEntity.class);
                        reciveView.sendGift(giftEntity);
                    } else if (cmd.equals(WebSocketFeedConst.currency)) {
                        CurrencyEntity currencyEntity = new Gson().fromJson(str, CurrencyEntity.class);
                        reciveView.currency(currencyEntity);
                    } else if (TextUtils.equals(WebSocketFeedConst.manage_s, cmd)) {     // 禁言
                        ManageEntity manageEntity = new Gson().fromJson(str, ManageEntity.class);
                        reciveView.bannedLive(manageEntity);
                    } else if (TextUtils.equals(WebSocketConst.ndsend9r, cmd)) {
                        NdSendEntity ndSendEntity = new Gson().fromJson(str, NdSendEntity.class);
                        SendMsgEntity sendMsgEntity = new SendMsgEntity(ndSendEntity.getUserId(), ndSendEntity.getUsername(), "", ndSendEntity.getContent(), "");
                        reciveView.addMsg(sendMsgEntity);
                    } else if (cmd.equals(WebSocketFeedConst.getout9r)) {
                        KickEntity kickEntity = new Gson().fromJson(str, KickEntity.class);
                        reciveView.kickLive(kickEntity);
                    } else if (cmd.equals(WebSocketFeedConst.start_live)) {
                        StartLiveEntity startLiveEntity = new Gson().fromJson(str, StartLiveEntity.class);
                        reciveView.startLive(startLiveEntity);
                    } else if (cmd.equals(WebSocketFeedConst.stop_live)) {
                        StopLiveEntity stopLiveEntity = new Gson().fromJson(str, StopLiveEntity.class);
                        reciveView.stopLive(stopLiveEntity);
                    } else if (cmd.equals(WebSocketFeedConst.invite9r)) {
                        InviteEntity inviteEntity = new Gson().fromJson(str, InviteEntity.class);
                        reciveView.invite9r(inviteEntity);
                    } else if (cmd.equals(WebSocketFeedConst.invite9f)) {
                        InviteAgreedEntity inviteAgreedEntity = new Gson().fromJson(str, InviteAgreedEntity.class);
                        reciveView.inviteAgree(inviteAgreedEntity);
                    } else if (cmd.equals(WebSocketFeedConst.refused_link)) {
                        InviteRefusedEntity inviteRefusedEntity = new Gson().fromJson(str, InviteRefusedEntity.class);
                        reciveView.inviteRefused(inviteRefusedEntity);
                    } else if (cmd.equals(WebSocketFeedConst.invite9c)) {
                        InviteNoticeEntity inviteNoticeEntity = new Gson().fromJson(str, InviteNoticeEntity.class);
                        reciveView.inviteNotice(inviteNoticeEntity);
                    } else if (cmd.equals(WebSocketFeedConst.close_invite)) {
                        CloseEntity closeEntity = new Gson().fromJson(str, CloseEntity.class);
                        reciveView.closeInvite(closeEntity);
                    } else if (cmd.equals(WebSocketFeedConst.close_invite_all)) {
                        CloseAllEntity closeAllEntity = new Gson().fromJson(str, CloseAllEntity.class);
                        reciveView.closeAllInvite(closeAllEntity);
                    } else if (cmd.equals(WebSocketFeedConst.error)) {
                        ErrorEntity errorEntity = new Gson().fromJson(str, ErrorEntity.class);
                        reciveView.err(errorEntity);
                    } else if (cmd.equals(WebSocketFeedConst.red)) {
                        RedEntity redEntity = new Gson().fromJson(str, RedEntity.class);
                        reciveView.red(redEntity);
                    } else if (cmd.equals(WebSocketFeedConst.red_currency)) {
                        RedCurrencyEntity currencyEntity = new Gson().fromJson(str, RedCurrencyEntity.class);
                        reciveView.redCurrrency(currencyEntity);
                    } else if (cmd.equals(WebSocketFeedConst.receive_red)) {
                        ReceiveRedEntity receiveRedEntity = new Gson().fromJson(str, ReceiveRedEntity.class);
                        reciveView.receiveRed(receiveRedEntity);
                    } else if (cmd.equals(WebSocketFeedConst.lead_red)) {
                        ReceiveLeadRedEntity receiveLeadRedEntity = new Gson().fromJson(str, ReceiveLeadRedEntity.class);
                        reciveView.receiveleadRed(receiveLeadRedEntity);
                    } else if (cmd.equals(WebSocketFeedConst.cake)) {
                        reciveView.showCake();
                    } else if (cmd.equals(WebSocketFeedConst.candle)) {
                        CandleEntity candleEntity = new Gson().fromJson(str, CandleEntity.class);
                        reciveView.candle(candleEntity.getCandleId());
                    } else if (cmd.equals(WebSocketFeedConst.candle_all)) {
                        if (!isHost) {
                            reciveView.showLight();
                        }
                    } else if (cmd.equals(WebSocketFeedConst.vow)) {
                        if (!isHost) {
                            reciveView.showTree();
                        }
                    } else if (cmd.equals(WebSocketFeedConst.new_cake)) {
                        reciveView.showStar();
                    } else if (cmd.equals(WebSocketFeedConst.blow_out)) {
                        if (!isHost) {
                            reciveView.hiddenLight();
                        }
                    } else if (cmd.equals(WebSocketFeedConst.all_out)) {
                        if (!isHost) {
                            reciveView.clearAnim();
                        }
                    } else if (cmd.equals(WebSocketFeedConst.see_template)) {
                        SeeTemplateEntity seeTemplateEntity = new Gson().fromJson(str, SeeTemplateEntity.class);
                        reciveView.showTemplate(seeTemplateEntity);
                    } else if (cmd.equals(WebSocketFeedConst.quit_template)) {
                        reciveView.quitTemplate();
                    }else if (cmd.equals(WebSocketFeedConst.pendant9f)) {
                        PandanEntity pandanEntity = new Gson().fromJson(str, PandanEntity.class);
                        reciveView.showTags(pandanEntity.getPid());
                    } else if (cmd.equals(WebSocketFeedConst.pendant9r)) {
                        reciveView.hiddenTags(false);
                    }else if (cmd.equals(WebSocketFeedConst.stop_up)) {
                        reciveView.stopUp();
                    } else if (cmd.equals(WebSocketFeedConst.updateRoom)) {
                        UpdateRoomEntity entity = new Gson().fromJson(str, UpdateRoomEntity.class);
                        reciveView.updateRoom(entity);
                    } else if (cmd.equals(WebSocketFeedConst.playbill)) {
                        PlayBillEntity entity = new Gson().fromJson(str, PlayBillEntity.class);
                        reciveView.showPlayBill(entity);
                    } else if (cmd.equals(WebSocketFeedConst.multiroom_public_message)) {
                        //跨房间发送公共消息，对方房间内所有人都能收到
                        MultiRoomMessageEntity entity =
                            GsonConverter.fromJson(str, MultiRoomMessageEntity.class);
                        reciveView.messageByMultiRoom(entity);
                    } else if (cmd.equals(WebSocketFeedConst.entertainment)) {
                        //娱乐活动展示轮盘
                        EntertainEntity entertainEntity = new Gson().fromJson(str, EntertainEntity.class);
                        reciveView.showEntertainLuck(entertainEntity);
                    }else if (cmd.equals(WebSocketFeedConst.open_room_introduction)){
                        InductionEntity inductionEntity = new Gson().fromJson(str,InductionEntity.class);
                        reciveView.showInduction(inductionEntity);
                    } else if (cmd.equals(WebSocketFeedConst.extractAudience)) {
                        //被抽到的幸运观众 功能描述：点击随机抽取幸运观众之后前端返回的用户，服务端推送该用户给房间内所有人
                        ExtractAudienceEntity extractAudienceEntity = new Gson().fromJson(str, ExtractAudienceEntity.class);
                        reciveView.showExtractAudience(extractAudienceEntity);
                    } else if (cmd.equals(WebSocketFeedConst.close_induction)) {
                        //关闭转盘或自我介绍  功能描述： 通知房间内所有人关闭自我介绍或者转盘
                        CloseInductionEntity closeInductionEntity = new Gson().fromJson(str,CloseInductionEntity.class);
                        reciveView.showCloseInduction(closeInductionEntity);
                    } else if (cmd.equals(WebSocketFeedConst.lucky_audience_feedback)) {
                        //被抽到的幸运观众反馈
                        LuckyAudienceEntity luckyAudienceEntity = new Gson().fromJson(str, LuckyAudienceEntity.class);
                        reciveView.FeedbackLucky(luckyAudienceEntity);
                    } else if (cmd.equals(WebSocketFeedConst.self_introduction_random_users)) {
                        //自我介绍随机抽几名幸运观众
                        RandomLuckyEntity randomLuckyEntity = new Gson().fromJson(str,RandomLuckyEntity.class);
                        reciveView.FiveAudience(randomLuckyEntity);
                    } else if (cmd.equals(WebSocketFeedConst.self_introduction)) {
                        //自我介绍连麦
                        LianFeedEntity lianFeedEntity = new Gson().fromJson(str,LianFeedEntity.class);
                        reciveView.lianFeedBack(lianFeedEntity);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

}
