package com.newproject.hardqing.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class ServiceUtil {
    /**
     * 判断服务是否在运行
     * @param mContext
     * @param className　　Service.class.getName();
     * @return
     */
    public static boolean isServiceRunning(Context mContext, String className){
        boolean isRunning = false ;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> seviceList = activityManager.getRunningServices(200);//200:是运行的service的集合大小，当设置过小时，我没有获取到正在运行的Serice
        if (seviceList.size() <= 0){
            return false;
        }
        for (int i=0 ;i < seviceList.size();i++){
            if (seviceList.get(i).service.getClassName().toString().equals(className)){
                isRunning = true;
                break;
            }
        }
        return  isRunning;
    }
}
