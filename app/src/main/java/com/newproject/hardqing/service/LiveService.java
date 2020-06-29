package com.newproject.hardqing.service;

import com.google.gson.Gson;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.newproject.hardqing.R;
import com.newproject.hardqing.base.BaseApplication;
import com.newproject.hardqing.constant.UrlConst;
import com.newproject.hardqing.constant.WebSocketFeedConst;
import com.newproject.hardqing.entity.LivePlayMessage;
import com.newproject.hardqing.listener.NetBroadcastReceiver;
import com.newproject.hardqing.listener.NetStatusMonitor;
import com.newproject.hardqing.ui.receivecmd.MusicEntity;
import com.newproject.hardqing.ui.sendcmd.HeartSocket;
import com.newproject.hardqing.util.JsonUtil;
import com.newproject.hardqing.util.LogUtil;
import com.newproject.hardqing.util.ThreadUtil;
import com.newproject.hardqing.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 全局socket， 登录、登出； 心跳维持;
 */
public class LiveService extends Service implements NetStatusMonitor {
    private static final String TAG = "LiveService";
    public static final String RECEIVE_MSG = "live_receive_msg";
    public static final String SEND_MSG = "live_send_msg";
    public static final String ACTION_SEND_MSG = "live_send_msg";
    public static final List<String> liveSocketList = new ArrayList<>();

    private ExecutorService executorService;

    private static final int KEEP_HEART = 1001; //心跳 1分钟没请求会断开；
    private static final int WS_MSG = 1002;     //web socket server 发出的msg;
    private static final int RECONNECT = 1003; //重连
    private static final int ERROR = 1005; //连接错误

    //启动socket服务；
    public static void start(Context context) {
        context.startService(new Intent(context, LiveService.class));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        isFrist = true;
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_SEND_MSG);
        registerReceiver(receiver, filter);
        initNetReceiver();

    }

    NetBroadcastReceiver netBroadcastReceiver;

    private void initNetReceiver() {
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        netBroadcastReceiver = new NetBroadcastReceiver();
        netBroadcastReceiver.setStatusMonitor(this);
        registerReceiver(netBroadcastReceiver, filter1);
    }

    private boolean isCreate;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isCreate = true;
        executorService = ThreadUtil.newDynamicSingleThreadedExecutor();

        initChatWebSocket();

        initLiveSocketList();
        return super.onStartCommand(intent, flags, startId);
    }

    //直播间socket 集合；
    private void initLiveSocketList() {
        liveSocketList.add(WebSocketFeedConst.getfd);
        liveSocketList.add(WebSocketFeedConst.hardwareLaunch);
        liveSocketList.add(WebSocketFeedConst.tourist_in);
        liveSocketList.add(WebSocketFeedConst.tourist_out);
        liveSocketList.add(WebSocketFeedConst.hardwareFailure);
        liveSocketList.add(WebSocketFeedConst.baping);
        liveSocketList.add(WebSocketFeedConst.updateRoom);

        liveSocketList.add(WebSocketFeedConst.inroom);
        liveSocketList.add(WebSocketFeedConst.outroom);

        liveSocketList.add(WebSocketFeedConst.send9r);
        liveSocketList.add(WebSocketFeedConst.room_chat);

        liveSocketList.add(WebSocketFeedConst.gift9r);
        liveSocketList.add(WebSocketFeedConst.currency);
        liveSocketList.add(WebSocketFeedConst.manage_s);
        liveSocketList.add(WebSocketFeedConst.ndsend9r);
        liveSocketList.add(WebSocketFeedConst.getout9r);

        liveSocketList.add(WebSocketFeedConst.start_live);
        liveSocketList.add(WebSocketFeedConst.stop_live);

        liveSocketList.add(WebSocketFeedConst.invite9r);
        liveSocketList.add(WebSocketFeedConst.invite9f);
        liveSocketList.add(WebSocketFeedConst.invite9c);
        liveSocketList.add(WebSocketFeedConst.refused_link);
        liveSocketList.add(WebSocketFeedConst.close_invite);
        liveSocketList.add(WebSocketFeedConst.close_invite_all);
        liveSocketList.add(WebSocketFeedConst.error);
        liveSocketList.add(WebSocketFeedConst.red);
        liveSocketList.add(WebSocketFeedConst.red_currency);
        liveSocketList.add(WebSocketFeedConst.receive_red);
        liveSocketList.add(WebSocketFeedConst.lead_red);
//娱乐系统
        liveSocketList.add(WebSocketFeedConst.entertainment);
        liveSocketList.add(WebSocketFeedConst.extractAudience);
        liveSocketList.add(WebSocketFeedConst.close_induction);
        liveSocketList.add(WebSocketFeedConst.open_room_introduction);
        liveSocketList.add(WebSocketFeedConst.lucky_audience_feedback);
        liveSocketList.add(WebSocketFeedConst.self_introduction_random_users);
        liveSocketList.add(WebSocketFeedConst.self_introduction);

        liveSocketList.add(WebSocketFeedConst.cake);
        liveSocketList.add(WebSocketFeedConst.candle);
        liveSocketList.add(WebSocketFeedConst.candle_all);
        liveSocketList.add(WebSocketFeedConst.vow);
        liveSocketList.add(WebSocketFeedConst.new_cake);
        liveSocketList.add(WebSocketFeedConst.blow_out);
        liveSocketList.add(WebSocketFeedConst.all_out);


        liveSocketList.add(WebSocketFeedConst.see_template);
        liveSocketList.add(WebSocketFeedConst.quit_template);

        liveSocketList.add(WebSocketFeedConst.pendant9f);
        liveSocketList.add(WebSocketFeedConst.pendant9r);
        liveSocketList.add(WebSocketFeedConst.stop_up);

        liveSocketList.add(WebSocketFeedConst.switch_video_source);
        liveSocketList.add(WebSocketFeedConst.change_live_status);
        liveSocketList.add(WebSocketFeedConst.playbill);
        liveSocketList.add(WebSocketFeedConst.multiroom_public_message);
    }


    ConnectWebSocket webSocketRunnable;

    //连接聊天服务器；
    private void initChatWebSocket() {
        if (webSocketClient != null && (webSocketClient.isOpen() || webSocketClient.isConnecting())) {
            return;
        }
        webSocketRunnable = new ConnectWebSocket();
        executorService.execute(webSocketRunnable);
    }

    private WebSocketClient webSocketClient;
    private boolean isFrist = false;

    @Override
    public void onNetChange(int netStatus) {
        if (isFrist) {
            isFrist = false;
            return;
        }
        if (netStatus != 0) {
            initWebSocket();
        }
    }

    private void initWebSocket() {
        if (webSocketClient != null) {
            if (!webSocketClient.isOpen()) {
                webSocketClient.reconnect();
            }
        } else {
            initChatWebSocket();
        }
    }

    private static String roomId = "";

    public static void setRoomId(String mRoomId) {
        roomId = mRoomId;
    }

    class ConnectWebSocket implements Runnable {
        @Override
        public void run() {
            try {
                webSocketClient = new WebSocketClient(new URI(UrlConst.WS_SERVER_LIVE)) {
                    boolean isFrist = false;

                    @Override
                    public void onOpen(ServerHandshake handshakedata) {
                        LogUtil.d("livew", "打开通道" + handshakedata.getHttpStatus());
                        LogUtil.d("livew", "isOpen  " + webSocketClient.isOpen());
                        if (webSocketClient.isOpen()) {
                            CommonUtil.socketGetId(LiveService.this);
//                            if (!TextUtils.isEmpty(roomId)) {
//                            CommonUtil.inroom(BaseApplication.getApp(), roomId);
//                            }
                            handler.sendEmptyMessage(KEEP_HEART);
                        } else {
//                            handler.sendEmptyMessage(RECONNECT);
                        }
                    }

                    @Override
                    public void onMessage(String message) {
                        LogUtil.d("livew", "接收消息" + message);
                        //服务器web socket返回的json数据；
                        if (TextUtils.equals(JsonUtil.getCmd(message), "hardwareLaunch")) {
                            LogUtil.d(TAG, "login inRoom" + message);
                            LivePlayMessage livePlayMessage = new LivePlayMessage();
                            livePlayMessage.setCode(4);
                            EventBus.getDefault().post(livePlayMessage);
                        } else if (TextUtils.equals(JsonUtil.getCmd(message), WebSocketFeedConst.hardware_room_command)) {
                            MusicEntity musicEntity = new Gson().fromJson(message, MusicEntity.class);
                            LogUtil.d("livew", "接收消息  musicEntity " + musicEntity);
                            if (musicEntity != null) {
                                EventBus.getDefault().post(musicEntity);
                            }
                        }
                        handler.obtainMessage(WS_MSG, message).sendToTarget();
                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {
                        LogUtil.d("livew", "通道关闭 code ： " + code + "   reason ： " + reason + "   remote ： " + remote);
                    }

                    @Override
                    public void onMessage(ByteBuffer bytes) {
                        super.onMessage(bytes);
                    }

                    @Override
                    public void onError(Exception ex) {
                        LogUtil.d("livew", "链接错误  ex ：" + ex);
//                        handler.sendEmptyMessage(RECONNECT);//断线重连
                        if (isFrist) {
                            handler.sendEmptyMessage(ERROR);
                            isFrist = false;
                        }
                    }
                };
                webSocketClient.connect();
                LogUtil.d(TAG, "websocket 建立连接");

            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IllegalThreadStateException e) {
                e.printStackTrace();
            }
        }
    }


    private void sendMsg(final String msg) {
        if (!isCreate) {
            return;
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (webSocketClient.isOpen()) {
                        webSocketClient.send(msg);
                    } else if (webSocketClient.getReadyState().equals(WebSocket.READYSTATE.CLOSED) || webSocketClient.getReadyState().equals(WebSocket.READYSTATE.CLOSING)) {
                        if (isNetConnect())
                            initWebSocket();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private int perMin = 6 * 1000;
    Runnable keepRunnable = new Runnable() {
        @Override
        public void run() {
            HeartSocket heartSocket = new HeartSocket();
            sendMsg(new Gson().toJson(heartSocket));
            handler.postDelayed(this, perMin);
        }
    };


    private Handler handler = new MsgHandler(this);

    private static class MsgHandler extends Handler {
        WeakReference<LiveService> softReference;

        private MsgHandler(LiveService service) {
            softReference = new WeakReference<LiveService>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LiveService service = softReference.get();
            int what = msg.what;
            switch (what) {
                case KEEP_HEART:
                    postDelayed(service.keepRunnable, service.perMin);
                    break;
                case WS_MSG:
                    String jsonMsg = (String) msg.obj;
                    service.postMsg(jsonMsg);
                    break;
                case RECONNECT:
                    service.initChatWebSocket();
                    break;
                case ERROR:
                    ToastUtil.showShort(BaseApplication.getApp(), BaseApplication.getApp().getString(R.string.net_low));
                    break;
                default:
                    break;
            }
        }


    }


    //使用eventbus把消息发送到对应的页面做进一步处理；
    private void postMsg(String jsonMsg) {
        String cmd = JsonUtil.getSocketCmd(jsonMsg);
        for (String liveCmd : liveSocketList) {
            if (TextUtils.equals(cmd, liveCmd)) {
                livePlayMsg(jsonMsg);
                break;
            }
        }

    }


    //直播间的socket消息；
    private void livePlayMsg(String jsonMsg) {
        Log.e(TAG, "livePlayMsg: " + jsonMsg);
        LivePlayMessage livePlayMessage = new LivePlayMessage();
        livePlayMessage.setCode(200);
        livePlayMessage.setContent(jsonMsg);
        EventBus.getDefault().post(livePlayMessage);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        roomId = "";
        if (webSocketClient != null) {
            webSocketClient.close();
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (netBroadcastReceiver != null) {
            //注销广播
            unregisterReceiver(netBroadcastReceiver);
        }
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        if (executorService != null) {
            executorService.shutdown();
            isCreate = false;
        }
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                return;
            }
            String jsonMsg = intent.getStringExtra(SEND_MSG);
            Log.e(TAG, "发送消息: " + jsonMsg);
            switch (action) {
                case ACTION_SEND_MSG:
                    sendMsg(jsonMsg);
                    break;
            }
        }
    };

    private boolean isNetConnect() {
        ConnectivityManager connectivity = (ConnectivityManager) BaseApplication.getApp()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
