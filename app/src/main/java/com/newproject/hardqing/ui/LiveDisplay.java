package com.newproject.hardqing.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Presentation;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.lzx.musiclibrary.manager.TimerTaskManager;
import com.newproject.hardqing.R;
import com.newproject.hardqing.adapter.AudienceListAdapter;
import com.newproject.hardqing.adapter.LiveMessageAdapter;
import com.newproject.hardqing.adapter.UserIconPcAdapter;
import com.newproject.hardqing.base.BaseApplication;
import com.newproject.hardqing.entity.GiftMode;
import com.newproject.hardqing.entity.QrcodeBean;
import com.newproject.hardqing.entity.UserDetailBean;
import com.newproject.hardqing.giftlibrary.widget.GiftControl;
import com.newproject.hardqing.permission.BaseObserver;
import com.newproject.hardqing.ui.dialog.DownChorusMusicDialog;
import com.newproject.hardqing.ui.lala.LalaOnlineAdapter;
import com.newproject.hardqing.ui.presenter.LivePushHandler;
import com.newproject.hardqing.ui.presenter.WatchPresenter;
import com.newproject.hardqing.ui.receivecmd.BaScreenEntity;
import com.newproject.hardqing.ui.receivecmd.EntertainEntity;
import com.newproject.hardqing.ui.receivecmd.ExtractAudienceEntity;
import com.newproject.hardqing.ui.receivecmd.GiftEntity;
import com.newproject.hardqing.ui.receivecmd.InRoomEntity;
import com.newproject.hardqing.ui.receivecmd.LuckyAudienceEntity;
import com.newproject.hardqing.ui.receivecmd.PlayBillEntity;
import com.newproject.hardqing.ui.receivecmd.RedEntity;
import com.newproject.hardqing.ui.receivecmd.UpdateRoomEntity;
import com.newproject.hardqing.ui.view.Danmu;
import com.newproject.hardqing.ui.view.DanmuView;
import com.newproject.hardqing.util.AnimationUtils;
import com.newproject.hardqing.util.CreateDataUtil;
import com.newproject.hardqing.util.CustomPoPupAnim;
import com.newproject.hardqing.util.DensityUtil;
import com.newproject.hardqing.util.GlideUtil;
import com.newproject.hardqing.util.LogUtil;
import com.newproject.hardqing.util.NumShow;
import com.newproject.hardqing.util.QRCodeUtil;
import com.newproject.hardqing.uvc.VideoCaptureFactoryDemo;
import com.newproject.hardqing.view.DivergeView;
import com.newproject.hardqing.view.FullScreenVideoView;
import com.newproject.hardqing.view.GifView;
import com.newproject.hardqing.view.QingLrcView;
import com.newproject.hardqing.view.RedPacketView;
import com.newproject.hardqing.view.Roll3DView;
import com.newproject.hardqing.view.ViewLive;
import com.newproject.hardqing.view.ball.CommonUtils;
import com.newproject.hardqing.view.ball.TagCloudAdapter;
import com.newproject.hardqing.view.ball.TagCloudView;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.zego.zegoavkit2.ZegoMediaPlayer;
import com.zego.zegoavkit2.mediaside.ZegoMediaSideInfo;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zego.zegoliveroom.constants.ZegoVideoViewMode;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import tyrantgit.explosionfield.ExplosionField;

/**
 * 副屏只显示直播内容
 * Created by LLhon
 */
public class LiveDisplay extends Presentation {

    public static final String TAG = "LiveDisplay";

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
    //    //期待精彩表演
    TextView tvQi, tvDai, tvJing, tvCai, tvBiao, tvYan;
    LinearLayout llSubtitles;
    TextView tvSubtitles;
    ImageView mIvChorusTimer;
    SVGAImageView mSivRedPackets;
    TagCloudView mTagCloudView;
    DanmuView mDanmuView;

    private LivePlayActivity mActivity;
    private Context mContext;
    private AnimationUtils animationUtils;
    private boolean isFrist = true;
    private boolean startAnimation = false;
    private List<GiftEntity> mGiftData = new ArrayList<>();
    private boolean isShow = false;
    public boolean isRedRain;//是否在下红包雨

    public LiveDisplay(Context outerContext, Display display, LivePlayActivity activity) {
        super(outerContext, display);
        mContext = outerContext;
        mActivity = activity;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_live_display);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color.transparent)));
        }
        initView();
        initData();
    }

    private void initView() {
        initViewId();
        //显示派对星球动画
        CustomPoPupAnim.loadSvga(mSivSubject, "xingqiu.svga");
        mActivity.giftControl2.setGiftLayout(false, giftLl2, 4);
        setLayout();
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        llSubtitles.measure(w, h);

        initZanData();
        initIconRv();
        initMessageRv();
        showTopicAnim(QingMainActivity.category_uri);
        initGiftView();
        mRlAudienceList.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void initData() {

    }

    private void initViewId() {
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
        mDanmuView = findViewById(R.id.danmu_view);
        mShowGifImageView = findViewById(R.id.iv_custom_gift_view);
        mZhuBoAvatar = findViewById(R.id.headicon);
        mZhuBoName = findViewById(R.id.tv_zhu_bo_name);
        mZhuBoFansCount = findViewById(R.id.tv_fans_num);
        mWatchUserRecyclerView = findViewById(R.id.rv_watch_user_avatar);
        mWatchUserCount = findViewById(R.id.tv_watch_user_count);
        mShowAllView = findViewById(R.id.ll_show_all_view);
        mTvPartySubject = findViewById(R.id.rtv_title);
        mVideoViewBg = findViewById(R.id.vv_bg);
        mSivSubject = findViewById(R.id.iv_tv_zhu_ti);
        tvAudience1 = findViewById(R.id.tv_audience1);
        mLocalPreview = findViewById(R.id.tv_local_preview);
        llSubtitles = findViewById(R.id.ll_subtitles);
        tvSubtitles = findViewById(R.id.tv_subtitles);
        mIvChorusTimer = findViewById(R.id.iv_chorus_timer);
        mSivRedPackets = findViewById(R.id.siv_red_packets);
        mTagCloudView = findViewById(R.id.tag_cloud);
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
        tvQi = findViewById(R.id.tv_qi);
        tvDai = findViewById(R.id.tv_dai);
        tvJing = findViewById(R.id.tv_jing);
        tvCai = findViewById(R.id.tv_cai);
        tvBiao = findViewById(R.id.tv_biao);
        tvYan = findViewById(R.id.tv_yan);
    }

    private void setLayout() {
        //        bottomLayout2.setVisibility(View.VISIBLE);
        tvName.setVisibility(View.VISIBLE);
        if (mActivity.isAnchor) {
            ivSet.setVisibility(View.GONE);
        }
        if (!mActivity.isAnchor) {
            ivLove.setVisibility(View.GONE);
        }
        ivShare.setVisibility(View.VISIBLE);
        ivAudience.setVisibility(View.GONE);
        mWatchUserRecyclerView.setVisibility(View.VISIBLE);
        rvMesssage.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initZanData() {
        zanViewh.post(new Runnable() {
            @Override
            public void run() {
                zanViewh.setEndPoint(new PointF(zanViewh.getMeasuredWidth() / 2, 0));
                zanViewh.setDivergeViewProvider(new Provider());
            }
        });
    }

    private void initIconRv() {
        int width = -DensityUtil.dip2px(BaseApplication.getApp(), 4);
        mWatchUserRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, true));
        mWatchUserRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) != mActivity.userIconAdapter.getData().size() - 1) {
                    outRect.right = width;
                }

            }
        });
        mWatchUserRecyclerView.setAdapter(mActivity.userIconAdapter);
    }

    private void initMessageRv() {
        rvMesssage.setLayoutManager(new LinearLayoutManager(mContext));
        rvMesssage.setAdapter(mActivity.messageAdapter);
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
        mGifView.setItemViewWH(DensityUtil.dip2px(mContext, 96), DensityUtil.dip2px(mContext, 96));
    }

    public void ChangeTextColor(Random random) {
        tvQi.setTextColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        tvDai.setTextColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        tvJing.setTextColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        tvCai.setTextColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        tvBiao.setTextColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        tvYan.setTextColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
    }

    public void bapin(final BaScreenEntity baScreenEntity) {
        // 0 图片 1 视频
        if (TextUtils.equals(baScreenEntity.getType(), "0")) {
            ivTemplate.setVisibility(View.VISIBLE);
            ivTemplateBg.setVisibility(View.VISIBLE);
            mBaPinAll.setVisibility(View.VISIBLE);
            ivTemplate.setAlpha(0f);
            GlideUtil.setImage(ivTemplateBg, mContext, baScreenEntity.getUri());
            Glide.with(mContext).asBitmap().load(baScreenEntity.getUri()).apply(new RequestOptions().diskCacheStrategy(
                DiskCacheStrategy.NONE))
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
            showBaScreenTimer(baScreenEntity);
        }
        String text = baScreenEntity.getText();
        startHengFu(text);
    }

    public void showBaScreenTimer(BaScreenEntity baScreenEntity) {
        if (mTvBaScreenTimer == null || baScreenEntity == null) {
            return;
        }
        mLlBaScreenTimer.setVisibility(View.VISIBLE);
    }

    public void hideBaScreenTimer(long time) {
        if (mLlBaScreenTimer == null) {
            return;
        }
        if (time == 0) {
            mLlBaScreenTimer.setVisibility(View.GONE);
        } else {
            mTvBaScreenTimer.setText(String.valueOf(time));
            CustomPoPupAnim.startScaleAnimation(mTvBaScreenTimer);
        }
    }

    public void onCloseBaPingClicked() {
        if (ivTemplate == null) {
            return;
        }
        stopBapin();
    }

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
    }

    private void startHengFu(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (isFrist) {
                animationUtils = new AnimationUtils(llSubtitles, mActivity.llwidth, mActivity.screenWidth, tvSubtitles, BaseApplication
                    .getApp());
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

    public void showTopicAnim(final String uri) {
        if (!TextUtils.isEmpty(uri) && mBaPinAll != null) {
            mBaPinAll.postDelayed(new Runnable() {
                @Override
                public void run() {
                    subjectTransAnim();
                    CustomPoPupAnim.loadSvga(categoryui, uri, new CustomPoPupAnim.AnimListener() {
                        @Override
                        public void onFinish() {
                            showTopicAnim(mActivity.mTopicURL);
                        }
                    });
                }
            }, 60 * 1000);

        }
    }

    public void subjectTransAnim() {
        if (categoryui == null) {
            return;
        }
        Animation animation = android.view.animation.AnimationUtils.loadAnimation(mContext, R.anim.live_type_animation);
        categoryui.startAnimation(animation);
    }

    class Provider implements DivergeView.DivergeViewProvider {
        @Override
        public Bitmap getBitmap(Object obj) {
            return mActivity.mList == null ? null : mActivity.mList.get((int) obj);
        }

    }

    public void addDanmu(Danmu danmu) {
        mDanmuView.add(danmu);
    }

    public void updateLrcTime(long timeStamp) {
        mLrcChorusView.updateTime(timeStamp);
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

        if (!TextUtils.isEmpty(lrcUrl)) {
            //http://ks3.score.joyk.com.cn/shineAccompani/560685.lrc?AccessKeyId=nXLz3axA9cpFXPsrs0fS&Expires=1589961395&Signature=TvjwdHnMdcGfhjKbfpw/rGGKZi4%3D&
            mLrcChorusView.loadLrcByUrl(lrcUrl);
        } else {
            mLrcChorusView.setLabel(mContext.getString(R.string.lrc_none));
        }
        mLrcChorusView.updateTime(0);
        mLrcChorusView.setVisibility(View.VISIBLE);
    }

    public void setShowVideoWindow(boolean isVisible, boolean isChangeLiveStatus) {
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

    public boolean isShowVideoWindow() {
        return mShowAllView.getVisibility() == View.VISIBLE;
    }

    public void setQRCode(QrcodeBean qrcodeBean) {
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

    public void setLrcViewVisibility(boolean show) {
        mLrcChorusView.setVisibility(show ? View.VISIBLE : View.GONE);
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

    public void releaseLiveView() {
        tvAudience1.setFree();
    }

    public void showGifAnimation() {
        mGifView.startAnimation(mGifView.getWidth(), mGifView.getHeight());
    }

    public void startPlayingStream(String streamId) {
        mLrcChorusView.setVisibility(View.GONE);
        tvAudience1.setVisibility(View.VISIBLE);
        tvAudience1.setStreamID(streamId);
        tvAudience1.setUserAvatarViewGone();
        mActivity.mZegoLiveRoom.startPlayingStream(streamId, tvAudience1.getTextureView());
        mActivity.mZegoLiveRoom.setViewMode(ZegoVideoViewMode.ScaleAspectFill, streamId);
    }

    public void scrollMessage(int position) {
        rvMesssage.scrollToPosition(position);
    }

    public void startPlayDanmu() {
        mDanmuView.startPlay(true);
    }

    public void setWatchUserCount(String num) {
        mWatchUserCount.setText(num);
    }

    public void setZhuBoInfo(String avatar, String userName) {
        try {
            GlideUtil.setImage(mZhuBoAvatar, mContext, avatar, GlideUtil.createRp(R.mipmap.pic_3));
        } catch (Exception e) {
        }
        mZhuBoName.setText(userName);
    }

    public void setTvPartySubject(String title) {
        mTvPartySubject.setSelected(true);
        mTvPartySubject.setText(title);
    }

    public void setTagCloudData() {
        mTagCloudView.setBackgroundColor(CommonUtils.getColor(R.color.transparent));
        mTagCloudView.setAdapter(mActivity.mTagCloudAdapter);
    }

    public void setWatchNum() {
        if (mActivity.mWatchNum >= 0) {
            mWatchUserCount.setText(NumShow.formatLiveNum(mActivity.mWatchNum + "", false, mContext));
        } else {
            mWatchUserCount.setText(NumShow.formatLiveNum(mActivity.mWatchNum + "", false, mContext));
        }
    }

    public void showZan() {
        Random random = new Random();
        zanViewh.startDiverges(random.nextInt(6));
    }

    public void sendGift(GiftEntity giftEntity) {
        showZan();
        if (giftEntity.getGiftStyle().equals("0")) {
        } else if (giftEntity.getGiftStyle().equals("1")) {
            mGiftData.add(giftEntity);
            showAnim(mGiftData.get(0));
        } else {
            mGiftData.add(giftEntity);
            showAnim(mGiftData.get(0));
        }
    }

    public void showAnim(final GiftEntity giftEntity) {
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
                            mActivity.handler.postDelayed(new Runnable() {
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
    }

    private void playRedPacketVideoRaw() {
        try {
            if (mSivRedPackets == null) {
                return;
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
        } catch (Exception e) {
            playRedPacketVideoRaw();
        }
    }

    /**
     * 停止下红包雨
     */
    public void stopRedRain() {
        if (mSivRedPackets == null) {
            return;
        }
        if (mActivity.mUpVideoViewShowing) {
            mShowAllView.setVisibility(View.VISIBLE);
        }
        redPacketsView.stopRainNow();
        redPacketsView.setVisibility(View.GONE);
        ivCloseRed.setVisibility(View.INVISIBLE);
        mSivRedPackets.setVisibility(View.GONE);
    }

    public void showEntertainLuck() {
        ///娱乐活动-轮盘显示
        mRlShowLuck.setVisibility(View.VISIBLE);
        mRlLuckP.setVisibility(View.VISIBLE);
        mRlSvga.setVisibility(View.GONE);
        mRlNameLian.setVisibility(View.GONE);
        mTvWaitAudience.setVisibility(View.VISIBLE);
    }

    public void showInduction() {
        //显示自我介绍
        mRlShowInduction.setVisibility(View.VISIBLE);
        mRlShowLuck.setVisibility(View.GONE);
        mRlLuckP.setVisibility(View.GONE);
    }

    public void showExtractAudience(ExtractAudienceEntity extractAudienceEntity) {
        //观众
        mRlShowLuck.setVisibility(View.VISIBLE);
        mRlLuckP.setVisibility(View.VISIBLE);
        mRlSvga.setVisibility(View.GONE);
        mRlNameLian.setVisibility(View.GONE);
        mTvWaitAudience.setVisibility(View.VISIBLE);
        mTvLuckResult.setText("");
        CustomPoPupAnim.loadSvgaAnim(mSvgaExtractAudience, "activity_get.svga", new CustomPoPupAnim.AnimListener() {
            @Override
            public void onFinish() {
                mRlNameLian.setVisibility(View.VISIBLE);
                mTvWaitAudience.setVisibility(View.GONE);
                mTvExtractName.setText(extractAudienceEntity.getUsername());
            }
        });
    }

    public void showCloseInduction() {
        mRlShowLuck.setVisibility(View.GONE);
        mRlShowInduction.setVisibility(View.GONE);
    }

    public void feedbackLucky(LuckyAudienceEntity luckyAudienceEntity) {
        //被抽到的幸运观众反馈
        mRlSvga.setVisibility(View.VISIBLE);
        mSvgaLuckResult.setVisibility(View.VISIBLE);
        mRlLuckP.setVisibility(View.GONE);
        CustomPoPupAnim.loadSvgaAnim(mSvgaLuckResult, "luck.svga", new CustomPoPupAnim.AnimListener() {
            @Override
            public void onFinish() {
                //被抽到的幸运观众反馈
                mTvLuckResult.setText(luckyAudienceEntity.getContent());
            }
        });
    }

    public void fiveAudience() {
        CustomPoPupAnim.loadSvgaAnim(mSvgaExtractAudience, "activity_get.svga", new CustomPoPupAnim.AnimListener() {
            @Override
            public void onFinish() {
                //数据填充适配器
                mRlAudienceList.setAdapter(mActivity.audienceListAdapter);
            }
        });
    }

    public void lianFeedBack() {
        mRlAudienceList.setAdapter(mActivity.audienceListAdapter);
    }
}
