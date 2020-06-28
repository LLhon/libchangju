
package com.newproject.hardqing.util.http;

import android.graphics.Bitmap;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;

/**
 * Created 17/2/15.
 */

class HttpCallBackBuild {
    private static final String TAG = "HttpCallBackBuild";

    static AbsCallback buildStringCallback(final Callback<String> callback) {
        AbsCallback result;
        if (callback == null) {
            return new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                }
            };
        }

        result = new StringCallback() {
            String body;
            String url;
            Exception e;

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                callback.onConnect(request);
                url = request.getUrl();
            }

            @Override
            public String convertResponse(okhttp3.Response response) throws Throwable {
                body = super.convertResponse(response);
                return body;
            }

            @Override
            public void onSuccess(Response<String> response) {
                body = response.body();
                callback.onResponse(body, response.message());
                callback.onSuccess(body, response.message());
            }



            @Override
            public void onCacheSuccess(Response<String> response) {
                super.onCacheSuccess(response);
                callback.onCache(response.body());
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                e = new Exception(response.getException());
                Exception exception = new Exception(e);
                callback.onFailure(response.message(), exception);
                callback.onFailure(response.code(), exception);

            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);
                callback.onProgress(progress.currentSize, progress.totalSize, progress.fraction, progress.speed);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                callback.onFinish(body, e);

                callback.onFinish(body,url);
            }


        };
        return result;
    }

    public static AbsCallback buildBitmapCallback(final Callback<Bitmap> callback) {
        if (callback == null) {
            return new BitmapCallback() {
                @Override
                public void onSuccess(Response<Bitmap> response) {
                }
            };
        }

        AbsCallback result;
        result = new BitmapCallback() {
            Bitmap body;
            Exception e;

            @Override
            public void onStart(Request<Bitmap, ? extends Request> request) {
                super.onStart(request);
                callback.onConnect(request);
            }

            @Override
            public void onSuccess(Response<Bitmap> response) {
                callback.onSuccess(response.body(), response.message());
                callback.onResponse(response.body(), response.message());
            }

            @Override
            public void onCacheSuccess(Response<Bitmap> response) {
                super.onCacheSuccess(response);
                body = response.body();
                callback.onCache(body);
            }

            @Override
            public void onError(Response<Bitmap> response) {
                super.onError(response);
                e = new Exception(response.getException());
                callback.onFailure(response.message(), e);
                callback.onFailure(response.code(), e);
            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);
                callback.onProgress(progress.currentSize, progress.totalSize, progress.fraction, progress.speed);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                callback.onFinish(body, e);
            }

        };
        return result;
    }

    static AbsCallback buildFileCallback(final DownloadCallback callback) {
        if (callback == null) {
            return new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                }
            };
        }
        AbsCallback result;
        result = new FileCallback(callback.getDestFileDir(), callback.getDestFileName()) {
            File body;
            Exception e;

            @Override
            public void onStart(Request<File, ? extends Request> request) {
                super.onStart(request);
                callback.onConnect(request);
            }

            @Override
            public void onSuccess(Response<File> response) {
                body = response.body();
                callback.onSuccess(body, response.message());
                callback.onResponse(body, response.message());
            }

            @Override
            public void onCacheSuccess(Response<File> response) {
                super.onCacheSuccess(response);
                callback.onCache(response.body());
            }

            @Override
            public void onError(Response<File> response) {
                super.onError(response);
                e = new Exception(response.getException());
                callback.onFailure(response.message(), e);
                callback.onFailure(response.code(), e);
            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);
                callback.onProgress(progress.currentSize, progress.totalSize, progress.fraction, progress.speed);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                callback.onFinish(body, e);
            }
        };

        return result;
    }
}
