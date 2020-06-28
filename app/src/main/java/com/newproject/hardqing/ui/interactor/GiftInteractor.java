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

public class GiftInteractor {
    //我觉的这个CallBack都可以抽出来 省的每次都要复制这个接口的代码
    public interface CallBack {

        void onFailure(@Nullable String msg);

        void onSuccess(@Nullable String msg);

    }

    public static void getGiftData(String tag, final GiftInteractor.CallBack callBack) {
        String url = UrlConst.GifList;
        HashMap<String, String> param = new HashMap<>();
        final PostRequest<String> request = OkGo.<String>post(url)
                .tag(tag);
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
