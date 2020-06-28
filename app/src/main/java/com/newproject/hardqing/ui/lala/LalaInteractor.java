package com.newproject.hardqing.ui.lala;

import android.support.annotation.Nullable;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.newproject.hardqing.constant.UrlConst;
import com.newproject.hardqing.ui.LoginUserManager;
import com.newproject.hardqing.util.JsonUtil;

import java.util.HashMap;

/**
 * lala星interactor
 */
public class LalaInteractor {
    public interface CallBack {

        void onFailure(@Nullable String msg);

        void onSuccess(@Nullable String msg);

    }

    /**
     * 根据礼物排行获取lala星列表
     *
     * @param tag
     * @param city
     * @param type
     * @param start
     * @param limit
     * @param callBack
     */
    public static void listLalaByReward(String tag, String city, String type, String start, String limit, final LalaInteractor.CallBack callBack) {
        String url = UrlConst.lara_listLalaByReward;
        final GetRequest<String> request = OkGo.<String>get(url)
                .tag(tag);
        request.params("token", LoginUserManager.getToken());
        request.params("city", city);
        request.params("type", type);
        request.params("start", start);
        request.params("limit", limit);
        request.params("limit", limit)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (JsonUtil.is200(response.body())) {
                            callBack.onSuccess(response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Log.e("listLalaByReward", "onfail: " + response.getException());
                        callBack.onFailure(response.getException().toString());
                    }
                });
    }

    /**
     * 获取在线拉拉星
     *
     * @param tag
     * @param callBack
     */
    public static void listLalaOnline(String tag, final LalaInteractor.CallBack callBack) {
        String url = UrlConst.lara_listLalaOnline;
        final GetRequest<String> request = OkGo.<String>get(url)
                .tag(tag);
        request.params("token", LoginUserManager.getToken())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (JsonUtil.is200(response.body())) {
                            callBack.onSuccess(response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Log.e("listLalaOnline", "onfail: " + response.getException());
                        callBack.onFailure(response.getException().toString());
                    }
                });
    }

    /**
     * 获取推荐拉拉星
     *
     * @param tag
     * @param callBack
     */
    public static void listRecommendLala(String tag, final LalaInteractor.CallBack callBack) {
        String url = UrlConst.lara_listRecommendLala;
        final GetRequest<String> request = OkGo.<String>get(url)
                .tag(tag);
        request.params("token", LoginUserManager.getToken())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (JsonUtil.is200(response.body())) {
                            callBack.onSuccess(response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Log.e("listRecommendLala", "onfail: " + response.getException());
                        callBack.onFailure(response.getException().toString());
                    }
                });
    }

    /**
     * 根据用户的坐标查找拉拉星按照远近排序
     *
     * @param tag
     * @param lng
     * @param lat
     * @param callBack
     */
    public static void listLikeLala(String tag, String lng, String lat, String start, String limit, final LalaInteractor.CallBack callBack) {
        String url = UrlConst.lara_listLikeLala;
        HashMap<String, String> param = new HashMap<>();
        final PostRequest<String> request = OkGo.<String>post(url)
                .tag(tag);
        param.put("token", LoginUserManager.getToken());
        param.put("lng", lng);
        param.put("lat", lat);
        param.put("start", start);
        param.put("limit", limit);
        Log.d("listLikeLala", param.toString());
        request.params(param)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (JsonUtil.is200(response.body())) {
                            callBack.onSuccess(response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Log.e("listLikeLala", "onfail: " + response.getException());
                        callBack.onFailure(response.getException().toString());
                    }
                });
    }

    /**
     * 根据用户的坐标查找指定特长的拉拉星按照远近排序
     *
     * @param tag
     * @param lng
     * @param lat
     * @param callBack
     */
    public static void listSpecialityLala(String tag, String lng, String lat, String ids, String start, String limit, final LalaInteractor.CallBack callBack) {
        String url = UrlConst.lara_listSpecialityLala;
        HashMap<String, String> param = new HashMap<>();
        final PostRequest<String> request = OkGo.<String>post(url)
                .tag(tag);
        param.put("token", LoginUserManager.getToken());
        param.put("lng", lng);
        param.put("lat", lat);
        param.put("category_id", ids);
        param.put("start", start);
        param.put("limit", limit);
        Log.d("listSpecialityLala", param.toString());
        request.params(param)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (JsonUtil.is200(response.body())) {
                            callBack.onSuccess(response.body());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Log.e("listSpecialityLala", "onfail: " + response.getException());
                        callBack.onFailure(response.getException().toString());
                    }
                });
    }
}
