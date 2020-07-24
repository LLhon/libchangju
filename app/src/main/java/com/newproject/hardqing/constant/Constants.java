package com.newproject.hardqing.constant;

import com.newproject.hardqing.BuildConfig;

/**
 * Copyright © 2017 Zego. All rights reserved.
 */

public class Constants {

    public static int CHANNEL_ID_SUN_NET = 1;//阳光网络摄像头
    public static int CHANNEL_ID_SUN_USB = 2;//阳光USB摄像头
    public static int CHANNEL_ID_YIN_CHUANG = 3;//音创设备

    public static final String KEY_RTMP = "rtmp";

    public static final String KEY_HLS = "Hls";

    public static final String FIRST_ANCHOR = "first";

    public static final String KEY_MIX_STREAM_ID = "mixStreamID";

    //竖屏
    public static final int ZEGO_ROTATION_0 = 1;
    //横屏
    public static final int ZEGO_ROTATION_270 = 0;

    public static String getCompanyId() {
        String companyId = "9999";
        if (BuildConfig.channel_id == CHANNEL_ID_SUN_NET) {
            companyId = String.valueOf(CHANNEL_ID_SUN_NET);
        } else if (BuildConfig.channel_id == CHANNEL_ID_SUN_USB) {
            companyId = String.valueOf(CHANNEL_ID_SUN_USB);
        } else if (BuildConfig.channel_id == CHANNEL_ID_YIN_CHUANG) {
            companyId = String.valueOf(CHANNEL_ID_YIN_CHUANG);
        }
        return companyId;
    }

    public static final int REQUEST_CODE_PERMISSION = 10001;
}
