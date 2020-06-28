
package com.newproject.hardqing.util.http;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.request.base.Request;
import com.newproject.hardqing.R;
import com.newproject.hardqing.util.JsonUtil;
import com.newproject.hardqing.util.LogUtil;
import com.newproject.hardqing.util.ToastUtil;
import com.newproject.hardqing.util.manager.NetWorkUtil;

import java.lang.ref.WeakReference;


/**
 * 用于UI层使用的网络连接回调类，不直接使用第三方库的网络连接回调目的：可以对网络框架的封装，以便以后更换网络框架而不会
 * 影响上层代码，使其与应用代码分离
 * <p>
 * Created 17/2/15.
 */
public abstract class Callback<T> {
    private String tag = "Callback";
    private WeakReference<Context> weakContext = new WeakReference<>(null);

    public Callback() {
    }

    /**
     * 开始连接
     */
    protected void onConnect(Request request) {
        LogUtil.d(request.getTag().toString(),request.getUrl());
        if (!NetWorkUtil.isNetworkConnected(weakContext.get())){
            ToastUtil.showShort(weakContext.get(),weakContext.get().getString(R.string.not_network));
        }
    }

    /**
     * 连接失败
     *
     * @param msg 失败返回的信息
     * @param e   异常信息
     */
    protected void onFailure(@Nullable String msg, @Nullable Exception e) {
        if (LogUtil.isDebug){
            ToastUtil.showShort(weakContext.get(),msg);
        }
        if (TextUtils.equals(msg,"用户错误")){
//            LoginUserManager.logout(weakContext.get());
        }
    }
    /**
     * 连接失败
     *
     * @param errorCode 失败返回的信息代号
     * @param e   异常信息
     */
    protected void onFailure(int errorCode, @Nullable Exception e) {
        if (errorCode == 14){
//            LoginUserManager.refreshToken(weakContext.get());
        }
    }

    /**
     * 有缓存时的回调
     * @param s - 缓存的数据封装类型
     */
    protected void onCache(T s) {

    }

    /**
     * 服务器成功并的响应
     *
     * @param t         - 返回的封装数据
     * @param response  - 响应信息
     */
    protected void onResponse(T t, @Nullable String response) {
        Log.d(tag, "onResponse() called with: t = [" + t + "], response = [" + response + "]");
    }

    protected void onSuccess(T t, @Nullable String response) {
        Log.d(tag, "onSuccess() called with: t = [" + t + "], response = [" + response + "]");
        if (JsonUtil.getIntByKey("code", (String) t) == 14){
//            LoginUserManager.refreshToken(weakContext.get());
        }
    }

    /**
     * 请求进度回调
     * @param currentSize   - 当前请求到的数据大小
     * @param totalSize     - 总数据大小
     * @param progress      - 进度百分比
     * @param networkSpeed  - 当前网络下载速度
     */
    protected void onProgress(long currentSize, long totalSize, float progress, long networkSpeed) {

    }

    /**
     * 网络请求完成的数据回调，无论请求失败还是成功都会回调这个数据
     * @param t - 请求的数据封装，若失败有可能为空
     * @param e - 请求失败的异常
     */
    protected void onFinish(@Nullable T t, @Nullable Exception e) {
        String msg = null;
        if (t != null){
            msg = JsonUtil.getMsg((String) t);
        }


        if (TextUtils.equals(msg,"用户错误")){
//            LoginUserManager.logout(weakContext.get());
//            ToastUtil.showShortDebug(weakContext.get(),"Callback onFinish "   + msg);
        }
    }

    /**
     * 网络请求完成的数据回调，无论请求失败还是成功都会回调这个数据
     * @param t - 请求的数据封装，若失败有可能为空
     * @param url - 请求url
     */
    protected void onFinish(@Nullable T t, String url) {
        String msg = null;
        if (t != null){
            msg = JsonUtil.getMsg((String) t);
        }


        if (TextUtils.equals(msg,"用户错误")){
//            LoginUserManager.logout(weakContext.get());
//            ToastUtil.showLongDebung(weakContext.get(),"url: "  + url + "Callback onFinish "   + msg );
        }
    }

    public void setTag(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            this.tag = tag;
        }
    }

    public void setContext(Context context) {
        this.weakContext = new WeakReference<>(context);
    }

    public String getTag() {
        return tag;
    }
}
