package com.newproject.hardqing.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.newproject.hardqing.util.NetUtil;


/**
 * Created by ruancw on 2018/5/27.
 * 用于实时监听app的网络状态
 */

public class NetBroadcastReceiver extends BroadcastReceiver implements NetStatusMonitor {
    //网络状态监听接口
    private NetStatusMonitor netStatusMonitor;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            //获取网络状态的类型
            int netStatus = NetUtil.getNetStatus(context);
            if (netStatusMonitor != null)
                // 接口传递网络状态的类型到注册广播的页面
                netStatusMonitor.onNetChange(netStatus);
        }
    }

    @Override
    public void onNetChange(int netStatus) {

    }


    /**
     * 设置网络状态监听接口
     */
    public void setStatusMonitor(NetStatusMonitor netStatusMonitor) {
        this.netStatusMonitor = netStatusMonitor;
    }
}