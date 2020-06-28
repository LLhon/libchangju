package com.newproject.hardqing.ui.presenter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.newproject.hardqing.ai_effects.AppExecutors;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.PLOnVideoFrameListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;

public class PLVideoPresenter {

    public static final String TAG = "PLVideoPresenter";

    public PLVideoPresenter() {
    }


    public void init(PLVideoTextureView mVideoView, boolean isVideoStart) {
        AVOptions options = new AVOptions();
        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
        options.setInteger(AVOptions.KEY_LOG_LEVEL, 4);
        options.setInteger(AVOptions.KEY_VIDEO_DATA_CALLBACK, 1);
        if (isVideoStart) {
            // 打开重试次数，设置后若打开流地址失败，则会进行重试
            options.setInteger(AVOptions.KEY_OPEN_RETRY_TIMES, 5);
            // 快开模式，启用后会加快该播放器实例再次打开相同协议的视频流的速度
            options.setInteger(AVOptions.KEY_FAST_OPEN, 1);
            // 打开视频时单次 http 请求的超时时间，一次打开过程最多尝试五次
//            options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        }
        mVideoView.setAVOptions(options);
    }

    private boolean isSetPlayMode = false;
    private int oriention = 2;

    public void setOrigin(int oriention) {
        isSetPlayMode = false;
        this.oriention = oriention;
    }


    public void setListener(final PLVideoTextureView mVideoView) {
        mVideoView.setOnInfoListener(mOnInfoListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnErrorListener(mOnErrorListener);
        setMode(mVideoView, PLVideoView.ASPECT_RATIO_FIT_PARENT);
        mVideoView.setOnVideoFrameListener(new PLOnVideoFrameListener() {
            @Override
            public void onVideoFrameAvailable(byte[] data, int size, final int width, final int height, int format, long ts) {
                Log.i("TAG", "onVideoFrameAvailable: " + size + ", " + width + " x " + height + ", " + format + ", " + ts);

                //在UI线程处理任务；
                Log.i("TAG", "isSetPlayMode" + isSetPlayMode);
                if (!isSetPlayMode) {
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        //                    @Override
                        public void run() {
                            if (width >= height) {
                                if (oriention == 1) {
                                    setMode(mVideoView, PLVideoView.ASPECT_RATIO_FIT_PARENT);
                                } else {
                                    setMode(mVideoView, PLVideoView.ASPECT_RATIO_PAVED_PARENT);
                                }

                            } else {
                                if (oriention == 1) {
                                    setMode(mVideoView, PLVideoView.ASPECT_RATIO_PAVED_PARENT);
                                } else {
                                    setMode(mVideoView, PLVideoView.ASPECT_RATIO_FIT_PARENT);
                                }
                            }
                            isSetPlayMode = true;
                        }
                    });
                }


            }
        });
    }

    private void setMode(PLVideoTextureView mVideoView, int aspectRatioFitParent) {
        mVideoView.setDisplayAspectRatio(aspectRatioFitParent);
    }

    private PLOnVideoFrameListener mOnVideoFrameListener = new PLOnVideoFrameListener() {
        @Override
        public void onVideoFrameAvailable(byte[] data, int size, int width, int height, int format, long ts) {
            Log.i("TAG", "onVideoFrameAvailable: " + size + ", " + width + " x " + height + ", " + format + ", " + ts);
//            openCVHelper.detectFaceQN(data, width, height, format);
        }
    };

    private String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    public void setPath(PLVideoTextureView mVideoView, String path) {
        mVideoView.setVideoPath(path);
    }

    private PLOnInfoListener mOnInfoListener = new PLOnInfoListener() {
        @Override
        public void onInfo(int what, int extra) {
//            Log.i(TAG, "OnInfo, what = " + what + ", extra = " + extra);
            switch (what) {
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_START:
                    Log.i(TAG, "MEDIA_INFO_BUFFERING_START: " + extra + "ms");
                    break;
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_END:
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_RENDERING_START:
                    if (ivBg != null) {
                        ivBg.setVisibility(View.GONE);
                    }
//                    Utils.showToastTips(BaseApplication.getApp(), "First video render time: " + extra + "ms");
                    break;
                case PLOnInfoListener.MEDIA_INFO_AUDIO_RENDERING_START:
                    Log.i(TAG, "First audio render time: " + extra + "ms");
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_FRAME_RENDERING:
                    Log.i(TAG, "video frame rendering, ts = " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_AUDIO_FRAME_RENDERING:
                    Log.i(TAG, "audio frame rendering, ts = " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_GOP_TIME:
                    Log.i(TAG, "Gop Time: " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_SWITCHING_SW_DECODE:
                    Log.i(TAG, "Hardware decoding failure, switching software decoding!");
                    break;
                case PLOnInfoListener.MEDIA_INFO_METADATA:
//                    Log.i(TAG, mVideoView.getMetadata().toString());
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_BITRATE:
                case PLOnInfoListener.MEDIA_INFO_VIDEO_FPS:
                    break;
                case PLOnInfoListener.MEDIA_INFO_CONNECTED:
                    Log.i(TAG, "Connected !");
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                    Log.i(TAG, "Rotation changed: " + extra);
                    break;
                default:
                    break;
            }
        }
    };

    private PLOnCompletionListener mOnCompletionListener = new PLOnCompletionListener() {
        @Override
        public void onCompletion() {

        }
    };

    private PLOnErrorListener mOnErrorListener = new PLOnErrorListener() {
        @Override
        public boolean onError(int errorCode) {
            switch (errorCode) {
                case PLOnErrorListener.ERROR_CODE_IO_ERROR:
                    return false;
                case PLOnErrorListener.ERROR_CODE_OPEN_FAILED:
                    break;
                case PLOnErrorListener.ERROR_CODE_SEEK_FAILED:
                    return true;
                default:
                    break;
            }
            Log.e("TAG", "onError: " + errorCode);
            return true;
        }
    };

    public void onPause(PLVideoTextureView mVideoView) {
        mVideoView.pause();
    }

    public void setLoop(PLVideoTextureView mVideoView) {
        mVideoView.setLooping(true);
    }

    public void onResume(PLVideoTextureView mVideoView) {
        mVideoView.start();
    }

    public void stopPLayBack(PLVideoTextureView mVideoView) {
        mVideoView.stopPlayback();
    }

    ImageView ivBg;

    public void setCover(ImageView ivBg) {
        this.ivBg = ivBg;
        ivBg.setAlpha(0.5f);
//        mVideoView.setCoverView(ivBg);
    }
}
