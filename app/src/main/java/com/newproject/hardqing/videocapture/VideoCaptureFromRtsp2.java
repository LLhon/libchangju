package com.newproject.hardqing.videocapture;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import com.blankj.utilcode.util.LogUtils;
import com.llhon.rtspdemo.H264FrameCallBack;
import com.newproject.hardqing.base.BaseApplication;
import com.newproject.hardqing.constant.PreConst;
import com.newproject.hardqing.util.FileUtil;
import com.newproject.hardqing.uvc.IFrameCallback;
import com.newproject.hardqing.uvc.UVCCameraHelper;
import com.zego.zegoavkit2.ZegoVideoCaptureDevice;
import com.zego.zegoavkit2.entities.EncodedVideoFrame;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 网络摄像头采集 -- 采用SURFACE_TEXTURE方式传递数据
 * Created by LLhon
 */
public class VideoCaptureFromRtsp2 extends ZegoVideoCaptureDevice implements TextureView.SurfaceTextureListener {

    // SDK 内部实现的、同样实现 ZegoVideoCaptureDevice.Client 协议的客户端，用于通知SDK采集结果
    Client mClient = null;

    // 预设分辨率宽
    int mWidth = 640;
    // 预设分辨率高
    int mHeight = 480;
    // 预设采集帧率
    int mFrameRate = 30;
    // 默认不旋转
    int mRotation = 0;

    private static final String TAG = "VideoCaptureFromRtsp";
    private TextureView mView;

    // Arbitrary queue depth.  Higher number means more memory allocated & held,
    // lower number means more sensitivity to processing time in the client (and
    // potentially stalling the capturer if it runs out of buffers to write to).
    private static final int NUMBER_OF_CAPTURE_BUFFERS = 3;
    private final Set<byte[]> queuedBuffers = new HashSet<byte[]>();
    private int mFrameSize = 0;
    //  AVCANNEXB 模式解码器
    private AVCDecoder mAVCDecoder = null;
    private Surface mSurface;
    private static final long DEFAULT_TIMEOUT_US = 1000 * 10;

    /**
     * 初始化资源，必须实现
     * @param client 通知ZEGO SDK采集结果的客户端
     */
    @Override
    protected void allocateAndStart(Client client) {
        Log.d(TAG, "allocateAndStart()");
        mClient = client;

        //获取SDK提供的 SurfaceTexture
        SurfaceTexture surfaceTexture = mClient.getSurfaceTexture();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            // 设置Surface的分辨率
            surfaceTexture.setDefaultBufferSize(mWidth, mHeight);
        }
        mSurface = new Surface(surfaceTexture);

        // 启动摄像头相关的线程
        RtspCameraHelper.sharedInstance().init();

        // 设置视频帧回调监听
        RtspCameraHelper.sharedInstance().setFrameCallback(mIFrameCallback);

        if (mAVCDecoder == null){
            // 创建解码器
            mAVCDecoder = new AVCDecoder(mSurface, mWidth, mHeight, mClient);
            // 启动解码器
            mAVCDecoder.startDecoder();
        }
    }

    /**
     * 释放资源，必须实现
     * 先停止采集任务再清理client对象，以保证ZEGO SDK调用stopAndDeAllocate后，没有残留的异步任务导致野指针crash
     */
    @Override
    protected void stopAndDeAllocate() {
        Log.d(TAG, "stopAndDeAllocate()");
        // 释放摄像头并处理采集线程
        RtspCameraHelper.sharedInstance().uninit();
        RtspCameraHelper.sharedInstance().setFrameCallback(null);

        mClient.destroy();
        mClient = null;
        mSurface.release();
        mSurface = null;

        // 释放MediaCodec
        if (mAVCDecoder != null) {
            mAVCDecoder.stopAndReleaseDecoder();
            mAVCDecoder = null;
        }
    }

    // 开始推流时，ZEGO SDK 调用 startCapture 通知外部采集设备开始工作，必须实现
    @Override
    protected int startCapture() {
        Log.d(TAG, "startCapture()");
        return 0;
    }

    // 停止推流时，ZEGO SDK 调用 stopCapture 通知外部采集设备停止采集，必须实现
    @Override
    protected int stopCapture() {
        Log.d(TAG, "stopCapture()");
        return 0;
    }

    /**
     * 告知ZEGOSDK 当前采集的数据类型，必须实现
     * @return
     */
    @Override
    protected int supportBufferType() {
        // SurfaceTexture 类型
        return PIXEL_BUFFER_TYPE_SURFACE_TEXTURE;
    }

    @Override
    protected int setFrameRate(int frameRate) {
        mFrameRate = frameRate;

        // 更新camera的采集帧率
        return 0;
    }

    @Override
    protected int setResolution(int width, int height) {
        Log.d(TAG, "setResolution width: " + width + ", height: " + height);
        mWidth = width;
        mHeight = height;

        return 0;
    }

    @Override
    protected int setFrontCam(int bFront) {
        return 0;
    }

    @Override
    protected int setView(View view) {
        Log.d(TAG, "setView()");
        if (mView != null) {
            if (mView.getSurfaceTextureListener().equals(this)) {
                mView.setSurfaceTextureListener(null);
            }
            mView = null;
        }
        mView = (TextureView) view;
        if (mView != null) {
            mView.setSurfaceTextureListener(this);
            if (mView.isAvailable()) {

            }
            //为UVC 摄像头设置预览视图
            RtspCameraHelper.sharedInstance().setCameraView(mView);
        }
        return 0;
    }

    @Override
    protected int setViewMode(int mode) {
        return 0;
    }

    @Override
    protected int setViewRotation(int rotation) {

        return 0;
    }

    @Override
    protected int setCaptureRotation(int rotation) {
        mRotation = rotation;
        return 0;
    }

    // 启动预览，ZEGO SDK方法
    @Override
    protected int startPreview() {
        Log.d(TAG, "startPreview()");
        // 启动摄像头采集
        RtspCameraHelper.sharedInstance().startCameraCapture(mWidth, mHeight, mFrameRate);
        return 0;
    }

    // 停止预览，ZEGO SDK方法
    @Override
    protected int stopPreview() {
        Log.d(TAG, "stopPreview()");
        // 停止摄像头采集
        RtspCameraHelper.sharedInstance().stopCameraCapture();
        return 0;
    }

    @Override
    protected int enableTorch(boolean b) {
        return 0;
    }

    @Override
    protected int takeSnapshot() {
        return 0;
    }

    @Override
    protected int setPowerlineFreq(int i) {
        return 0;
    }

    // 摄像头采集数据帧回调
    public final H264FrameCallBack mIFrameCallback = new H264FrameCallBack() {

        @Override
        public void onFrameDataReceived(byte[] data, int length, int timeStamp, boolean isKeyFrame,
            String var) {
            if (mClient == null) {
                return;
            }
            //Log.e(TAG, "******onFrame****** size:" + data.length + ", isKeyFrame:" + isKeyFrame + ", timeStamp:" + timeStamp);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (mAVCDecoder != null) {
                    // 为解码提供视频数据，时间戳
                    mAVCDecoder.inputFrameToDecoder(data, (long) timeStamp);
                }
            } else {
                decode(data);
            }
        }
    };

    /**
     * h264 解码为 yuv
     * @param h264Data
     */
    public void decode(byte[] h264Data) {
        //设置解码等待时间，0为不等待，-1为一直等待
        int inputBufferId = mAVCDecoder.getMediaCodec().dequeueInputBuffer(DEFAULT_TIMEOUT_US);
        Log.e("输入缓冲区索引", "inputBufferId=" + inputBufferId);
        if (inputBufferId >= 0) { // 0
            ByteBuffer inputBuffer;
            // 获取MediaCodec的输入流
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                inputBuffer = mAVCDecoder.getMediaCodec().getInputBuffer(inputBufferId);
            } else {
                inputBuffer = mAVCDecoder.getMediaCodec().getInputBuffers()[inputBufferId];
            }
            if (inputBuffer != null) {
                inputBuffer.clear();
                // 填充数据到输入流
                inputBuffer.put(h264Data, 0, h264Data.length);
                // 入空数据进MediaCodec队列
                mAVCDecoder.getMediaCodec().queueInputBuffer(inputBufferId, 0, h264Data.length, 0, 0);
            }
        }

        //获取MediaCodec的输出流
        int outputBufferId = mAVCDecoder.getMediaCodec().dequeueOutputBuffer(mAVCDecoder.getBufferInfo(), DEFAULT_TIMEOUT_US);
        Log.e("输出缓冲区索引", "outputBufferId=" + outputBufferId);
        ByteBuffer outputBuffer;
        while (outputBufferId > 0) { // -1
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                outputBuffer = mAVCDecoder.getMediaCodec().getOutputBuffer(outputBufferId);
            } else {
                outputBuffer = mAVCDecoder.getMediaCodec().getOutputBuffers()[outputBufferId];
            }
            if (outputBuffer != null) {
                outputBuffer.position(0);
                outputBuffer.limit(mAVCDecoder.getBufferInfo().offset + mAVCDecoder.getBufferInfo().size);
                byte[] yuvData = new byte[outputBuffer.remaining()];
                outputBuffer.get(yuvData);

                 MediaFormat outputFormat = mAVCDecoder.getMediaCodec().getOutputFormat();
                switch (outputFormat.getInteger(MediaFormat.KEY_COLOR_FORMAT)) {
                    case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV411Planar:
                        Log.e("解码后的帧格式", "COLOR_FormatYUV411Planar");
                        break;
                    case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV411PackedPlanar:
                        Log.e("解码后的帧格式", "COLOR_FormatYUV411PackedPlanar");
                        break;
                    case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420PackedPlanar:
                        Log.e("解码后的帧格式", "COLOR_FormatYUV420PackedPlanar");
                        break;
                    case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar:
                        Log.e("解码后的帧格式", "COLOR_FormatYUV420SemiPlanar");
                        break;
                    case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420PackedSemiPlanar:
                        Log.e("解码后的帧格式", "COLOR_FormatYUV420PackedSemiPlanar");
                        break;
                    case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar:
                        //{image-data=java.nio.HeapByteBuffer[pos=0 lim=104 cap=104], mime=video/raw, crop-top=0, crop-right=359, slice-height=640, color-format=19, height=704, width=1280, crop-bottom=639, crop-left=0, stride=360}
                        Log.e("解码后的帧格式", "COLOR_FormatYUV420Planar");
                        break;
                    default:
                        //{crop-right=1279, color-format=2141391876, slice-height=1024, image-data=java.nio.HeapByteBuffer[pos=0 lim=104 cap=104], mime=video/raw, hdr-static-info=java.nio.HeapByteBuffer[pos=0 lim=25 cap=25], stride=1536, color-standard=2, color-transfer=3, crop-bottom=703, crop-left=0, width=1280, color-range=2, crop-top=0, height=704}
                        Log.e("解码后的帧格式", outputFormat.toString()); //2141391876
                        break;
                }

                //释放缓存区，并把缓存区发送到 Surface 渲染
                long now = System.currentTimeMillis() * 1000;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mAVCDecoder.getMediaCodec().releaseOutputBuffer(outputBufferId, now);
                } else {
                    mAVCDecoder.getMediaCodec().releaseOutputBuffer(outputBufferId, true);
                }
                outputBuffer.clear();
            }
            outputBufferId = mAVCDecoder.getMediaCodec().dequeueOutputBuffer(mAVCDecoder.getBufferInfo(), DEFAULT_TIMEOUT_US);
        }
    }

    // TextureView.SurfaceTextureListener 回调
    @Override public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.d(TAG, "onSurfaceTextureAvailable()");
        //mTexture = surface;
        // 启动采集
        startCapture();
        // 不能使用 restartCam ，因为切后台时再切回时，isCameraRunning 已经被置为 false
        //restartCam();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        //mTexture = surface;
        // 视图size变化时重启camera
        //restartCam();
    }

    @Override public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        //mTexture = null;
        // 停止采集
        stopCapture();
        return true;
    }

    @Override public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
