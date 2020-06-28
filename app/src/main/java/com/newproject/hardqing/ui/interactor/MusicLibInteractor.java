package com.newproject.hardqing.ui.interactor;

import android.support.annotation.Nullable;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.newproject.hardqing.constant.PreConst;
import com.newproject.hardqing.constant.UrlConst;
import com.newproject.hardqing.util.CommonUtils;
import com.newproject.hardqing.util.SignUtils;
import java.util.HashMap;

/**
 * @author LLhon
 * @description 音乐库
 * @date 2019/8/17 9:51
 */
public class MusicLibInteractor {

    public interface CallBack {

        void onFailure(@Nullable String msg);

        void onSuccess(@Nullable String msg);
    }

    /**
     * 获取服务器系统时间戳
     * @param tag
     * @param callBack
     */
    public static void getTimeStamp(String tag, final MusicLibInteractor.CallBack callBack) {
        final GetRequest<String> request = OkGo.<String>get(UrlConst.SONG_TIME_STAMP).tag(tag);
        request.execute(new StringCallback() {
            @Override public void onSuccess(Response<String> response) {
                callBack.onSuccess(response.body());
            }

            @Override public void onError(Response<String> response) {
                callBack.onFailure(response.getException().toString());
            }
        });
    }

    /**
     * 获取歌曲信息
     * @param tag
     * @param callBack
     */
    public static void songSearch(String tag, String keyword, String timeStamp, final MusicLibInteractor.CallBack callBack) {
        HashMap<String, String> param = new HashMap<>();
        final PostRequest<String> request = OkGo.<String>post(UrlConst.SONG_SEARCH).tag(tag);
        param.put("platcode", PreConst.SUN_PLAT_CODE);
        param.put("search", keyword);
        param.put("timestamp", timeStamp);
        param.put("noncestr", CommonUtils.getRandomString(32));
        String sign = SignUtils.getSign(param, PreConst.SUN_API_KEY);
        param.put("sign", sign);
        request.params(param).execute(new StringCallback() {
            @Override public void onSuccess(Response<String> response) {
                callBack.onSuccess(response.body());
            }

            @Override public void onError(Response<String> response) {
                callBack.onFailure(response.getException().toString());
            }
        });
    }

    /**
     * 获取歌曲下载地址
     * @param tag
     * @param songCode
     * @param anchor_id
     * @param anchor_name
     * @param callBack
     */
    public static void getSongAddress(String tag, String songCode, String anchor_id,
        String anchor_name, String timeStamp, final MusicLibInteractor.CallBack callBack) {
        HashMap<String, String> param = new HashMap<>();
        final PostRequest<String> request = OkGo.<String>post(UrlConst.SONG_ADDRESS).tag(tag);
        String noncestr = ("" + (int) (Math.random() * 10000000));
        param.put("platcode", PreConst.SUN_PLAT_CODE);
        param.put("songid", songCode);
        param.put("anchorid", anchor_id);
        param.put("timestamp", String.valueOf(timeStamp));
        param.put("noncestr", noncestr);
        String sign = SignUtils.getSign(param, PreConst.SUN_API_KEY);
        param.put("sign", sign);
        request.params(param).execute(new StringCallback() {
            @Override public void onSuccess(Response<String> response) {
                callBack.onSuccess(response.body());
            }

            @Override public void onError(Response<String> response) {
                callBack.onFailure(response.getException().toString());
            }
        });
    }
}
