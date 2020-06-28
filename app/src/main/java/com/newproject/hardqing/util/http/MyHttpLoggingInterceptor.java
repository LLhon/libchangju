package com.newproject.hardqing.util.http;


import android.os.Build;
import android.support.annotation.RequiresApi;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.utils.IOUtils;
import com.lzy.okgo.utils.OkLogger;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;

public class MyHttpLoggingInterceptor extends HttpLoggingInterceptor {


    public MyHttpLoggingInterceptor(String tag) {
        super(tag);
        logger = Logger.getLogger(tag);
    }

    private long endTime = 0;

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private volatile HttpLoggingInterceptor.Level printLevel = HttpLoggingInterceptor.Level.NONE;
    private java.util.logging.Level colorLevel;
    private Logger logger;

    public enum Level {
        NONE,       //不打印log
        BASIC,      //只打印 请求首行 和 响应首行
        HEADERS,    //打印请求和响应的所有 Header
        BODY        //所有数据全部打印
    }


    public void setPrintLevel(HttpLoggingInterceptor.Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        printLevel = level;
    }

    public void setColorLevel(java.util.logging.Level level) {
        colorLevel = level;
    }

    private void log(String message) {
        logger.log(colorLevel, message);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (printLevel == HttpLoggingInterceptor.Level.NONE) {
            return chain.proceed(request);
        }

        //请求日志拦截
        logForRequest(request, chain.connection());

        //执行请求，计算请求时间
        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            log("<-- HTTP FAILED: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        //响应日志拦截
        return logForResponse(response, tookMs);
    }

    private void logForRequest(Request request, Connection connection) throws IOException {
        boolean logBody = (printLevel == HttpLoggingInterceptor.Level.BODY);
        boolean logHeaders = (printLevel == HttpLoggingInterceptor.Level.BODY || printLevel == HttpLoggingInterceptor.Level.HEADERS);
        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;

        try {
            String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
            log(requestStartMessage);

            if (logHeaders) {
                if (hasRequestBody) {
                    // Request body headers are only present when installed as a network interceptor. Force
                    // them to be included (when available) so there values are known.
                    if (requestBody.contentType() != null) {
                        log("\tContent-Type: " + requestBody.contentType());
                    }
                    if (requestBody.contentLength() != -1) {
                        log("\tContent-Length: " + requestBody.contentLength());
                    }
                }
                Headers headers = request.headers();
                for (int i = 0, count = headers.size(); i < count; i++) {
                    String name = headers.name(i);
                    // Skip headers from the request body as they are explicitly logged above.
                    if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                        log("\t" + name + ": " + headers.value(i));
                    }
                }

                log(" ");
                if (logBody && hasRequestBody) {
                    if (isPlaintext(requestBody.contentType())) {
                        bodyToString(request);
                    } else {
                        log("\tbody: maybe [binary body], omitted!");
                    }
                }
            }
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
        } finally {
            log("--> END " + request.method());
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private Response logForResponse(Response response, long tookMs) {
        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        ResponseBody responseBody = clone.body();
        boolean logBody = (printLevel == HttpLoggingInterceptor.Level.BODY);
        boolean logHeaders = (printLevel == HttpLoggingInterceptor.Level.BODY || printLevel == HttpLoggingInterceptor.Level.HEADERS);

        try {
            log("<-- " + clone.code() + ' ' + clone.message() + ' ' + clone.request().url() + " (" + tookMs + "ms）");
            if (logHeaders) {
                Headers headers = clone.headers();
                for (int i = 0, count = headers.size(); i < count; i++) {
                    log("\t" + headers.name(i) + ": " + headers.value(i));
                }
                log(" ");
                if (logBody && HttpHeaders.hasBody(clone)) {
                    if (responseBody == null) return response;

                    if (isPlaintext(responseBody.contentType())) {
                        byte[] bytes = IOUtils.toByteArray(responseBody.byteStream());
                        MediaType contentType = responseBody.contentType();
                        String body = new String(bytes, getCharset(contentType));
                        //JSONObject jsonObject = new JSONObject(body);
                        //int code = jsonObject.getInt("code");
                        //String msg = jsonObject.getString("msg");
//                        if (code == 14) {
//                            //刷新 token 如果一个页面有多个网络请求 第一个网络请求是刷新了token 但是之后的网络请求会用没刷新之前的token 然后报token错误
//                            //能不能如果调用了刷新token的方法之后的网络请求不要返回token错误
//                            //只有在其他设备调用登录的情况下 才返回token错误 就是加一个判断 这个账号是第二次登录才有这个报错
//                            //这个如果token 刷新 其他的网路请求一定会报用户错误的 而且其他的用户数据已经请求完了
//                            //这个接口我请求了两次
//                            endTime = System.currentTimeMillis();
////                            refresh(clone.request().url() + "");
//                        } else if (msg.equals("用户错误")) {
//                            //这个判断没用是因为这个endTime 在打印的时候还是0
////                            if (System.currentTimeMillis() - endTime > 2000) {
////                                PreferenceUtil.clearPreference(BaseApplication.getApp(), PreferenceManager.getDefaultSharedPreferences(BaseApplication.getApp()));
////                                RefreshToken refreshToken = new RefreshToken();
////                                refreshToken.setCode(100);
////                                EventBus.getDefault().post(refreshToken);
////                            }
//                        }
                        log("\tbody:" + body);
                        responseBody = ResponseBody.create(responseBody.contentType(), bytes);
                        return response.newBuilder().body(responseBody).build();
                    } else {
                        log("\tbody: maybe [binary body], omitted!");
                    }
                }
            }
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
        } finally {
            log("<-- END HTTP");
        }
        return response;
    }

    private static Charset getCharset(MediaType contentType) {
        Charset charset = contentType != null ? contentType.charset(UTF8) : UTF8;
        if (charset == null) charset = UTF8;
        return charset;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private static boolean isPlaintext(MediaType mediaType) {
        if (mediaType == null) return false;
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        String subtype = mediaType.subtype();
        if (subtype != null) {
            subtype = subtype.toLowerCase();
            if (subtype.contains("x-www-form-urlencoded") || subtype.contains("json") || subtype.contains("xml") || subtype.contains("html")) //
                return true;
        }
        return false;
    }

    private void bodyToString(Request request) {
        try {
            Request copy = request.newBuilder().build();
            RequestBody body = copy.body();
            if (body == null) return;
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            Charset charset = getCharset(body.contentType());
            log("\tbody:" + buffer.readString(charset));
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
        }
    }

//    public synchronized void refresh(final String url) {
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("token", LoginUserManager.getToken());
//        OkGo.<String>post(UrlConst.RefreshToken)
//                .params(hashMap)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response.body());
//                            if (jsonObject.getInt("code") == 200) {
//                                RefreshEntity refreshEntity = new Gson().fromJson(response.body(), RefreshEntity.class);
//                                OkGo.getInstance().cancelAll();
//                                LoginUserManager.saveToken(refreshEntity.getData().getToken());
//                                RefreshToken refreshToken = new RefreshToken();
//                                refreshToken.setCode(200);
//                                refreshToken.setUrl(url);
//                                EventBus.getDefault().post(refreshToken);
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(com.lzy.okgo.model.Response<String> response) {
//                        super.onError(response);
//                    }
//                });
//
//    }
}
