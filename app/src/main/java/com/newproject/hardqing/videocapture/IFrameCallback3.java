package com.newproject.hardqing.videocapture;

import java.nio.ByteBuffer;

/**
 * Created by LLhon
 */
public interface IFrameCallback3 {

    void onFrame(boolean isKeyFrame, long timeStamp, byte[] data);
}
