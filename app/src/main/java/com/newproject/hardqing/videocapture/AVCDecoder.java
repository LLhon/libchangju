package com.newproject.hardqing.videocapture;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Surface;
import com.newproject.hardqing.util.FileUtil;
import com.zego.zegoavkit2.ZegoVideoCaptureDevice;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import static android.media.MediaCodec.MetricsConstants.MIME_TYPE;
import static com.zego.zegoavkit2.ZegoVideoCaptureDevice.PIXEL_BUFFER_TYPE_MEM;
import static com.zego.zegoavkit2.ZegoVideoCaptureDevice.PIXEL_FORMAT_RGBA32;

/**
 * AVCDecoder
 *
 * AVCANNEXB 模式解码器
 * 此类的作用是解码 SDK 抛出的未解码视频数据
 * 开发者可参考该类的代码实现接收 SDK 抛出的未解码数据并渲染
 *
 */
@TargetApi(23)
public class AVCDecoder {

    private final static String TAG = "Zego";
    private final static int CONFIGURE_FLAG_DECODE = 0;

    // 音视频编解码器组件
    private MediaCodec  mMediaCodec;
    // 媒体数据格式信息
    private MediaFormat mMediaFormat;
    private Surface     mSurface;
    // 预设分辨率宽
    int mWidth = 640;
    // 预设分辨率高
    int mHeight = 480;
    // 预设采集帧率
    int mFrameRate = 15;
    // 默认不旋转
    int mRotation = 0;
    private int mFrameSize = 0;
    // SDK 内部实现的、同样实现 ZegoVideoCaptureDevice.Client 协议的客户端，用于通知SDK采集结果
    ZegoVideoCaptureDevice.Client mClient = null;
    private MediaCodec.BufferInfo bufferInfo;

    /** 待解码数据信息
     *  包含时间戳和待解码的数据
     */
    static class DecodeInfo {
        public long timeStmp; // 纳秒
        public byte[] inOutData;
    }

    private final static ConcurrentLinkedQueue<DecodeInfo> mInputDatasQueue = new ConcurrentLinkedQueue<DecodeInfo>();
//    private final static ConcurrentLinkedQueue<DecodeInfo> mOutputDatasQueue = new ConcurrentLinkedQueue<DecodeInfo>();

    // 解码器回调
    private MediaCodec.Callback mCallback = new MediaCodec.Callback() {
        /**
         * 输入缓冲区回调，等待输入
         * 拿到一个输入buffer -> 填充数据 -> 入队交给 MediaCodec 处理
         * @param mediaCodec
         * @param inputBufferId
         */
        @Override
        public void onInputBufferAvailable(@NonNull MediaCodec mediaCodec, int inputBufferId) {
            try {
                // 获取MediaCodec的输入缓冲区buffer地址
                ByteBuffer inputBuffer = mediaCodec.getInputBuffer(inputBufferId);
                inputBuffer.clear();

                // 从视频帧数据队列取数据
                DecodeInfo decodeInfo = mInputDatasQueue.poll();

                if (decodeInfo != null) {
                    // 将视频帧数据写入MediaCodec的buffer中
                    inputBuffer.put(decodeInfo.inOutData, 0, decodeInfo.inOutData.length);
                    // 视频帧数据入MediaCodec队列，等待解码，需要传递线性递增的时间戳
                    mediaCodec.queueInputBuffer(inputBufferId, 0, decodeInfo.inOutData.length, decodeInfo.timeStmp * 1000, 0);
                } else {
                    long now = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        now = SystemClock.elapsedRealtimeNanos();
                    } else {
                        now = TimeUnit.MILLISECONDS.toNanos(SystemClock.elapsedRealtime());
                    }
                    // 入空数据进MediaCodec队列
                    mediaCodec.queueInputBuffer(inputBufferId, 0, 0, now * 1000, 0);
                }
            } catch (IllegalStateException exception) {
                Log.d(TAG, "encoder mediaCodec input exception: " + exception.getMessage());
            }
        }

        /**
         * 解码完成回调
         * 渲染由MediaCodec解码完成后进行，所以此回调中不处理解码后的数据
         * 拿出一个MediaCodec处理完的输出buffer -> 处理 -> 释放
         */
        @Override
        public void onOutputBufferAvailable(@NonNull MediaCodec mediaCodec, int id, @NonNull MediaCodec.BufferInfo bufferInfo) {
            //Log.d(TAG, "onOutputBufferAvailable()");
            // 根据buffer索引获取MediaCodec的输出缓冲区buffer地址
            ByteBuffer outputBuffer = mMediaCodec.getOutputBuffer(id);
            MediaFormat outputFormat = mMediaCodec.getOutputFormat(id);
            int width = outputFormat.getInteger("width");
            int height = outputFormat.getInteger("height");
            //Log.d(TAG, "decoder OutputBuffer, width: "+width+", height: "+height); //1280x704
            if(outputBuffer != null && bufferInfo.size > 0){
                outputBuffer.position(0);
                outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
                byte[] buffer = new byte[outputBuffer.remaining()];
                outputBuffer.get(buffer);

                /**
                 * 颜色格式映射：
                 * NV12(YUV420sp) —> COLOR_FormatYUV420PackedSemiPlanar
                 * NV21 -> COLOR_FormatYUV420SemiPlanar
                 * YV12(I420) -> COLOR_FormatYUV420Planar
                 */

                switch (outputFormat.getInteger(MediaFormat.KEY_COLOR_FORMAT)) {
                    case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV411Planar:
                        Log.e("解码后的帧格式", "COLOR_FormatYUV411Planar...width=" + outputFormat.getInteger(MediaFormat.KEY_WIDTH)
                            + ", height=" + outputFormat.getInteger(MediaFormat.KEY_HEIGHT));
                        break;
                    case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV411PackedPlanar:
                        Log.e("解码后的帧格式", "COLOR_FormatYUV411PackedPlanar...width=" + outputFormat.getInteger(MediaFormat.KEY_WIDTH)
                            + ", height=" + outputFormat.getInteger(MediaFormat.KEY_HEIGHT));
                        break;
                    case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420PackedPlanar:
                        Log.e("解码后的帧格式", "COLOR_FormatYUV420PackedPlanar...width=" + outputFormat.getInteger(MediaFormat.KEY_WIDTH)
                            + ", height=" + outputFormat.getInteger(MediaFormat.KEY_HEIGHT));
                        break;
                    case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar:
                        Log.e("解码后的帧格式", "COLOR_FormatYUV420SemiPlanar...width=" + outputFormat.getInteger(MediaFormat.KEY_WIDTH)
                            + ", height=" + outputFormat.getInteger(MediaFormat.KEY_HEIGHT));
                        //yuvData = yuv420spToYuv420P(yuvData, outputFormat.getInteger(MediaFormat.KEY_WIDTH), outputFormat.getInteger(MediaFormat.KEY_HEIGHT));
                        break;
                    case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420PackedSemiPlanar:
                        Log.e("解码后的帧格式", "COLOR_FormatYUV420PackedSemiPlanar...width=" + outputFormat.getInteger(MediaFormat.KEY_WIDTH)
                            + ", height=" + outputFormat.getInteger(MediaFormat.KEY_HEIGHT));
                        break;
                    case MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar:
                        //{image-data=java.nio.HeapByteBuffer[pos=0 lim=104 cap=104], mime=video/raw, crop-top=0, crop-right=359, slice-height=640, color-format=19, height=704, width=1280, crop-bottom=639, crop-left=0, stride=360}
                        Log.e("解码后的帧格式", "COLOR_FormatYUV420Planar...width=" + outputFormat.getInteger(MediaFormat.KEY_WIDTH)
                            + ", height=" + outputFormat.getInteger(MediaFormat.KEY_HEIGHT));
                        buffer = MediaCodecUtils.yuv420pToYuv420sp(buffer, outputFormat.getInteger(MediaFormat.KEY_WIDTH),
                            outputFormat.getInteger(MediaFormat.KEY_HEIGHT));
                        break;
                    default:
                        //{crop-right=1279, color-format=2141391876, slice-height=1024, image-data=java.nio.HeapByteBuffer[pos=0 lim=104 cap=104], mime=video/raw, hdr-static-info=java.nio.HeapByteBuffer[pos=0 lim=25 cap=25], stride=1536, color-standard=2, color-transfer=3, crop-bottom=703, crop-left=0, width=1280, color-range=2, crop-top=0, height=704}
                        Log.e("解码后的帧格式", outputFormat.toString()); //2141391876
                        break;
                }

                // 使用采集视频帧信息构造VideoCaptureFormat
                ZegoVideoCaptureDevice.VideoCaptureFormat format = new ZegoVideoCaptureDevice.VideoCaptureFormat();
                format.width = mWidth;
                format.height = mHeight;
                format.strides[0] = mWidth;
                format.strides[1] = mWidth;
                format.rotation = mRotation;
                format.pixel_format = ZegoVideoCaptureDevice.PIXEL_FORMAT_NV12; // camera的默认采集格式

                long now = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    now = SystemClock.elapsedRealtimeNanos();
                } else {
                    now = TimeUnit.MILLISECONDS.toNanos(SystemClock.elapsedRealtime());
                }
                mFrameSize = mWidth * mHeight * 3 / 2;
                // 将采集的数据传给ZEGO SDK
                mClient.onByteBufferFrameCaptured(buffer, mFrameSize, format, now, 1000000000);
            }

            boolean doRender = (bufferInfo.size != 0);
            // 处理完成，释放ByteBuffer数据
            mMediaCodec.releaseOutputBuffer(id, false); // false 不做渲染
        }

        @Override
        public void onError(@NonNull MediaCodec mediaCodec, @NonNull MediaCodec.CodecException e) {
            Log.d(TAG, "decoder onError");
        }

        @Override
        public void onOutputFormatChanged(@NonNull MediaCodec mediaCodec, @NonNull MediaFormat mediaFormat) {
            Log.d(TAG, "decoder onOutputFormatChanged");
            mMediaFormat = mediaFormat;
        }
    };

    /**
     * 初始化解码器
     * @param surface  显示解码内容的surface
     * @param width 渲染展示视图的宽
     * @param height 渲染展示视图的高
     */
    public AVCDecoder(Surface surface, int width, int height, ZegoVideoCaptureDevice.Client client){
        try {
            // 选用MIME类型为AVC、解码器来构造MediaCodec
            mMediaCodec = MediaCodec.createDecoderByType(MediaFormat.MIMETYPE_VIDEO_AVC);
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            mMediaCodec = null;
            return;
        }

        if(surface == null){
            return;
        }

        this.mWidth  = width;
        this.mHeight = height;
        this.mSurface = surface;
        this.mClient = client;

        bufferInfo = new MediaCodec.BufferInfo();

        // 设置解码器的MediaFormat
        mMediaFormat = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, mWidth, mHeight);
        //mMediaFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, mWidth * mHeight);
        //mMediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, mFrameRate);
        mMediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar);
    }

    // 为解码器提供视频帧数据
    public void inputFrameToDecoder(byte[] needEncodeData, long timeStmp){
        if (needEncodeData != null) {
            DecodeInfo decodeInfo = new DecodeInfo();
            decodeInfo.inOutData = needEncodeData;
            decodeInfo.timeStmp = timeStmp;
            boolean inputResult = mInputDatasQueue.offer(decodeInfo);
            if (!inputResult) {
                Log.d(TAG, "decoder inputDecoder queue result = " + inputResult + " queue current size = " + mInputDatasQueue.size());
            }
        }
    }

    // 启动解码器
    public void startDecoder(){
        Log.d(TAG, "startDecoder()");
        if(mMediaCodec != null && mSurface != null) {
            // 设置解码器的回调监听
            // TODO: 2020/6/30 test
            //mMediaCodec.setCallback(mCallback);  //5.0以后才支持异步解码
            // 配置MediaCodec，选择采用解码器功能
            mMediaCodec.configure(mMediaFormat, mSurface, null, CONFIGURE_FLAG_DECODE);
            // 启动解码器
            mMediaCodec.start();
        }else{
            throw new IllegalArgumentException("startDecoder failed, please check the MediaCodec is init correct");
        }
    }

    // 释放解码器
    public void stopAndReleaseDecoder(){
        if(mMediaCodec != null){
            try {
                mMediaCodec.stop();
                mMediaCodec.release();
                mInputDatasQueue.clear();
//            mOutputDatasQueue.clear();
                mMediaCodec = null;
            } catch (IllegalStateException e) {
                Log.d(TAG,"MediaCodec decoder stop exception: "+e.getMessage());
            }

        }
    }

    public MediaCodec getMediaCodec() {
        return mMediaCodec;
    }

    public MediaCodec.BufferInfo getBufferInfo() {
        return bufferInfo;
    }
}

