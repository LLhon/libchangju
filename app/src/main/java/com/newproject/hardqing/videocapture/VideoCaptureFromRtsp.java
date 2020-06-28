package com.newproject.hardqing.videocapture;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
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
import com.newproject.hardqing.uvc.IFrameCallback;
import com.newproject.hardqing.uvc.UVCCameraHelper;
import com.zego.zegoavkit2.ZegoVideoCaptureDevice;
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
 * 网络摄像头采集
 * Created by LLhon
 */
public class VideoCaptureFromRtsp extends ZegoVideoCaptureDevice implements SurfaceHolder.Callback {

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
    private SurfaceView mCameraView;
    private SurfaceHolder mSurfaceHolder;
    private ByteBuffer mEncodedBuffer;

    // Arbitrary queue depth.  Higher number means more memory allocated & held,
    // lower number means more sensitivity to processing time in the client (and
    // potentially stalling the capturer if it runs out of buffers to write to).
    private static final int NUMBER_OF_CAPTURE_BUFFERS = 3;
    private final Set<byte[]> queuedBuffers = new HashSet<byte[]>();
    private int mFrameSize = 0;

    /**
     * 初始化资源，必须实现
     * @param client 通知ZEGO SDK采集结果的客户端
     */
    @Override
    protected void allocateAndStart(Client client) {
        Log.d(TAG, "allocateAndStart()");
        mClient = client;

        // 启动摄像头相关的线程
        RtspCameraHelper.sharedInstance().init();

        // 设置视频帧回调监听
        RtspCameraHelper.sharedInstance().setFrameCallback(mIFrameCallback);

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
        //码流
        return PIXEL_BUFFER_TYPE_ENCODED_FRAME;
        // 内存拷贝，yuv格式
        //return PIXEL_BUFFER_TYPE_MEM;
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
        mCameraView = (SurfaceView) view;

        if (mCameraView != null) {
            mSurfaceHolder = mCameraView.getHolder();
            mSurfaceHolder.addCallback(this);

            //为UVC 摄像头设置预览视图
            RtspCameraHelper.sharedInstance().setCameraView(mCameraView);
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


            //已解码yuv方式
           /* // 使用采集视频帧信息构造VideoCaptureFormat
            VideoCaptureFormat format = new VideoCaptureFormat();
            format.width = mWidth;
            format.height = mHeight;
            format.strides[0] = mWidth * 4;
            //format.strides[1] = mWidth;
            format.rotation = mRotation;
            format.pixel_format = PIXEL_FORMAT_RGBA32; // 采集格式

            long now = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                now = SystemClock.elapsedRealtimeNanos();
            } else {
                now = TimeUnit.MILLISECONDS.toNanos(SystemClock.elapsedRealtime());
            }
            mFrameSize = mWidth * mHeight * 4;
            // 将采集的数据传给ZEGO SDK
            mClient.onByteBufferFrameCaptured(data, mFrameSize, format, now, 1000000000);*/

            //未解码码流方式
            // 编码器相关信息
            VideoCodecConfig config = new VideoCodecConfig();
            // Android端的编码类型必须选用 ZegoVideoCodecTypeAVCANNEXB
            config.codec_type = ZegoVideoCodecType.ZegoVideoCodecTypeAVCANNEXB;
            config.width = mWidth;
            config.height = mHeight;

            if (mEncodedBuffer == null) {
                mEncodedBuffer = ByteBuffer.allocateDirect(mWidth*mHeight*3/2);
            }
            if (data.length > mEncodedBuffer.capacity()) {
                mEncodedBuffer = ByteBuffer.allocateDirect(data.length);
            }
            mEncodedBuffer.clear();
            // 将编码后的数据存入ByteBuffer中
            mEncodedBuffer.put(data, 0, data.length);

            // 将编码后的视频数据传给ZEGO SDK，需要告知SDK当前传递帧是否为视频关键帧，以及当前视频帧的时间戳
            mClient.onEncodedFrameCaptured(mEncodedBuffer, data.length, config, isKeyFrame, (double) timeStamp);
        }
    };

    // surface 回调监听

    @Override public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated()");
        RtspCameraHelper.sharedInstance().setCameraView(mCameraView);
    }

    @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed()");
    }
}
