package com.newproject.hardqing.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.URISyntaxException;

/**
 * @Description: 网络监听
 */

public class NetWatchdog {

    private Activity mActivity;
    private NetChangeListener mNetChangeListener;

    private enum LastNetStatus{
        DisConnect, Mobile,Wifi
    }
    private LastNetStatus lastNetStatus;

    private IntentFilter mNetIntentFilter = new IntentFilter();

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //获取手机的连接服务管理器，这里是连接管理器类
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo wifiNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            NetworkInfo.State wifiState = NetworkInfo.State.UNKNOWN;
            NetworkInfo.State mobileState = NetworkInfo.State.UNKNOWN;

            if (wifiNetworkInfo != null) {
                wifiState = wifiNetworkInfo.getState();
            }
            if (mobileNetworkInfo != null) {
                mobileState = mobileNetworkInfo.getState();
            }

            if (NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED == mobileState) {
                if(lastNetStatus != LastNetStatus.Mobile){
                    lastNetStatus = LastNetStatus.Mobile;
                    if (mNetChangeListener != null) {
                        try {
                            mNetChangeListener.onWifiTo4G();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                }

            } else if (NetworkInfo.State.CONNECTED == wifiState && NetworkInfo.State.CONNECTED != mobileState) {
                if(lastNetStatus != LastNetStatus.Wifi) {
                    lastNetStatus = LastNetStatus.Wifi;
                    if (mNetChangeListener != null) {
                        try {
                            mNetChangeListener.on4GToWifi();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED != mobileState) {
                if(lastNetStatus != LastNetStatus.DisConnect) {
                    lastNetStatus = LastNetStatus.DisConnect;
                    if (mNetChangeListener != null) {
                        mNetChangeListener.onNetDisconnected();
                    }
                }
            }
        }
    };

    public NetWatchdog(Activity activity) {
        mActivity = activity;
        mNetIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    public interface NetChangeListener {
        void onWifiTo4G() throws URISyntaxException;

        void on4GToWifi() throws URISyntaxException;

        void onNetDisconnected();
    }

    public void setNetChangeListener(NetChangeListener l) {
        mNetChangeListener = l;
    }

    public void startWatch() {
        try {
            mActivity.registerReceiver(receiver, mNetIntentFilter);
        } catch (Exception e) {

        }
    }

    public void stopWatch() {

        try {
            mActivity.unregisterReceiver(receiver);
        } catch (Exception e) {

        }

    }

}
