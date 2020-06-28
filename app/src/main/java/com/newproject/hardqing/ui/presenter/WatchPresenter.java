package com.newproject.hardqing.ui.presenter;

import com.google.gson.Gson;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.newproject.hardqing.R;
import com.newproject.hardqing.constant.PreConst;
import com.newproject.hardqing.entity.GiftMode;
import com.newproject.hardqing.entity.LiveEntity;
import com.newproject.hardqing.entity.RoomUserBean;
import com.newproject.hardqing.entity.SunSongAddressResponse;
import com.newproject.hardqing.entity.TimeStampBean;
import com.newproject.hardqing.entity.UserDetailBean;
import com.newproject.hardqing.listener.ICallback;
import com.newproject.hardqing.ui.interactor.GiftInteractor;
import com.newproject.hardqing.ui.interactor.LiveInfoInteractor;
import com.newproject.hardqing.ui.interactor.MusicLibInteractor;
import com.newproject.hardqing.ui.interactor.RoomUserInteractor;
import com.newproject.hardqing.ui.interactor.UserDetailInteractor;
import com.newproject.hardqing.ui.view.WatchView;
import com.newproject.hardqing.util.GsonConverter;
import com.newproject.hardqing.util.JsonUtil;

import java.io.File;

public class WatchPresenter {

    private WatchView watchView;

    public WatchPresenter(WatchView watchView) {
        this.watchView = watchView;
    }

    public void getLiveInfo(String tag, String room_id) {
        if (watchView == null) {
            return;
        }
        LiveInfoInteractor.getLiveInfo(tag, room_id, new LiveInfoInteractor.CallBack() {
            @Override
            public void onFailure(@Nullable String msg) {
                if (watchView != null) {
                    watchView.showTip(R.string.net_err);
                }
            }

            @Override
            public void onSuccess(@Nullable String msg) {
                if (watchView != null) {
                    LiveEntity liveEntity = new Gson().fromJson(msg, LiveEntity.class);
                    watchView.getLiveView(liveEntity);
                }
            }
        });
    }

    public void getGiftData(String tag) {
        GiftInteractor.getGiftData(tag, new GiftInteractor.CallBack() {
            @Override
            public void onFailure(@Nullable String msg) {

            }

            @Override
            public void onSuccess(@Nullable String msg) {
                GiftMode giftMode = new Gson().fromJson(msg, GiftMode.class);
                watchView.getGiftData(giftMode);
            }
        });
    }


    public void getRoomUser(String tag, String start, String roomId) {
        if (watchView == null) {
            return;
        }
        RoomUserInteractor.getRoomUser(tag, start, "10", roomId, new RoomUserInteractor.CallBack() {
            @Override
            public void onFailure(@Nullable String msg) {
                if (watchView != null)
                    watchView.showTip(R.string.net_err);
            }

            @Override
            public void onSuccess(@Nullable String msg) {
                if (watchView != null) {
                    RoomUserBean roomUserBean = new Gson().fromJson(msg, RoomUserBean.class);
                    watchView.getRoomUser(roomUserBean.getData());
                }

            }
        });
    }

    //type 1 主播 2 用户
    public void getUserDetail(String tag, String roomId, String userId, final View view) {
        if (watchView == null) {
            return;
        }
        UserDetailInteractor.getUserDetail(tag, roomId, userId, new UserDetailInteractor.CallBack() {
            @Override
            public void onFailure(@Nullable String msg) {
                if (watchView != null)
                    watchView.showTip(R.string.net_err);
            }

            @Override
            public void onSuccess(@Nullable String msg) {
                if (watchView != null) {
                    UserDetailBean userDetailBean = new Gson().fromJson(msg, UserDetailBean.class);
                    watchView.getUserDetail(userDetailBean.getData(), view);
                }
            }
        });
    }

    /**
     * 获取歌曲下载地址
     */
    public void getSongAddress(final String tag, final String songCode,
                               final String anchor_id, final String anchor_name) {
        if (watchView == null) {
            return;
        }
        MusicLibInteractor.getTimeStamp(tag, new MusicLibInteractor.CallBack() {
            @Override
            public void onFailure(@Nullable String msg) {
                watchView.showFail(msg);
            }

            @Override
            public void onSuccess(@Nullable String msg) {
                if (watchView == null) {
                    return;
                }
                if (JsonUtil.is0(msg)) {
                    TimeStampBean bean = new Gson().fromJson(msg, TimeStampBean.class);
                    requestSongAddress(tag, songCode, anchor_id, anchor_name, bean.getResult());
                } else {
                    watchView.showFail(JsonUtil.getDescription(msg));
                }
            }
        });
    }

    public void requestSongAddress(final String tag, final String songCode,
                                   final String anchor_id, final String anchor_name, final String timeStamp) {
        MusicLibInteractor.getSongAddress(tag, songCode, anchor_id, anchor_name, timeStamp,
                new MusicLibInteractor.CallBack() {
                    @Override
                    public void onFailure(@Nullable String msg) {
                        requestSongAddress(tag, songCode, anchor_id, anchor_name, timeStamp);
                    }

                    @Override
                    public void onSuccess(@Nullable String msg) {
                        if (watchView == null) {
                            return;
                        }
                        if (JsonUtil.is0(msg)) {
                            SunSongAddressResponse response = new Gson().fromJson(msg, SunSongAddressResponse.class);
                            if (response == null || response.getResult() == null) {
                                watchView.showFail("response == null");
                                return;
                            }
                            downloadMp3(tag, response.getResult().getMp3(), anchor_id, anchor_name,
                                songCode, response.getResult().getLrc(), response.getResult().getMp3bc());
                        } else {
                            requestSongAddress(tag, songCode, anchor_id, anchor_name, timeStamp);
                        }
                    }
                });
    }

    /**
     * 下载原唱音乐
     */
    public void downloadMp3(final String tag, final String mp3, final String userId,
                            final String userName, final String songCode, final String lrc, final String mp3bc) {
        OkGo.<File>get(mp3).tag(tag)
                .execute(new FileCallback(PreConst.music_dir, getFileName(mp3)) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        Log.e(tag, "onFinish()");
                        if (response == null) {
                            return;
                        }
                        String musicPath = response.body().getAbsolutePath();
                        if (TextUtils.isEmpty(musicPath)) {
                            return;
                        }
                        downloadLrc(tag, musicPath, userId, userName, songCode, lrc);
                    }

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        super.onStart(request);
                        Log.e(tag, "onStart()");
                        //if (tvCount != null) tvCount.setProgress(0);
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        if (watchView == null) {
                            return;
                        }
                        super.downloadProgress(progress);
                        Log.e(tag, "downloadProgress: " + progress.fraction);
                        watchView.downloadProgress((int) (progress.fraction * 100));
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
        downloadMp3BC(tag, mp3bc);
    }

    /**
     * 下载伴唱音乐
     * @param tag
     * @param mp3bc
     */
    public void downloadMp3BC(final String tag, final String mp3bc) {
        OkGo.<File>get(mp3bc).tag(tag)
            .execute(new FileCallback(PreConst.music_dir, getFileName(mp3bc)) {
                @Override
                public void onSuccess(Response<File> response) {
                    Log.e(tag, "onFinish()");
                    if (response == null) {
                        return;
                    }
                    String musicPath = response.body().getAbsolutePath();
                    watchView.downloadMp3BcSuccess(musicPath);
                }

                @Override
                public void onStart(Request<File, ? extends Request> request) {
                    super.onStart(request);
                    Log.e(tag, "onStart()");
                    //if (tvCount != null) tvCount.setProgress(0);
                }

                @Override
                public void onError(Response<File> response) {
                    super.onError(response);
                }

                @Override
                public void downloadProgress(Progress progress) {
                    if (watchView == null) {
                        return;
                    }
                    super.downloadProgress(progress);
                    Log.e(tag, "downloadProgress: " + progress.fraction);
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                }
            });
    }

    /**
     * 下载歌词
     */
    public void downloadLrc(final String tag, final String mp3Path, final String userId,
                            final String userName, final String songCode, final String lrc) {
        OkGo.<File>get(lrc).tag(tag)
                .execute(new FileCallback(PreConst.music_dir, getFileName(lrc)) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        if (response == null) {
                            return;
                        }
                        String lrcPath = response.body().getAbsolutePath();
                        // 歌曲下载完成回调
                        watchView.downloadSongSuccess(userId, userName, songCode, lrc, lrcPath, mp3Path);
                    }

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        super.onStart(request);
                        Log.e(tag, "onStart()");
                        //if (tvCount != null) tvCount.setProgress(0);
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        Log.e(tag, "downloadProgress: " + progress.fraction);
                        //if (tvCount != null) tvCount.setProgress((int) (progress.fraction * 100));
                        //if (tvProgress != null)
                        //    tvProgress.setText((int) (progress.fraction * 100) + "%");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        Log.e(tag, "onFinish()");
                        //if (llDown != null && ivAdd != null) {
                        //    llDown.setVisibility(View.GONE);
                        //    ivAdd.setVisibility(View.VISIBLE);
                        //    ivAdd.setImageResource(R.mipmap.bofang);
                        //
                        //    DownSongMannager.insertSong(new DownSong(PreConst.music_dir + getFileName(songInfo.getDownloadUrl())));
                        //    MySongManager.insertSong(
                        //        new MySong(songInfo.getSongId(), songInfo.getSongName(), songInfo.getSongUrl(), songInfo.getArtist(), "", true,
                        //            Pinyin.toPinyin(songInfo.getSongName().charAt(0)).substring(0, 1).toUpperCase(), false, songInfo.getSite()));
                        //    if (mAdapter != null) {
                        //        mAdapter.notifyDataSetChanged();
                        //    }
                        //    EventBus.getDefault().post(new DeleteMusicMessage(1, 0));
                        //    EventBus.getDefault().post(new DeleteMusicMessage(4, 0));
                        //}
                    }
                });
    }

    public String getFileName(String downUrl) {
        return downUrl.substring(downUrl.lastIndexOf("/") + 1);
    }

    public void getUserInfo(String tag, String roomId, String userId, ICallback<UserDetailBean> callback) {
        if (watchView == null) {
            return;
        }
        UserDetailInteractor.getUserDetail(tag, roomId, userId, new UserDetailInteractor.CallBack() {
            @Override
            public void onFailure(@Nullable String msg) {
                if (watchView != null) {
                    watchView.showTip(R.string.net_err);
                }
                if (callback!=null){
                    callback.onError(new Throwable(msg));
                }
            }

            @Override
            public void onSuccess(@Nullable String msg) {
                UserDetailBean userDetailBean = GsonConverter.fromJson(msg, UserDetailBean.class);
                if (userDetailBean != null) {
                    if (callback!=null){
                        callback.onResult(userDetailBean);
                    }
                }else {
                    if (callback!=null){
                        callback.onError(new Throwable("getUserDetail userDetailBean null"));
                    }
                }
            }
        });
    }

}
