package com.newproject.hardqing.uvc;

import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;

public class ZegoUVCCamera {
    private static final String TAG = "ZegoUVCCamera";
    private static final int CAMERA_STOP_TIMEOUT_MS = 7000;
    private static final String DEFAULT_USBFS = "/dev/bus/usb";

    int mWidth = 640;
    int mHeight = 480;
    int mFrameRate = 15;
    int mRotation = 0;
    boolean mObjInit = false;

    private ZegoUSBMonitor.UsbControlBlock mCtrlBlock;

    private static boolean isLoaded;
    static {
//        if (!isLoaded) {
            System.loadLibrary("usb100");
            System.loadLibrary("uvc");
            System.loadLibrary("ZegoUVCCamera");
//            isLoaded = true;
//        }
    }

    public ZegoUVCCamera() {
    }

    public boolean IsInit() {
        return mObjInit;
    }

    public synchronized void Open(final ZegoUSBMonitor.UsbControlBlock ctrlBlock) {
        int result;
        try {
            mCtrlBlock = ctrlBlock.clone();
            result = nativeCreate(
                    mCtrlBlock.getVenderId(), mCtrlBlock.getProductId(),
                    mCtrlBlock.getFileDescriptor(),
                    mCtrlBlock.getBusNum(),
                    mCtrlBlock.getDevNum(),
                    getUSBFSName(mCtrlBlock));
        } catch (final Exception e) {
            Log.w(TAG, e);
            result = -1;
        }
        if (result != 0) {
            throw new UnsupportedOperationException("open failed:result=" + result);
        }
        mObjInit = result == 0;
    }

    public synchronized int SetFormat(int width, int height, int rate)
    {
        if (!mObjInit) return -1;
        return nativeSetVideoFormat(width, height, rate);
    }

    public synchronized int StartCapture()
    {
        if (!mObjInit) return -1;
        return nativeStartCapture();
    }

    public synchronized int StopCapture()
    {
        if (!mObjInit) return -1;
        return nativeStopCapture();
    }

    public synchronized void SetView(final Surface surface) {
        nativeSetView(surface);
    }

    public synchronized int setFrameCallback(final IFrameCallback callback) {
        if (!mObjInit) return -1;
        return nativeSetFrameCallback(callback);
    }

    private final String getUSBFSName(final ZegoUSBMonitor.UsbControlBlock ctrlBlock) {
        String result = null;
        final String name = ctrlBlock.getDeviceName();
        final String[] v = !TextUtils.isEmpty(name) ? name.split("/") : null;
        if ((v != null) && (v.length > 2)) {
            final StringBuilder sb = new StringBuilder(v[0]);
            for (int i = 1; i < v.length - 2; i++)
                sb.append("/").append(v[i]);
            result = sb.toString();
        }
        if (TextUtils.isEmpty(result)) {
            Log.w(TAG, "failed to get USBFS path, try to use default path:" + name);
            result = DEFAULT_USBFS;
        }
        return result;
    }
    private static native int nativeCreate(int venderId, int productId, int fileDescriptor, int busNum, int devAddr, String usbfs);
    private static native int nativeDestroy();
    private static native int nativeSetVideoFormat(int width, int height, int rate);
    private static native int nativeStartCapture();
    private static native int nativeStopCapture();
    private static native int nativeSetView(final Surface surface);
    private static native int nativeSetFrameCallback(final IFrameCallback callback);
}
