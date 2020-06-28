//package com.newproject.hardqing.ui.presenter;
//
//import android.app.Activity;
//import android.graphics.Bitmap;
//import android.util.Log;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.View;
//import android.widget.Toast;
//
//import com.alivc.player.AliVcMediaPlayer;
//import com.alivc.player.MediaPlayer;
//import com.newproject.hardqing.ai_effects.opencv.OpenCVHelper;
//import com.newproject.hardqing.base.BaseApplication;
//import com.newproject.hardqing.ui.LivePlayActivity;
//import com.newproject.hardqing.ui.view.SpecialEffectView;
//import com.newproject.hardqing.util.ToastUtil;
//
//import java.lang.ref.WeakReference;
//
////播放器 初始化 销毁 后台操作 监听
//public class LivePlayerPcPresenter {
//    public static final String TAG = "TAG";
//
//
//    public void addCallBack(final SurfaceView surfaceView, final AliVcMediaPlayer mPlayer) {
//        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
//
//            public void surfaceCreated(SurfaceHolder holder) {
//                holder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
//                holder.setKeepScreenOn(true);
//                Log.e(TAG, "AlivcPlayer onSurfaceCreated." + mPlayer);
//
//                // Important: surfaceView changed from background to front, we need reset surface to mediaplayer.
//                // 对于从后台切换到前台,需要重设surface;部分手机锁屏也会做前后台切换的处理
//                if (mPlayer != null) {
//                    mPlayer.setVideoSurface(surfaceView.getHolder().getSurface());
//                }
//                Log.e(TAG, "AlivcPlayeron SurfaceCreated over.");
//            }
//
//            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//                Log.e(TAG, "onSurfaceChanged is valid ? " + holder.getSurface().isValid());
//                if (mPlayer != null)
//                    mPlayer.setSurfaceChanged();
//            }
//
//            public void surfaceDestroyed(SurfaceHolder holder) {
//                Log.e(TAG, "onSurfaceDestroy.");
//            }
//        });
//
//    }
//
//
//    public AliVcMediaPlayer initVodPlayer(LivePlayActivity acitivity, SurfaceView surfaceView) {
//        AliVcMediaPlayer mPlayer = new AliVcMediaPlayer(acitivity, surfaceView);
//        setListener(acitivity, mPlayer);
//        return mPlayer;
//    }
//
//    OpenCVHelper mOpenCVHelper;
//    SpecialEffectView specialEffectView;
//    View vBox;
//
//    public void setOnVideoListenr(Activity activity, final AliVcMediaPlayer mPlayer, SpecialEffectView specialEffectView
//    ) {
//        this.specialEffectView = specialEffectView;
//        setFaceAndGestureListener(activity);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        while (startSnapshot) {
//                            if (mPlayer != null && mPlayer.isPlaying()) {
//                                Bitmap bitmap = mPlayer.snapShot();
//                                try {
//                                    Thread.sleep(100);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                                mOpenCVHelper.detectFace(bitmap);
//                                if (bitmap != null)
//                                    bitmap.recycle();
//                            }
//                        }
//                    }
//                }).start();
//            }
//        }).start();
//
//    }
//
//
//    private void setFaceAndGestureListener(Activity context) {
//        mOpenCVHelper = new OpenCVHelper(context, false); //OpenCV人脸识别
////        mOpenCVHelper.setvBox(vBox);
//        // 势识别
//
//        mOpenCVHelper.setFaceListener(new OpenCVHelper.FaceListener() {
//            @Override
//            public void onFaceBox(int x, int y, int width) {
//                if (specialEffectView != null) {
//                    specialEffectView.onFaceBox(x, y, width);
//                }
//            }
//        });
//
//    }
//
//
//    public boolean startSnapshot = true;
//
//    public void setNull() {
//        startSnapshot = false;
//    }
//
//
//    public AliVcMediaPlayer initPlayer(LivePlayActivity activity, SurfaceView surfaceView) {
//        AliVcMediaPlayer mPlayer = new AliVcMediaPlayer(activity, surfaceView);
//        setWListener(activity, mPlayer);
//        return mPlayer;
//    }
//
//
//    public void setWListener(LivePlayActivity acitivity, AliVcMediaPlayer mPlayer) {
//
//        mPlayer.enableNativeLog();
//        mPlayer.setVideoScalingMode(MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
//        //直播不需要播放完成监听
//        mPlayer.setCompletedListener(new MyWPlayerCompletedListener(acitivity));
//        //直播不需要进度监听
//        mPlayer.setFrameInfoListener(new MyWFrameInfoListener(acitivity));
//
//    }
//
//
//    public void setListener(LivePlayActivity acitivity, AliVcMediaPlayer mPlayer) {
//        //主播监听有可能遇到
//        mPlayer.setPreparedListener(new MyPreparedListener(acitivity));
//        //获取流的状态监听
//        mPlayer.setFrameInfoListener(new MyWFrameInfoListener(acitivity));
//        //直播需要播放错误监听
//        mPlayer.setErrorListener(new MyErrorListener(acitivity));
//        //直播需要播放停止监听 但是已经有socket 的操作了
//        mPlayer.setStoppedListener(new MyStoppedListener(acitivity));
//        mPlayer.enableNativeLog();
//        mPlayer.setVideoScalingMode(MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT);
//        //直播不需要播放完成监听
////        mPlayer.setCompletedListener(new MyPlayerCompletedListener(acitivity));
//        //直播不需要进度监听
////        mPlayer.setSeekCompleteListener(new MySeekCompleteListener(acitivity));
//    }
//
//    public void start(AliVcMediaPlayer mPlayer, String mUrl) {
//        if (mPlayer != null) {
//            mPlayer.prepareAndPlay(mUrl);
//        }
//    }
//
//    public void stop(AliVcMediaPlayer mPlayer) {
//        if (mPlayer != null) {
//            mPlayer.stop();
//        }
//    }
//
//
//    public void startPlay(AliVcMediaPlayer mPlayer, String mUrl) {
//        setMaxBufferDuration(mPlayer);
//        replay(mPlayer, mUrl);
//        mPlayer.setMuteMode(false);
//        Log.e(TAG, "开始请求播放 ");
//    }
//
//    public void replay(AliVcMediaPlayer mPlayer, String mUrl) {
//        stop(mPlayer);
//        start(mPlayer, mUrl);
//    }
//
//    private void setMaxBufferDuration(AliVcMediaPlayer mPlayer) {
//        if (mPlayer != null) {
//            mPlayer.setMaxBufferDuration(8000);
//        }
//    }
//
//    public void destroy(AliVcMediaPlayer mPlayer, String mUrl) {
//        stop(mPlayer);
//        if (mPlayer != null) {
//            mPlayer.stop();
//            mPlayer.destroy();
//        }
//    }
//
//
//    private static class MyWFrameInfoListener implements MediaPlayer.MediaPlayerFrameInfoListener {
//
//        private WeakReference<LivePlayActivity> activityWeakReference;
//
//        public MyWFrameInfoListener(LivePlayActivity activity) {
//            activityWeakReference = new WeakReference<>(activity);
//        }
//
//        @Override
//        public void onFrameInfoListener() {
//            LivePlayActivity activity = activityWeakReference.get();
//            if (activity != null) {
//                activity.onFrameInfoListener();
//            }
//        }
//    }
//
//
//    private static class MyPreparedListener implements MediaPlayer.MediaPlayerPreparedListener {
//
//        private WeakReference<LivePlayActivity> activityWeakReference;
//
//        private MyPreparedListener(LivePlayActivity activity) {
//            activityWeakReference = new WeakReference<>(activity);
//        }
//
//        @Override
//        public void onPrepared() {
//            LivePlayActivity activity = activityWeakReference.get();
//            if (activity != null) {
//                Log.e(TAG, "准备成功");
//            }
//        }
//    }
//
//
//    private static class MyErrorListener implements MediaPlayer.MediaPlayerErrorListener {
//
//
//        private WeakReference<LivePlayActivity> activityWeakReference;
//
//        private MyErrorListener(LivePlayActivity activity) {
//            activityWeakReference = new WeakReference<>(activity);
//        }
//
//        @Override
//        public void onError(int i, String s) {
//            LivePlayActivity activity = activityWeakReference.get();
//            if (activity != null) {
//                //这里暂时不知道做什么操作
//                Log.e(TAG, "播放错误");
//            }
//        }
//    }
//
//
//    private static class MyStoppedListener implements MediaPlayer.MediaPlayerStoppedListener {
//
//        private WeakReference<LivePlayActivity> activityWeakReference;
//
//        private MyStoppedListener(LivePlayActivity activity) {
//            activityWeakReference = new WeakReference<>(activity);
//        }
//
//
//        @Override
//        public void onStopped() {
//            LivePlayActivity activity = activityWeakReference.get();
//            if (activity != null) {
//                //这里暂时不知道做什么操作
//                Log.e(TAG, "播放暂停");
//
//            }
//        }
//    }
//
//    private static class MyWPlayerCompletedListener implements MediaPlayer.MediaPlayerCompletedListener {
//        private WeakReference<LivePlayActivity> activityWeakReference;
//
//        private MyWPlayerCompletedListener(LivePlayActivity activity) {
//            activityWeakReference = new WeakReference<>(activity);
//        }
//
//        @Override
//        public void onCompleted() {
//            LivePlayActivity activity = activityWeakReference.get();
//            if (activity != null) {
//                //这里暂时不知道做什么操作
//                Log.e(TAG, "播放暂停");
//            }
//        }
//    }
//
//}
