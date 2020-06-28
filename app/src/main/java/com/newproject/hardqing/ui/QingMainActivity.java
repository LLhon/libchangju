package com.newproject.hardqing.ui;

import com.google.gson.Gson;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;
import com.newproject.hardqing.BuildConfig;
import com.newproject.hardqing.R;
import com.newproject.hardqing.base.BaseActivity;
import com.newproject.hardqing.base.BaseApplication;
import com.newproject.hardqing.constant.PreConst;
import com.newproject.hardqing.constant.UrlConst;
import com.newproject.hardqing.constant.WebSocketFeedConst;
import com.newproject.hardqing.entity.GiftMode;
import com.newproject.hardqing.entity.LivePlayMessage;
import com.newproject.hardqing.entity.QrcodeBean;
import com.newproject.hardqing.service.CommonUtil;
import com.newproject.hardqing.service.LiveService;
import com.newproject.hardqing.ui.receivecmd.GetFdEntity;
import com.newproject.hardqing.ui.receivecmd.LanchEntity;
import com.newproject.hardqing.ui.receivecmd.TourInEntity;
import com.newproject.hardqing.util.DeviceUtil;
import com.newproject.hardqing.util.GetUidUtil;
import com.newproject.hardqing.util.JsonUtil;
import com.newproject.hardqing.util.LogUtil;
import com.newproject.hardqing.util.QRCodeUtil;
import com.newproject.hardqing.util.ServiceUtil;
import com.newproject.hardqing.util.ToastUtil;
import com.newproject.hardqing.util.http.Callback;
import com.newproject.hardqing.util.http.HttpUtil;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/12/10.
 */

public class QingMainActivity extends BaseActivity {

    private static final String TAG = QingMainActivity.class.getSimpleName();
    EditText etId;
    LinearLayout llAdd;
    RelativeLayout rlMain;
    ImageView ivCode;
    ImageView ivDownload;
    TextView tvInit;
    String room_id, roomNo;
    String tourId;
    static String category_uri = "/static/upload/party/uri/20190117/2c6e4187559eedf5089041ab561ecd31431.svga";
    public static final String SEND_MSG = "socket_send_msg";
    public static final String ACTION_SEND_MSG = "action_send_msg";

    /*static {
        String libs[] = {"SDL2", "avcodec-58", "avformat-58", "avutil-56", "curl", "native-lib", "swresample-3", "swscale-5"};
        // String libs[] = {"native-lib"};

        for (int index = 0; index < libs.length; index++) {
            System.loadLibrary(libs[index]);
        }
    }*/

    @Override
    public int getLayout() {
        return R.layout.activity_qing_main;
    }

    @Override
    public void initView() {
        etId = findViewById(R.id.et_id);
        llAdd = findViewById(R.id.ll_add);
        rlMain = findViewById(R.id.rl_main);
        ivCode = findViewById(R.id.iv_code);
        ivDownload = findViewById(R.id.iv_download);
        tvInit = findViewById(R.id.tv_init);
        getPermission();
        LogUtil.e("uid" + GetUidUtil.getUniquePsuedoID());
        mMProgressDialog.show(getString(R.string.loading));
        WindowManager wm = getWindowManager();
        Display d = wm.getDefaultDisplay();
        //拿到布局参数
        ViewGroup.LayoutParams l = ivCode.getLayoutParams();
        l.width = d.getHeight() / 4;
        l.height = d.getHeight() / 4;
        ivCode.setLayoutParams(l);
        ivDownload.setLayoutParams(l);
        //ivDownload.setImageBitmap(QRCodeUtil.createQRCodeBitmap("https://sj.qq.com/myapp/detail.htm?apkName=com.qingqingparty", 500, 500));
    }

    public void getPermission() {
        PermissionUtils.permission(PermissionConstants.PHONE, PermissionConstants.STORAGE, PermissionConstants.CAMERA, PermissionConstants.MICROPHONE)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(final ShouldRequest shouldRequest) {
                        LogUtils.d("QingMainActivity  getPermission  rationale()");
                        shouldRequest.again(true);
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        LogUtils.d("QingMainActivity  getPermission  onGranted()");
                        if (LoginUserManager.hasToken() && !ServiceUtil.isServiceRunning(QingMainActivity.this, LiveService.class.getName())) {
                            LiveService.setRoomId("");
                            LiveService.start(QingMainActivity.this);
                        } else {
                            CommonUtil.socketGetId(QingMainActivity.this);
                            LiveService.setRoomId("");
                            LiveService.start(QingMainActivity.this);
                        }
                        startUp();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever,
                                         List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            PermissionUtils.launchAppDetailsSettings();
                        }
                    }
                }).request();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initData() {
        sendBroadcastToKTVSystem();
        //List<Integer> fonts = CameraUtils.getBackCamera();
//        getGiftData();
        //String font = "BackCam::";
        //for (Integer s : fonts
        //) {
        //    font += s;
        //}

//        ToastUtil.showLong(this, font);

        //List<Integer> font2s = CameraUtils.getFontCamera();
        //String font2 = "BackCam::";
        //for (Integer s : font2s
        //) {
        //    font2 += s;
        //}
//        ToastUtil.showLong(this, font2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
    }

//    @OnClick({R.id.iv_activation, R.id.tv_init})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.iv_activation:
//                if (TextUtils.isEmpty(etId.getText().toString())) {
//                    ToastUtil.showShort(this, getString(R.string.hint_input));
//                    return;
//                }
//                add();
//                break;
//            case R.id.tv_init:
//                InitDialog initDialog = new InitDialog(QingMainActivity.this);
//                initDialog.show();
//                initDialog.setCanceledOnTouchOutside(false);
//                WindowManager.LayoutParams exitLps = initDialog.getWindow().getAttributes();
//                exitLps.width = DensityUtil.dip2px(QingMainActivity.this, 270);
//                exitLps.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                initDialog.getWindow().setAttributes(exitLps);
//                initDialog.setListener(new CommitListener() {
//                    @Override
//                    public void onClick() {
//                        unbind();
//                    }
//                });
//                break;
//        }
//    }

    //启动调用此接口
    public void startUp() {
        String url = UrlConst.startUp;
        HashMap<String, String> params = new HashMap<>();
        params.put("code", GetUidUtil.getUniquePsuedoID());
        HttpUtil.post(this, TAG, url, params, new Callback<String>() {
            @Override
            protected void onConnect(Request request) {
                super.onConnect(request);
            }

            @Override
            protected void onFailure(@Nullable String msg, @Nullable Exception e) {
                super.onFailure(msg, e);
                LogUtils.d(TAG + "startUp  onFailure  msg : " + msg + " Exception :  " + e);
                ToastUtil.showShort(QingMainActivity.this, getString(R.string.net_err));
            }

            @Override
            protected void onSuccess(String s, @Nullable String response) {
                super.onSuccess(s, response);
                LogUtils.d(TAG + "startUp  onSuccess  msg : " + s + " response :  " + response);
                try {
                    JSONObject root = new JSONObject(s);
                    rlMain.setVisibility(View.VISIBLE);
                    if (LoginUserManager.hasToken() && !ServiceUtil.isServiceRunning(QingMainActivity.this, LiveService.class.getName())) {
                        LiveService.setRoomId("");
                        LiveService.start(QingMainActivity.this);
                    }
                    if (root.getInt("code") == 200) {
                    } else if (root.getInt("code") == 0) {
                        Toast.makeText(QingMainActivity.this, root.getString("msg"), Toast.LENGTH_SHORT).show();
                    } else if (root.getInt("code") == 14) {
                        unbind();
                    }
                } catch (JSONException e) {
                }
            }

            @Override
            protected void onFinish(@Nullable String s, @Nullable Exception e) {
                super.onFinish(s, e);
                mMProgressDialog.dismiss();
            }
        });
    }

    //初始化设备
    public void init() {
        mMProgressDialog.show(getString(R.string.initing));
        String url = UrlConst.initialization;
        HashMap<String, String> params = new HashMap<>();
        params.put("code", GetUidUtil.getUniquePsuedoID());
        LogUtil.e("ssid" + GetUidUtil.getUniquePsuedoID());
        HttpUtil.post(this, TAG, url, params, new Callback<String>() {
            @Override
            protected void onConnect(Request request) {
                super.onConnect(request);
                LogUtils.d("QingMainActivity  init  () 6666");
            }

            @Override
            protected void onFailure(@Nullable String msg, @Nullable Exception e) {
                super.onFailure(msg, e);
                ToastUtil.showShort(QingMainActivity.this, getString(R.string.net_err));
            }

            @Override
            protected void onSuccess(String s, @Nullable String response) {
                super.onSuccess(s, response);
                try {
                    JSONObject root = new JSONObject(s);
                    if (root.getInt("code") == 200) {
                        rlMain.setVisibility(View.GONE);
                        llAdd.setVisibility(View.VISIBLE);
                        Toast.makeText(QingMainActivity.this, root.getString("msg"), Toast.LENGTH_SHORT).show();
                    } else if (root.getInt("code") == 0) {
                        Toast.makeText(QingMainActivity.this, root.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                }
            }

            @Override
            protected void onFinish(@Nullable String s, @Nullable Exception e) {
                super.onFinish(s, e);
                mMProgressDialog.dismiss();
            }
        });
    }

    //激活硬件
    public void add() {
        mMProgressDialog.show(getString(R.string.adding));
        String url = UrlConst.add;
        HashMap<String, String> params = new HashMap<>();
        params.put("code", GetUidUtil.getUniquePsuedoID());
        params.put("ssid", etId.getText().toString());
        HttpUtil.post(this, TAG, url, params, new Callback<String>() {
            @Override
            protected void onConnect(Request request) {
                super.onConnect(request);
            }

            @Override
            protected void onFailure(@Nullable String msg, @Nullable Exception e) {
                super.onFailure(msg, e);
                ToastUtil.showShort(QingMainActivity.this, getString(R.string.net_err));
            }

            @Override
            protected void onSuccess(String s, @Nullable String response) {
                super.onSuccess(s, response);
                try {
                    JSONObject root = new JSONObject(s);
                    if (root.getInt("code") == 200) {
                        Toast.makeText(QingMainActivity.this, root.getString("msg"), Toast.LENGTH_SHORT).show();
                        etId.setText("");
                        llAdd.setVisibility(View.GONE);
                        rlMain.setVisibility(View.VISIBLE);
                        LiveService.setRoomId("");
                        LiveService.start(QingMainActivity.this);
                    } else if (root.getInt("code") == 0) {
                        Toast.makeText(QingMainActivity.this, root.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                }
            }

            @Override
            protected void onFinish(@Nullable String s, @Nullable Exception e) {
                super.onFinish(s, e);
                mMProgressDialog.dismiss();
            }
        });
    }

    //解绑
    public void unbind() {
        mMProgressDialog.show("");
        String url = UrlConst.Unlock;
        HashMap<String, String> params = new HashMap<>();
        params.put("code", GetUidUtil.getUniquePsuedoID());
        LogUtil.e("ssid" + GetUidUtil.getUniquePsuedoID());
        HttpUtil.post(this, TAG, url, params, new Callback<String>() {
            @Override
            protected void onConnect(Request request) {
                super.onConnect(request);
            }

            @Override
            protected void onFailure(@Nullable String msg, @Nullable Exception e) {
                super.onFailure(msg, e);
                ToastUtil.showShort(QingMainActivity.this, getString(R.string.net_err));
            }

            @Override
            protected void onSuccess(String s, @Nullable String response) {
                super.onSuccess(s, response);
//                try {
//                    JSONObject root = new JSONObject(s);
//                    if (root.getInt("code") == 200) {
//                        Toast.makeText(QingMainActivity.this, root.getString("msg"), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(QingMainActivity.this, root.getString("msg"), Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                }
            }

            @Override
            protected void onFinish(@Nullable String s, @Nullable Exception e) {
                super.onFinish(s, e);
                mMProgressDialog.dismiss();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleSomethingElse(LivePlayMessage event) {
        LogUtils.d("QingMainActivity handleSomethingElse  event : " + event);
        switch (event.getCode()) {
            case 200:
                String jsonMsg = event.getContent();
                String cmd = JsonUtil.getSocketCmd(jsonMsg);
                if (cmd.equals(WebSocketFeedConst.getfd)) {
                    GetFdEntity entity = new Gson().fromJson(jsonMsg, GetFdEntity.class);
                    QrcodeBean qrcodeBean = new QrcodeBean();
                    qrcodeBean.setType("3");//type=3.是盒子的二维码类型
                    String deviceID = getCPUSerial();
                    LogUtils.d("QingMainActivity handleSomethingElse  deviceID : " + deviceID);
                    if (TextUtils.isEmpty(deviceID)) {
                        deviceID = DeviceUtil.getDeviceId();
                        if (TextUtils.isEmpty(deviceID)) {
                            deviceID = GetUidUtil.getUniquePsuedoID();
                        }
                    }
                    qrcodeBean.setContent(deviceID);
                    qrcodeBean.setFd(entity.getFd());
                    qrcodeBean.setChannelId(BuildConfig.channel_id);//厂家ID
                    String str = new Gson().toJson(qrcodeBean);
//                    ivCode.setImageBitmap(QRCode.createQRCode(str, 800));
                    ivCode.setImageBitmap(QRCodeUtil.createQRCodeBitmap(str, 500, 500));
                } else if (cmd.equals(WebSocketFeedConst.hardwareLaunch)) {
                    LanchEntity entity = new Gson().fromJson(jsonMsg, LanchEntity.class);
                    room_id = entity.getRoom_id();
                    roomNo = entity.getRoomNo();
                    String uri = entity.getCategory_uri();
                    if (uri != null && !uri.isEmpty()) {
                        category_uri = uri;
                    }
                    CommonUtil.inroom(this, entity.getRoom_id());
                    Log.e(TAG, "handleSomethingElse: " + room_id);
                } else if (cmd.equals(WebSocketFeedConst.tourist_in)) {
                    TourInEntity entity = new Gson().fromJson(jsonMsg, TourInEntity.class);
                    tourId = entity.getTourist_id();
                    Intent intent = new Intent(QingMainActivity.this, LivePlayActivity.class);
                    intent.putExtra(PreConst.RoomId, room_id);
                    intent.putExtra(PreConst.RoomNo, roomNo);
                    intent.putExtra("uid", tourId);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    public void aliPlay(View view) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //杀掉，这个应用程序的进程，释放 内存
        int id = android.os.Process.myPid();
        if (id != 0) {
            android.os.Process.killProcess(id);
        }
    }

//    public static String gethMac(Context context) {
//        String mac = "";
//        mac = getPerMac(context, "/sys/class/net/eth0/address");
//        if (mac.equals("")) {
//            mac = getPerMac(context, "/sys/class/net/wlan0/address");
//        }
//        if (mac.equals("")) {
//            mac = getMacAddressFromIp(context).replace(":", "");
//        }
//        return mac;
//    }

    public static String getCPUSerial() {
        String myCpuSerial = "";
        String chipSerial = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("/proc/cpuinfo");
            br = new BufferedReader(fr);
            String readline = null;
            while ((readline = br.readLine()) != null) {
                if (readline.trim().toLowerCase().startsWith("serial")) {
                    chipSerial = readline;
                    break;
                }
            }
        } catch (IOException io) {
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        String[] serialSplit = chipSerial != null ? chipSerial.split(":") : null;
        if (serialSplit != null && serialSplit.length == 2) {
            myCpuSerial = serialSplit[1].trim();
        }
        return myCpuSerial;
    }

    public void sendBroadcastToKTVSystem() {
        Intent intent = new Intent();
        intent.setAction("com.example.administrator.floatball.projectionfloat");
        sendBroadcast(intent);
    }

    public void getGiftData() {
        String url = UrlConst.GifList;
        HashMap<String, String> param = new HashMap<>();
        final PostRequest<String> request = OkGo.<String>post(url)
                .tag(TAG);
        request.params(param)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LogUtils.d(TAG + " getGiftData onSuccess() : " + response);
                        if (JsonUtil.is200(response.body())) {
                            String gifJson = response.body();
                            LogUtils.d(TAG + " getGiftData gifJson : " + gifJson);
                            if (TextUtils.isEmpty(gifJson)) {
                                return;
                            }
                            GiftMode giftMode = new Gson().fromJson(gifJson, GiftMode.class);
                            if (giftMode == null) {
                                return;
                            }
                            LogUtils.d(TAG + "getGiftData  giftMode : " + giftMode);
                            List<GiftMode.DataBean> dataBeanList = giftMode.getData();
                            LogUtils.d("getGiftData  dataBeanList : " + dataBeanList);
                            if (dataBeanList == null || dataBeanList.isEmpty()) {
                                return;
                            }
                            for (GiftMode.DataBean dataBean : dataBeanList) {
                                loadSvgaTem(dataBean.getImg2());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        LogUtils.d(TAG + " getGiftData onError() : " + response);
                    }
                });
    }

    //播放模板svga
    public void loadSvgaTem(String url) {
        LogUtils.d("getGiftData loadSvgaTem  url : " + url);
        SVGAParser parser = new SVGAParser(BaseApplication.getApp());
        try { // new URL needs try catch.
            parser.decodeFromURL(new URL(UrlConst.PICTURE_ADDRESS + url), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    LogUtils.d("getGiftData loadSvgaTem  onComplete()");
                }

                @Override
                public void onError() {
                    LogUtils.d("getGiftData loadSvgaTem  onError()");
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
            LogUtils.d("getGiftData loadSvgaTem  MalformedURLException()");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
