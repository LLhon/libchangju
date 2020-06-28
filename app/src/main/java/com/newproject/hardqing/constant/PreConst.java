package com.newproject.hardqing.constant;

import com.blankj.utilcode.util.SDCardUtils;

/**
 * Created by Administrator on 2016/11/2.
 */
public class PreConst {
    /*登录信息*/
    public static final String UPAPK = "upapk";
    public static final String APKPATH = "apkpath";

    public static String moPi = "moPi";
    public static String beauty = " beauty";

    //个人信息
    public static final String PERSON = "Person";

    //协议的url
    public static final String WEB_URL = "user_type";

    //微信登录的信息
    public static final String qq = "qq";

    //热门四大分类
    public static final String TITLE = "title";

    //评论下标
    public static final String POSITION = "position";


    //密码
    public static final String PASSWORD = "password";
    public static final String INDEX = "index";
    //用户在其他地方登陆
    public static final String OTHER_LOGIN = "other_login";
    //是否记住账号
    public static final String REMEMBER = "remember";
    //分类id
    public static final String CATEGORY_ID = "remember";


    //问答id
    public static String ANSWER_ID = "answer_id";
    //树洞内容
    public static String SHU_DONG_CONTENT = "shu_dong_content";
    //已发任务状态 1 全部任务 2 进行中 3 已完成
    public static final String STATUS = "status";

    public static final String TaskBean = "taskBean";
    //来自1 MyQAFragment 2 MineMessageActivity 3 HotQAFragment
    public static final String COME_FROM = "come_From";
    //是否正在下载
    public static final String IS_DOWN = "is_down";
    //qr_id
    public static final String QR_ID = "qr_id";


    /**
     * 方圆新增
     */
    public static final String USERID = "userid";
    public static final String USER_NAME = "user_name";
    public static final String MOBILE = "mobile";
    public static final String is_lara = "is_lara"; //1 通过 0未通过 2提交审核 3未提交过 lara星
    public static final String is_sm = "is_sm";//是否实名 0 未 1 实名
    public static final String id_card = "id_card";//身份证
    public static final String bank_card = "bank_card";//银行卡
    public static final String bank_type = "bank_type";//银行卡类型
    public static final String bank_username = "bank_username";//持卡人
    public static final String bank_mobile = "bank_mobile";//预留手机号
    public static final String sex = "sex";//性别 0未设置 1男 2女
    public static final String location = "location";//位置,市
    public static final String province = "province";//省
    public static final String lng = "lng";//经度
    public static final String lat = "lat";//维度
    public static final String gift_address = "gift_address";//礼物地址
    public static final String token = "token";
    public static final String AVATAR = "avatar";
    public static final String USER_TYPE = "user_type";//1 个人 2商家
    public static final String pay_password = "pay_password";//1 设置 0未设置 支付密码
    public static final String is_status = "is_status";
    public static final String is_review = "is_review";//在 user_type = 2时 使用 是否通过审核 0未 1通过
    public static final String notice = "notice";//是否推送 1推送 2不推送
    public static final String autograph = "autograph";//个性签名
    public static final String code_id = "code_id";
    public static final String card_id = "card_id";
    public static final String realname = "realname";
    public static final String refreshTime = "refreshTime"; //token刷新时间
    //动画常量
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";

    //o2o生日聚会
    public static final String KTV = "ktv";
    public static final String ALL_CITY = "all_city";

    // 直播封面
    public static final String LIVE_COVER = "live_cover";
    // 直播场景标题
    public static final String LIVE_TYPE_SCENE = "live_type_scene";
    // 直播场景id
    public static final String LIVE_TYPE_ID = "live_type_id";
    // 直播标题
    public static final String LIVE_NAME = "live_name";
    // 是否是自己的乐精彩
    public static final String IS_MINE = "is_mine";

    // 网站url
    public static final String Web_Url = "web_url";
    //乐精彩分类
    public static final String Classification = "classification";
    //乐精彩视频数据
    public static final String VIDEO_DATA = "video_data";
    //乐精彩视频下标
    public static final String VIDEO_INDEX = "video_data";
    //会员主页 乐精彩视频 类别 作品 喜欢
    public static final String VIDEO_TYPE = "video_type";
    //我喜欢的作品数
    public static final String LikeNum = "like_num";
    //视频播放详情页 使用哪个接口 3 首页 2 首页推荐 1 作品喜欢列表
    public static final String Sort = "sort";

    //啦啦星 视频详情 封面 和 视频地址
    public static final String COVER = "cover";
    public static final String VIDEO_PATH = "video_path";
    //啦啦星 money 和 时间
    public static final String MONEY = "money";
    public static final String TIME = "time";
    //房间号
    public static final String RoomNo = "room_no";
    //房间id
    public static final String RoomId = "room_id";
    //举报类型 1 举报主播 2 举报用户
    public static final String ReportType = "report_type";
    //type 1 禁言 2 踢人
    public static final String LiveType = "live_type";
    //操作者id
    public static final String auser_id = "auser_id";
    //禁言状态
    public static final String bannedType = "banned_type";
    //模板id
    public static final String tid = "tid";
    //xid
    public static final String xid = "xid";
    //是否从礼物池去买东西
    public static final String giftpool = "giftpool";
    //从礼物池去买东西,直播间roomid
    public static final String roomId = "roomId";
    //从礼物池去买东西,直播间主播名字
    public static final String anchor = "anchor";
    //来自直播间邀请啦啦星
    public static final String from_live = "from_live";
    //啦啦星直播间预约类型 0 派对机 1 快速直播
    public static String lala_type = "lala_type";
    // 0 拉拉星订单 1 我的订单
    public static final String order_type = "order_type";
    public static final String fd = "fd";
    public static final String mUrl = "mUrl";

    //下载文件目录
    public static String qingqing = "/qingqing/";
    public static final String music_dir = SDCardUtils.getSDCardPaths().get(0) + PreConst.qingqing;

    /**
     * 阳光音乐库接口签名秘钥
     */
    public static final String SUN_API_KEY = "RrC4mD1gD2bA2nQ0fF6eL4qF1aM2cM8Z";
    public static final String SUN_PLAT_CODE = "sh_hL8fK3fF0fB7";
}
