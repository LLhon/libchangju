package com.newproject.hardqing.util.http;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 管理证书和信任证书 -- 忽略HTTPS证书
 * Created by LLhon
 */
public class SSLSocketClient {

    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            // Install the all-trusting trust manager
            //SSLContext sslContext = SSLContext.getInstance("SSL");
            //sslContext.init(null, getTrustManager(), new SecureRandom());
            //// Create an ssl socket factory with our all-trusting manager
            //return sslContext.getSocketFactory();

            SSLContext sslContext = SSLContext.getInstance("TLSv1");
            sslContext.init(null, null, null);
            return new NoSSLv3SocketFactory(sslContext.getSocketFactory());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static TrustManager[] getTrustManager() {
        // Create a trust manager that does not validate certificate chains
        return new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {

            }

            @Override public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        }};
    }

    public static HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
    }
}
