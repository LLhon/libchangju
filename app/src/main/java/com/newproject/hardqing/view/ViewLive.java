package com.newproject.hardqing.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.newproject.hardqing.R;
import com.newproject.hardqing.base.BaseApplication;
import com.newproject.hardqing.entity.UserDetailBean;
import com.newproject.hardqing.util.GlideUtil;
import com.newproject.hardqing.util.LogUtil;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zego.zegoliveroom.constants.ZegoVideoViewMode;

import java.util.ArrayList;
import java.util.List;


/**
 * Copyright © 2017 Zego. All rights reserved. des: 直播view.
 */
public class ViewLive extends RelativeLayout {

    /**
     * 推拉流颜色.
     */
    private TextView mTvQualityColor;

    /**
     * 推拉流质量.
     */
    private TextView mTvQuality;

    /**
     * 全屏.
     */
    private TextView mTvSwitchToFullScreen;

    /**
     * 分享.
     */
    private TextView mTvShare;

    /**
     * 用于渲染视频.
     */
    private TextureView mTextureView;

    private int[] mArrColor;

    private String[] mArrLiveQuality;

    private Resources mResources;

    private View mRootView;

    private ZegoLiveRoom mZegoLiveRoom = null;

    private Activity mActivityHost = null;

    /**
     * 推拉流质量.
     */
    private int mLiveQuality = 0;

    /**
     * 视频显示模式.
     */
    private int mZegoVideoViewMode = ZegoVideoViewMode.ScaleAspectFit;

    /**
     * 分享地址.
     */
    private List<String> mListShareUrls = new ArrayList<>();

    /**
     * "切换全屏" 标记.
     */
    private boolean mNeedToSwitchFullScreen = false;

    private String mStreamID = null;

    private boolean mIsPublishView = false;

    private boolean mIsPlayView = false;

    private ImageView mUserAvatarView;


    private IShareToQQCallback mShareToQQCallback = null;


    public ViewLive(Context context) {
        super(context);
    }

    public ViewLive(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewLive(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewLive, defStyleAttr, 0);
        boolean isBigView = a.getBoolean(R.styleable.ViewLive_isBigView, false);
        boolean isLalaView = a.getBoolean(R.styleable.ViewLive_isLalaView, false);
        a.recycle();

        initViews(context, isBigView, isLalaView);
    }

    public void setZegoLiveRoom(ZegoLiveRoom zegoLiveRoom) {
        mZegoLiveRoom = zegoLiveRoom;
    }

    public void setActivityHost(Activity activity) {
        mActivityHost = activity;
    }

    public void destroy() {
        mActivityHost = null;
        mShareToQQCallback = null;
        mZegoLiveRoom = null;
        if (mTvSwitchToFullScreen != null) {
            mTvSwitchToFullScreen.setOnClickListener(null);
        }
    }

    public void setShareToQQCallback(IShareToQQCallback shareToQQCallback) {
        mShareToQQCallback = shareToQQCallback;
    }

    private void initViews(Context context, boolean isBigView, boolean isLalaView) {
        mResources = context.getResources();
//        mArrColor = new int[4];
//        mArrColor[0] = R.drawable.circle_green;
//        mArrColor[1] = R.drawable.circle_yellow;
//        mArrColor[2] = R.drawable.circle_red;
//        mArrColor[3] = R.drawable.circle_gray;
//        mArrLiveQuality = mResources.getStringArray(R.array.live_quality);
        LogUtils.d("ViewLive  initViews  isBigView : " + isBigView + "  isLalaView : " + isLalaView);
//        if (isBigView) {
//            mRootView = LayoutInflater.from(context).inflate(R.layout.view_live_big, this);
//        } else {
//            if (isLalaView) {
//                mRootView = LayoutInflater.from(context).inflate(R.layout.view_lala_live, this);
//            } else {
//                mRootView = LayoutInflater.from(context).inflate(R.layout.view_live, this);
//            }
//        }

        mRootView = LayoutInflater.from(context).inflate(R.layout.view_live, this);
        mTextureView = mRootView.findViewById(R.id.textureView);
        mUserAvatarView = mRootView.findViewById(R.id.iv_user_avatar);
        LogUtil.e(mTextureView.getLayoutParams().height + "");
        LogUtils.d("ViewLive  initViews  width : " + mTextureView.getWidth() + "  height : " + mTextureView.getHeight());
    }

    private String userName;

    public String getUserName() {
        return userName;
    }


    public void SetUserName(final String userName) {
        this.userName = userName;
    }

    /**
     * 返回view是否为"空闲"状态.
     */
    public boolean isFree() {
        return TextUtils.isEmpty(mStreamID);
    }

    /**
     * 释放view.
     */
    public void setFree() {
        mLiveQuality = 0;
        setVisibility(View.GONE);

        mZegoVideoViewMode = ZegoVideoViewMode.ScaleAspectFill;
        mNeedToSwitchFullScreen = false;

        if (mTvSwitchToFullScreen != null) {
            mTvSwitchToFullScreen.setVisibility(View.INVISIBLE);
        }

        mListShareUrls = new ArrayList<>();
        if (mTvShare != null) {
            mTvShare.setVisibility(View.INVISIBLE);
        }
        auser_id = null;
        lmid = null;
        mStreamID = null;
        mIsPublishView = false;
        mIsPlayView = false;
        if (mUserAvatarView != null) {
            mUserAvatarView.setVisibility(GONE);
        }
    }


    /**
     * 设置播放质量.
     */
    public void setLiveQuality(int quality) {

    }

    public void setLiveQuality(int quality, double videoFPS, double videoBitrates) {
    }


    /**
     * 设置mode.
     */
    public void setZegoVideoViewMode(boolean needToSwitchFullScreen, int mode) {
        mNeedToSwitchFullScreen = needToSwitchFullScreen;
        mZegoVideoViewMode = mode;

        if (mTvSwitchToFullScreen != null) {
            if (mNeedToSwitchFullScreen) {
                // mTvSwitchToFullScreen.setVisibility(View.VISIBLE);

                if (mode == ZegoVideoViewMode.ScaleAspectFill) {
                    // 退出全屏
                } else if (mode == ZegoVideoViewMode.ScaleAspectFit) {
                    // 全屏显示
                }
            } else {
                mTvSwitchToFullScreen.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 设置分享url列表.
     */
    public void setListShareUrls(List<String> listShareUrls) {
        mListShareUrls.clear();
        mListShareUrls.addAll(listShareUrls);

        if (mTvShare != null) {
            if (listShareUrls.size() > 0) {
                mTvShare.setVisibility(View.VISIBLE);
            } else {
                mTvShare.setVisibility(View.INVISIBLE);
            }
        }
    }


    public int getLiveQuality() {
        return mLiveQuality;
    }

    public TextureView getTextureView() {
        return mTextureView;
    }

    public void setTextureViewAlpha(float aplha) {
        if (mTextureView != null) {
            mTextureView.setAlpha(aplha);
        }
    }

    public boolean isNeedToSwitchFullScreen() {
        return mNeedToSwitchFullScreen;
    }


    public int getZegoVideoViewMode() {
        return mZegoVideoViewMode;
    }


    public List<String> getListShareUrls() {
        return mListShareUrls;
    }

    public void setStreamID(String streamID) {
        mStreamID = streamID;
    }

    public String getStreamID() {
        return mStreamID;
    }

    public boolean isPublishView() {
        return mIsPublishView;
    }

    public boolean isPlayView() {
        return mIsPlayView;
    }

    public void setPublishView(boolean publishView) {
        mIsPublishView = publishView;
    }

    public void setPlayView(boolean playView) {
        mIsPlayView = playView;
    }

    private String lmid;
    private String auser_id;

    public String getAuser_id() {
        return auser_id;
    }

    public void setAuser_id(String auser_id) {
        this.auser_id = auser_id;
    }

    public void setLmid(String lmid) {
        this.lmid = lmid;
    }

    public String getLmid() {
        return lmid;
    }

    public interface IShareToQQCallback {
        String getRoomID();
    }

    public interface CloseMaiLive {
        void close(String auser_id, String lmid);
    }

    public void setUserAvatarViewVISIBLE(UserDetailBean mLianMaiUserInfo) {
        if (mUserAvatarView != null) {
            if (mLianMaiUserInfo != null) {
                UserDetailBean.DataBean dataBean = mLianMaiUserInfo.getData();
                if (dataBean != null) {
                    LogUtils.d("getAnchorUserInfo  onStreamExtraInfoUpdated dataBean : " + dataBean);
                    GlideUtil.setImage(mUserAvatarView, BaseApplication.getInstance(), dataBean.getAvatar(), GlideUtil.createRp(R.mipmap.pic_3));
                    mUserAvatarView.setVisibility(VISIBLE);
                }
            } else {
                mUserAvatarView.setVisibility(GONE);
            }
        }
    }

    public void setUserAvatarViewGone() {
        if (mUserAvatarView != null) {
            mUserAvatarView.setVisibility(GONE);
        }
    }
}
