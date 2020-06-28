//package com.newproject.hardqing.util;
//
//import com.blankj.utilcode.util.LogUtils;
//import com.wk.player.AudioPlayerEventListener;
//import com.wk.player.MonkeyAudioPlayer;
//
//import java.io.File;
//
//public class SunPlayerManager {
//
//    private static volatile SunPlayerManager sunPlayerManager;
//
//    private MonkeyAudioPlayer mMonkeyAudioPlayer;
//
//    private static volatile String lyricUrl;
//
//    private AudioPlayerEventListener mListener;
//
//    private SunPlayerManager() {
//
//    }
//
//    public static SunPlayerManager getInstance() {
//        if (sunPlayerManager == null) {
//            synchronized (SunPlayerManager.class) {
//                if (sunPlayerManager == null) {
//                    sunPlayerManager = new SunPlayerManager();
//                }
//            }
//        }
//        return sunPlayerManager;
//    }
//
//    public void initMonkeyAudioPlayer() {
//        LogUtils.d("MonkeyAudioPlayer initMonkeyAudioPlayer()");
//        String path = "/sdcard/monkeyplayer/mp3cache";
//        File dir = new File(path);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        MonkeyAudioPlayer.nativeSetupJNI(path);
//        mMonkeyAudioPlayer = new MonkeyAudioPlayer();
//        mMonkeyAudioPlayer.initizlize();
//        mMonkeyAudioPlayer.setMixConfig(48000, 16, 2);
//        mMonkeyAudioPlayer.setPlayerMode(true);
//
//        mMonkeyAudioPlayer.startPlayer();
//        mMonkeyAudioPlayer.setPlayerEventListener(new AudioPlayerEventListener() {
//            @Override
//            public void onPrepare(String s, int i, String s1, String s2, String s3) {
//                LogUtils.d("MonkeyAudioPlayer onPrepare  s1:" + s1 + "  s2 : " + s2 + "  s3 : " + s3 + "  s : " + s + " i : " + i);
//                //mMonkeyAudioPlayer.play(s1, s2, 1);
//                lyricUrl = s3;
//                if (mListener != null) {
//                    mListener.onPrepare(s, i, s1, s2, s3);
//                }
//            }
//
//            @Override
//            public void onCacheProgress(String s, int i) {
//                if (mListener != null) {
//                    mListener.onCacheProgress(s, i);
//                }
//            }
//
//            @Override
//            public void onPlayProgress(int cur, int total) {
//                if (mListener != null) {
//                    mListener.onPlayProgress(cur, total);
//                }
//            }
//
//            @Override
//            public void onMixdata(byte[] bytes, long l, int i, int i1, int i2) {
//                if (mListener != null) {
//                    mListener.onMixdata(bytes, l, i, i1, i2);
//                }
//            }
//
//            @Override
//            public void onComplete(String s) {
//                if (mListener != null) {
//                    mListener.onComplete(s);
//                }
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                LogUtils.d("MonkeyAudioPlayer onError i : " + i + "  String : " + s);
//                if (mListener != null) {
//                    mListener.onError(i, s);
//                }
//            }
//        });
//    }
//
//    public MonkeyAudioPlayer getMonkeyAudioPlayer() {
//        return mMonkeyAudioPlayer;
//    }
//
//    public void setPlayerListener(AudioPlayerEventListener listener) {
//        this.mListener = listener;
//    }
//
//    public void prepare(String songId, String userId) {
//        if (mMonkeyAudioPlayer != null) {
//            mMonkeyAudioPlayer.prepare(songId, userId);
//        }
//    }
//
//    public void stopPlay() {
//        if (mMonkeyAudioPlayer != null) {
//            mMonkeyAudioPlayer.stop();
//        }
//    }
//
//    public void pause() {
//        if (mMonkeyAudioPlayer != null) {
//            mMonkeyAudioPlayer.pause();
//        }
//    }
//
//    public void resume() {
//        if (mMonkeyAudioPlayer != null) {
//            mMonkeyAudioPlayer.resume();
//        }
//    }
//
//    public void destroy() {
//        if (mMonkeyAudioPlayer != null) {
//            mMonkeyAudioPlayer.destroy();
//        }
//    }
//
//    /**
//     * PlayerMode true  播放音乐并且推流 false 只播放不推流
//     */
//    public void setPlayerMode(boolean var1) {
//        if (mMonkeyAudioPlayer != null) {
//            mMonkeyAudioPlayer.setPlayerMode(true);
//        }
//    }
//
//    /**
//     * 切换原唱伴唱 true：原唱 false：伴唱
//     */
//    //切换原唱 伴唱  true：原唱   false：伴唱
//    public void changeOrigin(boolean original) {
//        if (mMonkeyAudioPlayer != null) {
//            mMonkeyAudioPlayer.change_origin(original);
//        }
//    }
//
//    /**
//     * 获取当前播放进度
//     */
//    public int getCurrentDuration() {
//        if (mMonkeyAudioPlayer != null) {
//            return mMonkeyAudioPlayer.currentDuration();
//        }
//        return 0;
//    }
//
//    /**
//     * 获取歌曲总时长
//     * @return
//     */
//    public int getTotalDuration() {
//        if (mMonkeyAudioPlayer != null) {
//            return mMonkeyAudioPlayer.totalDuration();
//        }
//        return 0;
//    }
//
//    public String getNetLyricPath() {
//        return lyricUrl;
//    }
//
//    public String getLocalLyricPath() {
//        if (mMonkeyAudioPlayer != null) {
//            return mMonkeyAudioPlayer.getLrcPath();
//        }
//        return "";
//    }
//}
