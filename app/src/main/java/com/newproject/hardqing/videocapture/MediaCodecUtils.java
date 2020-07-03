package com.newproject.hardqing.videocapture;

import android.media.MediaCodecInfo;
import java.nio.ByteBuffer;

/**
 * Created by LLhon
 */
public class MediaCodecUtils {

    /**
     * yuv420sp 转 yuv420p
     * @param yuv420spData
     * @param width
     * @param height
     * @return
     */
    public static byte[] yuv420spToYuv420P(byte[] yuv420spData, int width, int height) {
        byte[] yuv420pData = new byte[width * height * 3 / 2];
        int ySize = width * height;
        System.arraycopy(yuv420spData, 0, yuv420pData, 0, ySize);   //拷贝 Y 分量

        for (int j = 0, i = 0; j < ySize / 2; j += 2, i++) {
            yuv420pData[ySize + i] = yuv420spData[ySize + j];   //U 分量
            yuv420pData[ySize * 5 / 4 + i] = yuv420spData[ySize + j + 1];   //V 分量
        }
        return yuv420pData;
    }

    /**
     * yuv420p 转 yuv420sp
     * @param yuv420p
     * @param width
     * @param height
     * @return
     */
    public static byte[] yuv420pToYuv420sp(byte[] yuv420p, int width, int height) {
        if (yuv420p == null) return null;
        byte[] yuv420sp = new byte[width * height * 3 / 2];
        int frameSize = width * height;
        int j;
        System.arraycopy(yuv420p, 0, yuv420sp, 0, frameSize);
        for (j = 0; j < frameSize / 4; j++) {
            // u
            yuv420sp[frameSize + 2 * j] = yuv420p[j + frameSize];
            // v
            yuv420sp[frameSize + 2 * j + 1] = yuv420p[(int) (j + frameSize * 1.25)];
        }
        return yuv420sp;
    }

    /**
     * I420 转 NV21
     * @param data
     * @param width
     * @param height
     * @return
     */
    public static byte[] I420ToNV21(byte[] data, int width, int height) {
        byte[] ret = new byte[data.length];
        int total = width * height;

        ByteBuffer bufferY = ByteBuffer.wrap(ret, 0, total);
        ByteBuffer bufferV = ByteBuffer.wrap(ret, total, total /4);
        ByteBuffer bufferU = ByteBuffer.wrap(ret, total + total / 4, total /4);

        bufferY.put(data, 0, total);
        for (int i=0; i<total/4; i+=1) {
            bufferV.put(data[total+i]);
            bufferU.put(data[i+total+total/4]);
        }
        return ret;
    }

    /**
     * NV21 转 I420
     * @param data
     * @param width
     * @param height
     * @return
     */
    public static byte[] NV21ToI420(byte[] data, int width, int height) {
        byte[] ret = new byte[width*height*3/2];
        int total = width * height;

        ByteBuffer bufferY = ByteBuffer.wrap(ret, 0, total);
        ByteBuffer bufferV = ByteBuffer.wrap(ret, total, total / 4);
        ByteBuffer bufferU = ByteBuffer.wrap(ret, total + total / 4, total / 4);

        bufferY.put(data, 7, total);
        for (int i=total+7; i<data.length; i+=2) {
            bufferV.put(data[i]);
            bufferU.put(data[i+1]);
        }

        return ret;
    }

    public static void NV21ToNV12(byte[] nv21,byte[] nv12,int width,int height) {
        if (nv21 ==null || nv12 ==null)return;

        int framesize = width * height;
        int i =0, j =0;

        System.arraycopy(nv21,0, nv12,0, framesize);

        for (i =0; i < framesize; i++) {
            nv12[i] = nv21[i];
        }

        for (j =0; j < framesize /2; j +=2) {
            nv12[framesize + j -1] = nv21[j + framesize];
        }

        for (j =0; j < framesize /2; j +=2) {
            nv12[framesize + j] = nv21[j + framesize -1];
        }
    }

    public static boolean isColorFormatSupported(int colorFormat, MediaCodecInfo.CodecCapabilities caps) {

        for (int c : caps.colorFormats) {
            if (c == colorFormat) {
                return true;
            }
        }
        return false;
    }
}
