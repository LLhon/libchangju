package com.newproject.hardqing.uvc;

import com.zego.zegoavkit2.ZegoVideoCaptureDevice;
import com.zego.zegoavkit2.ZegoVideoCaptureFactory;

public class VideoCaptureFactoryDemo extends ZegoVideoCaptureFactory {

    private ZegoVideoCaptureDevice mDevice = null;

    @Override
    protected ZegoVideoCaptureDevice create(String device_id) {
        mDevice = new VideoCaptureFromUVCCamera(); //mContext

        return mDevice;
    }

    @Override
    protected void destroy(ZegoVideoCaptureDevice zegoVideoCaptureDevice) {
        mDevice = null;
    }
}
