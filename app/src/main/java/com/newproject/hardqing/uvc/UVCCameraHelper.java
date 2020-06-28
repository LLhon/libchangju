package com.newproject.hardqing.uvc;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

public class UVCCameraHelper {

    private static UVCCameraHelper uvcCameraHelper = null;

    private ZegoUVCCamera mUVCCamera;
    private IFrameCallback mIFrameCallback = null;
    private SurfaceView mCameraView = null;
    private final Object mSync = new Object();
    // USB Manager
    private ZegoUSBMonitor mUSBMonitor;
    private ZegoUSBMonitor.UsbControlBlock musbControlBlock = null;
    private boolean isUsbConnected = false;

    private Handler mHandler;
    private HandlerThread mHandlerThread;

    public static UVCCameraHelper sharedInstance() {
        synchronized (UVCCameraHelper.class) {
            if (uvcCameraHelper == null) {
                uvcCameraHelper = new UVCCameraHelper();
            }
        }
        return uvcCameraHelper;
    }

    public void init() {
        mHandlerThread = new HandlerThread("UVCHandle");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    public void uninit() {
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

        if (mUSBMonitor != null) {
            mUSBMonitor.destroy();
            mUSBMonitor = null;
        }

        uvcCameraHelper = null;
    }

    private void setUsbConnected(boolean isUsbConnected) {
        this.isUsbConnected = isUsbConnected;
    }

    private void setUsbControlBlock(ZegoUSBMonitor.UsbControlBlock ctrlBlk) {
        this.musbControlBlock = ctrlBlk;
    }

    // 设置采集数据帧回调监听
    public void setFrameCallback(IFrameCallback callback) {
        this.mIFrameCallback = callback;
    }

    // 设置采集预览视图
    public void setCameraView(SurfaceView view) {
        this.mCameraView = view;
        if (mUVCCamera != null) {
            mUVCCamera.SetView(mCameraView.getHolder().getSurface());
        }
    }


    // 启动 UVC 摄像头的采集
    public void startCameraCapture(final int width, final int height, final int frameRate) {

        stopCameraCapture();

        if (isUsbConnected && musbControlBlock != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    synchronized (mSync) {
                        if (mUVCCamera == null) {
                            final ZegoUVCCamera camera = new ZegoUVCCamera();
                            try {
                                camera.Open(musbControlBlock);
                            } catch (UnsupportedOperationException e) {
                                e.printStackTrace();
                            }

                            mUVCCamera = camera;
                        }
                    }
                    if (mUVCCamera == null || mCameraView == null) {
                        return;
                    }
                    SurfaceHolder surfaceHolder = mCameraView.getHolder();
                    if (surfaceHolder == null || mUVCCamera == null) {
                        return;
                    }
                    mUVCCamera.setFrameCallback(mIFrameCallback);
                    mUVCCamera.SetFormat(width, height, frameRate);
                    mUVCCamera.SetView(surfaceHolder.getSurface());
                    mUVCCamera.StartCapture();
                }
            });
        }
    }

    // 关闭 UVC 摄像头的采集
    public void stopCameraCapture() {
        synchronized (mSync) {
            if (mUVCCamera != null) {
                try {
                    mUVCCamera.StopCapture();
                    mUVCCamera.setFrameCallback(null);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 释放 UVC 摄像头
    public synchronized void releaseCamera() {
        synchronized (mSync) {
            if (mUVCCamera != null) {
                try {
                    mUVCCamera.StopCapture();
                    mUVCCamera.setFrameCallback(null);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
                mUVCCamera = null;
            }
        }
    }

    public void initUSBMonitor(Context context, final OnMyDevConnectListener listener) {

        mUSBMonitor = new ZegoUSBMonitor(context, new ZegoUSBMonitor.OnDeviceConnectListener() {

            // called by checking usb device
            // do request device permission
            @Override
            public void onAttach(UsbDevice device) {
                if (listener != null) {
                    listener.onAttachDev(device);
                }
            }

            // called by taking out usb device
            // do close camera
            @Override
            public void onDettach(UsbDevice device) {
                UVCCameraHelper.sharedInstance().setUsbConnected(false);
                UVCCameraHelper.sharedInstance().releaseCamera();
                if (listener != null) {
                    listener.onDettachDev(device);
                }
            }

            // called by connect to usb camera
            // do open camera,start previewing
            @Override
            public void onConnect(UsbDevice device, ZegoUSBMonitor.UsbControlBlock ctrlBlock, boolean createNew) {
                UVCCameraHelper.sharedInstance().setUsbConnected(true);
                UVCCameraHelper.sharedInstance().releaseCamera();
                UVCCameraHelper.sharedInstance().setUsbControlBlock(ctrlBlock);

                if (listener != null) {
                    listener.onConnectDev(device);
                }
            }

            // called by disconnect to usb camera
            // do nothing
            @Override
            public void onDisconnect(UsbDevice device, ZegoUSBMonitor.UsbControlBlock ctrlBlock) {
                UVCCameraHelper.sharedInstance().setUsbConnected(false);
//            UVCCameraHelper.sharedInstance().setUsbControlBlock(ctrlBlock);
                UVCCameraHelper.sharedInstance().releaseCamera();

                if (listener != null) {
                    listener.onDisConnectDev(device);
                }
            }

            @Override
            public void onCancel(UsbDevice device) {
                if (listener != null) {
                    listener.onCancelDev(device);
                }
            }
        });
    }

    public List<UsbDevice> getDeviceList() {
        if (mUSBMonitor != null) {
            return mUSBMonitor.getDeviceList();
        } else {
            return null;
        }
    }

    public void registerUSB() {
        if (mUSBMonitor != null) {
            mUSBMonitor.register();
        }
    }

    public void unregisterUSB() {
        if (mUSBMonitor != null) {
            mUSBMonitor.unregister();
        }
    }

    public interface OnMyDevConnectListener {
        void onAttachDev(UsbDevice device);

        void onDettachDev(UsbDevice device);

        void onConnectDev(UsbDevice device);

        void onDisConnectDev(UsbDevice device);

        void onCancelDev(UsbDevice device);
    }

    public void setUVCCameraFrameCallback() {
        if (mUVCCamera != null) {
            mUVCCamera.setFrameCallback(mIFrameCallback);
        }
    }

    public void removeUVCCameraFrameCallback() {
        if (mUVCCamera != null) {
            mUVCCamera.setFrameCallback(null);
        }
    }
}
