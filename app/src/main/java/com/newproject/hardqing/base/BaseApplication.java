package com.newproject.hardqing.base;

import android.app.Application;
import android.content.Context;
import android.net.http.HttpResponseCache;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.Utils;
import com.newproject.hardqing.constant.PreConst;
import com.newproject.hardqing.ui.LoginUserManager;
import com.newproject.hardqing.ui.ZegoLiveManager;
import com.newproject.hardqing.util.SystemUtil;
import com.newproject.hardqing.util.ThreadUtil;
import com.newproject.hardqing.util.http.HttpUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.zego.zegoliveroom.ZegoLiveRoom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;

public class BaseApplication extends MultiDexApplication {


    protected static BaseApplication mInstance;
    private ExecutorService executorService;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static BaseApplication getInstance() {
        if (mInstance != null) {
            return mInstance;
        } else {
            mInstance = new BaseApplication();
            mInstance.onCreate();
            return mInstance;
        }
    }

    public static Context getApp() {
        return getInstance().getApplicationContext();
    }

    public BaseApplication() {
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //注册微信
        initOkGo();
//        MobSDK.init(this);//上线打开
        // 创建线程池
        executorService = ThreadUtil.newDynamicSingleThreadedExecutor();
//        initMusic();
//        initZegoLive();
        initBugly();
        initSvgaCache();
        initCacheFiles();
        Utils.init(this);
        String userID = getNowTimeStr();
        ZegoLiveRoom.setSDKContext(new ZegoLiveRoom.SDKContext() {
            @Nullable
            @Override
            public String getSoFullPath() {
                return null;
            }

            @Nullable
            @Override
            public String getLogPath() {
                return null;
            }

            @NonNull
            @Override
            public Application getAppContext() {
                return BaseApplication.this;
            }
        });
        setUser();
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


    private void initSvgaCache() {
        try {
            File cacheDir = new File(getApplicationContext().getCacheDir(), "http");
            HttpResponseCache.install(cacheDir, 1024 * 1024 * 128);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除本地歌曲缓存文件
     */
    private void initCacheFiles() {
        if (SDCardUtils.isSDCardEnable()) {
            try {
                FileUtils.deleteDir(PreConst.music_dir);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initBugly() {
        /* Bugly SDK初始化
         * 参数1：上下文对象
         * 参数2：APPID，平台注册时得到,注意替换成你的appId
         * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
         */
        Context context = getApplicationContext();
// 获取当前包名
        String packageName = context.getPackageName();
// 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        //CrashReport.initCrashReport(getApplicationContext(), "4a4c2cbf2e", true, strategy);
        CrashReport.initCrashReport(getApplicationContext(), "a9d91f8a4a", true, strategy);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    public void initZegoLive() {
        ZegoLiveManager.getInstance().initZegoLive();
    }

//    private void initMusic() {
//        if (BaseUtil.getCurProcessName(this).equals("com.newproject.hardqing")) {
//            musicLibrary = new MusicLibrary.Builder(this)
////                    .setNotificationCreater(creater)
//                    .setUseMediaPlayer(true)
//                    .build();
//            musicLibrary.startMusicService();
//        }
//
//    }


    public void initOkGo() {
        HttpUtil.init();
    }


    public ExecutorService getExecutorService() {
        return executorService;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    static final private SimpleDateFormat sFormat = new SimpleDateFormat();

    static public String getNowTimeStr() {
        sFormat.applyPattern("yyMMddHHmmssSSS");
        return sFormat.format(new Date());
    }
}
