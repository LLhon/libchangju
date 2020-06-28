package com.newproject.hardqing.uvc;

import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.zego.zegoavkit2.ZegoVideoCaptureDevice;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * uvc 摄像头采集，由usb摄像头的插拔管理摄像头采集的开关
 */
public class VideoCaptureFromUVCCamera extends ZegoVideoCaptureDevice implements SurfaceHolder.Callback {

    ZegoVideoCaptureDevice.Client mClient = null;

    // 预设分辨率宽
    int mWidth = 640;
    // 预设分辨率高
    int mHeight = 480;
    // 预设采集帧率
    int mFrameRate = 15;
    // 默认不旋转
    int mRotation = 0;

    private SurfaceView mCameraView;
    private SurfaceHolder mSurfaceHolder;

    @Override
    protected void allocateAndStart(Client client) {
        mClient = client;

        // 启动摄像头相关的线程
        UVCCameraHelper.sharedInstance().init();

        // 设置视频帧回调监听
        UVCCameraHelper.sharedInstance().setFrameCallback(mIFrameCallback);
    }

    @Override
    protected void stopAndDeAllocate() {

        // 释放摄像头并处理采集线程
        UVCCameraHelper.sharedInstance().uninit();

        mClient.destroy();
        mClient = null;
    }

    @Override
    protected int startCapture() {

//        // 启动摄像头采集
//        UVCCameraHelper.sharedInstance().startCameraCapture(mWidth, mHeight, mFrameRate);

        return 0;
    }

    @Override
    protected int stopCapture() {

//        // 停止摄像头采集
//        UVCCameraHelper.sharedInstance().stopCameraCapture();

        return 0;
    }

    @Override
    protected int supportBufferType() {
        return PIXEL_BUFFER_TYPE_MEM;
    }

    @Override
    protected int setFrameRate(int frameRate) {
        mFrameRate = frameRate;

        // 更新camera的采集帧率
        return 0;
    }

    @Override
    protected int setResolution(int width, int height) {
        Log.e("test", "setResolution width: " + width + ", height: " + height);
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
        mCameraView = (SurfaceView) view;

        if (mCameraView != null) {
            mSurfaceHolder = mCameraView.getHolder();
            mSurfaceHolder.addCallback(this);
            //为UVC 摄像头设置预览视图
            UVCCameraHelper.sharedInstance().setCameraView(mCameraView);
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

    @Override
    protected int startPreview() {
        LogUtils.d("LivePlayActivity VideoCaptureFromUVCCamera 00000 myDevConnectListener  startPreview()  ");
        // 启动摄像头采集
        UVCCameraHelper.sharedInstance().startCameraCapture(mWidth, mHeight, mFrameRate);
        return 0;
    }

    @Override
    protected int stopPreview() {
        // 停止摄像头采集
        UVCCameraHelper.sharedInstance().stopCameraCapture();
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
    public final IFrameCallback mIFrameCallback = new IFrameCallback() {
        @Override
        public void onFormat(int width, int height, int format) {
            Log.i("zego", "onFormat: width[" + width + "]height[" + height + "]format[" + format + "]");
        }

        @Override
        public void onFrame(final ByteBuffer frame) {
            Log.i("test", "********** 收到onFrame size:" + frame.limit());
            // 使用采集视频帧信息构造VideoCaptureFormat
            VideoCaptureFormat format = new VideoCaptureFormat();
            format.width = mWidth;
            format.height = mHeight;
            format.strides[0] = mWidth * 4;
            format.rotation = mRotation;
            format.pixel_format = PIXEL_FORMAT_RGBA32; // yuv

            long now = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                now = SystemClock.elapsedRealtimeNanos();
            } else {
                now = TimeUnit.MILLISECONDS.toNanos(SystemClock.elapsedRealtime());
            }
            // 将采集的数据传给ZEGO SDK
            mClient.onByteBufferFrameCaptured(frame, mFrameRate, format, now, 1000000000);
        }
    };

    // surface 回调监听

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        UVCCameraHelper.sharedInstance().setCameraView(mCameraView);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}
