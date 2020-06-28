package com.newproject.hardqing.ui.view;

import android.view.View;


import com.newproject.hardqing.entity.GiftMode;
import com.newproject.hardqing.entity.LiveEntity;
import com.newproject.hardqing.entity.RoomUserBean;
import com.newproject.hardqing.entity.UserDetailBean;

import com.newproject.hardqing.ui.receivecmd.PlayBillEntity;
import java.util.List;

public interface WatchView {

    void showTip(int msg);

    void showFail(String msg);

    void getGiftData(GiftMode giftMode);

    void getLiveView(LiveEntity liveEntity);

    void getYbi(String moeny);

    void showToast(String msg);

    void getRoomUser(List<RoomUserBean.DataBean> data);

    void getUserDetail(UserDetailBean.DataBean data, View view);

    void downloadProgress(int progress);

    /**
     * 歌曲下载成功回调
     */
    void downloadSongSuccess(String userId, String userName, String songCode, String lrcUrl, String lrcPath, String songPath);

    void downloadMp3BcSuccess(String mp3bcPath);

    void showPlayBill(PlayBillEntity entity);
}
