package com.newproject.hardqing.util.http;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import android.util.Log;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.DeleteRequest;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.PutRequest;
import com.lzy.okgo.request.base.Request;
import com.newproject.hardqing.base.BaseApplication;


import java.io.File;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;

/**
 * Created 17/2/15.
 */

public class HttpUtil {
    private static final String TAG = "OkGo";

    public static void init() {
        // 初始化OkGo
        OkGo.getInstance().init(BaseApplication.getInstance());
        setConfig(HttpConfig.DEFAULT_CONFIG);
    }

    /**
     * 设置全局配置，如：缓存模式、连接超时、head参数。缺省已经添加了一个{@link HttpConfig#DEFAULT_CONFIG}的配置
     * 如果没有明确需要修改配置不需要添加。</br>
     * {@link HttpConfig}的构造函数已经私有化不能直接构建，构建的方法可以参照{@link HttpConfig#DEFAULT_CONFIG}
     *
     * @param config - 配置实例
     * @see HttpConfig.Builder
     * @see HttpConfig
     */
    public static void setConfig(HttpConfig config) {
        long timeout = config.getTimeout();
        CacheMode mode = CacheMode.DEFAULT;
        switch (config.getCacheMode()) {
            case HttpConfig.DEFAULT:
                mode = CacheMode.DEFAULT;
                break;
            case HttpConfig.NO_CACHE:
                mode = CacheMode.NO_CACHE;
                break;
            case HttpConfig.REQUEST_FAILED_READ_CACHE:
                mode = CacheMode.REQUEST_FAILED_READ_CACHE;
                break;
            case HttpConfig.IF_NONE_CACHE_REQUEST:
                mode = CacheMode.IF_NONE_CACHE_REQUEST;
                break;
            case HttpConfig.FIRST_CACHE_THEN_REQUEST:
                mode = CacheMode.FIRST_CACHE_THEN_REQUEST;
                break;
            default:
                break;
        }

        HttpHeaders httpHeaders = null;
        Map<String, String> headers = config.getHeaders();
        if (headers != null && !headers.isEmpty()) {
            // 图片加载也需要加载header
//            ImageLoader.getInstance().addCommonHeader(headers);

            httpHeaders = new HttpHeaders();
            for (String s : headers.keySet()) {
                httpHeaders.put(s, headers.get(s));
            }
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        MyHttpLoggingInterceptor loggingInterceptor = new MyHttpLoggingInterceptor(TAG);
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder
                // 打开该调试开关
                .addInterceptor(loggingInterceptor)
                // 如果使用默认的 60秒,以下三行也不需要传
                //全局的连接超时时间
                .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                //全局的写入超时时间;
                .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                //全局的读取超时时间
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                //cookie持久化存储，如果cookie不过期，则一直有效
                .cookieJar(new CookieJarImpl(new DBCookieStore(BaseApplication.getApp())));

        // OkHttp在4.4及以下不支持TLS协议的解决方法
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(SSLSocketFactoryCompat.getTrustManager());
            builder.sslSocketFactory(sslSocketFactory, SSLSocketFactoryCompat.getTrustManager());
        }

        OkGo.getInstance()
                .setOkHttpClient(builder.build())
                // 设置全局缓存模式: 按照HTTP协议的默认缓存规则，例如有304响应头时缓存
                .setCacheMode(mode)

                // 可以全局统一设置缓存时间,默认永不过期
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                // 可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                .setRetryCount(4)
                .addCommonHeaders(httpHeaders);
    }

    public static void addUserHeader(@NonNull String key, @NonNull String value) {
        OkGo.getInstance().addCommonHeaders(new HttpHeaders(key, value));

//        ImageLoader.getInstance().addCommonHeader(key, value);
    }

    public static void addUserHeader(@NonNull Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.headersMap.putAll(headers);
        OkGo.getInstance().addCommonHeaders(httpHeaders);

//        ImageLoader.getInstance().addCommonHeader(headers);
    }

    public static void addUserParam(@NonNull String key, @NonNull String value) {
        OkGo.getInstance().addCommonParams(new HttpParams(key, value));
    }

    /**
     * 从服务器中获取数据
     *
     * @param context  - 上下文环境引用，建议使用{@link Activity}或{@link Fragment}的{@link Context}
     * @param tag      - 请求的 tag, 主要用于取消对应的请求
     * @param url      - 请求的网络路径
     * @param params   - 需要的{@code key-value}参数，可以为空
     * @param callback - 回调
     */
    public static void get(Context context, Object tag, @NonNull String url, Map<String, String>
            params, @Nullable Callback<String> callback, HttpConfig... configs) {
        GetRequest<String> request = OkGo.get(url);

        checkCallbackAndConfig(context, tag, callback, request, configs);

        request
                .tag(tag)
                .params(params, true)
                .execute(HttpCallBackBuild.buildStringCallback(callback));
    }

    /**
     * 获取网络图片
     *
     * @param context  - 上下文环境引用，建议使用{@link Activity}或{@link Fragment}的{@link Context}
     * @param tag      - 请求的 tag, 主要用于取消对应的请求
     * @param url      - 请求的网络路径
     * @param params   - 需要的{@code key-value}参数，可以为空
     * @param callback - 回调
     */
    public static void getBitmap(Context context, Object tag, @NonNull String url, Map<String,
            String> params, @Nullable final Callback<Bitmap> callback, HttpConfig... configs) {

        GetRequest<Bitmap> request = OkGo.get(url);

        checkCallbackAndConfig(context, tag, callback, request, configs);

        request
                .tag(tag)
                .params(params)
                .execute(HttpCallBackBuild.buildBitmapCallback(callback));
    }

    /**
     * 下载文件
     *
     * @param context  - 上下文环境引用，建议使用{@link Activity}或{@link Fragment}的{@link Context}
     * @param tag      - 请求的 tag, 主要用于取消对应的请求
     * @param url      - 请求的网络路径
     * @param params   - 需要的{@code key-value}参数，可以为空
     * @param callback - 回调
     */
    public static void download(Context context, Object tag, @NonNull String url, Map<String,
            String> params, @Nullable DownloadCallback callback, HttpConfig... configs) {
        GetRequest<File> request = OkGo.get(url);

        checkCallbackAndConfig(context, tag, callback, request, configs);

        request
                .tag(tag)
                .params(params, true)
                .execute(HttpCallBackBuild.buildFileCallback(callback));
    }

    /**
     * 上传参数
     *
     * @param context  - 上下文环境引用，建议使用{@link Activity}或{@link Fragment}的{@link Context}
     * @param tag      - 请求的 tag, 主要用于取消对应的请求
     * @param url      - 请求的网络路径
     * @param json     - post的数据
     * @param params   - 需要的{@code key-value}参数
     * @param callback - 回调
     * @param configs  - 本次请求的其它配置，如：缓存模式、连接超时、head参数。如不需要可以不传
     */
    public static void post(Context context, Object tag, @NonNull String url, String
            json, Map<String, String> params, @Nullable final Callback<String> callback,
                            HttpConfig... configs) {
        PostRequest<String> request = OkGo.post(url);

        checkCallbackAndConfig(context, tag, callback, request, configs);

        request
                .tag(tag)
                .upJson(json)
                .params(params)
                .execute(HttpCallBackBuild.buildStringCallback(callback));
    }

    public static void post(Context context, Object tag, @NonNull String url, Map<String, String> params,
                            @Nullable final Callback<String> callback,
                            HttpConfig... configs) {
        PostRequest<String> request = OkGo.post(url);

        checkCallbackAndConfig(context, tag, callback, request, configs);

        if (TextUtils.isEmpty(tag.toString())) {
            tag = getTag(url);
        }
        request
                .tag(tag)
                .params(params)
                .execute(HttpCallBackBuild.buildStringCallback(callback));
    }

    /**
     * @see #put(Context, Object, String, String, Map, Callback, HttpConfig...)
     */
    @Deprecated
    public static void put(Object tag, @NonNull String url, String json, Map<String, String>
            params, @Nullable final Callback<String> callback, HttpConfig... configs) {
        put(null, tag, url, json, params, callback, configs);
    }

    /**
     * put参数
     *
     * @param context  - 上下文环境引用，建议使用{@link Activity}或{@link Fragment}的{@link Context}
     * @param tag      - 请求的 tag, 主要用于取消对应的请求
     * @param url      - 请求的网络路径
     * @param params   - 需要的{@code key-value}参数， 不可以为空
     * @param callback - 回调
     * @param configs  - 本次请求的其它配置，如：缓存模式、连接超时、head参数。如不需要可以不传
     */
    public static void put(Context context, Object tag, @NonNull String url, String json,
                           Map<String, String> params, @Nullable Callback<String> callback,
                           HttpConfig... configs) {
        PutRequest<String> request = OkGo.put(url);

        checkCallbackAndConfig(context, tag, callback, request, configs);

        request
                .tag(tag)
                .upJson(json)
                .params(params)
                .execute(HttpCallBackBuild.buildStringCallback(callback));
    }


    /**
     * put参数
     *
     * @param context  - 上下文环境引用，建议使用{@link Activity}或{@link Fragment}的{@link Context}
     * @param tag      - 请求的 tag, 主要用于取消对应的请求
     * @param url      - 请求的网络路径
     * @param params   - 需要的{@code key-value}参数， 不可以为空
     * @param callback - 回调
     * @param configs  - 本次请求的其它配置，如：缓存模式、连接超时、head参数。如不需要可以不传
     */
    public static void delete(Context context, Object tag, @NonNull String url, Map<String, String> params,
                              @Nullable Callback<String> callback, HttpConfig... configs) {
        DeleteRequest<String> request = OkGo.delete(url);

        checkCallbackAndConfig(context, tag, callback, request, configs);

        request
                .tag(tag)
                .params(params)
                .execute(HttpCallBackBuild.buildStringCallback(callback));
    }

    /**
     * 上传文件
     *
     * @param context    - 上下文环境引用，建议使用{@link Activity}或{@link Fragment}的{@link Context}
     * @param tag        - 请求的 tag, 主要用于取消对应的请求
     * @param url        - 请求的网络路径
     * @param params     - 需要的{@code key-value}参数，可以为空
     * @param fileParams - 需要上传的文件集合，不可以为空
     * @param callback   - 回调
     * @param configs    - 本次请求的其它配置，如：缓存模式、连接超时、head参数。如不需要可以不传
     */
    public static void postFile(Context context, Object tag, @NonNull String url, Map<String,
            String> params, @NonNull Map<String, File> fileParams, @Nullable Callback<String>
                                        callback, HttpConfig... configs) {

        HttpParams httpParams = new HttpParams();
        for (String key : fileParams.keySet()) {
            httpParams.put(key, fileParams.get(key));
        }
        httpParams.put(params);

        PostRequest<File> request = OkGo.post(url);

        checkCallbackAndConfig(context, tag, callback, request, configs);

        request
                .tag(tag)
                .params(httpParams)
                .execute(HttpCallBackBuild.buildStringCallback(callback));
    }

    public static String getTag(String url) {
        String tag;
        int index = url.lastIndexOf("/");
        tag = url.substring(index);
        return tag;
    }

    /**
     * 取消对应{@code tag}的网络连接
     *
     * @param tag - 连接的Tag
     */
    public static void cancel(Object tag) {
        OkGo.getInstance().cancelTag(tag);
    }

    /**
     * 取消所有的网络连接
     */
    public static void cancelAll() {
        OkGo.getInstance().cancelAll();
    }

    /**
     * 设置{@link Request}的其它配置，如：缓存模式、连接超时、head参数
     *
     * @param request - 被设置的{@link Request}
     * @param config  - 配置封装实例
     */
    private static void setRequestConfig(Request request, HttpConfig config) {
        CacheMode mode = CacheMode.DEFAULT;
        switch (config.getCacheMode()) {
            case HttpConfig.DEFAULT:
                mode = CacheMode.DEFAULT;
                break;
            case HttpConfig.NO_CACHE:
                mode = CacheMode.NO_CACHE;
                break;
            case HttpConfig.REQUEST_FAILED_READ_CACHE:
                mode = CacheMode.REQUEST_FAILED_READ_CACHE;
                break;
            case HttpConfig.IF_NONE_CACHE_REQUEST:
                mode = CacheMode.IF_NONE_CACHE_REQUEST;
                break;
            case HttpConfig.FIRST_CACHE_THEN_REQUEST:
                mode = CacheMode.FIRST_CACHE_THEN_REQUEST;
                break;
            default:
                break;
        }

        HttpHeaders httpHeaders = null;
        Map<String, String> head = config.getHeaders();
        if (head != null && !head.isEmpty()) {
            httpHeaders = new HttpHeaders();
            for (String s : head.keySet()) {
                httpHeaders.put(s, head.get(s));
            }
        }

        request.cacheMode(mode);
        request.headers(httpHeaders);
    }

    private static void checkCallbackAndConfig(Context context, Object tag, @Nullable Callback callback, Request
            request, HttpConfig[] configs) {
        if (callback != null) {
            if (tag instanceof String) {
                callback.setTag((String) tag);
            }
            callback.setContext(context);
        }


        if (configs != null && configs.length > 0) {
            setRequestConfig(request, configs[0]);
        }
    }

}
