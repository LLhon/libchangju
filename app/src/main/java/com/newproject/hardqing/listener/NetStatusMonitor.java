package com.newproject.hardqing.listener;

/**
 * 网络状态类型改变的监听接口
 */
public interface NetStatusMonitor {
    void onNetChange(int netStatus);
}

