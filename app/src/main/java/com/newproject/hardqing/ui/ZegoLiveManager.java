package com.newproject.hardqing.ui;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.newproject.hardqing.base.BaseApplication;
import com.newproject.hardqing.util.SystemUtil;
import com.zego.zegoavkit2.mediaside.ZegoMediaSideInfo;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zego.zegoliveroom.callback.IZegoLogHookCallback;
import com.zego.zegoliveroom.constants.ZegoAvConfig;

public class ZegoLiveManager {
    private static final String TAG = "ZegoLiveManager";
    private static ZegoLiveRoom zegoLiveRoom;//即构直播推流；

    private static ZegoLiveManager sInstance = null;

    private ZegoAvConfig zegoAvConfig;

    private final int[][] VIDEO_RESOLUTIONS = new int[][]{{320, 240}, {352, 288}, {640, 360},
            {960, 540}, {1280, 720}, {1920, 1080}};

    private int liveQualityLevel = ZegoAvConfig.Level.High;

    public static final String KEY_RTMP = "rtmp";

    public static final String KEY_HLS = "Hls";

    public static final String FIRST_ANCHOR = "first";

    public static final String KEY_MIX_STREAM_ID = "mixStreamID";

    //竖屏
    public static final int ZEGO_ROTATION_0 = 1;
    //横屏
    public static final int ZEGO_ROTATION_270 = 0;

    private static long appID = 2876747424L;

    public static String appKey = "0x2b,0x19,0x9d,0xbb,0x6f,0xfa,0x53,0x23,0xe3,0xcc,0x99" +
            ",0x0e,0x1e,0x52,0xa3,0x57,0xb1,0xe0,0x21,0xf9,0xab,0xd4,0x16,0x1c,0x54,0xae,0xe4,0xce,0xfd,0xed,0x98,0x8c";

    private ZegoMediaSideInfo zegoMediaSideInfo = new ZegoMediaSideInfo();

    private ZegoLiveManager() {
        zegoLiveRoom = new ZegoLiveRoom();
    }

    public static ZegoLiveManager getInstance() {
        if (sInstance == null) {
            synchronized (ZegoLiveManager.class) {
                if (sInstance == null) {
                    sInstance = new ZegoLiveManager();
                }
            }
        }
        return sInstance;
    }

    public ZegoLiveRoom getG_ZegoApi() {
        return zegoLiveRoom;
    }


    public void initZegoLive() {
//        setSdkContext();
//        // 测试环境开关
//        ZegoLiveRoom.setTestEnv(true);
//        // 根据当前运行模式是否打开调试信息，仅供参考
//        ZegoLiveRoom.setVerbose(BuildConfig.DEBUG);

//        setUser();

//        byte[] bytes = parseSignKeyFromString(appKey);
//
//        Log.d(TAG, "appKey: " + convertSignKey2String(bytes));
//        boolean ret = zegoLiveRoom.initSDK(2876747424L, bytes, new IZegoInitSDKCompletionCallback() {
//            @Override
//            public void onInitSDK(int errorCode) {
//                Log.d(TAG, "Zego errCode " + errorCode);
//            }
//        });
//
//        if (ret) {
//            zegoAvConfig = new ZegoAvConfig(liveQualityLevel);//配置清晰度；
//            setZegoConfig(zegoAvConfig);
//        } else {
//            LogUtil.d(TAG, "Zego SDK初始化失败!");
//        }

        zegoAvConfig = new ZegoAvConfig(liveQualityLevel);//配置清晰度；
        // 设置分辨率，注意此处设置的分辨率需要是uvccamera所支持的
        zegoAvConfig.setVideoCaptureResolution(1280, 720); //1280, 720
        zegoAvConfig.setVideoEncodeResolution(1280, 720); // 3840, 2160 / 1920, 1080
        zegoAvConfig.setVideoFPS(15);
        zegoAvConfig.setVideoBitrate(1200000);
        setZegoConfig(zegoAvConfig);

        /**
         发送媒体次要信息开关
         @param start 开启媒体次要信息传输，true 开启媒体次要信息传输, false 关闭媒体次要信息传输。start 为 true 时，onlyAudioPublish 开关才有效
         @param onlyAudioPublish 是否纯音频直播，true 纯音频直播，不传输视频数据，false 音视频直播，传输视频数据。默认为 false。如果本次只有音频直播，必须将 onlyAudioPublish 置为 true，此时将会由音频来驱动次要信息的传输，同时忽略视频流传输
         @discussion 初始化 SDK 后，开始推流前调用。
         */

        if (zegoMediaSideInfo != null) {
            zegoMediaSideInfo.setMediaSideFlags(true, false, 0);
        }
        //统一设置所有拉流的播放音量
        zegoLiveRoom.setPlayVolume(100);

        //再降噪处理
        //zegoLiveRoom.setAECMode(ZegoConstants.AECMode.aggressive);
    }

    private void setSdkContext() {
        ZegoLiveRoom.SDKContext sdkContext = new ZegoLiveRoom.SDKContextEx() {
            /**
             * 指明 SDK 单个日志文件的最大尺寸。
             *
             * @return SDK 单个日志文件的大小，必须在 [5M, 100M] 之间。当返回 0 时，取默认大小 5M
             */
            @Override
            public long getLogFileSize() {
                return 10 * 1024 * 1024;
            }

            @Nullable
            @Override
            public String getSubLogFolder() {
                return null;
            }

            @Override
            public IZegoLogHookCallback getLogHookCallback() {
                return null;
            }

            /**
             * 获取外部 libzegoliveroom.so 路径。
             *
             * 此方法用于想通过外部加载 .so 文件时使用，如将 .so 存放到服务器上，等 app 运行时再加载，以降低 apk 尺寸。
             *
             * 注意：1. SDK 会优先使用 APK 自带的 libzegoliveroom.so；
             *     2. 目前只支持本地文件路径，不支持网络路径；
             *     3. 请确保文件有可读权限。
             *
             * @return null 当不需要此特性时；
             *         libzegoliveroom.so 全路径，当加载默认 libzegoliveroom.so 失败时，会加载该方法提供的 so lib
             */
            @Nullable
            @Override
            public String getSoFullPath() {
                return null;
            }

            /**
             * 指明 SDK 日志文件存储目录（全路径）。
             *
             * 需要确保 SDK 具备存取该目录的权限。
             *
             * @return SDK 日志文件存储路径，当返回 null 时，SDK 使用默认路径
             */
            @Nullable
            @Override
            public String getLogPath() {
                return null;
            }

            /**
             * 提供当前 App 的上下文。
             *
             * @return Application Context，不能为空
             */
            @NonNull
            @Override
            public Application getAppContext() {
                return BaseApplication.getInstance();
            }
        };
        ZegoLiveRoom.setSDKContext(sdkContext);
    }

    public void setZegoConfig(ZegoAvConfig config) {
        zegoAvConfig = config;
        zegoLiveRoom.setAVConfig(config);
    }

    public ZegoAvConfig getZegoAvConfig() {
        return zegoAvConfig;
    }

    /**
     * 刷新配置
     *
     * @param orientation 竖屏还是横屏
     */
    public void refreshZegoAvConfig(int orientation) {
        zegoAvConfig = initZegoAvConfig(orientation);
        zegoLiveRoom.setAVConfig(zegoAvConfig);
    }


    public ZegoAvConfig initZegoAvConfig(int orientation) {

        //默认竖屏
        int number = 1;
        if (orientation == ZEGO_ROTATION_270) {
            //切换横屏
            number = 0;
        }

        zegoAvConfig = getZegoAvConfig();
        if (zegoAvConfig == null) {
            zegoAvConfig = new ZegoAvConfig(ZegoAvConfig.Level.High);
        }

        int width = VIDEO_RESOLUTIONS[liveQualityLevel][number];
        int height = VIDEO_RESOLUTIONS[liveQualityLevel][number == 0 ? 1 : 0];
        Log.e("initZegoAvConfig", String.format("width: %d", width));
        Log.e("initZegoAvConfig", String.format("height: %d", height));
        zegoAvConfig.setVideoEncodeResolution(width, height);
        zegoAvConfig.setVideoCaptureResolution(width, height);

        return zegoAvConfig;
    }

    static public String convertSignKey2String(byte[] signKey) {
        StringBuilder buffer = new StringBuilder();
        for (int b : signKey) {
            buffer.append("0x").append(Integer.toHexString((b & 0x000000FF) | 0xFFFFFF00).substring(6)).append(",");
        }
        buffer.setLength(buffer.length() - 1);
        return buffer.toString();
    }

    static public byte[] parseSignKeyFromString(String strSignKey) throws NumberFormatException {
        String[] keys = strSignKey.split(",");
        if (keys.length != 32) {
            throw new NumberFormatException("App Sign Key Illegal");
        }
        byte[] byteSignKey = new byte[32];
        for (int i = 0; i < 32; i++) {
            int data = Integer.valueOf(keys[i].trim().replace("0x", ""), 16);
            byteSignKey[i] = (byte) data;
        }
        return byteSignKey;
    }


    // 必须设置用户信息
    public static void setUser() {
        String userID = LoginUserManager.getUserId();
        String userName = LoginUserManager.getUsername();

        if (TextUtils.isEmpty(userID) || TextUtils.isEmpty(userName)) {
            long ms = System.currentTimeMillis();
            userID = ms + "";
            userName = "Android_" + SystemUtil.getOsInfo() + "-" + ms;

            // 保存用户信息
            LoginUserManager.setUserId(userID);
            LoginUserManager.saveUsername(userName);
        }
        ZegoLiveRoom.setUser(userID, LoginUserManager.getUsername());
    }


    public void reInitSDK() {
        initZegoLive();
    }

    public void releaseSDK() {
        zegoLiveRoom.unInitSDK();
        zegoLiveRoom = null;
        sInstance = null;
    }

    public ZegoMediaSideInfo getZegoMediaSideInfo() {
        return zegoMediaSideInfo;
    }
}
