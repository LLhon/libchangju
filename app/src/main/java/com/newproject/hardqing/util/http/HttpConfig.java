

package com.newproject.hardqing.util.http;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lzy.okgo.OkGo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;


/**
 * Http协议的配置类类，为使配置内容设置后不能被修改，此类不能直接构建且所有成员变量皆为私有，只能通过{@link Builder}
 * 来配置并创建，一个应用只需要定义一次此配置即可
 *
 * Created 17/6/6.
 */

public class HttpConfig {
    /** 按照HTTP协议的默认缓存规则，例如有304响应头时缓存 */
    public static final int DEFAULT                     =1000;

    /** 不使用缓存 */
    public static final int NO_CACHE                    =1001;

    /** 请求网络失败后，读取缓存 */
    public static final int REQUEST_FAILED_READ_CACHE   =1002;

    /** 如果缓存不存在才请求网络，否则使用缓存 */
    public static final int IF_NONE_CACHE_REQUEST       =1003;

    /** 先使用缓存，不管是否存在，仍然请求网络 */
    public static final int FIRST_CACHE_THEN_REQUEST    =1004;

    public static final HttpConfig DEFAULT_CONFIG = new Builder().create();

    /**
     * 缓存模式，限定为5种
     * @see #DEFAULT
     * @see #NO_CACHE
     * @see #REQUEST_FAILED_READ_CACHE
     * @see #IF_NONE_CACHE_REQUEST
     * @see #FIRST_CACHE_THEN_REQUEST
     */
    @IntDef({DEFAULT, NO_CACHE, REQUEST_FAILED_READ_CACHE, IF_NONE_CACHE_REQUEST,
            FIRST_CACHE_THEN_REQUEST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CacheMode{}

    /**
     * 打印{@link Log}的级别
     */
    private Level level;

    public void setLevel(Level level) {
        this.level = level;
    }

    @CacheMode
    private int cacheMode;

    /**
     * 连接超时时长(ms)
     */
    private long timeout;

    @Nullable
    private Map<String, String> head;

    /**
     * 私有构建函数必须使用内部的{@link Builder}类来创建，确保所内部变量在使用过程不被修改
     * @see Builder
     */
    private HttpConfig() {
    }

    Level getLevel() {
        return level;
    }

    int getCacheMode() {
        return cacheMode;
    }

    long getTimeout() {
        return timeout;
    }

    @Nullable
    Map<String, String> getHeaders() {
        return head;
    }

    /**
     * {@link HttpConfig}的构建类
     */
    public static class Builder{
        /**
         * 打印{@link Log}的级别
         */

        /**
         * 缓存模式，限定为5种
         * @see CacheMode
         */
        @CacheMode
        private int cacheMode = DEFAULT;

        /**
         * 连接超时时长(ms)
         */
        private long timeout = OkGo.DEFAULT_MILLISECONDS;

        @Nullable
        private Map<String, String> head;

        public Builder() {
            // 定义header,不支持中文
            head = new LinkedHashMap<>(6);
        }

        public Builder(Builder builder) {
            cacheMode   = builder.cacheMode;
            timeout     = builder.timeout;
            head        = builder.head;
        }

        public Builder(HttpConfig config) {
            cacheMode   = config.cacheMode;
            timeout     = config.timeout;
            head        = config.head;
        }

        public HttpConfig create(){
            HttpConfig config = new HttpConfig();
            config.cacheMode   = cacheMode;
            config.timeout     = timeout;
            config.head        = head;
            return config;
        }

        /**
         * 设置打印Log的级别
         * @param level - 打印级别
         * @see Level
         */

        /**
         * @param cacheMode - 缓存模式{@link CacheMode}
         * @see #DEFAULT
         * @see #NO_CACHE
         * @see #REQUEST_FAILED_READ_CACHE
         * @see #IF_NONE_CACHE_REQUEST
         * @see #FIRST_CACHE_THEN_REQUEST
         */
        public void setCacheMode(@CacheMode int cacheMode) {
            this.cacheMode = cacheMode;
        }

        /**
         * @param timeout   - 超时时长(ms)
         */
        public void setTimeout(long timeout) {
            this.timeout = timeout;
        }

        /**
         *
         * @param head  - 请求头
         */
        public void setHead(@Nullable Map<String, String> head) {
            this.head = head;
        }
    }
}
