package com.newproject.hardqing.videocapture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.usb.UsbDevice;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.llhon.rtspdemo.H264FrameCallBack;
import com.llhon.rtspdemo.RtspClient;
import com.newproject.hardqing.base.BaseApplication;
import com.newproject.hardqing.util.FileUtil;
import com.newproject.hardqing.uvc.ZegoUVCCamera;
import com.zego.zegoavkit2.entities.VideoFrame;
import com.zego.zegoavkit2.enums.VideoPixelFormat;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by LLhon
 */
public class RtspCameraHelper {

    private static final String TAG = "RtspCameraHelper";
    private static RtspCameraHelper uvcCameraHelper = null;

    private H264FrameCallBack mIFrameCallback = null;
    private TextureView mCameraView = null;
    private final Object mSync = new Object();
    private RtspClient mRtspClient;
    private Handler mHandler;
    private HandlerThread mHandlerThread;

    private ByteBuffer[] mByteBuffers = new ByteBuffer[4];
    private ByteBuffer mByteBuffer;
    private String mStreamID = com.zego.zegoavkit2.ZegoConstants.ZegoVideoDataMainPublishingStream;
    int mCount = 0;
    // 预设分辨率宽
    int mWidth = 640;
    // 预设分辨率高
    int mHeight = 480;
    // 预设采集帧率
    int mFrameRate = 30;
    // 默认不旋转
    int mRotation = 0;

    private boolean initBmp = false;
    private int mPixels[];
    private Bitmap mBitmap;
    private int mNum = 0;
    private Disposable mDisposable;
    private boolean testInitBmp = false;
    private int mTestPixels[];
    private Bitmap mTestBitmap;

    public static RtspCameraHelper sharedInstance() {
        synchronized (RtspCameraHelper.class) {
            if (uvcCameraHelper == null) {
                uvcCameraHelper = new RtspCameraHelper();
            }
        }
        return uvcCameraHelper;
    }

    public void init() {
        Log.d(TAG, "init()");
        mHandlerThread = new HandlerThread("UVCHandle");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    public void uninit() {
        Log.d(TAG, "uninit()");
        // 释放camera
        releaseCamera();

        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;

        if (Build.VERSION.SDK_INT >= 18) {
            mHandlerThread.quitSafely();
        } else {
            mHandlerThread.quit();
        }
        mHandlerThread = null;

        uvcCameraHelper = null;
    }

    // 设置采集数据帧回调监听
    public void setFrameCallback(H264FrameCallBack callback) {
        this.mIFrameCallback = callback;
    }

    // 设置采集预览视图
    public void setCameraView(TextureView view) {
        this.mCameraView = view;
    }

    //设置流id
    public void setStreamID(String streamID) {
        mStreamID = streamID;
    }

    // 启动 UVC 摄像头的采集
    public void startCameraCapture(final int width, final int height, final int frameRate) {
        Log.d(TAG, "startCameraCapture");
        mWidth = width;
        mHeight = height;
        mFrameRate = frameRate;

        mHandler.post(new Runnable() {
            @Override public void run() {
                //欣豪网络摄像头专用代码
                /*RtspClient.probeDevices(new RtspClient.ProbeDevicesListener() {
                    @Override public void probeResult(ArrayList<String> arrayList) {
                        if (arrayList == null || arrayList.size() == 0) {
                            return;
                        }
                        synchronized (mSync) {
                            if (mRtspClient == null) {
                                //连接方式：tcp, udp
                                //mRtspClient = new RtspClient("udp", "rtsp://192.168.101.112:554/live.sdp");
                                String ipAddress = "rtsp://" + arrayList.get(0) + ":554/live.sdp";
                                //String ipAddress = "rtsp://" + arrayList.get(0) + ":554/stream0";
                                mRtspClient = new RtspClient("udp", ipAddress);
                                Log.e(TAG, "ipAddress:" + ipAddress);
                            }
                        }
                        mRtspClient.setH264FrameCallBack(mH264FrameCallBack);
                        mRtspClient.start();
                        Log.e(TAG, "RtspClient...thread:" + Thread.currentThread().getId());
                    }
                });*/

                synchronized (mSync) {
                    if (mRtspClient == null) {
                        mRtspClient = new RtspClient();
                    }
                }
                List<String> ipList = mRtspClient.getIP();
                if (ipList == null || ipList.size() == 0) {
                    return;
                }
                Log.e(TAG, "搜索到网络摄像头的IP地址:" + ipList.toString()); //rtsp://192.168.101.159:8554/stream0
                mRtspClient.setH264FrameCallBack(mIFrameCallback);
                mRtspClient.start(ipList.get(0));
            }
        });
    }

    private void doPreview(byte[] frame, int width, int height) {
        if(!initBmp){
            initBmp = true;
            int area = width * height;
            mPixels= new int[area];
            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        int area = width * height;
        for (int i = 0; i < area; i++) {
            int r = frame[4 * i];
            int g = frame[4 * i + 1];
            int b = frame[4 * i + 2];
            if (r < 0) r += 255;
            if (g < 0) g += 255;
            if (b < 0) b += 255;
            mPixels[i] = Color.rgb(r, g, b);
        }

        // int[] argb = I420toARGB(frame, width, height);
        // Log.i("rtspclientAcitvity","start show bitmap.");
        // 如果不显示，注释下面两行
        mBitmap.setPixels(mPixels,0,width,0,0,width,height);
        //mBitmap.setPixels(argb,0,width,0,0,width,height);
        //mIvPreview.setImageBitmap(mBitmap);

        drawFrame(frame, width, height);
    }

    /**
     * bitmap 渲染到 surfaceview
     * @param data
     * @param width
     * @param height
     */
    private void drawFrame(byte[] data, int width, int height) {
        //Canvas canvas = mCameraView.getHolder().lockCanvas();
        //canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
        //canvas.drawBitmap(mBitmap, 0, 0, null);
        //mCameraView.getHolder().unlockCanvasAndPost(canvas);
    }

    // 关闭 UVC 摄像头的采集
    public void stopCameraCapture() {
        Log.d(TAG, "stopCameraCapture()");
        if (mRtspClient != null) {
            mRtspClient.stop();
            mRtspClient = null;
        }
    }

    // 释放 UVC 摄像头
    public synchronized void releaseCamera() {
        Log.d(TAG, "releaseCamera()");
        if (mRtspClient != null) {
            mRtspClient.stop();
            mRtspClient = null;
        }
    }

    public void setUVCCameraFrameCallback() {

    }

    public void removeUVCCameraFrameCallback() {

    }

    public static Bitmap nv21ToBitmap(byte[] nv21, int width, int height) {
        Bitmap bitmap = null;
        try {
            YuvImage image = new YuvImage(nv21, ImageFormat.NV21, width, height, null);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compressToJpeg(new Rect(0, 0, width, height), 80, stream);
            bitmap = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void saveBitmap(byte[] frame, int width, int height) {
        if(!testInitBmp){
            testInitBmp = true;
            int area = width * height;
            mTestPixels= new int[area];
            mTestBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        int area = width * height;
        for (int i = 0; i < area; i++) {
            int r = frame[4 * i];
            int g = frame[4 * i + 1];
            int b = frame[4 * i + 2];
            if (r < 0) r += 255;
            if (g < 0) g += 255;
            if (b < 0) b += 255;
            mTestPixels[i] = Color.rgb(r, g, b);
        }

        // int[] argb = I420toARGB(frame, width, height);
        // Log.i("rtspclientAcitvity","start show bitmap.");
        // 如果不显示，注释下面两行
        mTestBitmap.setPixels(mTestPixels,0,width,0,0,width,height);
        //mBitmap.setPixels(argb,0,width,0,0,width,height);
        //mIvPreview.setImageBitmap(mBitmap);

        try {
            String fileName = BaseApplication.getApp().getFilesDir().getAbsolutePath()+ "/pic"+ mNum + ".jpg";
            Log.i("rtspclientAcitvity","start save bitmap: " +fileName);
            File file = new File(fileName);
            FileOutputStream out = new FileOutputStream(file);
            mTestBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();
            mNum +=1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
