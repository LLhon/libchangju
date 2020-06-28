package com.newproject.hardqing.ui;

import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.newproject.hardqing.R;
import com.newproject.hardqing.base.BaseApplication;
import com.newproject.hardqing.uvc.GetAppIdConfig;
import com.newproject.hardqing.uvc.UVCCameraHelper;
import com.newproject.hardqing.uvc.VideoCaptureFactoryDemo;
import com.zego.zegoavkit2.ZegoExternalVideoCapture;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zego.zegoliveroom.callback.IZegoInitSDKCompletionCallback;
import com.zego.zegoliveroom.callback.IZegoLivePlayerCallback;
import com.zego.zegoliveroom.callback.IZegoLivePublisherCallback;
import com.zego.zegoliveroom.callback.IZegoLoginCompletionCallback;
import com.zego.zegoliveroom.constants.ZegoAvConfig;
import com.zego.zegoliveroom.constants.ZegoConstants;
import com.zego.zegoliveroom.constants.ZegoVideoViewMode;
import com.zego.zegoliveroom.entity.ZegoPlayStreamQuality;
import com.zego.zegoliveroom.entity.ZegoPublishStreamQuality;
import com.zego.zegoliveroom.entity.ZegoStreamInfo;

import java.util.HashMap;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private SurfaceView mSurfaceView;
    private TextureView mTextureView;

    private VideoCaptureFactoryDemo factoryDemo;
    private ZegoLiveRoom mZegoLiveRoom;
    private String mRoomID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mSurfaceView = findViewById(R.id.sv_test);
        mTextureView = findViewById(R.id.ttv_play);
        initCamera();
        getPermission();
        initStreamCallback();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //        mUSBMonitor.register();
        // step.2 register USB event broadcast
        UVCCameraHelper.sharedInstance().registerUSB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // step.3 unregister USB event broadcast
        UVCCameraHelper.sharedInstance().unregisterUSB();
    }

    public void getPermission() {
        PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.MICROPHONE,
                PermissionConstants.STORAGE).rationale(new PermissionUtils.OnRationaleListener() {
            @Override
            public void rationale(final ShouldRequest shouldRequest) {
                shouldRequest.again(true);
            }
        }).callback(new PermissionUtils.FullCallback() {
            @Override
            public void onGranted(List<String> permissionsGranted) {
                initSDKAndLoginRoom();
            }

            @Override
            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {

            }
        }).request();
    }

    public void initCamera() {
        mZegoLiveRoom = ZegoLiveManager.getInstance().getG_ZegoApi();
        factoryDemo = new VideoCaptureFactoryDemo();
        ZegoExternalVideoCapture.setVideoCaptureFactory(factoryDemo, ZegoConstants.PublishChannelIndex.MAIN);
        // step.1 init USB
        UVCCameraHelper.sharedInstance().initUSBMonitor(this, myDevConnectListener);
    }

    public void initSDKAndLoginRoom() {
        ZegoLiveRoom.setTestEnv(true);
        mZegoLiveRoom.initSDK(GetAppIdConfig.appId, GetAppIdConfig.appSign, new IZegoInitSDKCompletionCallback() {
            @Override
            public void onInitSDK(int errorcode) {
                if (errorcode == 0) {
                    Toast.makeText(TestActivity.this, "初始化 SDK 成功", Toast.LENGTH_SHORT).show();
                    mRoomID = "uvc-" + BaseApplication.getNowTimeStr();
                    mZegoLiveRoom.loginRoom(mRoomID, ZegoConstants.RoomRole.Anchor, new IZegoLoginCompletionCallback() {
                        @Override
                        public void onLoginCompletion(int errorcode, ZegoStreamInfo[] zegoStreamInfos) {
                            if (errorcode == 0) {
                                Toast.makeText(TestActivity.this, "登录房间成功", Toast.LENGTH_SHORT).show();
                                //                                startPreview();
                            } else {
                                Toast.makeText(TestActivity.this, "登录房间失败， err: " + errorcode, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(TestActivity.this, "初始化 SDK 失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void startPlay(View view) {
        startPreview();
    }

    public void stopPlay(View view) {
        mZegoLiveRoom.stopPreview();
    }

    public void startPreview() {
        Toast.makeText(TestActivity.this, "开始预览", Toast.LENGTH_SHORT).show();
        ZegoAvConfig config = new ZegoAvConfig(ZegoAvConfig.Level.High);
        // 设置分辨率，注意此处设置的分辨率需要是uvccamera所支持的
        config.setVideoCaptureResolution(1280, 720); //1280, 720
        config.setVideoEncodeResolution(1280, 720); // 3840, 2160 / 1920, 1080
        config.setVideoFPS(15);
        config.setVideoBitrate(1200000);
        mZegoLiveRoom.setAVConfig(config);
        mZegoLiveRoom.setPreviewView(mSurfaceView); // 此处的view是surfaceview类型，是为了让uvc做预览渲染
        mZegoLiveRoom.setPreviewViewMode(ZegoVideoViewMode.ScaleAspectFill);
        boolean previewSuccess = mZegoLiveRoom.startPreview();
        LogUtils.d("TestActivity 预览成功：" + previewSuccess);
        boolean ret = mZegoLiveRoom.startPublishing(mRoomID, "", ZegoConstants.PublishFlag.JoinPublish);
        LogUtils.d("TestActivity 推流成功：" + ret);

    }

    private final UVCCameraHelper.OnMyDevConnectListener myDevConnectListener = new UVCCameraHelper.OnMyDevConnectListener() {
        @Override
        public void onAttachDev(UsbDevice device) {
            Toast.makeText(TestActivity.this, "onAttachDev", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDettachDev(UsbDevice device) {
            Toast.makeText(TestActivity.this, "onDettachDev", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onConnectDev(UsbDevice device) {
            Toast.makeText(TestActivity.this, "onConnectDev", Toast.LENGTH_SHORT).show();
            //            startPreview();
        }

        @Override
        public void onDisConnectDev(UsbDevice device) {
            Toast.makeText(TestActivity.this, "onDisConnectDev", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelDev(UsbDevice device) {
            Toast.makeText(TestActivity.this, "onCancelDev", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void finish() {
        super.finish();
        if (mZegoLiveRoom != null) {
            ZegoExternalVideoCapture.setVideoCaptureFactory(null, ZegoConstants.PublishChannelIndex.MAIN);
            mZegoLiveRoom.setZegoLivePublisherCallback(null);
            mZegoLiveRoom.setZegoLivePlayerCallback(null);
            mZegoLiveRoom.logoutRoom();
        }
    }

    private void initStreamCallback() {
        // 设置推流回调监听
        mZegoLiveRoom.setZegoLivePublisherCallback(new IZegoLivePublisherCallback() {
            @Override
            public void onPublishStateUpdate(int errorcode, String streamID, HashMap<String, Object> hashMap) {
                if (errorcode == 0) {
                    LogUtils.d("TestActivity onPublishStateUpdate 推流成功 streamID: " + streamID);
                    // 拉流
                    mZegoLiveRoom.startPlayingStream(streamID, mTextureView);
                    mZegoLiveRoom.setViewMode(ZegoVideoViewMode.ScaleAspectFill, streamID);
                } else {
                    LogUtils.d("TestActivity 推流失败，err: %d" + errorcode);
                }
            }

            @Override
            public void onJoinLiveRequest(int i, String s, String s1, String s2) {
                LogUtils.d("TestActivity onJoinLiveRequest");
            }

            public void onPublishQualityUpdate(String s, ZegoPublishStreamQuality zegoPublishStreamQuality) {
                LogUtils.d("TestActivity onPublishQualityUpdate");
            }

            @Override
            public void onCaptureVideoSizeChangedTo(int i, int i1) {

            }

            @Override
            public void onCaptureVideoFirstFrame() {

            }

            @Override
            public void onCaptureAudioFirstFrame() {

            }
        });

        // 设置拉流回调监听
        mZegoLiveRoom.setZegoLivePlayerCallback(new IZegoLivePlayerCallback() {
            @Override
            public void onPlayStateUpdate(int err, String streamID) {
                LogUtils.d("TestActivity onPlayStateUpdate");
            }

            @Override
            public void onPlayQualityUpdate(String streamID, ZegoPlayStreamQuality zegoPlayStreamQualit) {
                // 拉流质量回调
                LogUtils.d("TestActivity onPlayQualityUpdate");
            }

            @Override
            public void onInviteJoinLiveRequest(int i, String s, String s1, String s2) {
                LogUtils.d("TestActivity onInviteJoinLiveRequest");
            }

            @Override
            public void onRecvEndJoinLiveCommand(String s, String s1, String s2) {
                LogUtils.d("TestActivity onRecvEndJoinLiveCommand");
            }

            @Override
            public void onVideoSizeChangedTo(String s, int i, int i1) {
                LogUtils.d("TestActivity onVideoSizeChangedTo");
            }
        });
    }

}
