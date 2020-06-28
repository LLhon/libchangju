# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keepattributes *Annotation*,Signature,Exceptions,InnerClasses
-keepclassmembers enum * {
    **[] $VALUES;
    public *;
}

# 保留support下的所有类及其内部类
-keep class android.support.** {*;}

# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

# 保留R下面的资源
-keep class **.R$* {*;}

# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#================== UMeng ========================#
-keep class com.umeng.** {*;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class com.newproject.hardqing.R$*{
public static final int *;
}

#================== Bugly ========================#
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#================== MobSDK ========================#
-keep class cn.sharesdk.**{*;}
	-keep class com.sina.**{*;}
	-keep class **.R$* {*;}
	-keep class **.R{*;}
	-keep class com.mob.**{*;}
	-dontwarn com.mob.**
	-dontwarn cn.sharesdk.**
	-dontwarn **.R$*

#================== JPush =========================#
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt
-ignorewarnings

-keepattributes Signature

-keepattributes *Annotation*

-keep class sun.misc.Unsafe { *; }
-keep public class android.webkit.** {*;}
-keep public class com.loopj.android.http.** {*;}
-keep public class org.apache.http.cookie.** {*;}


#================== 百度地图 & 高德地图sdk=========================#
# 3D 地图
    -keep   class com.amap.api.mapcore.**{*;}
    -keep   class com.amap.api.maps.**{*;}
    -keep   class com.autonavi.amap.mapcore.*{*;}
    -keep   class com.amap.api.trace.**{*;}
    -keep class com.baidu.** {*;}
    -keep class vi.com.** {*;}
    -keep class de.hdodenhof.** {*;}
    -dontwarn com.baidu.**

#    定位
    -keep class com.amap.api.location.**{*;}
    -keep class com.amap.api.fence.**{*;}
    -keep class com.autonavi.aps.amapapi.model.**{*;}
#    搜索
    -keep   class com.amap.api.services.**{*;}
#    2D地图
    -keep class com.amap.api.maps2d.**{*;}
    -keep class com.amap.api.mapcore2d.**{*;}
#    导航
    -keep class com.amap.api.navi.**{*;}
    -keep class com.autonavi.**{*;}


#==================gson && protobuf==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keep class com.newproject.hardqing.db.entity.** {*;}
-keep class com.newproject.hardqing.entity.** {*;}
-keep class com.newproject.hardqing.tcp.** {*;}
-keep class com.newproject.hardqing.ui.lala.** {*;}
-keep class com.newproject.hardqing.ui.receivecmd.** {*;}
-keep class com.newproject.hardqing.ui.sendcmd.** {*;}
-keep class com.newproject.hardqing.ui.view.** {*;}


#------------------butterknife混淆配置---------------#
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
@butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
@butterknife.* <methods>;
}


#================== glide ========================#
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.integration.okhttp.OkHttpGlideModule


#================== greendao ========================#
### greenDAO 3
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties {*;}
# If you do not use SQLCipher:
-dontwarn net.sqlcipher.database.**
# If you do not use RxJava:
-dontwarn rx.**

### greenDAO 2
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

#================== EventBus ========================#
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#================== retrolambda ========================#
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*

#================== Firebase ========================#
-keepclassmembers class com.exutech.chacha.app.data.** {
  *;
}
-dontwarn com.android.installreferrer
-keep class com.google.android.gms.** {*;}
-keep class com.google.firebase.** {*;}
-dontwarn com.google.android.gms.**
-dontwarn com.google.firebase.**

#================== Fabric ========================#
-keepattributes SourceFile,LineNumberTable
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

#================== Adjust ========================#
-keep public class com.adjust.sdk.** { *; }
-keep class com.google.android.gms.common.ConnectionResult {
    int SUCCESS;
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {
    com.google.android.gms.ads.identifier.AdvertisingIdClient$Info getAdvertisingIdInfo(android.content.Context);
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {
    java.lang.String getId();
    boolean isLimitAdTrackingEnabled();
}
-keep class dalvik.system.VMRuntime {
    java.lang.String getRuntime();
}
-keep class android.os.Build {
    java.lang.String[] SUPPORTED_ABIS;
    java.lang.String CPU_ABI;
}
-keep class android.content.res.Configuration {
    android.os.LocaledList getLocales();
    java.util.Locale locale;
}
-keep class android.os.LocaledList {
    java.util.Locale get(int);
}

#================== Matisse ========================#
-dontwarn com.squareup.picasso.**

#================== Retrofit ========================#
-dontwarn javax.annotation.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#================== Okhttp ========================#
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}

#================== Okgo ========================#
-dontwarn okio.**
-keep class okio.**{*;}

#================== RxJava ========================#
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#================== GSYVideoPlayer ========================#
-keep class com.shuyu.gsyvideoplayer.video.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.video.**
-keep class com.shuyu.gsyvideoplayer.video.base.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.video.base.**
-keep class com.shuyu.gsyvideoplayer.utils.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.utils.**
-keep class tv.danmaku.ijk.** { *; }
-dontwarn tv.danmaku.ijk.**

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#================== BottomNavigationViewHelper ========================#
-keepclassmembers class android.support.design.internal.BottomNavigationMenuView {
    boolean mShiftingMode;
}

#================== UCrop ========================#
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

#================== AgentWeb ========================#
-keep class com.just.agentweb.** {
    *;
}
-dontwarn com.just.agentweb.**

#================== immersionbar ========================#
 -keep class com.gyf.immersionbar.* {*;}
 -dontwarn com.gyf.immersionbar.**

 #================== SVGAPlayer-Android ========================#
 -keep class com.squareup.wire.** { *; }
 -keep class com.opensource.svgaplayer.proto.** { *; }

  #================== pictureplayerview ========================#
 -dontwarn com.xiuyukeji.pictureplayerview.**
 -keep class com.xiuyukeji.pictureplayerview.** { *; }
 -dontwarn com.xiuyukeji.scheduler.**
 -keep class com.xiuyukeji.scheduler.** { *; }

 #================== ShareSDK ========================#
 -keep class cn.sharesdk.**{*;}
 -keep class com.sina.**{*;}
 -keep class **.R$* {*;}
 -keep class **.R{*;}
 -keep class com.mob.**{*;}
 -keep class m.framework.**{*;}
 -keep class com.bytedance.**{*;}
 -keep class cn.smssdk.**{*;}
 -dontwarn cn.sharesdk.**
 -dontwarn com.sina.**
 -dontwarn com.mob.**
 -dontwarn **.R$*

#================== WeChatPay ========================#
 -keep class com.tencent.mm.opensdk.** {*;}
 -keep class com.tencent.wxop.** { *;}
 -keep class com.tencent.mm.sdk.** { *;}

  #================== PLDroidPlayer ========================#
 -keep class com.pili.pldroid.player.** { *; }
 -keep class com.qiniu.qplayer.mediaEngine.MediaPlayer{*;}

 #================== ZegoSDK ========================#
 -keep class com.zego.**{*;}
 -keep class com.newproject.hardqing.uvc.**{*;}

 #================== opencv ========================#
  -keep class com.googlecode.** { *; }
  -keep class org.opencv.** { *; }

  #================== tensorflow ========================#
  -keep class org.tensorflow.** { *; }

# 环信
-keep class com.superrtc.** {*;}
-keep class com.hyphenate.** {*;}
-dontwarn com.hyphenate.**

# 如果你需要兼容6.0系统，请不要混淆org.apache.http.legacy.jar
-dontwarn android.net.compatibility.**
-dontwarn android.net.http.**
-dontwarn com.android.internal.http.multipart.**
-dontwarn org.apache.commons.**
-dontwarn org.apache.http.**
-keep class android.net.compatibility.**{*;}
-keep class android.net.http.**{*;}
-keep class com.android.internal.http.multipart.**{*;}
-keep class org.apache.commons.**{*;}
-keep class org.apache.http.**{*;}

#================== rtspclient.jar ========================#
-keep class com.llhon.rtspdemo.**{*;}
