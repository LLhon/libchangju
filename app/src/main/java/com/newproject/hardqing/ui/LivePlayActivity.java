package com.newproject.hardqing.ui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.usb.UsbDevice;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.OnKeyboardListener;
import com.lzx.musiclibrary.manager.TimerTaskManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.newproject.hardqing.BuildConfig;
import com.newproject.hardqing.R;
import com.newproject.hardqing.adapter.AudienceListAdapter;
import com.newproject.hardqing.adapter.LiveMessageAdapter;
import com.newproject.hardqing.adapter.UserIconPcAdapter;
import com.newproject.hardqing.ai_effects.tf.Classifier;
import com.newproject.hardqing.base.BaseActivity;
import com.newproject.hardqing.base.BaseApplication;
import com.newproject.hardqing.constant.Constants;
import com.newproject.hardqing.constant.PreConst;
import com.newproject.hardqing.constant.UrlConst;
import com.newproject.hardqing.entity.BaPingChatMessage;
import com.newproject.hardqing.entity.GiftMode;
import com.newproject.hardqing.entity.LiveChatMessage;
import com.newproject.hardqing.entity.LiveEntity;
import com.newproject.hardqing.entity.LivePlayMessage;
import com.newproject.hardqing.entity.QrcodeBean;
import com.newproject.hardqing.entity.RoomUserBean;
import com.newproject.hardqing.entity.SwitchCamera;
import com.newproject.hardqing.entity.UserDetailBean;
import com.newproject.hardqing.giftlibrary.widget.GiftControl;
import com.newproject.hardqing.giftlibrary.widget.GiftModel;
import com.newproject.hardqing.listener.ICallback;
import com.newproject.hardqing.permission.BaseObserver;
import com.newproject.hardqing.service.CommonUtil;
import com.newproject.hardqing.service.LiveService;
import com.newproject.hardqing.service.LiveSocketUtil;
import com.newproject.hardqing.ui.dialog.DownChorusMusicDialog;
import com.newproject.hardqing.ui.dialog.IosDialog;
import com.newproject.hardqing.ui.dialog.XiTongDialog;
import com.newproject.hardqing.ui.lala.LalaInteractor;
import com.newproject.hardqing.ui.lala.LalaOnlineAdapter;
import com.newproject.hardqing.ui.lala.LalaOnlineBean;
import com.newproject.hardqing.ui.presenter.LivePushHandler;
import com.newproject.hardqing.ui.presenter.WatchPresenter;
import com.newproject.hardqing.ui.receivecmd.BaScreenEntity;
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
import com.newproject.hardqing.ui.receivecmd.MultiRoomMessageEntity;
import com.newproject.hardqing.ui.receivecmd.MusicEntity;
import com.newproject.hardqing.ui.receivecmd.OutRoomEntity;
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
import com.newproject.hardqing.ui.sendcmd.MultiRoomMessageSocket;
import com.newproject.hardqing.ui.sendcmd.PublicMessageSocket;
import com.newproject.hardqing.ui.view.Danmu;
import com.newproject.hardqing.ui.view.DanmuView;
import com.newproject.hardqing.ui.view.SpecialEffectView;
import com.newproject.hardqing.ui.view.WatchView;
import com.newproject.hardqing.util.AnimationUtils;
import com.newproject.hardqing.util.AppManager;
import com.newproject.hardqing.util.CreateDataUtil;
import com.newproject.hardqing.util.CustomPoPupAnim;
import com.newproject.hardqing.util.DensityUtil;
import com.newproject.hardqing.util.DeviceUtil;
import com.newproject.hardqing.util.FileUtil;
import com.newproject.hardqing.util.GlideUtil;
import com.newproject.hardqing.util.GsonConverter;
import com.newproject.hardqing.util.JsonUtil;
import com.newproject.hardqing.util.LogUtil;
import com.newproject.hardqing.util.NetWatchdog;
import com.newproject.hardqing.util.NumShow;
import com.newproject.hardqing.util.QRCodeUtil;
import com.newproject.hardqing.util.ToastUtil;
import com.newproject.hardqing.uvc.GetAppIdConfig;
import com.newproject.hardqing.uvc.UVCCameraHelper;
import com.newproject.hardqing.uvc.VideoCaptureFactoryDemo;
import com.newproject.hardqing.videocapture.VideoCaptureFactoryDemo3;
import com.newproject.hardqing.view.DivergeView;
import com.newproject.hardqing.view.FullScreenVideoView;
import com.newproject.hardqing.view.GifView;
import com.newproject.hardqing.view.QingLrcView;
import com.newproject.hardqing.view.RedPacketView;
import com.newproject.hardqing.view.Roll3DView;
import com.newproject.hardqing.view.SocketReciveView;
import com.newproject.hardqing.view.ViewLive;
import com.newproject.hardqing.view.ball.CommonUtils;
import com.newproject.hardqing.view.ball.TagCloudAdapter;
import com.newproject.hardqing.view.ball.TagCloudView;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.zego.zegoavkit2.IZegoMediaPlayerWithIndexCallback;
import com.zego.zegoavkit2.ZegoExternalVideoCapture;
import com.zego.zegoavkit2.ZegoMediaPlayer;
import com.zego.zegoavkit2.audioprocessing.ZegoAudioProcessing;
import com.zego.zegoavkit2.audioprocessing.ZegoAudioReverbMode;
import com.zego.zegoavkit2.audioprocessing.ZegoAudioReverbParam;
import com.zego.zegoavkit2.camera.ZegoCamera;
import com.zego.zegoavkit2.camera.ZegoCameraExposureMode;
import com.zego.zegoavkit2.camera.ZegoCameraFocusMode;
import com.zego.zegoavkit2.mediaside.ZegoMediaSideInfo;
import com.zego.zegoavkit2.mixstream.IZegoMixStreamCallback;
import com.zego.zegoavkit2.mixstream.ZegoCompleteMixStreamInfo;
import com.zego.zegoavkit2.mixstream.ZegoMixStreamInfo;
import com.zego.zegoavkit2.mixstream.ZegoStreamMixer;
import com.zego.zegoavkit2.soundlevel.ZegoSoundLevelMonitor;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zego.zegoliveroom.callback.IZegoAudioPrepCallback2;
import com.zego.zegoliveroom.callback.IZegoAudioRecordCallback2;
import com.zego.zegoliveroom.callback.IZegoInitSDKCompletionCallback;
import com.zego.zegoliveroom.callback.IZegoLivePlayerCallback;
import com.zego.zegoliveroom.callback.IZegoLivePublisherCallback;
import com.zego.zegoliveroom.callback.IZegoLoginCompletionCallback;
import com.zego.zegoliveroom.callback.IZegoRoomCallback;
import com.zego.zegoliveroom.callback.im.IZegoIMCallback;
import com.zego.zegoliveroom.constants.ZegoAvConfig;
import com.zego.zegoliveroom.constants.ZegoBeauty;
import com.zego.zegoliveroom.constants.ZegoConstants;
import com.zego.zegoliveroom.constants.ZegoIM;
import com.zego.zegoliveroom.constants.ZegoVideoViewMode;
import com.zego.zegoliveroom.entity.ZegoAudioFrame;
import com.zego.zegoliveroom.entity.ZegoAudioRecordConfig;
import com.zego.zegoliveroom.entity.ZegoBigRoomMessage;
import com.zego.zegoliveroom.entity.ZegoExtPrepSet;
import com.zego.zegoliveroom.entity.ZegoPlayStreamQuality;
import com.zego.zegoliveroom.entity.ZegoPublishStreamQuality;
import com.zego.zegoliveroom.entity.ZegoRoomMessage;
import com.zego.zegoliveroom.entity.ZegoStreamInfo;
import com.zego.zegoliveroom.entity.ZegoUserState;

import de.hdodenhof.circleimageview.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import tyrantgit.explosionfield.ExplosionField;

public class LivePlayActivity extends BaseActivity implements
        WatchView, SocketReciveView, NetWatchdog.NetChangeListener, SpecialEffectView {

    private static final String TAG = "LivePlayActivity";

    ImageView titleBack;
    View topView;
    LinearLayout giftLl2;
    DivergeView zanViewh;
    RelativeLayout rlTop;
    TextView tvName;
    ImageView ivShare;
    ImageView ivLove;
    ImageView ivSet;
    RelativeLayout rlContianer;
    ImageView ivAudience;
    RecyclerView rvIcon;
    RecyclerView rvMesssage;
    LinearLayout top;
    ImageView ivTemplate;
    ImageView ivTemplateBg;
    RedPacketView redPacketsView;
    SVGAImageView svgaAnim;
    ImageView ivCloseRed;
    ImageView ivRedEnvelpopes;
    Toolbar toolbar;
    SVGAImageView categoryui;
    RelativeLayout rlAnim;
    GifImageView giftAnim;
    LinearLayout mLlBaScreenTimer;
    TextView mTvBaScreenTimer;
    QingLrcView mLrcChorusView;
    ImageView ivQRCode;
    RecyclerView rvLalaOnline;
    RelativeLayout mBaPinAll;
    GifView mGifView;
    ImageView mShowGifImageView;
    CircleImageView mZhuBoAvatar;
    TextView mZhuBoName;
    TextView mZhuBoFansCount;
    RecyclerView mWatchUserRecyclerView;
    TextView mWatchUserCount;
    LinearLayout mShowAllView;
    TextView mTvPartySubject;
    ImageView mLivePicImage;
    FullScreenVideoView mVideoViewBg;
    SVGAImageView mSivSubject;
    ViewLive tvAudience1;
    ViewLive mLocalPreview;
    //    轮盘附近对应id
    SVGAImageView mSvgaExtractAudience;
    RelativeLayout mRlShowLuck;//控制轮盘是否显示
    ImageView mLpLuckPan;
    ImageView mImgStart;
    RelativeLayout mRlSvga;//控制svga区域是否显示
    SVGAImageView mSvgaLuckResult;
    RelativeLayout mRlLuckP;
    //    这一块是特效消失后展示的轮盘结果
    TextView mTvLuckResult;
    TextView mTvExtractName;
    TextView mTvExtractLian;
    RelativeLayout mRlNameLian;
    //   自我介绍
    RelativeLayout mRlShowInduction;
    RecyclerView mRlAudienceList;
    TextView mTvWaitAudience;

    private TextureView mMVTextureView;

    LinearLayout llSubtitles;
    TextView tvSubtitles;
    private String yq_type;

    ImageView mIvChorusTimer;
    SVGAImageView mSivRedPackets;
    RelativeLayout mRlPlayBillContainer;
    TagCloudView mTagCloudView;
    RelativeLayout mRlPlayBill;
    Roll3DView mRoll3DView;

    private TagCloudAdapter mTagCloudAdapter;
    private WatchPresenter watchPresenter;
    private String mUrl;
    String anchor;//主播名字
    String anchorAvatar;
    private GiftControl giftControl, giftControl2;
    LivePushHandler handler;
    private static final int LIVE_ROOM_MSG = 0x123;
    private String currentBalance;
    private ArrayList<Bitmap> mList;
    private boolean mIsPopup;
    private UserIconPcAdapter userIconAdapter;
    private int start = 0;
    private boolean isShowDialog = true;
    String isLikes = "0";//是否关注主播
    private LiveMessageAdapter messageAdapter;
    private boolean isLianMai = false;//是否连麦状态，连麦状态要断掉连麦才能切换竖屏
    private boolean isAnchor;//是否主播
    private boolean canMai = true;//发起连麦后30s才能再操作
    private boolean isRedRain;//是否在下红包雨
    String url1, url2, url3;//三个窗口的推流
    String aid1, aid2, aid3;//三个窗口的推流userid
    String lmid1, lmid2, lmid3;//三个窗口的连麦userid
    String mPushUrl;
    String type;//直播类型，0派对机开播，1快速开播
    //推流器
    String roomNo;
    String tourId;//游客id
    private boolean isShowCake = false;
    WindowManager wm;
    int swidth, sheight;

    private MediaPlayer mEffectsMediaPlayer;

    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private LalaOnlineAdapter lalaOnlineAdapter;
    private String mTopicURL;
    private String mMusicURL;
    private boolean mUpVideoViewShowing;
    private MediaPlayer mGiftMediaPlayer;
    private DownChorusMusicDialog mDownChorusMusicDialog;
    private String mMusicPath; //原唱音乐地址
    private String mChorusUserId;
    private String mChorusUserName;
    private boolean isDownLoadSuccess;
    private boolean isReceiveDownLoadSuccess;
    private TimerTaskManager mChorusTimerTaskManager;
    private VideoCaptureFactoryDemo factoryDemo;
    private boolean mIsLoginRoomSuccess;
    private boolean mInitSDKSuccess;
    private boolean mIsUsbConnected;
    //是否正在霸屏中
    private boolean mIsBaping;
    private ZegoLiveRoom mZegoLiveRoom;
    private boolean isUsbDevice;
    private ExplosionField mExplosionField;
    private Disposable mExplosionDisposable;
    private Disposable mJalousieDisposable;
    private Disposable mRollDisposable;
    private PlayBillEntity mPlayBillData;
    private CountDownTimer mChorusProgressTimer;
    private ZegoMediaPlayer mBgZegoMediaPlayer;
    private ZegoMediaPlayer mChorusZegoMediaPlayer;
    private int mChorusType;
    private int mCurPlayMusicType; //音乐类型：0 背景音乐 1 点歌 2 合唱
    private String mMp3BcPath; //伴唱音乐地址
    private long mTimeStamp; //音乐播放进度时间戳
    private boolean mIsStartPublishing; //是否开始启动推流
    private int mIsEnableCamera; //开关播状态
    private String mLrcUrl;
    private String mMultiRoomStreamId;
    private String mMultiRoomId;
    private UserDetailBean mLianMaiUserInfo;
    private String mLianMaiStreamID;

    private ZegoMediaPlayer mvZegoMediaPlayer;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleSomethingElse(LivePlayMessage event) {
        switch (event.getCode()) {
            case 4:
                CommonUtil.inroom(BaseApplication.getApp(), roomId);
                break;
            case 200:
                Message message = new Message();
                message.obj = event.getContent();
                message.what = LIVE_ROOM_MSG;
                handler.sendMessage(message);
                LogUtil.e("消息内容:" + event.getContent());
                break;
            default:
                break;
        }
    }

    public void startEffectsMusic(@RawRes int position) {
        if (mEffectsMediaPlayer != null) {
            mEffectsMediaPlayer.stop();
        }
        switch (position) {
            case 1:
                mEffectsMediaPlayer = MediaPlayer.create(this, R.raw.sound_effects01);
                mEffectsMediaPlayer.setLooping(false);
                mEffectsMediaPlayer.start();
                break;
            case 2:
                mEffectsMediaPlayer = MediaPlayer.create(this, R.raw.sound_effects02);
                mEffectsMediaPlayer.setLooping(false);
                mEffectsMediaPlayer.start();
                break;
            case 3:
                mEffectsMediaPlayer = MediaPlayer.create(this, R.raw.sound_effects03);
                mEffectsMediaPlayer.setLooping(false);
                mEffectsMediaPlayer.start();
                break;
            case 4:
                mEffectsMediaPlayer = MediaPlayer.create(this, R.raw.sound_effects04);
                mEffectsMediaPlayer.setLooping(false);
                mEffectsMediaPlayer.start();
                break;
            case 5:
                mEffectsMediaPlayer = MediaPlayer.create(this, R.raw.sound_effects05);
                mEffectsMediaPlayer.setLooping(false);
                mEffectsMediaPlayer.start();
                break;
            case 6:
                mEffectsMediaPlayer = MediaPlayer.create(this, R.raw.sound_effects06);
                mEffectsMediaPlayer.setLooping(false);
                mEffectsMediaPlayer.start();
                break;
            case 7:
                mEffectsMediaPlayer = MediaPlayer.create(this, R.raw.sound_effects07);
                mEffectsMediaPlayer.setLooping(false);
                mEffectsMediaPlayer.start();
                break;
            case 8:
                mEffectsMediaPlayer = MediaPlayer.create(this, R.raw.sound_effects08);
                mEffectsMediaPlayer.setLooping(false);
                mEffectsMediaPlayer.start();
                break;
            case 9:
                mEffectsMediaPlayer = MediaPlayer.create(this, R.raw.sound_effects09);
                mEffectsMediaPlayer.setLooping(false);
                mEffectsMediaPlayer.start();
                break;
            default:
                break;

        }
    }

    public void onCloseBaPingClicked() {
        if (ivTemplate == null) {
            return;
        }
        stopBapin();
        unDispose();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMusicEntity(final MusicEntity musicEntity) {
        LogUtil.d("livew", " onReceiveMusicEntity  musicEntity " + musicEntity);
        if (musicEntity == null) {
            return;
        }
        if (musicEntity.isBaPing()) {
            if (musicEntity.isStopBaPing()) {
                onCloseBaPingClicked();
            } else {
                String baPingData = musicEntity.getBaPingData();
                if (TextUtils.isEmpty(baPingData)) {
                    return;
                }
                BaScreenEntity baScreenEntity = GsonConverter.fromJson(musicEntity.getBaPingData(), BaScreenEntity.class);
                LogUtil.d("livew", "SelectTypeWindow  onReceiveMusicEntity  baScreenEntity " + baScreenEntity);
                if (baScreenEntity != null) {
                    switch (baScreenEntity.getBaScreenType()) {
                        case 3:
                            String liveTitle = baScreenEntity.getLiveTitle();
                            String liveType = baScreenEntity.getLiveType();
                            mTopicURL = baScreenEntity.getTopicURL();
                            if (!TextUtils.isEmpty(mTopicURL)) {
                                showTopicAnim(mTopicURL); //根据派对主题展示动态特效
                            }
                            break;
                        default:
                            bapin(baScreenEntity);
                            String bapingText = baScreenEntity.getText();
                            if (!TextUtils.isEmpty(bapingText) && mDanmuView != null) {
                                Danmu danmu = new Danmu();
                                danmu.setHeaderUrl("");
                                danmu.setUserName("");
                                danmu.setInfo("霸屏主题：" + bapingText);
                                mDanmuView.add(danmu);
                            }
                            break;

                    }
                }
            }
        } else if (musicEntity.isSoundEffects()) {
            startEffectsMusic(musicEntity.getEffectsMusicId());
        } else if ("-1".equals(musicEntity.getId())) {
            //暂停点歌/合唱音乐
            pauseChorusZegoMediaPlayer();
            //if (!isLianMai) {
            //    resumeBgMediaPlayer();
            //}
        } else if ("-2".equals(musicEntity.getId())) {
            //恢复点歌/合唱音乐
            resumeChorusZegoMediaPlayer();
            startChorusTimerTaskManager();
            //pauseBgMediaPlayer();
        } else if ("-3".equals(musicEntity.getId())) {
            //切换主题音乐
            //startBgMediaPlayer(musicEntity.getSongUrl(), true);
        } else if ("-4".equals(musicEntity.getId())) {
            //切换为伴唱音乐
            if (mChorusZegoMediaPlayer != null) {
                mChorusZegoMediaPlayer.stop();
                // 听说KTV用的是双音轨，原唱一个音轨，伴奏一个音轨，转换的时候切换音轨就行了
                mChorusZegoMediaPlayer.start(mMp3BcPath, false);
            }
        } else if ("-5".equals(musicEntity.getId())) {
            //切换为原唱音乐
            if (mChorusZegoMediaPlayer != null) {
                mChorusZegoMediaPlayer.stop();
                mChorusZegoMediaPlayer.start(mMusicPath, false);
            }
        } else if ("-6".equals(musicEntity.getId())) {
            //暂停背景音乐
            //pauseBgMediaPlayer();
        } else if ("-7".equals(musicEntity.getId())) {
            //恢复背景音乐
            //resumeBgMediaPlayer();
        } else if ("-8".equals(musicEntity.getId())) {
            //收到歌词进度的消息
            mTimeStamp = musicEntity.getLrcTimeStamp();
            mLrcChorusView.updateTime(mTimeStamp);
        } else if (musicEntity.isDownSuccess() == 1) {
            //app看客端歌曲已下载完成，可以准备倒计时合唱
            Log.e("LLhon", "LivePlayActivity--收到app端已下载歌曲完成的消息...");
           /* isReceiveDownLoadSuccess = true;
            if (isDownLoadSuccess) {
                showChorusCountDown(mLrcPath, mMusicPath);
            }
            if (mDownChorusMusicDialog != null && isDownLoadSuccess) {
                mDownChorusMusicDialog.dismiss();
            }
            if (mChorusProgressTimer != null) {
                mChorusProgressTimer.cancel();
            }*/
        } else {
            //app端已接受对方发起的合唱请求，开始下载歌曲
            Log.e("LLhon", "userId:" + musicEntity.getUserId() + ", senderId:" + musicEntity.getSenderId());
            mChorusType = musicEntity.getChorusType();
            mChorusUserId = musicEntity.getUserId();
            mChorusUserName = musicEntity.getUserName();
            mMusicPath = musicEntity.getMp3Url();
            mMp3BcPath = musicEntity.getBcMp3Url();
            mLrcUrl = musicEntity.getLrcUrl();
            if (mChorusType == 0) {
                //合唱
                showChorusCountDown(mLrcUrl, mMusicPath);
            } else {
                //点歌
                //点歌，这条消息将发送到直播间内的所有观众端，用于显示歌词
                PublicMessageSocket socket = new PublicMessageSocket("", "", 0,
                        mLrcUrl, roomId);
                socket.setChorusType(1);
                LiveSocketUtil.sendPublicMessage(this, socket);
                playChorusMusic(mMusicPath, mLrcUrl);
            }

            switch (musicEntity.getChorusType()) {
                case 0:
                    //默认是合唱
                    mCurPlayMusicType = 2;
                    break;
                case 1:
                    //下载完直接播放歌曲
                    mCurPlayMusicType = 1;
                    isReceiveDownLoadSuccess = true;
                    break;
                case 2:
                    //app看客端歌曲已下载完成，可以准备倒计时合唱
                    Log.e("LLhon", "LivePlayActivity--收到app端已下载歌曲完成的消息...");
                   /* isReceiveDownLoadSuccess = true;
                    if (isDownLoadSuccess) {
                        showChorusCountDown(mLrcPath, mMusicPath);
                    }
                    if (mChorusProgressTimer != null) {
                        mChorusProgressTimer.cancel();
                    }
                    if (mDownChorusMusicDialog != null && isDownLoadSuccess) {
                        mDownChorusMusicDialog.dismiss();
                    }*/
                    break;
                case 3:
                    //关闭音乐
                    stopChorusMediaPlayer();
                    //取消合唱
                    OkGo.getInstance().cancelTag("download_song");
                    if (mDownChorusMusicDialog != null && mDownChorusMusicDialog.isShow()) {
                        mDownChorusMusicDialog.dismiss();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void showTopicAnim(final String uri) {
        if (!TextUtils.isEmpty(uri) && mBaPinAll != null) {
            mBaPinAll.postDelayed(new Runnable() {
                @Override
                public void run() {
                    subjectTransAnim();
                    CustomPoPupAnim.loadSvga(categoryui, uri, new CustomPoPupAnim.AnimListener() {
                        @Override
                        public void onFinish() {
                            showTopicAnim(mTopicURL);
                        }
                    });
                }
            }, 60 * 1000);

        }
    }

    private void subjectTransAnim() {
        if (categoryui == null) {
            return;
        }
        Animation animation = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.live_type_animation);
        categoryui.startAnimation(animation);
    }

    public void startPreview() {
        titleBack.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isUsbDevice) {
                    ZegoAvConfig config = new ZegoAvConfig(ZegoAvConfig.Level.High);
                    // 设置分辨率，注意此处设置的分辨率需要是uvccamera所支持的
                    config.setVideoCaptureResolution(1280, 720); //1280, 720 / 640x480
                    config.setVideoEncodeResolution(1280, 720); // 3840, 2160 / 1920, 1080
                    config.setVideoFPS(15);
                    config.setVideoBitrate(1200000);
                    mZegoLiveRoom.setAVConfig(config);
                } else {
                    String deviceId = DeviceUtil.getDeviceId();
                    LogUtils.d("LivePlayActivity  startPreview ()  deviceId :  " + deviceId);
                    if (TextUtils.isEmpty(deviceId)) {
                        return;
                    }
                    setAppOrientation();
                    mZegoLiveRoom.enableCamera(true);
                }
                boolean isPreview = mZegoLiveRoom.setPreviewView(mLocalPreview.getTextureView());//设置预览；
                setTextureViewAlpha60();
                mZegoLiveRoom.setPreviewViewMode(ZegoVideoViewMode.ScaleAspectFill);
                boolean previewSuccess = mZegoLiveRoom.startPreview();
                Log.d(TAG, "预览是否成功：" + previewSuccess);
                initLoopBack();
                //UVCCameraHelper.sharedInstance().removeUVCCameraFrameCallback();
//                LogUtils.d("LivePlayActivity  startPreview () previewSuccess : " + previewSuccess);
                if (!mIsStartPublishing) {
                    mIsStartPublishing = mZegoLiveRoom.startPublishing(ZegoRoomUtil.getPublishStreamID(mHostUserId), mHostUserId, ZegoConstants.PublishFlag.MixStream);
                    Log.d(TAG, "推流是否成功: " + mIsStartPublishing);
                    mPublishStreamID = ZegoRoomUtil.getPublishStreamID(mHostUserId);
                    // TODO: 2020/7/9 拉自己推的流作为本地预览画面
                    mZegoLiveRoom.startPlayingStream(ZegoRoomUtil.getPublishStreamID(mHostUserId), mLocalPreview.getTextureView());
                    mZegoLiveRoom.setPlayVolume(0, ZegoRoomUtil.getPublishStreamID(mHostUserId));
                    mZegoLiveRoom.setViewMode(ZegoVideoViewMode.ScaleAspectFill, ZegoRoomUtil.getPublishStreamID(mHostUserId));
                }
                setBeauty();
            }
        }, 1000);
    }

    /**
     * 设置推流朝向.
     */
    protected void setAppOrientation() {
        if (mZegoLiveRoom == null) {
            return;
        }
        // 设置app朝向
        int currentOrientation = getWindowManager().getDefaultDisplay().getRotation();
        mZegoLiveRoom.setAppOrientation(currentOrientation);
        // 设置推流配置
        ZegoAvConfig currentConfig = ZegoLiveManager.getInstance().getZegoAvConfig();
        if (currentConfig != null) {
            // 设置分辨率，注意此处设置的分辨率需要是uvccamera所支持的
            currentConfig.setVideoCaptureResolution(1280, 720); //1280, 720
            currentConfig.setVideoEncodeResolution(1280, 720); // 3840, 2160 / 1920, 1080
            currentConfig.setVideoFPS(15);
            currentConfig.setVideoBitrate(1200000);
            int videoWidth = currentConfig.getVideoEncodeResolutionWidth();
            int videoHeight = currentConfig.getVideoEncodeResolutionHeight();
            if (((currentOrientation == Surface.ROTATION_0 || currentOrientation == Surface.ROTATION_180) && videoWidth > videoHeight) ||
                    ((currentOrientation == Surface.ROTATION_90 || currentOrientation == Surface.ROTATION_270) && videoHeight > videoWidth)) {
                currentConfig.setVideoEncodeResolution(videoHeight, videoWidth);
                currentConfig.setVideoCaptureResolution(videoHeight, videoWidth);
            }
            ZegoLiveManager.getInstance().setZegoConfig(currentConfig);
        }
    }

    private void setBeauty() {
        boolean xinMoPi = LoginUserManager.getXinMoPi(this);
        boolean xinBeautyLevel = LoginUserManager.getXinBeautyLevel(this);
        if (xinMoPi && xinBeautyLevel) {
            mZegoLiveRoom.enableBeautifying(ZegoBeauty.POLISH | ZegoBeauty.SKIN_WHITEN);
        }
        if (!xinMoPi && !xinBeautyLevel) {
            mZegoLiveRoom.enableBeautifying(ZegoBeauty.NONE);
        }
        if (!xinMoPi && xinBeautyLevel) {
            mZegoLiveRoom.enableBeautifying(ZegoBeauty.POLISH);
        }
        if (xinMoPi && !xinBeautyLevel) {
            mZegoLiveRoom.enableBeautifying(ZegoBeauty.SKIN_WHITEN);
        }
        mZegoLiveRoom.setWhitenFactor(0.3f);
        mZegoLiveRoom.setPolishStep(8);
    }


    @Override
    public int getLayout() {
        return R.layout.activity_live_play;
    }

    public void initViewId() {
        titleBack = findViewById(R.id.title_back);
        topView = findViewById(R.id.top_view);
        giftLl2 = findViewById(R.id.gift_ll2);
        zanViewh = findViewById(R.id.zan_viewh);
        rlTop = findViewById(R.id.rl_top);
        tvName = findViewById(R.id.tv_name);
        ivShare = findViewById(R.id.iv_share);
        ivLove = findViewById(R.id.iv_love);
        ivSet = findViewById(R.id.iv_set);
        rlContianer = findViewById(R.id.rl_contianer);
        ivAudience = findViewById(R.id.iv_audience);
        rvIcon = findViewById(R.id.rv_icon);
        rvMesssage = findViewById(R.id.rv);
        top = findViewById(R.id.top);
        ivTemplate = findViewById(R.id.iv_template);
        ivTemplateBg = findViewById(R.id.iv_template_bg);
        redPacketsView = findViewById(R.id.red_packets_view1);
        svgaAnim = findViewById(R.id.svga_anim);
        ivCloseRed = findViewById(R.id.iv_close_red);
        ivRedEnvelpopes = findViewById(R.id.iv_red_envelpopes);
        toolbar = findViewById(R.id.toolbar);
        categoryui = findViewById(R.id.svga_categoryui);
        rlAnim = findViewById(R.id.rl_anim);
        giftAnim = findViewById(R.id.gift_anim);
        mLlBaScreenTimer = findViewById(R.id.ll_bascreen_timer);
        mTvBaScreenTimer = findViewById(R.id.tv_bascreen_timer);
        mLrcChorusView = findViewById(R.id.lrc_chorus_view);
        ivQRCode = findViewById(R.id.iv_QRCode);
        rvLalaOnline = findViewById(R.id.rv_lalaOnline);
        mBaPinAll = findViewById(R.id.rl_ba_pin_all);
        mGifView = findViewById(R.id.gif_view);
        mShowGifImageView = findViewById(R.id.iv_custom_gift_view);
        mZhuBoAvatar = findViewById(R.id.headicon);
        mZhuBoName = findViewById(R.id.tv_zhu_bo_name);
        mZhuBoFansCount = findViewById(R.id.tv_fans_num);
        mWatchUserRecyclerView = findViewById(R.id.rv_watch_user_avatar);
        mWatchUserCount = findViewById(R.id.tv_watch_user_count);
        mShowAllView = findViewById(R.id.ll_show_all_view);
        mTvPartySubject = findViewById(R.id.rtv_title);
        mLivePicImage = findViewById(R.id.iv_live_pic);
        mVideoViewBg = findViewById(R.id.vv_bg);
        mSivSubject = findViewById(R.id.iv_tv_zhu_ti);
        tvAudience1 = findViewById(R.id.tv_audience1);
        mLocalPreview = findViewById(R.id.tv_local_preview);
        llSubtitles = findViewById(R.id.ll_subtitles);
        tvSubtitles = findViewById(R.id.tv_subtitles);
        mIvChorusTimer = findViewById(R.id.iv_chorus_timer);
        mSivRedPackets = findViewById(R.id.siv_red_packets);
        mRlPlayBill = findViewById(R.id.rl_playbill);
        mRlPlayBillContainer = findViewById(R.id.rl_playbill_container);
        mRoll3DView = findViewById(R.id.roll_view);
        mTagCloudView = findViewById(R.id.tag_cloud);
        mMVTextureView = findViewById(R.id.tv_mv_video_view);

        mSvgaExtractAudience = findViewById(R.id.svga_extract_audience);
        mRlShowLuck = findViewById(R.id.rl_show_luck);
        mLpLuckPan = findViewById(R.id.lp_luckPan);
        mImgStart = findViewById(R.id.img_start);
        mRlSvga = findViewById(R.id.rl_svga);
        mSvgaLuckResult = findViewById(R.id.svga_luck_result);
        mRlLuckP = findViewById(R.id.rl_luckPan);
        mTvLuckResult = findViewById(R.id.tv_luck_result);
        mTvExtractName = findViewById(R.id.tv_extract_name);
        mTvWaitAudience = findViewById(R.id.tv_wait_audience);
        mRlNameLian = findViewById(R.id.rl_name_lian);
        mRlShowInduction = findViewById(R.id.rl_show_induction);
        mRlAudienceList = findViewById(R.id.rl_audience_list);

        mShowGifImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGifView == null) {
                    return;
                }
                mGifView.startAnimation(mGifView.getWidth(), mGifView.getHeight());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void initView() {
        initViewId();
        mZegoLiveRoom = ZegoLiveManager.getInstance().getG_ZegoApi();
        roomId = getIntent().getStringExtra(PreConst.RoomId);
        roomNo = getIntent().getStringExtra(PreConst.RoomNo);
        LiveService.setRoomId(roomId);
        tourId = getIntent().getStringExtra("uid");
        mUrl = getIntent().getStringExtra(PreConst.mUrl);
        //ui
        initUi();
        //播放器
        initPlay();
        //点赞
        initZanData();
        initIconRv();
        initMessageRv();
        initAdapterListener();
        initZegoLivePushListener();
        LoginUserManager.setLiveVideoSource("app");
        showTopicAnim(QingMainActivity.category_uri);
        mDanmuView = (DanmuView) findViewById(R.id.danmu_view);
        //在线拉拉星
        rvLalaOnline.setLayoutManager(new LinearLayoutManager(this));
        lalaOnlineAdapter = new LalaOnlineAdapter(null, this);
        rvLalaOnline.setAdapter(lalaOnlineAdapter);
        initGiftView();
    }

    public void initGiftView() {
        List<Drawable> drawableList = new ArrayList<>();
        //drawableList.add(getResources().getDrawable(R.drawable.ic_favorite_indigo_900_24dp));
        //drawableList.add(getResources().getDrawable(R.drawable.ic_favorite_deep_purple_900_24dp));
        //drawableList.add(getResources().getDrawable(R.drawable.ic_favorite_cyan_900_24dp));
        //drawableList.add(getResources().getDrawable(R.drawable.ic_favorite_blue_900_24dp));
        //drawableList.add(getResources().getDrawable(R.drawable.ic_favorite_deep_purple_900_24dp));
        //drawableList.add(getResources().getDrawable(R.drawable.ic_favorite_light_blue_900_24dp));
        //drawableList.add(getResources().getDrawable(R.drawable.ic_favorite_lime_a200_24dp));
        //drawableList.add(getResources().getDrawable(R.drawable.ic_favorite_pink_900_24dp));
        //drawableList.add(getResources().getDrawable(R.drawable.ic_favorite_red_900_24dp));
        drawableList.add(getResources().getDrawable(R.drawable.ic_gif_heart));
        drawableList.add(getResources().getDrawable(R.drawable.ic_gif_heart));
        drawableList.add(getResources().getDrawable(R.drawable.ic_gif_heart));
        drawableList.add(getResources().getDrawable(R.drawable.ic_gif_heart));
        drawableList.add(getResources().getDrawable(R.drawable.ic_gif_heart));
        drawableList.add(getResources().getDrawable(R.drawable.ic_gif_heart));
        drawableList.add(getResources().getDrawable(R.drawable.ic_gif_heart));
        drawableList.add(getResources().getDrawable(R.drawable.ic_gif_heart));
        drawableList.add(getResources().getDrawable(R.drawable.ic_gif_heart));
        mGifView.setDrawableList(drawableList);
        mGifView.setItemViewWH(DensityUtil.dip2px(this, 96), DensityUtil.dip2px(this, 96));
    }

    public void getPermission() {
        PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.CAMERA, PermissionConstants.MICROPHONE)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(final ShouldRequest shouldRequest) {
                        LogUtils.d("LivePlayActivity  getPermission  rationale()");
                        shouldRequest.again(true);
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        LogUtils.d("LivePlayActivity  getPermission  onGranted()");
                        isLive = false;
                        doBusiness();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever,
                                         List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            PermissionUtils.launchAppDetailsSettings();
                        }
                    }
                }).request();
    }


    @Override
    public void ChangeLiveStatus(ChangeLiveStatusEntity entity) {
        if (entity == null) {
            return;
        }
        mIsEnableCamera = entity.getStatus();
        if (entity.getStatus() == 1) {
            setShowVideoWindow(true, true);
            mRlPlayBill.setVisibility(View.GONE);
            clearPlayBillAnimation();
            if (mIsLoginRoomSuccess && !mIsStartPublishing) {
                mIsStartPublishing = mZegoLiveRoom.startPublishing(ZegoRoomUtil.getPublishStreamID(mHostUserId), mHostUserId, ZegoConstants.PublishFlag.MixStream);
                mPublishStreamID = ZegoRoomUtil.getPublishStreamID(mHostUserId);
                // TODO: 2020/7/9 拉自己推的流作为本地预览画面
                mZegoLiveRoom.startPlayingStream(ZegoRoomUtil.getPublishStreamID(mHostUserId), mLocalPreview.getTextureView());
                mZegoLiveRoom.setPlayVolume(0, ZegoRoomUtil.getPublishStreamID(mHostUserId));
                mZegoLiveRoom.setViewMode(ZegoVideoViewMode.ScaleAspectFill, ZegoRoomUtil.getPublishStreamID(mHostUserId));
            }
            enableCamera(true);
        } else {
//            if (mZegoLiveRoom != null) {
//                boolean isStopPushSuccess = mZegoLiveRoom.stopPublishing();
//                LogUtils.d("LivePlayActivity startPublishing initSDKAndLoginRoom  isStopPushSuccess : " + isStopPushSuccess);
//            }
            setShowVideoWindow(false, true);
            enableCamera(false);
            if (mPlayBillData != null) {
                mRlPlayBill.setVisibility(View.VISIBLE);
                startPlayBillAnimation();
            }
        }
    }

    public void enableCamera(boolean isOpen) {
        if (mZegoLiveRoom == null) {
            return;
        }
        if (BuildConfig.channel_id == Constants.CHANNEL_ID_SUN_USB) {

        } else if (BuildConfig.channel_id == Constants.CHANNEL_ID_YIN_CHUANG) {
            mZegoLiveRoom.enableCamera(isOpen);
        } else if (BuildConfig.channel_id == Constants.CHANNEL_ID_SUN_NET) {
            // TODO: 2020/6/19 网络摄像头的开关
            mZegoLiveRoom.enableCamera(isOpen);
        }
    }

    private void setShowVideoWindow(boolean isVisible, boolean isChangeLiveStatus) {
        if (mShowAllView == null) {
            return;
        }
        if (isVisible) {
            mShowAllView.setVisibility(View.VISIBLE);
        } else {
            if (isChangeLiveStatus) {
                mShowAllView.setVisibility(View.GONE);
            } else {
                tvAudience1.setVisibility(View.GONE);
            }
        }
    }

    private boolean isShowVideoWindow() {
        return mShowAllView.getVisibility() == View.VISIBLE;
    }

    @Override
    public void initData() {
        watchPresenter = new WatchPresenter(this);
        watchPresenter.getLiveInfo(TAG, roomId);
        watchPresenter.getGiftData(TAG);
        //二维码
        final QrcodeBean qrcodeBean = new QrcodeBean();
        qrcodeBean.setType("1");
        qrcodeBean.setContent(roomNo);
        listLalaOnline();
//        getGiftData();
        if (ivQRCode == null) {
            return;
        }
        ivQRCode.post(new Runnable() {
            @Override
            public void run() {
                if (ivQRCode == null) {
                    return;
                }
                ivQRCode.setImageBitmap(QRCodeUtil.createQRCodeBitmap(new Gson().toJson(qrcodeBean), ivQRCode.getWidth(), ivQRCode.getHeight()));
            }
        });
    }

    private int llwidth;//霸屏消息
    private int screenWidth;

    private void initUi() {
        wm = this.getWindowManager();
        swidth = wm.getDefaultDisplay().getWidth();//屏幕宽度
        sheight = wm.getDefaultDisplay().getHeight();//屏幕高度

        //显示派对星球动画
        CustomPoPupAnim.loadSvga(mSivSubject, "xingqiu.svga");

        handler = new LivePushHandler(this, this, false);
        // 礼物动画
        giftControl2 = new GiftControl(this);
        giftControl2.setGiftLayout(false, giftLl2, 4);
        mTagMode = new GiftMode();
        CreateDataUtil.createTags(mTagMode);
        mList = new ArrayList<>();
        setLayout();

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        llSubtitles.measure(w, h);
        llwidth = llSubtitles.getMeasuredWidth();
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();

        mExplosionField = ExplosionField.attach2Window(this);
    }

    private void initPlay() {
        if (mZegoLiveRoom == null) {
            mZegoLiveRoom = ZegoLiveManager.getInstance().getG_ZegoApi();
        }
        roomId = getIntent().getStringExtra(PreConst.RoomId);
        tourId = getIntent().getStringExtra("uid");
        tvAudience1.setZegoLiveRoom(mZegoLiveRoom);
        // step.1 init USB
        UVCCameraHelper.sharedInstance().initUSBMonitor(this, myDevConnectListener);

        //用于播放背景音乐
        mBgZegoMediaPlayer = new ZegoMediaPlayer();
        mBgZegoMediaPlayer.init(ZegoMediaPlayer.PlayerTypeAux, ZegoMediaPlayer.PlayerIndex.First);
        mChorusZegoMediaPlayer = new ZegoMediaPlayer();
        mChorusZegoMediaPlayer.init(ZegoMediaPlayer.PlayerTypeAux, ZegoMediaPlayer.PlayerIndex.Second);
        mBgZegoMediaPlayer.setVolume(100);
        mChorusZegoMediaPlayer.setVolume(100);
        initZegoListener();
        initZegoMediaPlayerListener();
    }

    public void initUVCCamera() {
        factoryDemo = new VideoCaptureFactoryDemo();
        ZegoExternalVideoCapture.setVideoCaptureFactory(factoryDemo, ZegoConstants.PublishChannelIndex.MAIN);
        ZegoLiveRoom.setConfig("adapt_to_system_karaoke=true");
        ZegoLiveRoom.requireHardwareEncoder(true);
        ZegoLiveRoom.requireHardwareDecoder(true);
    }

    /**
     * 视频外部采集
     */
    private void setVideoCaptureFactory() {
        // camera作为采集源，采用的数据传递类型是YUV格式
        VideoCaptureFactoryDemo3.CaptureOrigin captureOrigin = VideoCaptureFactoryDemo3.CaptureOrigin.CaptureOrigin_CameraV3; //摄像头 yuv数据
        // 创建外部采集工厂
        VideoCaptureFactoryDemo3 videoCaptureFactory = new VideoCaptureFactoryDemo3(captureOrigin);
        videoCaptureFactory.setContext(this);
        //设置外部采集工厂对象
        ZegoExternalVideoCapture.setVideoCaptureFactory(videoCaptureFactory, ZegoConstants.PublishChannelIndex.MAIN);  // videoCapture 是 ZegoExternalVideoCapture实例
        //支持本地预览
        //ZegoLiveRoom.setConfig("vcap_external_support_preview=true");
    }

    String mPublishStreamID = null;
    private ZegoStreamMixer mStreamMixer = new ZegoStreamMixer();

    private void initZegoListener() {
        initZegoLivePlayerCallBack();
        initZegoRoomCallBack();
        initZegoImCallBack();
        initPhoneCallingListener();

        mStreamMixer.setCallback(new IZegoMixStreamCallback() {
            @Override
            public void onMixStreamConfigUpdate(int i, String s, HashMap<String, Object> hashMap) {
                handleMixStreamStateUpdate(i, s, hashMap);
            }
        });
    }

    /**
     * 即构媒体播放器监听
     */
    private void initZegoMediaPlayerListener() {
        IZegoMediaPlayerWithIndexCallback callback = new IZegoMediaPlayerWithIndexCallback() {
            @Override
            public void onPlayStart(int i) {
                switch (i) {
                    case ZegoMediaPlayer.PlayerIndex.First:
                        break;
                    case ZegoMediaPlayer.PlayerIndex.Second:
                        //暂停背景音乐
                        //pauseBgMediaPlayer();
                        if (mTimeStamp > 0) {
                            // 切换原唱伴奏的时候需要seek进度
                            mChorusZegoMediaPlayer.seekTo(mTimeStamp);
                        }

                        //发送歌曲总时长到主播端和观众端，用于显示播放进度
                        PublicMessageSocket socket = new PublicMessageSocket("", "", 3,
                                "", roomId);
                        socket.setDuration(mChorusZegoMediaPlayer.getDuration()); //269453
                        LiveSocketUtil.sendPublicMessage(LivePlayActivity.this, socket);

                        if (!TextUtils.isEmpty(mMultiRoomId)) {
                            //此时为多房间连麦情况下，发送歌曲总时长到对方房间
                            //{"aroom_id":"371","auser_id":"","cmd":"multiroom_public_message","lrc_progress":0,"room_id":"138","song_duration":269453,"type":2,"user_id":""}
                            MultiRoomMessageSocket socket3 = new MultiRoomMessageSocket("", "", roomId,
                                    mMultiRoomId, 2);
                            socket3.setSongDuration(mChorusZegoMediaPlayer.getDuration());
                            LiveSocketUtil.sendMessageByMultiRoom(LivePlayActivity.this, socket3);
                        }
                        break;
                }
            }

            @Override
            public void onPlayPause(int i) {
                switch (i) {
                    case ZegoMediaPlayer.PlayerIndex.First:
                        break;
                    case ZegoMediaPlayer.PlayerIndex.Second:
                        //开始背景音乐
                        //if (!isLianMai) {
                        //    resumeBgMediaPlayer();
                        //}
                        break;
                }
            }

            @Override
            public void onPlayStop(int i) {

            }

            @Override
            public void onPlayResume(int i) {
                switch (i) {
                    case ZegoMediaPlayer.PlayerIndex.First:
                        break;
                    case ZegoMediaPlayer.PlayerIndex.Second:
                        //暂停背景音乐
                        //pauseBgMediaPlayer();
                        break;
                }
            }

            @Override
            public void onPlayError(int i, int i1) {
                switch (i) {
                    case ZegoMediaPlayer.PlayerIndex.First:
                        break;
                    case ZegoMediaPlayer.PlayerIndex.Second:
                        //开始背景音乐
                        //if (!isLianMai) {
                        //    resumeBgMediaPlayer();
                        //}
                        break;
                }
            }

            @Override
            public void onVideoBegin(int i) {

            }

            @Override
            public void onAudioBegin(int i) {

            }

            @Override
            public void onPlayEnd(int i) {
                switch (i) {
                    case ZegoMediaPlayer.PlayerIndex.First:
                        //播放完一次就不需要再播放了
                        releaseBgMediaPlayer();
                        break;
                    case ZegoMediaPlayer.PlayerIndex.Second:
                        //开始背景音乐
                        //if (!isLianMai) {
                        //    resumeBgMediaPlayer();
                        //}
                        //隐藏自己的歌词
                        mLrcChorusView.setVisibility(View.GONE);
                        //隐藏观众的歌词
                        PublicMessageSocket socket = new PublicMessageSocket("", "", 6,
                                "", roomId);
                        LiveSocketUtil.sendPublicMessage(LivePlayActivity.this, socket);

                        //发送歌曲播放完成的消息到主播端
                        PublicMessageSocket socket2 = new PublicMessageSocket("", "", 7,
                                "", roomId);
                        LiveSocketUtil.sendPublicMessage(LivePlayActivity.this, socket2);

                        if (!TextUtils.isEmpty(mMultiRoomId)) {
                            //此时为多房间连麦情况下，发送歌曲播放完成的消息到连麦房间，通知对方房间隐藏歌词
                            //{"aroom_id":"371","auser_id":"","cmd":"multiroom_public_message","lrc_progress":0,"room_id":"138","song_duration":0,"type":1,"user_id":""}
                            MultiRoomMessageSocket socket3 = new MultiRoomMessageSocket("", "", roomId,
                                    mMultiRoomId, 1);
                            LiveSocketUtil.sendMessageByMultiRoom(LivePlayActivity.this, socket3);
                        }
                        break;
                }
            }

            @Override
            public void onBufferBegin(int i) {

            }

            @Override
            public void onBufferEnd(int i) {

            }

            @Override
            public void onSeekComplete(int i, long l, int i1) {

            }

            @Override
            public void onSnapshot(Bitmap bitmap, int i) {

            }

            @Override
            public void onLoadComplete(int i) {

            }

            @Override
            public void onProcessInterval(long l, int i) {

            }
        };
        mBgZegoMediaPlayer.setEventWithIndexCallback(callback);
        mChorusZegoMediaPlayer.setEventWithIndexCallback(callback);
    }

    private final UVCCameraHelper.OnMyDevConnectListener myDevConnectListener = new UVCCameraHelper.OnMyDevConnectListener() {
        @Override
        public void onAttachDev(UsbDevice device) {
            LogUtils.d("LivePlayActivity  myDevConnectListener  USB_DEVICE_ATTACHED");
            isUsbDevice = true;
            //            Toast.makeText(LivePlayActivity.this, "USB_DEVICE_ATTACHED", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDettachDev(UsbDevice device) {
            LogUtils.d("LivePlayActivity  myDevConnectListener  USB_DEVICE_DETTACH");
        }

        @Override
        public void onConnectDev(UsbDevice device) {
            LogUtils.d("LivePlayActivity  myDevConnectListener  USB_DEVICE_CONNECT 设备连接成功");
            mIsUsbConnected = true;
            isUsbDevice = true;
            getPermission();
        }

        @Override
        public void onDisConnectDev(UsbDevice device) {
            LogUtils.d("LivePlayActivity  myDevConnectListener  onDisConnectDev");
            mIsUsbConnected = false;
            isUsbDevice = false;
        }

        @Override
        public void onCancelDev(UsbDevice device) {
            LogUtils.d("LivePlayActivity  myDevConnectListener  USB_DEVICE_CANCEL");
        }
    };

    private void initZegoImCallBack() {
        mZegoLiveRoom.setZegoIMCallback(new IZegoIMCallback() {
            @Override
            public void onUserUpdate(ZegoUserState[] listUser, int updateType) {
                for (ZegoUserState zegoUserState : listUser) {
                    if (zegoUserState.updateFlag == ZegoIM.UserUpdateFlag.Deleted) {
                        if (zegoUserState.roomRole == ZegoConstants.RoomRole.Anchor) {
                            stopPlayStream();
                            out();
                            Log.e(TAG, "收到即构IM退出房间");
                        }
                    }
                }
            }

            @Override
            public void onRecvRoomMessage(String roomID, ZegoRoomMessage[] listMsg) {

            }

            @Override
            public void onUpdateOnlineCount(String s, int i) {
                //mWatchNum = i - 1;
                //setWatchNum();
            }

            @Override
            public void onRecvBigRoomMessage(String s, ZegoBigRoomMessage[] zegoBigRoomMessages) {

            }
        });
    }


    int pusherr = 0;

    private void initZegoLivePushListener() {
        mZegoLiveRoom.setZegoLivePublisherCallback(new IZegoLivePublisherCallback() {
            @Override
            public void onPublishStateUpdate(int stateCode, String streamID, HashMap<String, Object> streamInfo) {
                LogUtils.d("LivePlayActivity startPlay startPublishing  sinitZegoLivePushListener tartPublishing initZegoLivePushListener stateCode : " + stateCode + "  streamID : " + streamID);
                //推流状态更新
                if (stateCode == 0) {
                    pusherr = 0;
                    isLive = true;
                    handlePublishSuccMix(streamID, streamInfo);
                    //initZegoAudioPrepCallback();
                    mIsStartPublishing = true;
                } else {
                    LogUtil.e("推流失败：" + streamID + " StateCode:" + stateCode);
                    if (pusherr < 3 && !isLive) {
                        pusherr++;
                        //Push();
                    } else {
                        handlePublishStop(stateCode, streamID);
                    }
                }
            }

            @Override
            public void onJoinLiveRequest(final int seq, String fromUserID, String fromUserName, String roomID) {
            }

            @Override
            public void onPublishQualityUpdate(String streamID, ZegoPublishStreamQuality streamQuality) {
                Log.d(TAG, "************ onPublishQualityUpdate ************quality=" + streamQuality.quality
                        + ", vkbps=" + streamQuality.vkbps + ", vcapFps=" + streamQuality.vcapFps
                        + ", akbps=" + streamQuality.akbps + ", 延时" + streamQuality.rtt + "ms");
            }

            @Override
            public void onCaptureVideoSizeChangedTo(int width, int height) {

            }

            @Override
            public void onCaptureVideoFirstFrame() {

            }

            @Override
            public void onCaptureAudioFirstFrame() {

            }
        });
    }


    private void handleMixStreamStateUpdate(int errorCode, String mixStreamID, HashMap<String, Object> streamInfo) {
        int seq = -1;
        if (streamInfo != null) {
            seq = (int) streamInfo.get(ZegoConstants.StreamKey.MIX_CONFIG_SEQ);
        }
        if (errorCode == 0) {
            ViewLive viewLivePublish = getViewLiveByStreamID(mPublishStreamID);
            List<String> listUrls = getShareUrlList(streamInfo);

            if (listUrls.size() == 0) {
                LogUtil.e("混流失败...errorCode: %d, seq: %d" + errorCode + seq);
            } else {
                LogUtil.e("混流成功 地址: %s; seq: %d" + listUrls.get(0) + seq);
            }
            if (viewLivePublish != null && listUrls.size() >= 2) {
                LogUtil.e("混流地址: %s; seq: %d" + listUrls.get(1) + seq);
                setListShareUrls(listUrls);

                // 将混流ID通知观众
                Map<String, String> mapUrls = new HashMap<>();
                mapUrls.put(Constants.FIRST_ANCHOR, String.valueOf(true));
                mapUrls.put(Constants.KEY_MIX_STREAM_ID, mixStreamID);
                mapUrls.put(Constants.KEY_HLS, listUrls.get(0));
                mapUrls.put(Constants.KEY_RTMP, listUrls.get(1));

                Gson gson = new Gson();
                String json = gson.toJson(mapUrls);
                mZegoLiveRoom.updateStreamExtraInfo(json);
            }
        } else {
            LogUtil.e("handleMixStreamStateUpdate ErrCode:" + errorCode);
        }
    }

    /**
     * 获取流地址.
     */
    protected List<String> getShareUrlList(HashMap<String, Object> info) {
        List<String> listUrls = new ArrayList<>();

        if (info != null) {
            String[] hlsList = (String[]) info.get(ZegoConstants.StreamKey.HLS_URL_LST);
            if (hlsList != null && hlsList.length > 0) {
                listUrls.add(hlsList[0]);
            }

            String[] rtmpList = (String[]) info.get(ZegoConstants.StreamKey.RTMP_URL_LIST);
            if (rtmpList != null && rtmpList.length > 0) {
                listUrls.add(rtmpList[0]);
            }
        }
        return listUrls;
    }


    /**
     * 分享地址.
     */
    private List<String> mListShareUrls = new ArrayList<>();

    private void setListShareUrls(List<String> listShareUrls) {
        mListShareUrls.clear();
        mListShareUrls.addAll(listShareUrls);
    }


    /**
     * 通过streamID查找正在publish或者play的ViewLive.
     */
    protected ViewLive getViewLiveByStreamID(String streamID) {
        if (TextUtils.isEmpty(streamID)) {
            return null;
        }
        if (streamID.equals(tvAudience1.getStreamID())) {
            return tvAudience1;
        }
        return null;
    }

    protected boolean mIsPublishing = false;

    /**
     * 停止推流.
     */
    private void handlePublishStop(int stateCode, String streamID) {
        Log.d(TAG, "*********** handlePublishStop ***********streamID=" + streamID + ", stateCode=" + stateCode);
        mIsPublishing = false;
        isLive = false;
        releaseLiveView(streamID);
    }

    // 如何判断当前的View 是自己 问题是有两个view都显示close
    // 我要的结果是 显示推流的控件显示关闭按钮
    private void releaseLiveView(String streamID) {
        Log.d(TAG, "*********** releaseLiveView ***********streamID=" + streamID);
        if (TextUtils.isEmpty(streamID)) {
            return;
        }
        if (streamID.equals(tvAudience1.getStreamID())) {
            setShowVideoWindow(false, false);
            tvAudience1.setFree();
            stopChorusMediaPlayer();
            isLianMai = false;
            setTextureViewAlpha60();
        }
    }


    private void handlePublishSuccMix(String streamID, HashMap<String, Object> streamInfo) {
        mIsPublishing = true;
        isLive = true;
        final int margin = 25;
        ZegoMixStreamInfo mixStreamInfo = new ZegoMixStreamInfo();
        mixStreamInfo.streamID = mPublishStreamID;
        mixStreamInfo.top = 0;
        mixStreamInfo.bottom = ZegoLiveManager.getInstance().getZegoAvConfig().getVideoEncodeResolutionHeight();
        mixStreamInfo.left = 0;
        mixStreamInfo.right = ZegoLiveManager.getInstance().getZegoAvConfig().getVideoEncodeResolutionWidth();
        mMixStreamInfos.add(mixStreamInfo);
        startMixStream();
    }

    protected List<ZegoMixStreamInfo> mMixStreamInfos = new ArrayList<>();
    private int mixStreamRequestSeq = 1;

    private void startMixStream() {
        int size = mMixStreamInfos.size();
        ZegoMixStreamInfo[] inputStreamList = new ZegoMixStreamInfo[size];
        for (int i = 0; i < size; i++) {
            inputStreamList[i] = mMixStreamInfos.get(i);
        }
        ZegoCompleteMixStreamInfo mixStreamConfig = new ZegoCompleteMixStreamInfo();
        mixStreamConfig.inputStreamList = inputStreamList;
        mixStreamConfig.outputStreamId = mMixStreamID;
        mixStreamConfig.outputIsUrl = false;
        mixStreamConfig.outputWidth = ZegoLiveManager.getInstance().getZegoAvConfig().getVideoCaptureResolutionWidth();
        mixStreamConfig.outputHeight = ZegoLiveManager.getInstance().getZegoAvConfig().getVideoCaptureResolutionHeight();
        mixStreamConfig.outputFps = 15;
        mixStreamConfig.outputBitrate = 600 * 1000;
        mixStreamConfig.outputBackgroundColor = 0xc8c8c800;
        //        mZegoLiveRoom.mixStream(mixStreamConfig, mixStreamRequestSeq++);
        mStreamMixer.mixStream(mixStreamConfig, mixStreamRequestSeq++);
    }

    private void initZegoLivePlayerCallBack() {
        // 观众回调
        mZegoLiveRoom.setZegoLivePlayerCallback(new IZegoLivePlayerCallback() {
            @Override
            public void onPlayStateUpdate(int stateCode, String streamID) {
                // 拉流状态更新
                if (stateCode == 0) {
                    handlePlaySucc(streamID);
                } else {
                    handlePlayStop(stateCode, streamID);
                }
            }

            @Override
            public void onPlayQualityUpdate(String streamID, ZegoPlayStreamQuality zegoPlayStreamQuality) {
                // 拉流质量回调
                Log.d(TAG, "************ onPlayQualityUpdate ************quality=" + zegoPlayStreamQuality.quality
                        + ", vkbps=" + zegoPlayStreamQuality.vkbps + ", vdecFps=" + zegoPlayStreamQuality.vdecFps
                        + ", akbps=" + zegoPlayStreamQuality.akbps + ", 延时" + zegoPlayStreamQuality.rtt + "ms");
            }

            @Override
            public void onInviteJoinLiveRequest(int seq, String fromUserID, String fromUserName, String roomID) {

            }

            @Override
            public void onRecvEndJoinLiveCommand(String fromUserId, String fromUserName, String roomId) {
                handleEndJoinLiveCommand(fromUserId, fromUserName, roomId);
            }

            @Override
            public void onVideoSizeChangedTo(String streamID, int width, int height) {
                handleVideoSizeChanged(streamID, width, height);
            }
        });

    }

    /**
     * 拉流分辨率更新.
     */
    protected void handleVideoSizeChanged(String streamID, int width, int height) {
        if (width > height) {
            ViewLive viewLivePlay = getViewLiveByStreamID(streamID);
            if (viewLivePlay != null) {
                if (viewLivePlay.getWidth() < viewLivePlay.getHeight()) {
                    viewLivePlay.setZegoVideoViewMode(false, ZegoVideoViewMode.ScaleAspectFill);
                    mZegoLiveRoom.setViewMode(ZegoVideoViewMode.ScaleAspectFill, streamID);
                } else {
                    viewLivePlay.setZegoVideoViewMode(false, ZegoVideoViewMode.ScaleAspectFill);
                    mZegoLiveRoom.setViewMode(ZegoVideoViewMode.ScaleAspectFill, streamID);
                }
            }
        }
        //        mRlytControlHeader.bringToFront();

    }

    /**
     * 响应结束连麦信令
     */
    private void handleEndJoinLiveCommand(String fromUserId, String fromUserName, String roomId) {
        LogUtil.e("handleEndJoinLiveCommand fromUserId " + fromUserId + " fromUserName " + fromUserName + " roomId " + roomId);
        stopPublish();
    }

    private void handlePlayStop(int stateCode, String streamID) {
        Log.d(TAG, "*********** handlePlayStop ***********streamID=" + streamID + ", stateCode=" + stateCode);
        // 释放View
        releaseLiveView(streamID);
        if (streamID.equals(ZegoRoomUtil.getMixStreamID(mHostUserId))) {
            exit();
        }
    }

    /**
     * 拉流成功.
     */
    protected void handlePlaySucc(String streamID) {
        LogUtil.e("MY_SELF: onPlaySucc(" + streamID + ")");
    }


    private IosDialog netReturn;

    private void initZegoRoomCallBack() {
        mZegoLiveRoom.setZegoRoomCallback(new IZegoRoomCallback() {

            @Override
            public void onKickOut(int i, String s, String s1) {

            }

            //用户掉线
            @Override
            public void onDisconnect(int errorCode, String roomID) {
                //                handleDisconnect(errorCode, roomID);
                if (netReturn == null) {
                    netReturn = XiTongDialog.showSystemDialog(LivePlayActivity.this, getString(R.string.play_net_err), new XiTongDialog.CallBack<String>() {
                        @Override
                        public void onFailure(@Nullable String msg) {

                        }

                        @Override
                        public void onSuccess(@Nullable String msg) {
                            finish();
                        }
                    });
                }
                if (!netReturn.isShow()) {
                    netReturn.show();
                }
                LogUtil.e("MY_SELF : onDisconnected, roomID:" + roomID + ", errorCode:" + errorCode);
            }

            @Override
            public void onReconnect(int i, String s) {

            }

            @Override
            public void onTempBroken(int i, String s) {

            }

            @Override
            public void onStreamUpdated(final int type, final ZegoStreamInfo[] listStream, final String roomID) {
                Log.d(TAG, "********* onStreamUpdated ********* type=" + type + ", roomId=" + roomID);
                if (listStream != null && listStream.length > 0) {
                    switch (type) {
                        case ZegoConstants.StreamUpdateType.Added:
                            // 处理房间内主播的流
                            handleStreamAdded(listStream, roomID);
                            break;
                        case ZegoConstants.StreamUpdateType.Deleted:
                            handleStreamDeleted(listStream, roomID);
                            break;
                    }
                    handleZegoStreamInfo(listStream);
                }
            }

            @Override
            public void onStreamExtraInfoUpdated(ZegoStreamInfo[] zegoStreamInfos, String s) {
                handleZegoStreamInfo(zegoStreamInfos);
            }

            @Override
            public void onRecvCustomCommand(String userID, String userName, String content, String roomID) {

            }
        });
    }

    boolean mIsPlayingMixStream = true;

    /**
     * 房间内用户创建流.
     */
    protected void handleStreamAdded(final ZegoStreamInfo[] listStream, final String roomID) {
        if (listStream != null && listStream.length > 0) {
            for (ZegoStreamInfo streamInfo : listStream) {
                LogUtil.e(": added stream(" + streamInfo.streamID + ")");
                mListStreamOfRoom.add(streamInfo);
                //                if (!mIsPlayingMixStream) {
                //                    if (streamInfo.streamID.equals(ZegoRoomUtil.getPublishStreamID(mHostUserId)))
                //                        startPlay(streamInfo.streamID, 0);
                //                }
                //                if (!isStreamExisted(ZegoRoomUtil.getMixStreamID(mHostUserId))) {
                //                    if (LoginUserManager.getLiveVideoSource().equals("app")) {
                //                        startPlay(streamInfo.streamID, 0);
                //                    }
                //                }

                if (!streamInfo.streamID.equals(ZegoRoomUtil.getPublishStreamID(mHostUserId))) {
                    startPlay(streamInfo.streamID, streamInfo);
                }
            }
        }
    }

    private String lalaAid = null;

    private void handleStreamDeleted(ZegoStreamInfo[] listStream, String roomID) {
        if (listStream != null && listStream.length > 0) {
            for (ZegoStreamInfo streamInfo : listStream) {
                LogUtil.e(streamInfo.userName + ": deleted stream(" + streamInfo.streamID + ")");
                for (ZegoStreamInfo info : mListStreamOfRoom) {
                    if (streamInfo.streamID.equals(info.streamID)) {
                        mListStreamOfRoom.remove(info);
                        if (!TextUtils.isEmpty(lalaAid)) {
                            if (streamInfo.streamID.equals(ZegoRoomUtil.getPublishStreamID(lalaAid))) {
                                lalaAid = null;
                            }
                        }
                        break;
                    }

                }

                stopPlay(streamInfo.streamID);

            }

            isLianMai = false;
            setShowVideoWindow(false, false);
            //恢复背景音乐
            //resumeBgMediaPlayer();
            //关闭合唱
            stopChorusMediaPlayer();
            setTextureViewAlpha60();
        }
    }


    protected PhoneStateListener mPhoneStateListener = null;
    protected boolean mHostHasBeenCalled = false;

    /**
     * 电话状态监听.
     */
    protected void initPhoneCallingListener() {
        mPhoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE:
                        if (mHostHasBeenCalled) {
                            mHostHasBeenCalled = false;
                            LogUtil.e(": call state idle");
                            //收到PhoneStateManager回调的 TelephonyManager.CALL_STATE_IDLE 事件时，可能系统的设备还未释放完毕，需要延迟2s再调用mZegoLiveRoom.resumeModule(ZegoConstants.ModuleType.AUDIO)
                            getWindow().getDecorView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mZegoLiveRoom.resumeModule(ZegoConstants.ModuleType.AUDIO);
                                }
                            }, 2000);
                        }

                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                        LogUtil.e(": call state ringing");
                        mHostHasBeenCalled = true;
                        // 来电，暂停音频模块
                        mZegoLiveRoom.pauseModule(ZegoConstants.ModuleType.AUDIO);
                        break;

                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        break;
                }
            }
        };

        TelephonyManager tm = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initZanData() {
        mList.add(((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.mipmap.heart0, null))).getBitmap());
        mList.add(((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.mipmap.heart2, null))).getBitmap());
        mList.add(((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.mipmap.heart3, null))).getBitmap());
        mList.add(((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.mipmap.heart4, null))).getBitmap());
        mList.add(((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.mipmap.heart7, null))).getBitmap());
        mList.add(((BitmapDrawable) Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.mipmap.heart8, null))).getBitmap());
        zanViewh.post(new Runnable() {
            @Override
            public void run() {
                zanViewh.setEndPoint(new PointF(zanViewh.getMeasuredWidth() / 2, 0));
                zanViewh.setDivergeViewProvider(new Provider());
            }
        });
    }

    private void initMessageRv() {
        rvMesssage.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new LiveMessageAdapter(null, this, this);
        rvMesssage.setAdapter(messageAdapter);
    }

    //横屏消息显示
    public void addMessage(SendMsgEntity sendMsgEntity, boolean isScrollBottom) {
        if (sendMsgEntity != null && sendMsgEntity.isClickGiftMessage()) {
            if (mGifView == null) {
                return;
            }
            mGifView.startAnimation(mGifView.getWidth(), mGifView.getHeight());
        } else {
            String itemContent = sendMsgEntity.getContent();
            if (!TextUtils.isEmpty(itemContent) && GsonConverter.isJson(itemContent)) {
                BaPingChatMessage baPingChatMessage = GsonConverter.fromJson(itemContent, BaPingChatMessage.class);
                if (baPingChatMessage != null) {
                    switch (baPingChatMessage.getType()) {
                        case 6:
                            if (mDanmuView != null) {
                                Danmu danmu = new Danmu();
                                danmu.setHeaderUrl(null);
                                danmu.setUserName(null);
                                danmu.setInfo(baPingChatMessage.getMusicName());
                                mDanmuView.add(danmu);
                            }
                            return;
                        case 7:
                            int soundEffect = baPingChatMessage.getSoundEffect();
                            startEffectsMusic(soundEffect);
                            return;
                        case 15:
                            if (mZegoLiveRoom != null) {
                                mZegoLiveRoom.enableMic(false);
                            }
                            return;
                        case 16:
                            if (mZegoLiveRoom != null) {
                                mZegoLiveRoom.enableMic(true);
                            }
                            return;
                        case 17:
                            int progress = baPingChatMessage.getVoiceControlProgress();
                            float volumeValue = (progress * 1.0f) / 100;
                            if (mGiftMediaPlayer != null) {
                                mGiftMediaPlayer.setVolume(volumeValue, volumeValue);
                            }
                            if (mEffectsMediaPlayer != null) {
                                mEffectsMediaPlayer.setVolume(volumeValue, volumeValue);
                            }
                            if (mBgZegoMediaPlayer != null) {
                                mBgZegoMediaPlayer.setVolume(progress);
                            }
                            if (mChorusZegoMediaPlayer != null) {
                                mChorusZegoMediaPlayer.setVolume(progress);
                            }
                            return;
                        case 18:
                            if (mZegoLiveRoom != null) {
                                mZegoLiveRoom.setCaptureVolume(baPingChatMessage.getVoiceControlProgress());
                            }
                            return;

                        case 20:
                            mMultiRoomStreamId = baPingChatMessage.getStreamID();
                            mMultiRoomId = baPingChatMessage.getAroom_id();
                            mShowAllView.setVisibility(View.VISIBLE);
                            tvAudience1.setVisibility(View.VISIBLE);
                            tvAudience1.setStreamID(mMultiRoomStreamId);
                            tvAudience1.setUserAvatarViewGone();
                            mZegoLiveRoom.startPlayingStream(mMultiRoomStreamId, tvAudience1.getTextureView());
                            isLianMai = true;
                            setShowVideoWindow(true, false);
                            stopChorusZegoMediaPlayer();
                            //pauseBgMediaPlayer();
                            mLrcChorusView.setVisibility(View.GONE);
                            setTextureViewAlpha100();
                            return;
                        case 21:
                            int alphaProgress = baPingChatMessage.getVoiceControlProgress();
                            float alphaValue = (alphaProgress * 1.0f) / 100;
                            if (isLianMai) {
                                return;
                            } else {
                                if (mLocalPreview != null) {
                                    mLocalPreview.setTextureViewAlpha(alphaValue);
                                }
                            }
                            return;
                        case 22:
                            GiftEntity giftEntity = baPingChatMessage.getGiftEntity();
                            if (giftEntity != null) {
                                showAnim(giftEntity);
                            }
                            return;
                        default:
                            break;
                    }
                    String bapingText = baPingChatMessage.getText();
                    if (!TextUtils.isEmpty(bapingText) && mDanmuView != null) {
                        Danmu danmu = new Danmu();
                        danmu.setHeaderUrl(sendMsgEntity.getAvatar());
                        danmu.setUserName(sendMsgEntity.getUsername());
                        danmu.setInfo("霸屏主题：" + bapingText);
                        mDanmuView.add(danmu);
                    }
                }
            }
            messageAdapter.addData(sendMsgEntity);
            if (isScrollBottom) {
                rvMesssage.scrollToPosition(messageAdapter.getData().size() - 1);
            }
        }
    }

    private int width;

    private void initIconRv() {
        width = -DensityUtil.dip2px(BaseApplication.getApp(), 4);
        mWatchUserRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        mWatchUserRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) != userIconAdapter.getData().size() - 1) {
                    outRect.right = width;
                }

            }
        });
        userIconAdapter = new UserIconPcAdapter(null);
        mWatchUserRecyclerView.setAdapter(userIconAdapter);
    }

    private void initAdapterListener() {
        userIconAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RoomUserBean.DataBean dataBean = userIconAdapter.getData().get(position);
                //                showPw(dataBean.getUserId(), view);
            }
        });

        mWatchUserRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            // 用来标记是否正在向左滑动
            private boolean isSlidingToLeft = false;

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // dx值大于0表示正在向左滑动，小于或等于0表示向右滑动或停止
                isSlidingToLeft = dx > 0;
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滑动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 获取最后一个完全显示的itemPosition
                    if (manager != null) {
                        int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
                        int itemCount = manager.getItemCount();
                        // 判断是否滑动到了最后一个item，并且是向左滑动
                        if (lastItemPosition == (itemCount - 1) && isSlidingToLeft) {
                            int size = userIconAdapter.getData().size();
                            if (size <= 10) {
                                start = 0;
                                watchPresenter.getRoomUser(TAG, String.valueOf(start), roomId);
                            }
                        }
                    }
                }
            }
        });
    }


    private void showZan() {
        Random random = new Random();
        zanViewh.startDiverges(random.nextInt(6));
    }

    @Override
    public void onFaceBox(int x, int y, int width) {
        //        Log.e(TAG, "onFaceBox:  x = " + x + " y = " + y + " width  = " + width)
       /* if (isShowTag) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tags.getLayoutParams();
            // 横坐标移动系数
            int mWidth = 0;
            // 纵坐标移动系数
            int mHeight = 0;
            // 宽
            int mW = 0;
            switch (pid) {
                case "0":
                    //小马
                    Log.e(TAG, "onFaceBox: ");
                    mWidth = (int) (width * 3);
                    mHeight = -width / 4;
                    layoutParams.topMargin = y - ((mWidth - width) / 2) + mHeight;
                    layoutParams.leftMargin = x - ((mWidth - width) / 2) + width / 2;
                    layoutParams.width = mWidth;
                    layoutParams.height = mWidth;
                    tags.setLayoutParams(layoutParams);
                    break;
                case "1":
                    //辫子 ok
                    mWidth = (int) (width * 1.2);
                    mHeight = -width / 4;
                    layoutParams.topMargin = y - ((mWidth - width) / 2) + mHeight;
                    layoutParams.leftMargin = x - ((mWidth - width) / 2) + width / 20;
                    layoutParams.width = mWidth;
                    layoutParams.height = mWidth;
                    tags.setLayoutParams(layoutParams);
                    break;
                case "2"://
                    //恶魔头饰 ok
                    mWidth = (int) (width * 1.8);
                    mHeight = -width / 10;
                    layoutParams.topMargin = y - ((mWidth - width) / 2) + mHeight;
                    layoutParams.leftMargin = x - ((mWidth - width) / 2) + width / 10;
                    layoutParams.width = mWidth;
                    layoutParams.height = mWidth;
                    tags.setLayoutParams(layoutParams);
                    break;
                case "3":
                    //狗狗
                    mWidth = (int) (width * 2);
                    mHeight = -width / 8;
                    layoutParams.topMargin = y - ((mWidth - width) / 2) + mHeight;
                    layoutParams.leftMargin = x - ((mWidth - width) / 2) + width / 5;
                    layoutParams.width = mWidth;
                    layoutParams.height = mWidth * 2;
                    tags.setLayoutParams(layoutParams);
                    break;
                case "4":
                    //鸡冠
                    mWidth = (int) (width * 2);
                    layoutParams.topMargin = y - ((mWidth - width) / 2) - width / 2;
                    layoutParams.leftMargin = x - ((mWidth - width) / 2) + width / 5;
                    layoutParams.width = mWidth;
                    layoutParams.height = mWidth * 2;
                    tags.setLayoutParams(layoutParams);
                    break;
                case "5":
                    //老虎
                    mWidth = (int) (width * 1.5);
                    mHeight = 0;
                    layoutParams.topMargin = y - ((mWidth - width) / 2) + mHeight;
                    layoutParams.leftMargin = x - ((mWidth - width) / 2) + width / 20;
                    layoutParams.width = mWidth;
                    layoutParams.height = mWidth;
                    tags.setLayoutParams(layoutParams);
                    break;
                case "6":
                    //老鼠
                    mWidth = (int) (width * 1.8);
                    mHeight = mWidth / 20;
                    layoutParams.topMargin = y - ((mWidth - width) / 2);
                    layoutParams.leftMargin = x - ((mWidth - width) / 2) + width / 8;
                    layoutParams.width = mWidth;
                    layoutParams.height = mWidth;
                    tags.setLayoutParams(layoutParams);
                    break;
                case "7":
                    //帽子
                    mWidth = width;
                    mHeight = width;
                    layoutParams.topMargin = y - ((mWidth - width) / 2) - width * 3 / 4;
                    layoutParams.leftMargin = x - ((mWidth - width) / 2) + width / 15;
                    layoutParams.width = mWidth;
                    layoutParams.height = mWidth;
                    tags.setLayoutParams(layoutParams);
                    break;
                case "8":
                    //牛
                    mWidth = (int) (width * 2);
                    mHeight = -width / 4;
                    layoutParams.topMargin = y - ((mWidth - width) / 2) + mHeight;
                    layoutParams.leftMargin = x - ((mWidth - width) / 2) + width / 20;
                    layoutParams.width = mWidth;
                    layoutParams.height = mWidth;
                    tags.setLayoutParams(layoutParams);
                    break;
                case "9":
                    //兔子
                    mWidth = (int) (width * 1.5);
                    mHeight = -width / 2;
                    layoutParams.topMargin = y - ((mWidth - width) / 2) + mHeight;
                    layoutParams.leftMargin = x - ((mWidth - width) / 2) + width / 20;
                    layoutParams.width = mWidth;
                    layoutParams.height = mWidth;
                    tags.setLayoutParams(layoutParams);
                    break;
                case "10":
                    //羊头饰
                    //羊头饰
                    mWidth = (int) (width * 2);
                    mHeight = 0;
                    layoutParams.topMargin = y - ((mWidth - width) / 2) + mHeight;
                    layoutParams.leftMargin = x - ((mWidth - width) / 2) + width / 15;
                    layoutParams.width = mWidth;
                    layoutParams.height = mWidth;
                    tags.setLayoutParams(layoutParams);
                    break;
                case "11":
                    //猪鼻
                    mWidth = (int) (width * 1.3);
                    mHeight = width / 10;
                    layoutParams.topMargin = y - ((mWidth - width) / 2) - mHeight;
                    layoutParams.leftMargin = x - ((mWidth - width) / 2) + width / 20;
                    layoutParams.width = mWidth;
                    layoutParams.height = mWidth;
                    tags.setLayoutParams(layoutParams);
                    break;

            }

        }*/
    }

    @Override
    public void gestures(Classifier.Recognition result) {

    }

    @Override
    public void ifBlow(boolean isBlow) {

    }


    class Provider implements DivergeView.DivergeViewProvider {
        @Override
        public Bitmap getBitmap(Object obj) {
            return mList == null ? null : mList.get((int) obj);
        }

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        initViewId();
        mImmersionBar
                .keyboardEnable(true)
                .statusBarView(topView)
                .setOnKeyboardListener(new OnKeyboardListener() {    //软键盘监听回调
                    @Override
                    public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                        //isPopup为true，软键盘弹出，为false，软键盘关闭
                        if (!isPopup && keyboardHeight == 0) {

                        } else {

                        }
                        mIsPopup = isPopup;
                    }
                })
                .init();
    }

    //24 * 150
    private void showCloseRed() {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setMessage(getString(R.string.close_red));
        alert.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopRedRain();
                isRedRain = false;
            }
        });
        alert.show();
    }


    private GiftMode giftMode;
    private GiftMode mTagMode;

    @Override
    protected void onStart() {
        super.onStart();
        //setLocalMVVideo();
        // step.2 register USB event broadcast
        UVCCameraHelper.sharedInstance().registerUSB();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDanmuView.startPlay(true);
        if (BuildConfig.channel_id == Constants.CHANNEL_ID_SUN_USB) {
            //阳光点歌机使用UVCCamera
            List<UsbDevice> usbDeviceList = UVCCameraHelper.sharedInstance().getDeviceList();
            LogUtils.d("LivePlayActivity  onResume  usbDeviceList : " + usbDeviceList);
            if (usbDeviceList == null || usbDeviceList.isEmpty()) {
                String deviceId = DeviceUtil.getDeviceId();
                LogUtils.d("LivePlayActivity  onResume  deviceId : " + deviceId);
                if (TextUtils.isEmpty(deviceId)) {
                    return;
                }
                mZegoLiveRoom.enableCamera(true);
                isUsbDevice = false;
                getPermission();
            } else {
                isUsbDevice = true;
                initUVCCamera();
            }
        } else if (BuildConfig.channel_id == Constants.CHANNEL_ID_YIN_CHUANG) {
//        //音创点歌机使用系统Camera
            String deviceId = DeviceUtil.getDeviceId();
            LogUtils.d("LivePlayActivity  onResume  deviceId : " + deviceId);
            if (TextUtils.isEmpty(deviceId)) {
                return;
            }
            mZegoLiveRoom.setFrontCam(true);
            mZegoLiveRoom.enableCamera(true);
            isUsbDevice = false;
            getPermission();
        } else if (BuildConfig.channel_id == Constants.CHANNEL_ID_SUN_NET) {
            // 网络摄像头
            isUsbDevice = true;
            mIsUsbConnected = true;
            setVideoCaptureFactory();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // step.3 unregister USB event broadcast
        UVCCameraHelper.sharedInstance().unregisterUSB();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @Override
    public void onBackPressed() {
        LogUtils.d("onBackPressed");
        exit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d("onDestroy");
        LiveService.setRoomId("");
        clearAnim();
        //销毁礼物动画
        if (giftControl != null) {
            giftControl.cleanAll();
        }
        if (giftControl2 != null) {
            giftControl2.cleanAll();
        }
        if (mList != null) {
            mList.clear();
            mList = null;
        }

        handler.removeCallbacksAndMessages(null);
        handler = null;

        redPacketsView.stopRainNow();
        if (mChorusProgressTimer != null) {
            mChorusProgressTimer.cancel();
            mChorusProgressTimer = null;
        }
        releaseChorusTimerTaskManager();
        releaseMediaPlayer();
        stopPublish();
        zegoDestory();
        unDispose();
    }

    private void releaseMediaPlayer() {
        if (mEffectsMediaPlayer != null) {
            mEffectsMediaPlayer.stop();
            mEffectsMediaPlayer.release();
            mEffectsMediaPlayer = null;
        }
        if (mGiftMediaPlayer != null) {
            mGiftMediaPlayer.stop();
            mGiftMediaPlayer.release();
            mGiftMediaPlayer = null;
        }
        releaseBgMediaPlayer();
        releaseChorusZegoMediaPlayer();
    }

    private void zegoDestory() {
        LogUtils.d("zegoDestroy");
        ZegoExternalVideoCapture.setVideoCaptureFactory(null, ZegoConstants.PublishChannelIndex.MAIN);
        // 注销电话监听
        TelephonyManager tm = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
        mPhoneStateListener = null;
        // 清空回调, 避免内存泄漏
        mZegoLiveRoom.setZegoLivePublisherCallback(null);
        mZegoLiveRoom.setZegoLivePlayerCallback(null);
        mZegoLiveRoom.setZegoRoomCallback(null);
        mZegoLiveRoom.setZegoIMCallback(null);
        mZegoLiveRoom.setZegoAudioPrepCallback2(null);
        // 退出房间
        mZegoLiveRoom.logoutRoom();
        if (tvAudience1 != null) {
            tvAudience1.destroy();
            isLianMai = false;
        }
        ZegoLiveManager.getInstance().releaseSDK();
    }


    private void stopPublish() {
        LogUtils.d("stopPublish");
        if (mIsPublishing || isLive) {
            handlePublishStop(1, mPublishStreamID);
            LogUtil.e("MY_SELF : stop publishing(" + mPublishStreamID + ")");
            mZegoLiveRoom.stopPublishing();
            mZegoLiveRoom.stopPreview();
            mZegoLiveRoom.setPreviewView(null);
        }
    }


    @Override
    public void showTip(int msg) {
        Toast.makeText(LivePlayActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFail(String msg) {
        Toast.makeText(LivePlayActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getGiftData(GiftMode giftMode) {
        this.giftMode = giftMode;
    }

    private String roomId;
    private int mWatchNum = 0;
    private String mHostUserId = "";
    private String mHostUserName;

    private boolean isLive = false;

    @Override
    public void getLiveView(LiveEntity liveEntity) {
        if (liveEntity == null || titleBack == null) {
            return;
        }
        LiveEntity.ExtrasBean extrasBean = liveEntity.getExtras();
        //        mWatchNum = Integer.parseInt(extrasBean.getWatch());
        mWatchUserCount.setText(NumShow.formatNum(String.valueOf(mWatchNum), false, this));
        watchPresenter.getRoomUser(TAG, String.valueOf(start), roomId);
        RequestOptions priority = new RequestOptions().priority(Priority.HIGH).error(R.mipmap.pic_3);
        if (extrasBean == null) {
            return;
        }
        mHostUserId = extrasBean.getUser_id();
        mHostUserName = extrasBean.getUsername();
        if (!TextUtils.isEmpty(extrasBean.getYq_name())) {
            yq_type = extrasBean.getYq_type();
            if (extrasBean.getStatus().equals("2")) {
                if (extrasBean.getYq_type().equals("1")) {
                    SeeTemplateEntity seeTemplateEntity = new SeeTemplateEntity();
                    seeTemplateEntity.setUri(extrasBean.getYq_uri());
                    seeTemplateEntity.setCover(extrasBean.getYq_cover());
                    playVideo(seeTemplateEntity, 1f);
                }
                startHengFu(extrasBean.getYq_name() + "：" + extrasBean.getYq_describe());
            }
        }

        try {
            GlideUtil.setImage(mZhuBoAvatar, this, extrasBean.getAvatar(), GlideUtil.createRp(R.mipmap.pic_3));
        } catch (Exception e) {

        }
        mZhuBoName.setText(extrasBean.getUsername());

        //加载当前连麦的人的画面
        //        for (int i = 0; i < liveEntity.getData().size(); i++) {
        //            initInvite(liveEntity.getData().get(i).getMain_rtmp(), liveEntity.getData().get(i).getUser_id(), "", liveEntity.getData().get(i).getLara());
        //        }

        String title = extrasBean.getTitle();
        String categoryId = extrasBean.getCategory_id();
        String categoryName = extrasBean.getCategory_name();
        if (!TextUtils.isEmpty(title)) {
            mTvPartySubject.setSelected(true);
            mTvPartySubject.setText(title);
        }
        if (extrasBean.getCover().endsWith("mp4")) {
            //主题背景图为动态视频
            showDynamicBg(extrasBean.getCover());
        } else {
            //主题背景图为静态图片
            showStaticBg(extrasBean.getCover());
        }
        LogUtils.d("extrasBean  extrasBean : " + extrasBean);
        LogUtils.d("extrasBean  categoryId : " + categoryId);
        if ("1".equals(categoryId)) {
            initBall("生日派对");
            startBgMediaPlayer(FileUtil.getPath(this, "birthday.mp3"), false);
        } else if ("2".equals(categoryId)) {
            initBall("婚礼庆典");
            startBgMediaPlayer(FileUtil.getPath(this, "wedding.mp3"), false);
        } else if ("4".equals(categoryId)) {
            initBall("喜庆聚会");
            // /storage/emulated/0/Android/data/com.newproject.hardqing/cache/jv_hui.mp3
            startBgMediaPlayer(FileUtil.getPath(this, "jv_hui.mp3"), false);
        } else if ("5".equals(categoryId)) {
            initBall("商务庆典");
            startBgMediaPlayer(FileUtil.getPath(this, "nianhui.mp3"), false);
        } else if ("9".equals(categoryId)) {
            initBall("产品发布");
            startBgMediaPlayer(FileUtil.getPath(this, "chanping_fabu.mp3"), false);
        } else if ("11".equals(categoryId)) {
            initBall("同学聚会");
            startBgMediaPlayer(FileUtil.getPath(this, "hangye_xiaoju.mp3"), false);
        } else if ("13".equals(categoryId)) {
            initBall("行业小聚");
            startBgMediaPlayer(FileUtil.getPath(this, "hangye_xiaoju.mp3"), false);
        } else if ("17".equals(categoryId)) {
            initBall("好友聚会");
            startBgMediaPlayer(FileUtil.getPath(this, "hangye_xiaoju.mp3"), false);
        } else if ("19".equals(categoryId)) {
            initBall("边唱边聚");
            startBgMediaPlayer(FileUtil.getPath(this, "jv_hui.mp3"), false);
        } else if ("20".equals(categoryId)) {
            initBall("群友派对");
            startBgMediaPlayer(FileUtil.getPath(this, "jv_hui.mp3"), false);
        } else {
            initBall("行业小聚");
            startBgMediaPlayer(FileUtil.getPath(this, "hangye_xiaoju.mp3"), false);
        }
        getPermission();
    }

    public void initBall(String titleString) {
        mTagCloudView.setBackgroundColor(CommonUtils.getColor(R.color.transparent));
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            list.add(titleString);
        }
        mTagCloudAdapter = new TagCloudAdapter(list);
        mTagCloudView.setAdapter(mTagCloudAdapter);
    }

    protected List<ZegoStreamInfo> mListStreamOfRoom = new ArrayList<>();
    protected String mRoomID = null;
    private boolean isLogin = false;

    private void doBusiness() {
        LogUtils.d("LivePlayActivity doBusiness()");
        mRoomID = "#s-" + mHostUserId;
        LogUtils.d("LivePlayActivity  doBusiness() mRoomID : " + mRoomID + "   mIsUsbConnected : " + mIsUsbConnected);
        if (TextUtils.isEmpty(mHostUserId) || ((isUsbDevice && !mIsUsbConnected))) {
            return;
        }
        boolean setUserResult = ZegoLiveRoom.setUser("tv-" + mHostUserId, "tv" + mHostUserId);
        LogUtils.d("LivePlayActivity  测试Zego设置用户名以及UID:tv-" + mHostUserId + " name:tv" + mHostUserId + "  结果：" + setUserResult);
        mZegoLiveRoom.setRoomConfig(true, true);
        initSDKAndLoginRoom();
    }

    public void initSDKAndLoginRoom() {
//        if (ThreadExecutor.isMainThread()) {
//            ThreadExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    LogUtils.d("LivePlayActivity initSDKAndLoginRoom ThreadExecutor.isMainThread()");
//                    initSDKAndLoginRoom();
//                }
//            });
//            return;
//        }
        LogUtils.d("LivePlayActivity initSDKAndLoginRoom starta mInitSDKSuccess : " + mInitSDKSuccess + " mIsLoginRoomSuccess : " + mIsLoginRoomSuccess + "  isLive :" + isLive + " isUsbDevice : " + isUsbDevice);
        // TODO: 2020/5/15
        ZegoLiveRoom.setTestEnv(true);
        mZegoLiveRoom.initSDK(GetAppIdConfig.appId, GetAppIdConfig.appSign,
                new IZegoInitSDKCompletionCallback() {
                    @Override
                    public void onInitSDK(int errorcode) {
                        LogUtils.d("LivePlayActivity  initSDKAndLoginRoom  onInitSDK  errorcode : " + errorcode);
                        if (errorcode == 0) {
                            mInitSDKSuccess = true;
                            loginRoom();
                        } else {
                            mInitSDKSuccess = false;
                        }
                    }
                });
    }

    public void loginRoom() {
        if (mIsLoginRoomSuccess) {
            return;
        }
        LogUtils.d("LivePlayActivity loginRoom start mInitSDKSuccess : " + mInitSDKSuccess + " mIsLoginRoomSuccess : " + mIsLoginRoomSuccess + "  isLive :" + isLive);
        ZegoLiveManager.getInstance().initZegoLive();
        mZegoLiveRoom.loginRoom(mRoomID, mHostUserName, ZegoConstants.RoomRole.Anchor,
                new IZegoLoginCompletionCallback() {
                    @Override
                    public void onLoginCompletion(int errorCode, ZegoStreamInfo[] zegoStreamInfos) {
                        Log.d(TAG, "登录房间是否成功: " + (errorCode == 0));
                        if (errorCode == 0) {
                            //测试电流声问题
                            //configAudioRecord();
                            mIsLoginRoomSuccess = true;
                            startPreview();
                            handleAudienceLoginRoomSuccessMix(zegoStreamInfos);
                        } else {
                            mIsLoginRoomSuccess = false;
                            mZegoLiveRoom.logoutRoom();
                            loginRoom();
                        }
                    }
                });
    }

    private void configAudioRecord() {
        ZegoAudioRecordConfig recordConfig = new ZegoAudioRecordConfig();
        recordConfig.sampleRate = 44100;
        recordConfig.channels = 1;
        recordConfig.mask = ZegoConstants.AudioRecordMask.Capture;

        mZegoLiveRoom.enableSelectedAudioRecord(recordConfig);

        setRecordCallback();

        createFileStream();
    }

    private void setRecordCallback() {
        // 音频录制回调监听
        mZegoLiveRoom.setZegoAudioRecordCallback(new IZegoAudioRecordCallback2() {
            @Override
            public void onAudioRecordCallback(byte[] data, int sampleRate, int channelCount, int bitDepth, int type) {

                // **** onAudioRecordCallback sampleRate:44100,channelCount:1,bitDepth:16,type:1
                Log.e("test", "**** onAudioRecordCallback sampleRate:" + sampleRate + ",channelCount:" + channelCount
                        + ",bitDepth:" + bitDepth + ",type:" + type);
                if (outputStream != null && data.length > 0) {
                    try {
                        outputStream.write(data);
                    } catch (IOException e) {
                        Log.e("test", "**** write io exception: " + e.getMessage());
                    }
                }
            }
        });
    }

    private String mSaveFilePath = "";
    private OutputStream outputStream = null;

    public void createFileStream() {
        mSaveFilePath = "/sdcard/audio_" + System.currentTimeMillis() + ".pcm";
        Log.e("test", "**** saveFile path: " + mSaveFilePath);

        try {
            if (outputStream == null && !mSaveFilePath.equals("")) {

                File file = new File(mSaveFilePath);
                if (!file.exists()) {
                    file.createNewFile();
                }

                outputStream = new FileOutputStream(file);
            }
        } catch (FileNotFoundException e) {
            Log.e("test", "*** file not found");
        } catch (IOException e) {
            Log.e("test", "*** create file exception: " + e.getMessage());
        }
    }

    protected String mMixStreamID = null;

    private void handleAudienceLoginRoomSuccessMix(ZegoStreamInfo[] zegoStreamInfos) {
        if (zegoStreamInfos != null && zegoStreamInfos.length > 0) {
            for (ZegoStreamInfo info : zegoStreamInfos) {
                mListStreamOfRoom.add(info);
            }
            for (ZegoStreamInfo info : zegoStreamInfos) {
                final HashMap<String, String> mapExtraInfo =
                        (new Gson()).fromJson(info.extraInfo, new TypeToken<HashMap<String, String>>() {
                        }.getType());

                if (mapExtraInfo != null && mapExtraInfo.size() > 0) {
                    boolean firstAnchor = Boolean.valueOf(mapExtraInfo.get(Constants.FIRST_ANCHOR));
                    String mixStreamID = String.valueOf(mapExtraInfo.get(Constants.KEY_MIX_STREAM_ID));
                    if (firstAnchor && !TextUtils.isEmpty(mixStreamID)) {
                        // 播放混流
                        //                        mMixStreamID = mixStreamID;
                        //                        startPlay(mixStreamID, 0);
                        break;
                    }
                }


            }
        }
        //        mMixStreamID = ZegoRoomUtil.getMixStreamID(mHostUserId);
        //        startPlay(mMixStreamID, 0);
        // 打印log
        LogUtil.e("MY_SELF" + ": onLoginRoom success(" + mRoomID + "), streamCounts:" + zegoStreamInfos.length);

    }


    private void startPlay(String streamID, ZegoStreamInfo streamInfo) {
        /**
         * 开始播放流.
         */
        if (TextUtils.isEmpty(streamID)) {
            return;
        }
        if (isStreamExisted(streamID)) {
            return;
        }

        if (streamID.equals(ZegoRoomUtil.getPublishStreamID(mHostUserId))) {
            return;
        }
        if (tvAudience1 == null) {
            return;
        }
        //连麦中
        isLianMai = true;
        //暂停背景音乐
        //pauseBgMediaPlayer();
        stopChorusZegoMediaPlayer();
        mLrcChorusView.setVisibility(View.GONE);
        // 设置流信息
        tvAudience1.setStreamID(streamID);
        tvAudience1.setPlayView(true);
        tvAudience1.setAuser_id(streamInfo.userID);
        tvAudience1.SetUserName("");
        tvAudience1.setVisibility(View.VISIBLE);
        tvAudience1.setUserAvatarViewGone();
        setShowVideoWindow(true, false);
        LogUtil.e(" start play stream(" + streamID + ")");
        mZegoLiveRoom.startPlayingStream(streamID, tvAudience1.getTextureView());
        mZegoLiveRoom.setViewMode(ZegoVideoViewMode.ScaleAspectFill, streamID);
        setTextureViewAlpha100();
        getAnchorUserInfo(streamID);
        if (streamID.equals(ZegoRoomUtil.getMixStreamID(mHostUserId)) && isLive) {
            if (!TextUtils.isEmpty(yq_type)) {
                if (yq_type.equals("1")) {
                    stopVideo();
                }
            }
        }
    }

    public boolean isStreamExisted(String streamID) {
        if (TextUtils.isEmpty(streamID)) {
            return true;
        }
        boolean isExisted = false;
        if (streamID.equals(tvAudience1.getStreamID())) {
            isExisted = true;
        }
        return isExisted;
    }

    @Override
    public void getYbi(String moeny) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void getRoomUser(List<RoomUserBean.DataBean> data) {
        if (start == 0) {
            Collections.reverse(data);
            userIconAdapter.setNewData(data);
        } else {
            Collections.reverse(data);
            userIconAdapter.addData(data);
        }
    }

    @Override
    public void getUserDetail(UserDetailBean.DataBean data, View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
    }


    @Override
    public void joinSuccess(SendMsgEntity sendMsgEntity) {
        String nickname = getString(R.string.xitong);
        String commentsOne = getString(R.string.welcome_to) + tvName.getText().toString() + getString(R.string.commmentsone);
        SendMsgEntity entity = new SendMsgEntity("", nickname, "", commentsOne, "2");
        LiveChatMessage liveChatMessage = new LiveChatMessage();
        liveChatMessage.setScroll(false);
        liveChatMessage.setEntity(entity);
        EventBus.getDefault().post(liveChatMessage);
        LiveChatMessage liveChatMessage2 = new LiveChatMessage();
        liveChatMessage2.setScroll(false);
        liveChatMessage2.setEntity(sendMsgEntity);
        EventBus.getDefault().post(liveChatMessage2);
        //横屏展示消息
        //        addMessage(entity, false);
        //        addMessage(sendMsgEntity, false);
    }

    private boolean isHostLogin = false;

    @Override
    public void inRoom(InRoomEntity entity) {
        if (mHostUserId.equals(entity.getUserId())) {
            //不算上主播自己
            return;
        }
        mWatchNum++;
        setWatchNum();
        //        if (entity.getUserId().equals(mHostUserId)) {
        //            isHostLogin = true;
        //            if (!isLive) {
        //                isLive = true;
        //                doBusiness();
        //            }
        //        }

        RoomUserBean.DataBean dataBean = new RoomUserBean.DataBean(entity.getUserId(), entity.getAvatar());
        boolean isExist = false;
        for (int i = 0; i < userIconAdapter.getData().size(); i++) {
            if (userIconAdapter.getData().get(i).getUserId().equals(entity.getUserId())) {
                isExist = true;
            }
        }
        if (!isExist) {
            List<RoomUserBean.DataBean> data = userIconAdapter.getData();
            data.add(dataBean);
            userIconAdapter.setNewData(data);
        }
        SendMsgEntity sendMsgEntity = new SendMsgEntity(entity.getUserId(), entity.getUsername(), entity.getAvatar(), getString(R.string.inroom), entity.getIs_manage());
        addMessage(sendMsgEntity, true);
        playDanMu(sendMsgEntity);
    }

    @Override
    public void outRoom(OutRoomEntity outRoomEntity) {
        if (mWatchNum >= 1) {
            mWatchNum--;
        }
        setWatchNum();
        SendMsgEntity sendMsgEntity = new SendMsgEntity(outRoomEntity.getUserId(), outRoomEntity.getUsername(), outRoomEntity.getAvatar(), getString(R.string.outroom), "");
        addMessage(sendMsgEntity, true);
        List<RoomUserBean.DataBean> data = userIconAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getUserId().equals(outRoomEntity.getUserId())) {
                userIconAdapter.remove(i);
            }
        }

        //观众退出直播间不再显示已发送的礼物
        if (mGiftData.size() != 0) {
            Iterator<GiftEntity> iterator = mGiftData.iterator();
            while (iterator.hasNext()) {
                if (outRoomEntity.getUserId().equals(iterator.next().getUserId())) {
                    iterator.remove();
                }
            }
        }

        if (!isLive) {
            if (outRoomEntity.getUserId().endsWith(mHostUserId)) {
                out();
                Log.e(TAG, "这是没有开播，收到Socket退出房间的命令");
            }

        } else {
            if (outRoomEntity.getUserId().endsWith(mHostUserId)) {
                stopAllStream();
                stopPublish();
                out();
                Log.e(TAG, "这是开播，收到Socket退出房间的命令");
            }
        }
    }

    @Override
    public void sendMsg(SendMsgEntity sendMsgEntity) {
        LiveChatMessage liveChatMessage = new LiveChatMessage();
        liveChatMessage.setScroll(true);
        liveChatMessage.setEntity(sendMsgEntity);
        EventBus.getDefault().post(liveChatMessage);
        addMessage(sendMsgEntity, true);
    }

    DanmuView mDanmuView;

    @Override
    public void sendChatMsg(SendChatMsgEntity sendChatMsgEntity) {
        //{"room_id":"138","user_id":141,"content":"{\"gif_number\":0,\"message_value\":\"发出了一个拼手气红包给所有的在场朋友\",\"sound_effect\":0,\"type\":3,\"voice_control_progress\":0}","type":0,"cmd":"room_chat","is_manage":1,"fd":261,"device":"web","username":"我太难了","avatar":"\/uploads\/avatar\/20200414\/7a7934319c310e068b0ed5c737417f29822.jpg"}
        if (sendChatMsgEntity.getType() == 0) {
            LiveChatMessage liveChatMessage = new LiveChatMessage();
            liveChatMessage.setScroll(true);
            liveChatMessage.setEntity(sendChatMsgEntity);
            EventBus.getDefault().post(liveChatMessage);
            addMessage(sendChatMsgEntity, true);
        } else {
            Danmu danmu = new Danmu();
            danmu.setHeaderUrl(sendChatMsgEntity.getAvatar());
            danmu.setUserName(sendChatMsgEntity.getUsername());
            danmu.setInfo(sendChatMsgEntity.getContent());
            mDanmuView.add(danmu);
            Log.e(TAG, sendChatMsgEntity.getContent());
        }
    }

    public void playDanMu(SendMsgEntity sendMsgEntity) {
        if (mDanmuView == null) {
            return;
        }
        Danmu danmu = new Danmu();
        danmu.setHeaderUrl(sendMsgEntity.getAvatar());
        danmu.setUserName(sendMsgEntity.getUsername());
        danmu.setInfo(sendMsgEntity.getContent());
        mDanmuView.add(danmu);
    }

    List<GiftEntity> mGiftData = new ArrayList<>();

    @Override
    public void sendGift(GiftEntity giftEntity) {
        showZan();
        String giftImage;
        if (giftEntity.getGiftStyle().equals("0")) {
            giftImage = giftEntity.getGiftImg();
        } else if (giftEntity.getGiftStyle().equals("1")) {
            mGiftData.add(giftEntity);
            giftImage = giftEntity.getGiftSamll();
            showAnim(mGiftData.get(0));
        } else {
            mGiftData.add(giftEntity);
            giftImage = giftEntity.getGiftSamll();
            showAnim(mGiftData.get(0));
        }
        GiftModel giftModel = new GiftModel();
        int giftNum = Integer.parseInt(giftEntity.getGiftNum());
        giftModel.setGiftId(giftEntity.getGiftId()).setGiftName(getString(R.string.zengsong) + giftEntity.getGiftName()).setGiftCount(0).setGiftPic(giftImage)
                .setSendUserId(giftEntity.getUserId()).setSendUserName(giftEntity.getUsername()).setSendUserPic(giftEntity.getAvatar()).setSendGiftTime(System.currentTimeMillis())
                .setCurrentStart(true).setVolleyNums(giftNum);
        giftModel.setHitCombo(giftNum);

        giftControl2.loadGift(giftModel, true);
        giftControl2.setEndGiftCountListener(new GiftControl.EndGiftCountListener() {

            @Override
            public void dismiss(int count, String giftId, String giftPic) {

            }
        });

    }

    private boolean isShow = false;

    private void showAnim(final GiftEntity giftEntity) {
        if (!isShow) {
            //            Log.e(TAG, "showAnim: " + data.get(0).getGiftStyle());
            if (giftEntity.getGiftStyle().equals("1")) {
                isShow = true;
                CustomPoPupAnim.loadSvga(svgaAnim, giftEntity.getGiftImg(), new CustomPoPupAnim.AnimListener() {
                    @Override
                    public void onFinish() {
                        isShow = false;
                        if (mGiftData.size() > 0) {
                            mGiftData.remove(0);
                        }
                        ifShowAnim();

                    }
                });
                if (!TextUtils.isEmpty(giftEntity.getGiftSound())) {
                    playGiftSound(giftEntity.getGiftSound());
                }
            } else {
                isShow = true;
                CustomPoPupAnim.down(giftEntity.getGiftImg(), new CustomPoPupAnim.DownGiftPathListener() {
                    @Override
                    public void path(String path) {
                        try {
                            GifDrawable gifDrawable = new GifDrawable(path);
                            gifDrawable.setLoopCount(1);
                            giftAnim.setVisibility(View.VISIBLE);
                            giftAnim.setImageDrawable(gifDrawable);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    giftAnim.setVisibility(View.INVISIBLE);
                                    isShow = false;
                                    if (mGiftData.size() > 0) {
                                        mGiftData.remove(0);
                                    }
                                    ifShowAnim();
                                }
                            }, gifDrawable.getDuration());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        }

    }

    public void ifShowAnim() {
        if (mGiftData.size() > 0) {
            showAnim(mGiftData.get(0));
        }
    }

    /**
     * 播放红包背景音效
     */
    private void playRedPacketSound(int resId) {
        try {
            if (mGiftMediaPlayer == null) {
                mGiftMediaPlayer = new MediaPlayer();
            } else {
                mGiftMediaPlayer.stop();
                mGiftMediaPlayer.reset();
            }
            mGiftMediaPlayer.setVolume(0.5f, 0.5f);
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

            AssetFileDescriptor file = getResources().openRawResourceFd(resId);
            mGiftMediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
            mGiftMediaPlayer.setLooping(false);
            mGiftMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "onPrepared()");
                    mGiftMediaPlayer.start();
                }
            });
            mGiftMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.d(TAG, "onCompletion()");
                }
            });
            mGiftMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放礼物音效
     */
    private void playGiftSound(String path) {
        try {
            if (mGiftMediaPlayer == null) {
                mGiftMediaPlayer = new MediaPlayer();
            } else {
                mGiftMediaPlayer.stop();
                mGiftMediaPlayer.reset();
            }
            mGiftMediaPlayer.setDataSource(path);
            mGiftMediaPlayer.setLooping(false);
            mGiftMediaPlayer.setVolume(0.5f, 0.5f);
            mGiftMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "onPrepared()");
                    mGiftMediaPlayer.start();
                }
            });
            mGiftMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.d(TAG, "onCompletion()");
                }
            });
            mGiftMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void releaseGiftMediaPlayer() {
        if (mGiftMediaPlayer != null) {
            mGiftMediaPlayer.stop();
            mGiftMediaPlayer.release();
            mGiftMediaPlayer = null;
        }
    }

    @Override
    public void currency(CurrencyEntity currencyEntity) {
    }

    @Override
    public void bannedLive(ManageEntity manageEntity) {
        SendMsgEntity sendMsgEntity = new SendMsgEntity("", manageEntity.getUsername(), "", manageEntity.getContent(), "");
        addMessage(sendMsgEntity, true);
        LiveChatMessage liveChatMessage = new LiveChatMessage();
        liveChatMessage.setScroll(true);
        liveChatMessage.setEntity(sendMsgEntity);
        EventBus.getDefault().post(liveChatMessage);
    }

    @Override
    public void kickLive(KickEntity kickEntity) {
        SendMsgEntity sendMsgEntity = new SendMsgEntity("", kickEntity.getUsername(), "", kickEntity.getContent(), "");
        addMessage(sendMsgEntity, true);
        LiveChatMessage liveChatMessage = new LiveChatMessage();
        liveChatMessage.setScroll(true);
        liveChatMessage.setEntity(sendMsgEntity);
        EventBus.getDefault().post(liveChatMessage);
        if (mWatchNum >= 1) {
            mWatchNum--;
        }
        setWatchNum();
        iconRemove(kickEntity.getUserId());

    }

    @Override
    public void startLive(StartLiveEntity startLiveEntity) {
        Log.d(TAG, "startLive()");
        mUrl = startLiveEntity.getPlay_url();

        //if (!isLogin) {
        //    doBusiness();
        //} else {
        //                if(LoginUserManager.getLiveVideoSource().equals("app")) {
        //                    mMixStreamID = ZegoRoomUtil.getMixStreamID(mHostUserId);
        //                    startPlay(mMixStreamID, 0);
        //                }
        //                else
        //                {
        //
        //                }
        //}
        //        mMixStreamID = ZegoRoomUtil.getMixStreamID(mHostUserId);

    }


    @Override
    public void stopLive(StopLiveEntity stopLiveEntity) {
        //        out();
        //        ToastUtil.showShort(this, getString(R.string.anchor_exit));
        //        //退出直播间
        //        CommonUtil.outroom(this, roomId, tourId);
        //        finish();
    }

    @Override
    public void invite9r(final InviteEntity msg) {
    }

    @Override
    public void inviteAgree(InviteAgreedEntity inviteAgreedEntity) {

    }

    @Override
    public void inviteRefused(InviteRefusedEntity inviteRefusedEntity) {
        //        //邀请的人拒绝连麦
        //        ToastUtil.showShort(this, inviteRefusedEntity.getContent());

    }

    @Override
    public void inviteNotice(InviteNoticeEntity inviteNoticeEntity) {
        //收到连麦的流通知，如果不是自己的就初始化播放器播放
    }

    private void initInvite(String rtmp, String auserid, String lmid, String lala) {

        //收到观众连麦成功的推流
        if (lala.equals("1")) {
            if (TextUtils.isEmpty(url3)) {
                url3 = rtmp;
                aid3 = auserid;
                lmid3 = lmid;

            }
        } else {
            if (TextUtils.isEmpty(url1)) {
                url1 = rtmp;
                aid1 = auserid;
                lmid1 = lmid;
            } else if (TextUtils.isEmpty(url2)) {
                url2 = rtmp;
                aid2 = auserid;
                lmid2 = lmid;

            } else if (TextUtils.isEmpty(url3)) {
                url3 = rtmp;
                aid3 = auserid;
                lmid3 = lmid;
            }
        }
    }

    @Override
    public void closeInvite(CloseEntity closeEntity) {
        Log.d(TAG, "closeInvite()");
        //有人连麦断开，移除画面
        ToastUtil.showShort(this, closeEntity.getContent());
        String auserid = closeEntity.getAuser_id();
        if (auserid.equals(aid1)) {
            url1 = "";
            aid1 = "";
        } else if (auserid.equals(aid2)) {
            url2 = "";
            aid2 = "";
        } else if (auserid.equals(aid3)) {
            url3 = "";
            aid3 = "";
        }
        if (tvAudience1 != null && !tvAudience1.isFree() && tvAudience1.getAuser_id().equals(auserid)) {
            tvAudience1.setFree();
            isLianMai = false;
            stopChorusMediaPlayer();
            setTextureViewAlpha60();
        }
    }

    /**
     * 直播间连麦全部断开
     */
    @Override
    public void closeAllInvite(CloseAllEntity closeAllEntity) {
        Log.d(TAG, "closeAllInvite()");
        isLianMai = false;
        setShowVideoWindow(false, false);
        //恢复背景音乐
        //resumeBgMediaPlayer();
        //关闭合唱
        stopChorusMediaPlayer();

        if (!TextUtils.isEmpty(mMultiRoomStreamId)) {
            //多房间断开连麦
            mZegoLiveRoom.stopPlayingStream(mMultiRoomStreamId);
            tvAudience1.setFree();
            mMultiRoomStreamId = "";
            mMultiRoomId = "";
        }
        setTextureViewAlpha60();
    }

    @Override
    public void err(ErrorEntity errorEntity) {
        if (errorEntity.getContent().equals("已退出该直播间")) {
            CommonUtil.inroom(BaseApplication.getApp(), roomId);
            return;
        }
        ToastUtil.showShort(this, errorEntity.getContent());
    }

    @Override
    public void addMsg(SendMsgEntity sendMsgEntity) {
        addMessage(sendMsgEntity, true);
        LiveChatMessage liveChatMessage = new LiveChatMessage();
        liveChatMessage.setScroll(true);
        liveChatMessage.setEntity(sendMsgEntity);
        EventBus.getDefault().post(liveChatMessage);
    }

    @Override
    public void showLight() {
    }

    @Override
    public void clearAnim() {
    }

    @Override
    public void hiddenTree() {
    }

    @Override
    public void hiddenLight() {
    }

    @Override
    public void showTree() {
    }

    @Override
    public void showCake() {
    }

    @Override
    public void candle(String candleId) {
    }

    //收到红包
    @Override
    public void red(RedEntity redEntity) {
        redPacketsView.setVisibility(View.VISIBLE);
        ivCloseRed.setVisibility(View.VISIBLE);
        playRedPacketVideoRaw();
        startRedRain(redEntity);
    }

    /**
     * 开始下红包雨
     */
    private void startRedRain(final RedEntity redEntity) {
        if (!isRedRain) {
            redPacketsView.startRain();
            isRedRain = true;
        }
        handler.removeMessages(LivePushHandler.stopRain);
        handler.sendEmptyMessageDelayed(LivePushHandler.stopRain, 6 * 1000);

    }

    private void playRedPacketVideoRaw() {
        try {
            if (mSivRedPackets == null) {
                return;
            }
            if (mShowAllView.getVisibility() == View.VISIBLE) {
                mUpVideoViewShowing = true;
            } else {
                mUpVideoViewShowing = false;
            }
            mSivRedPackets.setVisibility(View.VISIBLE);
            SVGAParser parser = new SVGAParser(BaseApplication.getApp());
            parser.decodeFromAssets("hongbao.svga", new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    Log.e("TAG", "onComplete: ");
                    SVGADrawable drawable = new SVGADrawable(videoItem);
                    mSivRedPackets.setImageDrawable(drawable);
                    mSivRedPackets.startAnimation();
                }

                @Override
                public void onError() {
                    Log.e("TAG", "onError: ");
                }
            });
            //播放红包背景音效
            playRedPacketSound(R.raw.redpacket);
        } catch (Exception e) {
            playRedPacketVideoRaw();
        }
    }

    /**
     * 停止下红包雨
     */
    private void stopRedRain() {
        if (mSivRedPackets == null) {
            return;
        }
        if (mUpVideoViewShowing) {
            mShowAllView.setVisibility(View.VISIBLE);
        }
        redPacketsView.stopRainNow();
        redPacketsView.setVisibility(View.GONE);
        ivCloseRed.setVisibility(View.INVISIBLE);
        mSivRedPackets.setVisibility(View.GONE);
        releaseGiftMediaPlayer();
    }

    @Override
    public void redCurrrency(RedCurrencyEntity currencyEntity) {
    }

    //领取红包返回
    @Override
    public void receiveRed(ReceiveRedEntity receiveRedEntity) {
    }

    //收到观众发的主播红包
    @Override
    public void receiveleadRed(ReceiveLeadRedEntity entity) {
        SendMsgEntity sendMsgEntity = new SendMsgEntity(entity.getUser_id(), entity.getUsername(), entity.getAvatar(), String.format(getString(R.string.lead_red_info), entity.getAmount()), "");
        addMessage(sendMsgEntity, true);
    }

    @Override
    public void canMai() {
        canMai = true;
    }

    @Override
    public void stopRain() {
        stopRedRain();
        isRedRain = false;
    }

    @Override
    public void showEntertainLuck(EntertainEntity entertainEntity) {
///娱乐活动-轮盘显示
        mRlShowLuck.setVisibility(View.VISIBLE);
        mRlLuckP.setVisibility(View.VISIBLE);
        mRlSvga.setVisibility(View.GONE);
        mRlNameLian.setVisibility(View.GONE);
        mTvWaitAudience.setVisibility(View.VISIBLE);
    }

    @Override
    public void showInduction(InductionEntity inductionEntity) {
//显示自我介绍
        mRlShowInduction.setVisibility(View.VISIBLE);
        mRlShowLuck.setVisibility(View.GONE);
        mRlLuckP.setVisibility(View.GONE);
    }

    @Override
    public void showExtractAudience(ExtractAudienceEntity extractAudienceEntity) {
//观众
        mRlShowLuck.setVisibility(View.VISIBLE);
        mRlLuckP.setVisibility(View.VISIBLE);
        mRlSvga.setVisibility(View.GONE);
        mRlNameLian.setVisibility(View.GONE);
        mTvWaitAudience.setVisibility(View.VISIBLE);
        mTvLuckResult.setText("");
        mSvgaExtractAudience.setLoops(1);
        SVGAParser parser = new SVGAParser(BaseApplication.getApp());
        parser.decodeFromAssets("activity_get.svga", new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                SVGADrawable drawable = new SVGADrawable(videoItem);
                mSvgaExtractAudience.setImageDrawable(drawable);
                mSvgaExtractAudience.startAnimation();
            }

            @Override
            public void onError() {
                Log.e("TAG", "onError: ");
            }
        });
        mSvgaExtractAudience.setCallback(new SVGACallback() {
            @Override
            public void onPause() {

            }

            @Override
            public void onFinished() {
                mRlNameLian.setVisibility(View.VISIBLE);
                mTvWaitAudience.setVisibility(View.GONE);
                mTvExtractName.setText(extractAudienceEntity.getUsername());
            }

            @Override
            public void onRepeat() {

            }

            @Override
            public void onStep(int i, double v) {

            }
        });
    }

    @Override
    public void showCloseInduction(CloseInductionEntity closeInductionEntity) {
        mRlShowLuck.setVisibility(View.GONE);
        mRlShowInduction.setVisibility(View.GONE);
    }

    @Override
    public void FeedbackLucky(LuckyAudienceEntity luckyAudienceEntity) {
//被抽到的幸运观众反馈
        mRlSvga.setVisibility(View.VISIBLE);
        mSvgaLuckResult.setVisibility(View.VISIBLE);
        mRlLuckP.setVisibility(View.GONE);
        mSvgaLuckResult.setLoops(1);
        SVGAParser parser = new SVGAParser(BaseApplication.getApp());
        parser.decodeFromAssets("luck.svga", new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                SVGADrawable drawable = new SVGADrawable(videoItem);
                mSvgaLuckResult.setImageDrawable(drawable);
                mSvgaLuckResult.startAnimation();
            }

            @Override
            public void onError() {
                Log.e("TAG", "onError: ");
            }
        });
        mSvgaLuckResult.setCallback(new SVGACallback() {
            @Override
            public void onPause() {

            }

            @Override
            public void onFinished() {
                //被抽到的幸运观众反馈
                mTvLuckResult.setText(luckyAudienceEntity.getContent());
            }

            @Override
            public void onRepeat() {

            }

            @Override
            public void onStep(int i, double v) {

            }
        });
    }

    @Override
    public void FiveAudience(RandomLuckyEntity randomLuckyEntity) {
        List<RandomLuckyEntity.UsersBean> usersBeansList = new ArrayList<>();
        if (randomLuckyEntity.getUsers() != null)
            usersBeansList.addAll(randomLuckyEntity.getUsers());
        usersBeansList.addAll(randomLuckyEntity.getUsers());
        //数据填充适配器
        AudienceListAdapter audienceListAdapter = new AudienceListAdapter(R.layout.adapter_induction, usersBeansList);
        LinearLayoutManager a = new LinearLayoutManager(this);
        a.setOrientation(LinearLayoutManager.VERTICAL);
        mRlAudienceList.setLayoutManager(a);
        mRlAudienceList.setAdapter(audienceListAdapter);
    }

    @Override
    public void lianFeedBack(LianFeedEntity lianFeedEntity) {

    }

    private String playerUrl, cover;
    private String music;
    private String svgaUrl;

    @Override
    public void showTemplate(SeeTemplateEntity seeTemplateEntity) {
        // 0 图片 1 视频
        quitTemplate();
        if (TextUtils.equals(seeTemplateEntity.getType(), "0")) {
            ivTemplate.setVisibility(View.VISIBLE);
            ivTemplateBg.setVisibility(View.VISIBLE);
            mBaPinAll.setVisibility(View.VISIBLE);
            ivTemplate.setAlpha(0f);
            GlideUtil.setImage(ivTemplateBg, this, seeTemplateEntity.getUri());
            Glide.with(this).load(seeTemplateEntity.getUri())
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (ivTemplate != null) {
                                ivTemplate.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(ivTemplate, "alpha", 0.0f, 1.0f);
                                        ObjectAnimator rotationAnimation = ObjectAnimator.ofFloat(ivTemplate, "rotation", 0f, 720f);
                                        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(ivTemplate, "scaleX", 0f, 1f);
                                        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(ivTemplate, "scaleY", 0f, 1f);
                                        AnimatorSet animatorSet = new AnimatorSet();
                                        animatorSet.play(alphaAnimation).with(rotationAnimation).with(scaleXAnimation).with(scaleYAnimation);
                                        animatorSet.setDuration(1000);
                                        animatorSet.start();
                                        animatorSet.addListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                super.onAnimationEnd(animation);
                                            }
                                        });
                                    }
                                }, 500);
                            }
                            return false;
                        }
                    }).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .into(ivTemplate);
        } else if (TextUtils.equals(seeTemplateEntity.getType(), "1")) {
            playVideo(seeTemplateEntity, 0.5f);
        } else {
            ivTemplate.setVisibility(View.INVISIBLE);
            ivTemplateBg.setVisibility(View.INVISIBLE);
            mBaPinAll.setVisibility(View.INVISIBLE);
            svgaUrl = seeTemplateEntity.getUri();
        }
    }

    private void playVideo(SeeTemplateEntity seeTemplateEntity, Float alpha) {
        ivTemplate.setVisibility(View.VISIBLE);
        ivTemplateBg.setVisibility(View.VISIBLE);
        mBaPinAll.setVisibility(View.VISIBLE);
        playerUrl = seeTemplateEntity.getUri();
        cover = seeTemplateEntity.getCover();
        GlideUtil.setImage(ivTemplate, this, cover);
        GlideUtil.setImage(ivTemplateBg, this, cover);
    }

    @Override
    public void quitTemplate() {
    }

    private void stopVideo() {
        ivTemplate.setVisibility(View.INVISIBLE);
        ivTemplateBg.setVisibility(View.INVISIBLE);
        mBaPinAll.setVisibility(View.INVISIBLE);
    }

    private long endTime = 0;

    private void exit() {
        if (System.currentTimeMillis() - endTime > 2000) {
            Toast.makeText(this, getString(R.string.exit_info), Toast.LENGTH_SHORT).show();
            endTime = System.currentTimeMillis();
        } else {
            if (mIsPublishing || isLive) {
                LogUtils.d("onBackPressed...exit()");
                stopAllStream();
                //  mEnableFrontCam = true;
                mZegoLiveRoom.setFrontCam(true);

                ZegoAudioProcessing.enableVirtualStereo(false, 0);

                ZegoCamera.setCamFocusMode(ZegoCameraFocusMode.CONTINUOUS_VIDEO, ZegoConstants.PublishChannelIndex.MAIN);

                ZegoCamera.setCamExposureMode(ZegoCameraExposureMode.AUTO, ZegoConstants.PublishChannelIndex.MAIN);

                mZegoLiveRoom.enableSpeaker(true);

                ZegoSoundLevelMonitor.getInstance().stop();
                finish();
            } else {
                mZegoLiveRoom.enableSpeaker(true);
                stopAllStream();
                finish();
            }
            Intent liveService = new Intent(this, LiveService.class);
            stopService(liveService);
            AppManager.getAppManager().AppExit(this);
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            stopMediaPlayer();
        }
    }


    protected void stopPlayStream() {
        quitTemplate();
        stopAllStream();
    }

    protected void stopAllStream() {
        if (tvAudience1.isPublishView()) {
            stopPublish();
        } else if (tvAudience1.isPlayView()) {
            stopPlay(tvAudience1.getStreamID());
        }
        // 释放view
        tvAudience1.setFree();
        stopChorusMediaPlayer();
        isLianMai = false;
        setTextureViewAlpha60();
    }

    private void stopPlay(String streamID) {
        if (!TextUtils.isEmpty(streamID)) {
            // 临时处理
            handlePlayStop(1, streamID);
            // 输出播放状态
            LogUtil.e(" MY_SELF : stop play stream(" + streamID + ")");
            mZegoLiveRoom.stopPlayingStream(streamID);
        }
    }

    public void hideKeyborad() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mIsPopup) {
            //关闭键盘
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void scrollMyListViewToBottom() {
        if (messageAdapter.getItemCount() == 0) {
            return;
        }
        rvMesssage.post(new Runnable() {
            @Override
            public void run() {
                rvMesssage.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                messageAdapter.notifyDataSetChanged();
            }
        });
    }


    private void setWatchNum() {
        if (mWatchNum >= 0) {
            mWatchUserCount.setText(NumShow.formatLiveNum(mWatchNum + "", false, this));
        } else {
            mWatchNum = 0;
            mWatchUserCount.setText(NumShow.formatLiveNum(mWatchNum + "", false, this));
        }
    }

    private void iconRemove(String userId) {
        List<RoomUserBean.DataBean> data = userIconAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getUserId().equals(userId)) {
                userIconAdapter.remove(i);
            }
        }
    }

    private void setLayout() {
        //        bottomLayout2.setVisibility(View.VISIBLE);
        tvName.setVisibility(View.VISIBLE);
        if (isAnchor) {
            ivSet.setVisibility(View.GONE);
        }
        if (!isAnchor) {
            ivLove.setVisibility(View.GONE);
        }
        ivShare.setVisibility(View.VISIBLE);
        ivAudience.setVisibility(View.GONE);
        mWatchUserRecyclerView.setVisibility(View.VISIBLE);
        rvMesssage.setVisibility(View.VISIBLE);
    }


    @Override
    public void tourIn(TourInEntity tourInEntity) {
        tourId = tourInEntity.getTourist_id();
    }

    @Override
    public void hardFailure(HardwareFailureEntity tourInEntity) {
        //        out();
        Log.e(TAG, "硬件初始化失败的socket命令");
    }

    private AnimationUtils animationUtils;
    // 礼物池消息显示
    private boolean isFrist = true;
    private boolean startAnimation = false;

    @Override
    public void bapin(final BaScreenEntity baScreenEntity) {
        // 0 图片 1 视频
        if (TextUtils.equals(baScreenEntity.getType(), "0")) {
            ivTemplate.setVisibility(View.VISIBLE);
            ivTemplateBg.setVisibility(View.VISIBLE);
            mBaPinAll.setVisibility(View.VISIBLE);
            ivTemplate.setAlpha(0f);
            GlideUtil.setImage(ivTemplateBg, this, baScreenEntity.getUri());
            Glide.with(this).asBitmap().load(baScreenEntity.getUri()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource,
                                                    @Nullable Transition<? super Bitmap> transition) {
                            int width = resource.getWidth();
                            int height = resource.getHeight();
                            if (width > height) {
                                //横屏
                                ivTemplate.setScaleType(ImageView.ScaleType.FIT_XY);
                            } else {
                                //竖屏
                                ivTemplate.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            }
                            ivTemplate.setImageBitmap(resource);
                            if (ivTemplate != null) {
                                ivTemplate.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(ivTemplate, "alpha", 0.0f, 1.0f);
                                        ObjectAnimator rotationAnimation = ObjectAnimator.ofFloat(ivTemplate, "rotation", 0f, 720f);
                                        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(ivTemplate, "scaleX", 0f, 1f);
                                        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(ivTemplate, "scaleY", 0f, 1f);
                                        AnimatorSet animatorSet = new AnimatorSet();
                                        animatorSet.play(alphaAnimation).with(rotationAnimation).with(scaleXAnimation).with(scaleYAnimation);
                                        animatorSet.setDuration(1000);
                                        animatorSet.start();
                                        animatorSet.addListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                super.onAnimationEnd(animation);
                                            }
                                        });
                                    }
                                }, 500);
                            }
                            showBaScreenTimer(baScreenEntity);
                        }
                    });
        } else if (TextUtils.equals(baScreenEntity.getType(), "1")) {
            ivTemplate.setVisibility(View.INVISIBLE);
            ivTemplateBg.setVisibility(View.INVISIBLE);
            mBaPinAll.setVisibility(View.INVISIBLE);
            playerUrl = baScreenEntity.getUri();
            showBaScreenTimer(baScreenEntity);
        }
        String text = baScreenEntity.getText();
        startHengFu(text);
    }

    public void showBaScreenTimer(BaScreenEntity baScreenEntity) {
        if (mTvBaScreenTimer == null || baScreenEntity == null) {
            return;
        }
        final long time = Long.parseLong(baScreenEntity.getTime());
        Log.e(TAG, "bapin: " + (time * 1000));
        handler.sendEmptyMessageDelayed(LivePushHandler.stopBapin, time * 1000);
        //显示霸屏倒计时
        mIsBaping = true;
        mLlBaScreenTimer.setVisibility(View.VISIBLE);
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(time + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) {
                        return time - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        if (mLlBaScreenTimer == null) {
                            return;
                        }
                        if (aLong == 0) {
                            mLlBaScreenTimer.setVisibility(View.GONE);
                        } else {
                            mTvBaScreenTimer.setText(String.valueOf(aLong));
                            CustomPoPupAnim.startScaleAnimation(mTvBaScreenTimer);
                        }
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }
                });
    }

    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void unDispose() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }

    private void startHengFu(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (isFrist) {
                animationUtils = new AnimationUtils(llSubtitles, llwidth, screenWidth, tvSubtitles, BaseApplication.getApp());
                animationUtils.startAnimation();
                isFrist = false;
            }
            animationUtils.addData(text);
            //监听动画是否在播放
            animationUtils.setAnimationListener(new AnimationUtils.AnimationListener() {
                @Override
                public void showCompelete() {
                    startAnimation = true;
                }
            });
            //播放完才可以 播放其他动画。
            if (startAnimation) {
                animationUtils.startAnimation();
                startAnimation = false;
            }
        }
    }

    @Override
    public void stopBapin() {
        if (ivTemplate.getVisibility() == View.VISIBLE) {
            ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(ivTemplate, "alpha", 1f, 0.0f);
            ObjectAnimator rotationAnimation =
                    ObjectAnimator.ofFloat(ivTemplate, "rotation", 720f, 0f);
            ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(ivTemplate, "scaleX", 1f, 0f);
            ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(ivTemplate, "scaleY", 1f, 0f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(alphaAnimation).with(rotationAnimation).with(scaleXAnimation).with(scaleYAnimation);
            animatorSet.setDuration(1500);
            animatorSet.start();
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ivTemplate.setVisibility(View.GONE);
                    ivTemplateBg.setVisibility(View.GONE);
                    mBaPinAll.setVisibility(View.GONE);
                }
            });
        }
        mTvBaScreenTimer.clearAnimation();
        mTvBaScreenTimer.setVisibility(View.GONE);
        mIsBaping = false;
    }

    @Override
    public void showStar() {
        //clearAnim();
        //handler.sendEmptyMessageDelayed(LivePushHandler.showTree, 99000);
        //ivStar.setVisibility(View.VISIBLE);
        //GifDrawable gifFromResDrawable = null;
        //try {
        //    gifFromResDrawable = new GifDrawable(getResources(), R.drawable.star_xin_heng);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        //ivStar.setImageDrawable(gifFromResDrawable);


    }

    @Override
    public void showVisibleCake() {
    }

    @Override
    public void stopLight() {
    }

    @Override
    public void hiddenTags(boolean isMe) {
    }

    @Override
    public void showTags(String pid) {
    }

    @Override
    public void stopUp() {
    }

    /**
     * 修改房间主题和封面回调
     */
    @Override
    public void updateRoom(UpdateRoomEntity entity) {
        mTvPartySubject.setText(entity.getTitle());
        if (entity.getCover().endsWith("mp4")) {
            //主题背景图为动态视频
            showDynamicBg(entity.getCover());
        } else {
            //主题背景图为静态图片
            showStaticBg(entity.getCover());
        }
    }

    /**
     * 显示静态主题背景
     */
    private void showStaticBg(String imgUrl) {
        if (mLivePicImage == null) {
            return;
        }

        mLivePicImage.setVisibility(View.VISIBLE);
        mVideoViewBg.setVisibility(View.GONE);
        mVideoViewBg.stopPlayback();
        try {
            Glide.with(this)
                    .applyDefaultRequestOptions(new RequestOptions().placeholder(R.mipmap.bg).diskCacheStrategy(DiskCacheStrategy.NONE))
                    .load(imgUrl)
                    .into(mLivePicImage);
        } catch (Exception e) {

        }
    }

    /**
     * 显示动态主题背景
     */
    private void showDynamicBg(String videoUrl) {
        try {
            if (mVideoViewBg == null) {
                return;
            }
            mVideoViewBg.setVisibility(View.VISIBLE);
            mLivePicImage.setVisibility(View.GONE);
            //mVideoViewBg.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bg));
            mVideoViewBg.setVideoPath(Uri.parse(videoUrl).toString());
            mVideoViewBg.start();
            mVideoViewBg.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mVideoViewBg == null) {
                        return;
                    }
                    mVideoViewBg.start();
                }
            });
        } catch (Exception e) {
            showDynamicBg(videoUrl);
        }
    }

    @Override
    public void onWifiTo4G() throws URISyntaxException {
    }

    @Override
    public void on4GToWifi() throws URISyntaxException {

    }

    @Override
    public void onNetDisconnected() {

    }

    private boolean isOut;

    private void out() {
        if (!isOut) {
            ToastUtil.showShort(this, getString(R.string.anchor_exit));
            //退出直播间
            CommonUtil.outroom(this, roomId, tourId);
            finish();
        }
        isOut = true;
    }

    //什么时候需要退出这个界面

    // 1 用户没有开播的时候进入这个界面 主播退出 机顶盒需要退出开播界面

    // 2 主播开播失败服务器代码错误需要退出开播界面 机顶盒需要退出开播界面

    // 3 主播退出直播/主播异常断开直播  机顶盒需要退出开播界面

    public void startBgMediaPlayer(final String path, final boolean repeat) {
        //。。。。。。
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mBgZegoMediaPlayer == null) {
                    mBgZegoMediaPlayer = new ZegoMediaPlayer();
                    mBgZegoMediaPlayer.init(ZegoMediaPlayer.PlayerTypeAux, ZegoMediaPlayer.PlayerIndex.First);
                }
                mBgZegoMediaPlayer.start(path, repeat);
                mBgZegoMediaPlayer.setVolume(100);
            }
        }, 500);
    }

    public void pauseBgMediaPlayer() {
        if (mBgZegoMediaPlayer != null) {
            mBgZegoMediaPlayer.pause();
        }
    }

    public void resumeBgMediaPlayer() {
        if (mBgZegoMediaPlayer != null)
            mBgZegoMediaPlayer.resume();
    }

    public void stopBgMediaPlayer() {
        if (mBgZegoMediaPlayer != null)
            mBgZegoMediaPlayer.stop();
    }

    public void releaseBgMediaPlayer() {
        if (mBgZegoMediaPlayer != null) {
            mBgZegoMediaPlayer.setCallback(null);
            mBgZegoMediaPlayer.uninit();
            mBgZegoMediaPlayer = null;
        }
    }

    public void pauseChorusZegoMediaPlayer() {
        if (mChorusZegoMediaPlayer != null)
            mChorusZegoMediaPlayer.pause();
    }

    public void resumeChorusZegoMediaPlayer() {
        if (mChorusZegoMediaPlayer != null)
            mChorusZegoMediaPlayer.resume();
    }

    public void stopChorusZegoMediaPlayer() {
        if (mChorusZegoMediaPlayer != null)
            mChorusZegoMediaPlayer.stop();
    }

    public void releaseChorusZegoMediaPlayer() {
        if (mChorusZegoMediaPlayer != null) {
            mChorusZegoMediaPlayer.setCallback(null);
            mChorusZegoMediaPlayer.uninit();
            mChorusZegoMediaPlayer = null;
        }
    }

    public void stopMediaPlayer() {
        if (mEffectsMediaPlayer != null) {
            mEffectsMediaPlayer.stop();
            mEffectsMediaPlayer.release();
            mEffectsMediaPlayer = null;
        }
    }

    /**
     * 获取在线拉拉星
     */
    public void listLalaOnline() {
        LalaInteractor.listLalaOnline(TAG, new LalaInteractor.CallBack() {
            @Override
            public void onFailure(@Nullable String msg) {

            }

            @Override
            public void onSuccess(@Nullable String msg) {
                LalaOnlineBean lalaOnlineBean = new Gson().fromJson(msg, LalaOnlineBean.class);
                if (lalaOnlineBean == null) {
                    return;
                }
                List<LalaOnlineBean.DataBean> data = lalaOnlineBean.getData();
                if (data != null && data.size() > 5) {
                    data = data.subList(0, 5);
                }
                lalaOnlineAdapter.setNewData(data);
            }
        });
    }

    /**
     * 发送socket，通知对方歌曲已下载完毕
     */
    @Override
    public void downloadSongSuccess(String userId, String userName, String songCode, String lrcUrl,
                                    String lrcPath, String songPath) {
        Log.e("LLhon", "发送下载完成的消息");
        if (titleBack == null) {
            return;
        }
        //{"cmd":"private_message","lrc_path":"/storage/emulated/0/qingqing/3q8oFJoIbKrs%3D\u0026","songcode":"ToTFtzoiBNZ01mXKP5ZsvQ\u003d\u003d","type":4,"user_id":"-2","user_name":"-2"}
        if (mChorusType != 1) {
            //合唱类型，这条消息将发送到单一合唱目标观众端，用于通知对方歌曲已下载完成
            //LiveSocketUtil.sendChorusDownLoadSuccess(this, userId, userName, songCode,
            //        lrcUrl, roomId);
            //合唱，这条消息将发送到直播间内的所有观众端，用于显示歌词
            //观众端不显示歌词
            PublicMessageSocket socket = new PublicMessageSocket("", "", 0,
                    lrcUrl, roomId);
            socket.setChorusType(0);
            LiveSocketUtil.sendPublicMessage(this, socket);
        } else {

        }

        isDownLoadSuccess = true;
        mLrcUrl = lrcUrl;
        mMusicPath = songPath;

        showChorusCountDown(mLrcUrl, mMusicPath);
        if (mDownChorusMusicDialog != null) {
            mDownChorusMusicDialog.dismiss();
        }

       /* if (isReceiveDownLoadSuccess) {
            //对方也已经下载完歌曲
            showChorusCountDown(mLrcPath, mMusicPath);
            if (mDownChorusMusicDialog != null) {
                mDownChorusMusicDialog.dismiss();
            }
        } else {
            //对方还未下载完歌曲
            if (mDownChorusMusicDialog != null) {
                mDownChorusMusicDialog.setProgressText("等待对方缓冲歌曲中...");
            }
            if (mChorusProgressTimer != null) {
                mChorusProgressTimer.cancel();
            }
            mChorusProgressTimer = new CountDownTimer(30 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    if (isFinishing() || isDestroyed() || mDownChorusMusicDialog == null) {
                        return;
                    }
                    LogUtils.d("onFinish");
                    mDownChorusMusicDialog.dismiss();
                    resetChorusStates();
                    //发送消息到连麦观众端通知取消合唱
                    LiveSocketUtil.sendCancelChorus(LivePlayActivity.this,
                            mChorusUserId, mChorusUserName, "", "", roomId);
                }
            }.start();
        }*/
    }

    @Override
    public void downloadMp3BcSuccess(String mp3bcPath) {
        mMp3BcPath = mp3bcPath;
    }

    /**
     * 歌曲下载进度回调
     */
    @Override
    public void downloadProgress(int progress) {
        if (titleBack == null) {
            return;
        }
        if (mDownChorusMusicDialog == null) {
            mDownChorusMusicDialog = new DownChorusMusicDialog();
        }
        if (!mDownChorusMusicDialog.isShow()) {
            mDownChorusMusicDialog.show(getSupportFragmentManager(), DownChorusMusicDialog.TAG);
        }
        mDownChorusMusicDialog.setProgress(progress, 0, progress + "%");
    }

    /**
     * 显示合唱倒计时
     */
    public void showChorusCountDown(final String lrcUrl, final String musicPath) {
        mIvChorusTimer.setVisibility(View.VISIBLE);
        AnimationDrawable animationDrawable = (AnimationDrawable) mIvChorusTimer.getBackground();
        animationDrawable.start();
        int duration = 0;
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
            duration += animationDrawable.getDuration(i);
        }
        //监听动画结束
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //开始播放歌曲 显示歌词
                mIvChorusTimer.setVisibility(View.GONE);
                playChorusMusic(musicPath, lrcUrl);
            }
        }, duration);
    }

    public void playChorusMusic(String songPath, String lrcUrl) {
        LogUtils.d("WatchLiveActivity onStartChorusClicked() playChorusMusic  url : " + songPath);

        //暂停背景音乐
        pauseBgMediaPlayer();

        if (!TextUtils.isEmpty(lrcUrl)) {
            //http://ks3.score.joyk.com.cn/shineAccompani/560685.lrc?AccessKeyId=nXLz3axA9cpFXPsrs0fS&Expires=1589961395&Signature=TvjwdHnMdcGfhjKbfpw/rGGKZi4%3D&
            mLrcChorusView.loadLrcByUrl(lrcUrl);
        } else {
            mLrcChorusView.setLabel(getString(R.string.lrc_none));
        }
        mTimeStamp = 0;
        mLrcChorusView.updateTime(0);
        mLrcChorusView.setVisibility(View.VISIBLE);

        if (mChorusZegoMediaPlayer == null) {
            //初始化Zego媒体播放器
            mChorusZegoMediaPlayer = new ZegoMediaPlayer();
            //使用推流播放模式，会将音频混流推流中，调用端和播放端都可以听到播放的声音
            mChorusZegoMediaPlayer.init(ZegoMediaPlayer.PlayerTypeAux, ZegoMediaPlayer.PlayerIndex.Second);
            mChorusZegoMediaPlayer.setVolume(100);
        } else {
            mChorusZegoMediaPlayer.stop();
        }
        ///storage/emulated/0/qingqing/389111-yc.mp3?AccessKeyId=nXLz3axA9cpFXPsrs0fS&Expires=1576222294&Signature=u%2Bpz%2BTtU%2BXJLjvntNPI2jst5HWU%3D&
        mChorusZegoMediaPlayer.start(songPath, false);

        startChorusTimerTaskManager();
    }

    public void startChorusTimerTaskManager() {
        if (mChorusTimerTaskManager == null) {
            mChorusTimerTaskManager = new TimerTaskManager();
        }
        mChorusTimerTaskManager.setUpdateProgressTask(new Runnable() {
            @Override
            public void run() {
                if (mChorusZegoMediaPlayer != null && (mLrcChorusView != null) && (mLrcChorusView.getVisibility() == View.VISIBLE)) {
                    // 获取播放进度时间戳
                    long timeStamp = mChorusZegoMediaPlayer.getCurrentDuration();
                    if (timeStamp < mTimeStamp) {
                        return;
                    }
                    mTimeStamp = timeStamp;
                    mLrcChorusView.updateTime(mTimeStamp);

                    byte[] mediaSide = new byte[4];
                    mediaSide[0] = (byte) (mTimeStamp & 0xff);  // 低位(右边)的8个bit位
                    mediaSide[1] = (byte) ((mTimeStamp >> 8) & 0xff); //第二个8 bit位
                    mediaSide[2] = (byte) ((mTimeStamp >> 16) & 0xff); //第三个 8 bit位
                    mediaSide[3] = (byte) ((mTimeStamp >> 24) & 0xff); //第4个 8 bit位
                    ByteBuffer inData = ByteBuffer.allocateDirect(mediaSide.length);
                    inData.put(mediaSide, 0, mediaSide.length);
                    inData.flip();
                    /**
                     发送媒体次要信息
                     @param inData 需要传输的音视频次要信息数据，外部输入
                     @param dataLen 传入的 inData 总长度，不能大于 1000 Bytes
                     @param packet 是否外部已经打包好包头，true 已打包, false 未打包。如果没有特殊情况，建议用户使用内部打包
                     @discussion 主播端开启媒体次要信息开关，开始推流后调用。调用此 API 发送媒体次要信息后，观众端在
                     （ZegoLiveRoom）setZegoMediaSideCallback() 设置的回调中获取媒体次要信息。不需要发送媒体次要信息时，可调用 （ZegoLiveRoom）setMediaSideFlags(false,false) 关闭通道
                     */
                    ZegoMediaSideInfo zegoMediaSideInfo = ZegoLiveManager.getInstance().getZegoMediaSideInfo();
                    if (zegoMediaSideInfo != null) {
                        zegoMediaSideInfo.sendMediaSideInfo(inData, mediaSide.length, false, 0);
                    }
                }
            }
        });
        mChorusTimerTaskManager.scheduleSeekBarUpdate();
    }

    public void stopChorusTimerTaskManager() {
        if (mChorusTimerTaskManager != null) {
            mChorusTimerTaskManager.stopSeekBarUpdate();
        }
    }

    public void releaseChorusTimerTaskManager() {
        if (mChorusTimerTaskManager != null) {
            mChorusTimerTaskManager.onRemoveUpdateProgressTask();
        }
    }

    public void resetChorusStates() {
        isDownLoadSuccess = false;
        isReceiveDownLoadSuccess = false;
        mLrcUrl = "";
        mMusicPath = "";
        mMp3BcPath = "";
        mTimeStamp = 0;
    }

    public void stopChorusMediaPlayer() {
        stopChorusZegoMediaPlayer();
        stopChorusTimerTaskManager();
        if (mLrcChorusView != null) {
            mLrcChorusView.setVisibility(View.GONE);
        }
        if (mDownChorusMusicDialog != null && mDownChorusMusicDialog.isShow()) {
            //当前正在下载歌曲准备合唱，取消下载
            mDownChorusMusicDialog.dismiss();
            OkGo.getInstance().cancelTag("download_song");
        }
    }

    /**
     * 播放节目单
     */
    @Override
    public void showPlayBill(PlayBillEntity entity) {
        mPlayBillData = entity;
        mRlPlayBill.setVisibility(View.VISIBLE);
        clearPlayBillAnimation();
        startPlayBillAnimation();
    }

    private void startPlayBillAnimation() {
        switch (mPlayBillData.getEffect_type()) {
            case "1":
                //粒子爆炸
                showExplosionAnimation();
                break;
            case "2":
                //水波纹
                showRollAnimation();
                break;
            case "3":
                //百叶窗
                showShuttersAnimation();
                break;
            case "4":
                //螺纹旋转
                showRollAnimation();
                break;
        }
    }

    /**
     * 粒子爆炸动画
     */
    private void showExplosionAnimation() {
        mRlPlayBillContainer.setVisibility(View.VISIBLE);
        mRoll3DView.setVisibility(View.GONE);
        mRlPlayBillContainer.removeAllViews();
        for (int i = 0; i < mPlayBillData.getImages().size(); i++) {
            ImageView iv = new ImageView(BaseApplication.getApp());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            GlideUtil.setImage(iv, BaseApplication.getApp(), mPlayBillData.getImages().get(i));
            mRlPlayBillContainer.addView(iv);
        }
        Observable.intervalRange(0, Integer.MAX_VALUE, 3, 3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        try {
                            int childIndex = mRlPlayBillContainer.getChildCount() - 1 - aLong.intValue() % mRlPlayBillContainer.getChildCount();
                            View childView = mRlPlayBillContainer.getChildAt(childIndex);
                            mExplosionField.explode(childView);
                            if (aLong.intValue() != 0
                                    && aLong.intValue() % mRlPlayBillContainer.getChildCount() == 2) {
                                for (int i = 0; i < mRlPlayBillContainer.getChildCount(); i++) {
                                    mRlPlayBillContainer.getChildAt(i)
                                            .animate()
                                            .setDuration(50)
                                            .setStartDelay(0)
                                            .scaleX(1f)
                                            .scaleY(1f)
                                            .alpha(1f)
                                            .start();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        mExplosionDisposable = d;
                        addDisposable(mExplosionDisposable);
                    }
                });
    }

    /**
     * 百叶窗动画
     */
    private void showShuttersAnimation() {
        mRoll3DView.setVisibility(View.VISIBLE);
        mRlPlayBillContainer.setVisibility(View.GONE);
        //mRoll3DView.clear();
        for (int i = 0; i < mPlayBillData.getImages().size(); i++) {
            Glide.with(BaseApplication.getApp())
                    .asBitmap()
                    .load(mPlayBillData.getImages().get(i))
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).fitCenter())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource,
                                                    @Nullable Transition<? super Bitmap> transition) {
                            mRoll3DView.addImageBitmap(resource);
                        }
                    });
        }
        mRoll3DView.setRollMode(Roll3DView.RollMode.Jalousie);
        mRoll3DView.setRollDirection(-1);
        mRoll3DView.setPartNumber(5);
        mRoll3DView.setRollDuration(2000);
        Observable.intervalRange(0, Integer.MAX_VALUE, 3, 3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        mRoll3DView.toNext();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        mJalousieDisposable = d;
                        addDisposable(mJalousieDisposable);
                    }
                });
    }

    /**
     * 图片3D轮转效果
     */
    private void showRollAnimation() {
        mRoll3DView.setVisibility(View.VISIBLE);
        mRlPlayBillContainer.setVisibility(View.GONE);
        //mRoll3DView.clear();
        for (int i = 0; i < mPlayBillData.getImages().size(); i++) {
            Glide.with(BaseApplication.getApp())
                    .asBitmap()
                    .load(mPlayBillData.getImages().get(i))
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource,
                                                    @Nullable Transition<? super Bitmap> transition) {
                            mRoll3DView.addImageBitmap(resource);
                        }
                    });
        }
        mRoll3DView.setRollMode(Roll3DView.RollMode.RollInTurn);
        mRoll3DView.setRollDirection(1);
        mRoll3DView.setPartNumber(6);
        mRoll3DView.setRollDuration(1500);

        Observable.intervalRange(0, Integer.MAX_VALUE, 3, 2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        mRoll3DView.toNext();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        mRollDisposable = d;
                        addDisposable(mRollDisposable);
                    }
                });
    }

    /**
     * 清除节目单图片轮播动画
     */
    private void clearPlayBillAnimation() {
        if (mExplosionDisposable != null && !mExplosionDisposable.isDisposed()) {
            mExplosionDisposable.dispose();
        }
        if (mJalousieDisposable != null && !mJalousieDisposable.isDisposed()) {
            mJalousieDisposable.dispose();
        }
        if (mRollDisposable != null && !mRollDisposable.isDisposed()) {
            mRollDisposable.dispose();
        }
    }

    public void getGiftData() {
        String url = UrlConst.GifList;
        HashMap<String, String> param = new HashMap<>();
        final PostRequest<String> request = OkGo.<String>post(url)
                .tag(TAG);
        request.params(param)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LogUtils.d(TAG + " getGiftData onSuccess() : " + response);
                        if (JsonUtil.is200(response.body())) {
                            String gifJson = response.body();
                            LogUtils.d(TAG + " getGiftData gifJson : " + gifJson);
                            if (TextUtils.isEmpty(gifJson)) {
                                return;
                            }
                            GiftMode giftMode = new Gson().fromJson(gifJson, GiftMode.class);
                            if (giftMode == null) {
                                return;
                            }
                            LogUtils.d(TAG + "getGiftData  giftMode : " + giftMode);
                            List<GiftMode.DataBean> dataBeanList = giftMode.getData();
                            LogUtils.d("getGiftData  dataBeanList : " + dataBeanList);
                            if (dataBeanList == null || dataBeanList.isEmpty()) {
                                return;
                            }
                            for (GiftMode.DataBean dataBean : dataBeanList) {
                                loadSvgaTem(dataBean.getImg2());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        LogUtils.d(TAG + " getGiftData onError() : " + response);
                    }
                });
    }

    public void loadSvgaTem(String url) {
        LogUtils.d("getGiftData loadSvgaTem  url : " + url);
        SVGAParser parser = new SVGAParser(BaseApplication.getApp());
        try { // new URL needs try catch.
            parser.decodeFromURL(new URL(url), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    LogUtils.d("getGiftData loadSvgaTem  onComplete()");
                }

                @Override
                public void onError() {
                    LogUtils.d("getGiftData loadSvgaTem  onError()");
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
            LogUtils.d("getGiftData loadSvgaTem  MalformedURLException()");
        }
    }

//    /**
//     * 注意：阳光网络版需要调用此方法处理音频增益
//     */
//    public void initZegoAudioPrep() {
//        // 设置音频前处理回调，并在回调中处理 SDK 传入的音频数据
//        // 开启音频前处理，并设置预处理参数
//        ZegoExtPrepSet config = new ZegoExtPrepSet();
//        config.encode = false;    // 不需要编码前处理后的数据，输出 PCM 数据
//        config.sampleRate = 0; // 使用 SDK 默认采样率
//        config.channel = 0;    // 使用 SDK 默认声道数
//        config.samples = 0;    // 编码一帧所需要的采样数
//        ZegoLiveRoom.enableAudioPrep2(true, config);
//    }

    /**
     * 注意：阳光网络版需要调用此方法处理音频增益
     */
    public void initZegoAudioPrepCallback() {
        // 开启音频前处理，并设置预处理参数
        mZegoLiveRoom.setZegoAudioPrepCallback2(new IZegoAudioPrepCallback2() {
            @Override
            public ZegoAudioFrame onAudioPrep(ZegoAudioFrame inFrame) {
                LogUtils.d(TAG + "initZegoAudioPrepCallback 1 onAudioPrep  inFrame : " + inFrame);
                if (inFrame == null) {
                    return null;
                }
                ByteBuffer byteBuffer = inFrame.buffer.duplicate();
                byte[] byteYuan = decodeValue(byteBuffer);
                byte[] byteYuanNew = new byte[byteYuan.length];
                amplifyPCMData(byteYuan, byteYuan.length, byteYuanNew, 16, 8.0);
                ByteBuffer lastByteBuffer = encodeValue(byteYuanNew);
                LogUtils.d(TAG + "initZegoAudioPrepCallback 2 onAudioPrep  lastByteBuffer : " + lastByteBuffer);
                if (lastByteBuffer == null) {
                    return null;
                }
                ZegoAudioFrame outFrame = new ZegoAudioFrame();
                outFrame.frameType = inFrame.frameType;
                outFrame.samples = inFrame.samples;
                outFrame.bytesPerSample = inFrame.bytesPerSample;
                outFrame.channels = inFrame.channels;
                outFrame.sampleRate = inFrame.sampleRate;
                outFrame.timeStamp = inFrame.timeStamp;
                outFrame.configLen = inFrame.configLen;
                outFrame.bufLen = inFrame.bufLen;
                outFrame.buffer = lastByteBuffer;
                LogUtils.d(TAG + "initZegoAudioPrepCallback 3 onAudioPrep  outFrame : " + outFrame);
                return outFrame;
            }
        });
    }

    //调节PCM数据音量
    //pData原始音频byte数组，nLen原始音频byte数组长度，data2转换后新音频byte数组，nBitsPerSample采样率，multiple表示Math.pow()返回值
    public int amplifyPCMData(byte[] pData, int nLen, byte[] data2, int nBitsPerSample, double db) {
        int nCur = 0;
        double factor = Math.pow(10, (double) db / 20);
        if (16 == nBitsPerSample) {
            while (nCur < nLen) {
                short volum = getShort(pData, nCur);
                volum = (short) (volum * factor);
                data2[nCur] = (byte) (volum & 0xFF);
                data2[nCur + 1] = (byte) ((volum >> 8) & 0xFF);
                nCur += 2;
            }
        }
        return 0;
    }

    private short getShort(byte[] data, int start) {
        return (short) ((data[start] & 0xFF) | (data[start + 1] << 8));
    }

    public byte[] decodeValue(ByteBuffer bytes) {
        int len = bytes.limit() - bytes.position();
        byte[] bytes1 = new byte[len];
        bytes.get(bytes1);
        return bytes1;
    }

    public ByteBuffer encodeValue(byte[] value) {
        int valueLength = value.length;
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(valueLength);
        byteBuffer.clear();
        byteBuffer.put(value, 0, valueLength);
        return byteBuffer;
    }

 /*   public void initAudioPrepCallback() {
        final MonkeyAudioPlayer mAudioPlayer = SunPlayerManager.getInstance().getMonkeyAudioPlayer();
        mZegoLiveRoom.setZegoAudioPrepCallback2(new IZegoAudioPrepCallback2() {
            @Override
            public ZegoAudioFrame onAudioPrep(ZegoAudioFrame inFrame) {
                if (mAudioPlayer != null) {
                    byte[] array = new byte[inFrame.bufLen];
                    inFrame.buffer.get(array, 0, inFrame.bufLen);
                    mAudioPlayer.playMix(array, inFrame.bufLen, System.currentTimeMillis());
                    inFrame.buffer = encodeValue(array);
                }
                return inFrame;
            }
        });
    }*/

    /**
     * 音频前处理
     */
    public void initZegoAudioPrep() {
        // 设置音频前处理回调，并在回调中处理 SDK 传入的音频数据
        // 开启音频前处理，并设置预处理参数
        ZegoExtPrepSet config = new ZegoExtPrepSet();
        config.encode = false;    // 不需要编码前处理后的数据，输出 PCM 数据
        config.sampleRate = 48000; // 使用 SDK 默认采样率
        config.channel = 2;    // 使用 SDK 默认声道数
        config.samples = 500;    // 编码一帧所需要的采样数
        ZegoLiveRoom.enableAudioPrep2(true, config);
    }

    public void initLoopBack() {
        if (mZegoLiveRoom != null) {
            mZegoLiveRoom.enableLoopback(true);
            mZegoLiveRoom.setLoopbackVolume(30);
            // 设置混响参数
            ZegoAudioReverbParam param = new ZegoAudioReverbParam();
            param.damping = 0.7f; // 此处设置混响阻尼为 1.0
            param.dryWetRatio = 0.5f; // 此处设置干湿比为 0.5
            param.reverberance = 0.25f; // 此处设置余响为 0.2
            param.roomSize = 0.3f; // 此处设置房间大小为 0.5
            ZegoAudioProcessing.setReverbParam(param);
            ZegoAudioProcessing.enableReverb(true, ZegoAudioReverbMode.CONCERT_HALL);
        }

    }

    /**
     * 收到跨房间公共消息，房间内所有人都能收到
     */
    @Override
    public void messageByMultiRoom(MultiRoomMessageEntity entity) {
        switch (entity.getType()) {
            case 1:
                //此情况为对方房间合唱歌曲播放完成下，收到对方房间发来的隐藏歌词的消息
                //{"cmd":"multiroom_public_message","user_id":"","auser_id":"","room_id":"138","aroom_id":"371","lrc_progress":0,"song_duration":0,"type":1}
                mLrcChorusView.setVisibility(View.GONE);
                break;
        }
    }

    public void handleZegoStreamInfo(ZegoStreamInfo[] zegoStreamInfos) {
        if (zegoStreamInfos != null && zegoStreamInfos.length > 0) {
            for (ZegoStreamInfo zegoStreamInfo : zegoStreamInfos) {
                if (zegoStreamInfo != null) {
                    String extraInfo = zegoStreamInfo.extraInfo;
                    LogUtil.d(TAG, "getAnchorUserInfo  onStreamExtraInfoUpdated extraInfo : " + extraInfo);
                    if (!TextUtils.isEmpty(extraInfo)) {
                        SwitchCamera switchCamera = GsonConverter.fromJson(extraInfo, SwitchCamera.class);
                        if (switchCamera != null) {
                            String streamId = switchCamera.getStreamId();
                            if (!TextUtils.isEmpty(streamId) && streamId.equals(mLianMaiStreamID) && tvAudience1 != null) {
                                if (switchCamera.isCameraClose()) {
                                    tvAudience1.setUserAvatarViewVISIBLE(mLianMaiUserInfo);
                                } else {
                                    tvAudience1.setUserAvatarViewGone();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void getAnchorUserInfo(String streamID) {
        LogUtil.d(TAG, "getAnchorUserInfo  mStreamID : " + streamID);
        if (watchPresenter != null) {
            if (!TextUtils.isEmpty(streamID) && streamID.length() > 2) {
                mLianMaiStreamID = streamID;
                String userId = streamID.substring(2);
                LogUtil.d(TAG, "getAnchorUserInfo  userId : " + userId + " roomId : " + roomId);
                watchPresenter.getUserInfo(TAG, roomId, userId, new ICallback<UserDetailBean>() {
                    @Override
                    public void onResult(UserDetailBean userDetailBean) {
                        LogUtil.d(TAG, "getAnchorUserInfo getAnchorUserInfo onResult userDetailBean : " + userDetailBean);
                        mLianMaiUserInfo = userDetailBean;
                    }

                    @Override
                    public void onError(Throwable error) {
                        LogUtil.d(TAG, "getAnchorUserInfo getAnchorUserInfo  onError error : " + error);
                    }
                });
            }
        }
    }

    public void setLocalMVVideo() {

        mMVTextureView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mBgZegoMediaPlayer != null) {
                    mBgZegoMediaPlayer.pause();
                }

                if (mChorusZegoMediaPlayer != null) {
                    mChorusZegoMediaPlayer.pause();
                }
                mvZegoMediaPlayer = new ZegoMediaPlayer();
                mvZegoMediaPlayer.init(ZegoMediaPlayer.PlayerTypePlayer, ZegoMediaPlayer.PlayerIndex.Third);
                mvZegoMediaPlayer.setView(mMVTextureView);
                mvZegoMediaPlayer.setVolume(100);
                mvZegoMediaPlayer.setPlayerType(ZegoMediaPlayer.PlayerTypeAux);
                String path = FileUtil.getPath(LivePlayActivity.this, "sea.mp4");
                Log.i("MVVideo", " path : " + path);
                mvZegoMediaPlayer.start(path, true);
            }
        }, 3000);

    }

    public void setTextureViewAlpha60() {
        if (mLocalPreview != null) {
            mLocalPreview.setTextureViewAlpha(0.4f);
        }
        if (mvZegoMediaPlayer != null) {
            mvZegoMediaPlayer.resume();
        }

        if (mChorusZegoMediaPlayer != null) {
            mChorusZegoMediaPlayer.pause();
        }

    }

    public void setTextureViewAlpha100() {
        if (mLocalPreview != null) {
            mLocalPreview.setTextureViewAlpha(1.0f);
        }

        if (mvZegoMediaPlayer != null) {
            mvZegoMediaPlayer.pause();
        }
    }
}
