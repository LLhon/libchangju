package com.newproject.hardqing.ui.interactor;

import android.support.annotation.Nullable;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.newproject.hardqing.constant.UrlConst;
import com.newproject.hardqing.util.JsonUtil;

import java.util.HashMap;

public class RoomUserInteractor {
    public interface CallBack {

        void onFailure(@Nullable String msg);

        void onSuccess(@Nullable String msg);

    }

    /**
     * @param room_id 直播间id 获取直播间观众列表
     * @param callBack
     */
    public static void getRoomUser(String tag, String start, String limit, String room_id, final RoomUserInteractor.CallBack callBack) {
        String url = UrlConst.rommUser;
        HashMap<String, String> param = new HashMap<>();
        final PostRequest<String> request = OkGo.<String>post(url)
                .tag(tag);
        param.put("start", start);
        param.put("limit", limit);
        param.put("room_id", room_id);
        request.params(param)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (JsonUtil.is200(response.body()))
                            callBack.onSuccess(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Log.e("getAddress", "onfail: " + response.getException());
                        callBack.onFailure(response.getException().toString());
                    }
                });
    }
}
