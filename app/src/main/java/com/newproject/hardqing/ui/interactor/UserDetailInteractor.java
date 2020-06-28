package com.newproject.hardqing.ui.interactor;

import android.support.annotation.Nullable;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.newproject.hardqing.constant.UrlConst;
import com.newproject.hardqing.util.JsonUtil;

import java.util.HashMap;

public class UserDetailInteractor {
    public interface CallBack {

        void onFailure(@Nullable String msg);

        void onSuccess(@Nullable String msg);

    }


    /**
     * @param room_id 房间id
     * @param user_id 用户id
     */
    public static void getUserDetail(String tag, String room_id, String user_id, final UserDetailInteractor.CallBack callBack) {
        String url = UrlConst.userInfo;
        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", user_id);
        param.put("room_id", room_id);
        final PostRequest<String> request = OkGo.<String>post(url)
                .tag(tag);
        request.params(param)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LogUtils.d("getAnchorUserInfo  onSuccess response.body() : " + response.body());
                        if (JsonUtil.is200(response.body())) {
                            callBack.onSuccess(response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Log.e("getAddress", "onfail: " + response.getException());
                        callBack.onFailure(response.getException().toString());
                    }
                });
    }
}
