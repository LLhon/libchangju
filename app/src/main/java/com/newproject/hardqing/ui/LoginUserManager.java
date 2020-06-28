package com.newproject.hardqing.ui;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.newproject.hardqing.base.BaseApplication;
import com.newproject.hardqing.constant.PreConst;
import com.newproject.hardqing.util.PreferenceUtil;


public class LoginUserManager {

    private static long refreshTime;

    public static boolean hasToken() {
        return !TextUtils.isEmpty(getToken());
    }


    public static String getUserId() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.USERID, "");
    }

    public static void setUserId(String userId) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.USERID, userId);
    }


    public static String getUsername() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.USER_NAME, "");
    }

    public static void saveUsername(String name) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.USER_NAME, name);
    }

    public static String getSex() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.sex, "");
    }

    public static void saveSex(String sex) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.sex, sex);
    }

    public static String getLocation() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.location, "");
    }

    public static void saveLocation(String location) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.location, location);
    }

    public static String getProvince() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.province, "");
    }

    public static void saveProvince(String province) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.province, province);
    }

    public static String getLng() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.lng, "");
    }

    public static void saveLng(String lng) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.lng, lng);
    }

    public static String getLat() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.lat, "");
    }

    public static void saveLat(String lat) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.lat, lat);
    }

    public static String getSign() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.autograph, "");
    }

    public static void saveSign(String sign) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.autograph, sign);
    }


    public static void saveToken(String token) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.token, token);
    }

    public static String getToken() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.token, "emjdakhmi74gwwkww8ckoco08");
    }


    public static String getBankCard() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.bank_card, "");
    }

    public static void setBankCard(String num) {
        PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.bank_card, "");
    }

    public static void saveBankCard(String bankCard) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.bank_card, bankCard);
    }

    public static String getCardId() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.card_id, "");
    }

    public static void saveCardId(String id) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.card_id, id);
    }

    public static String getPhone() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.MOBILE, "");
    }

    public static void savePhone(String phone) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.MOBILE, phone);
    }

    public static String getGiftAddress() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.gift_address, "");
    }

    public static void saveGiftAddress(String address) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.gift_address, address);
    }

    //是否设置了pay pass，支付密码；
    public static boolean isSetPayPass() {
        if (PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.pay_password, "").equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    public static void setPaypass() {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.pay_password, "1");
    }


    public static String getUserType() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.USER_TYPE, "");
    }

    public static String getIsReview() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.USER_TYPE, "");
    }

    public static String getIsSm() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.is_sm, "");
    }

    public static String getIsLara() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.is_lara, "");
    }


    /**
     * 开播前数据设置
     *
     * @param liveCover     直播封面
     * @param liveTypeScene 直播场景标题
     * @param liveTypeId    直播场景id
     * @param liveName      直播标题
     */
    public static void setLiveForData(String liveCover, String liveTypeScene, String liveTypeId, String liveName) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.LIVE_COVER, liveCover);
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.LIVE_TYPE_SCENE, liveTypeScene);
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.LIVE_TYPE_ID, liveTypeId);
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.LIVE_NAME, liveName);
    }

    public static String getAvatar() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.AVATAR, "");
    }

    public static void setAvatar(String path) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.AVATAR, path);
    }

    public static String getMobile() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.MOBILE, "");
    }

    public static void setMobile(String mobile) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.MOBILE, mobile);
    }

    public static String getLiveCover() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.LIVE_COVER, "");
    }


    public static String getLiveTypeScene() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.LIVE_TYPE_SCENE, "");
    }

    public static String getLiveTypeId() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.LIVE_TYPE_ID, "");
    }

    public static String getLiveName() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.LIVE_NAME, "");
    }


    public static void setIsSm(String isSm) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.is_sm, isSm);
    }

    public static void setIsLara(String isLara) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.is_sm, isLara);
    }

    public static void setRefreshTime(long refreshTime) {
        PreferenceUtil.setSettingLong(BaseApplication.getApp(), PreConst.refreshTime, refreshTime);
    }

    public static Long getRefreshTime() {
        return PreferenceUtil.getPrefLong(BaseApplication.getApp(), PreConst.refreshTime, 0);
    }

    //是否从直播间去礼物池
    public static boolean isGiftPool() {
        return PreferenceUtil.getPrefBoolean(BaseApplication.getApp(), PreConst.giftpool, false);
    }

    public static void setGiftPool(boolean b) {
        PreferenceUtil.setPrefBoolean(BaseApplication.getApp(), PreConst.giftpool, b);
    }

    //从直播间去礼物池,房间id
    public static String getRoom() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.roomId, "");
    }

    public static void setRoom(String room) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.roomId, room);
    }

    //从直播间去礼物池,主播名字
    public static String getAnchor() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.anchor, "");
    }

    public static void setAnchor(String anchor) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.anchor, anchor);
    }


    public static void setTemplate(String tid, String xid) {
        setTip(tid);
        setXid(xid);
    }

    public static String getTip() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.tid, "");
    }

    public static void setTip(String path) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.tid, path);
    }

    public static String getXid() {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(), PreConst.xid, "");
    }

    public static void setXid(String path) {
        PreferenceUtil.setPrefString(BaseApplication.getApp(), PreConst.xid, path);
    }

    public static int getMoPi(Context context) {
        return PreferenceUtil.getPrefInt(context, PreConst.moPi, 50);
    }

    public static boolean getXinMoPi(Context context) {
        return PreferenceUtil.getPrefBoolean(context, PreConst.moPi, false);
    }

    public static int getBeautyLevel(Context context) {
        return PreferenceUtil.getPrefInt(context, PreConst.beauty, 50);
    }

    public static boolean getXinBeautyLevel(Context context) {
        return PreferenceUtil.getPrefBoolean(context, PreConst.beauty, false);
    }


    public  static  final  String  getLiveVideoSource()
    {
        return PreferenceUtil.getPrefString(BaseApplication.getApp(),"livevideosource", "app");
    }

    public  static  void  setLiveVideoSource(final  String videoSource)
    {
        PreferenceUtil.setPrefString(BaseApplication.getApp(),"livevideosource", videoSource);
    }
}
