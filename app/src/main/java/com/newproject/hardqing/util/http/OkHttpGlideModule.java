package com.newproject.hardqing.util.http;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import java.io.InputStream;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.OkHttpClient;

/**
 * Registers OkHttp related classes via Glide's annotation processor.
 *
 * <p>For Applications that depend on this library and include an {@link AppGlideModule} and Glide's
 * annotation processor, this class will be automatically included.
 */
@GlideModule
public class OkHttpGlideModule extends AppGlideModule {

    private static volatile OkHttpClient.Builder sBuilder;

    @Override
    public void registerComponents(
        @NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        Log.d("OkHttpGlideModule", "registerComponents");
        if (sBuilder == null) {
            synchronized (OkHttpGlideModule.class) {
                if (sBuilder == null) {
                    sBuilder = new OkHttpClient.Builder();
                }
            }
        }
        // OkHttp在4.4及以下不支持TLS协议的解决方法
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(SSLSocketFactoryCompat.getTrustManager());
            sBuilder.sslSocketFactory(sslSocketFactory, SSLSocketFactoryCompat.getTrustManager());
        }
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(sBuilder.build()));
    }
}