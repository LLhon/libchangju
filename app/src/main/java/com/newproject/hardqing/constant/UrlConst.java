package com.newproject.hardqing.constant;


/**
 * Created by Administrator on 2016/11/2.
 */
public class UrlConst {

    //服务器主地址
    public static final String SERVER_DOMAIN = "https://party.xiaoheshuo.com/";
    //public static String SERVER_ADDRESS = "https://qingqingparty.com/api/";
    public static String SERVER_ADDRESS = "https://party.xiaoheshuo.com/api/";
    public static String PICTURE_ADDRESS = "";

    public static final String BGM = "https://qingqingparty.com/party1.mp3";

    public static final String WS_SERVER_CHAT = "wss://party.xiaoheshuo.com:9503";
    public static final String WS_SERVER_LIVE = "wss://party.xiaoheshuo.com:9501";

    public static String weixin_APP_ID = "";

    //直播--快速开播 直播间还有3个接口
    public static final String Live = SERVER_ADDRESS + "Room/";
    //虚拟礼物列表
    public static final String GifList = Live + "gifList";
    //游客进入直播间
    public static final String yk_getInto = Live + "yk_getInto";
    //直播间观众列表
    public static final String rommUser = Live + "rommUser";


    //硬件
    public static final String hardware = SERVER_ADDRESS + "Hardware/";
    //启动调用此接口
    public static final String startUp = hardware + "startUp";
    //初始化硬件
    public static final String initialization = hardware + "Initialization";
    //强行解绑
    public static final String Unlock = hardware + "Unlock";
    //激活硬件
    public static final String add = hardware + "add";

    //直播间点击用户
    public static final String userInfo = Live + "userInfo";

    public static final String getMusicInfo = SERVER_ADDRESS + "music/get_one_music";

    //lala竞价时间保存
    public static final String lara_listLalaByReward = SERVER_ADDRESS + "member/laraRewardList";

    //获取在线lala星
    public static final String lara_listLalaOnline = SERVER_ADDRESS + "Room/online_lara";

    //获取推荐拉拉星列表
    public static final String lara_listRecommendLala = SERVER_ADDRESS + "member/laraRecommend";

    //根据用户的坐标查找拉拉星按照远近排序
    public static final String lara_listLikeLala = SERVER_ADDRESS + "member/nearList";


    public static final String lara_listSpecialityLala = SERVER_ADDRESS + "member/skillLaraList";

    /**
     * 音乐库接口
     */
    public static final String MUSIC_LIB_URL = "http://slbsearch.joyk.com.cn:800/api/musicplat/";
    // 服务器系统时间戳
    public static final String SONG_TIME_STAMP = MUSIC_LIB_URL + "times";
    // 搜索歌曲
    public static final String SONG_SEARCH = MUSIC_LIB_URL + "search";
    // 歌曲下载地址
    public static final String SONG_ADDRESS = MUSIC_LIB_URL + "get";


}
