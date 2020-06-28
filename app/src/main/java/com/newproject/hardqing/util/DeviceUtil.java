package com.newproject.hardqing.util;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Surface;
import android.view.WindowManager;

import com.newproject.hardqing.base.BaseApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static android.content.Context.TELEPHONY_SERVICE;

public class DeviceUtil {

    public static String getDeviceLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static String getPhoneModelName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    public static String getCountryCode() {
        String countryCode = "";
        TelephonyManager manager = (TelephonyManager) BaseApplication.getApp().getSystemService(TELEPHONY_SERVICE);
        String simCountryID = manager.getSimCountryIso().toUpperCase();
        String networkCountryID = manager.getNetworkCountryIso().toUpperCase();
        if (!TextUtils.isEmpty(simCountryID)) {
            countryCode = simCountryID;
        } else if (!TextUtils.isEmpty(networkCountryID)) {
            countryCode = networkCountryID;
        }
        return countryCode;
    }

    public static boolean isNorthAmerican() {
        return getCountryCode().equals("US") || getCountryCode().equals("CA");
    }

    public static boolean isIndia() {
        return getCountryCode().equals("IN");
    }

    public static boolean isRegional() {
        return isIndia() || getCountryCode().equals("PK") || getCountryCode().equals("TR") || getCountryCode().equals("BR") ||
                getCountryCode().equals("SA") || getCountryCode().equals("GB") || getCountryCode().equals("DE") ||
                getCountryCode().equals("IQ") || getCountryCode().equals("RU") || getCountryCode().equals("FR");
    }

    public static boolean isLatinAmerica() {
        return getCountryCode().equals("MX") || getCountryCode().equals("AR") || getCountryCode().equals("CO") ||
                getCountryCode().equals("CL") || getCountryCode().equals("PE") || getCountryCode().equals("CR") ||
                getCountryCode().equals("EC");
    }

    public static int getDisplayRotation() {
        int rotation = ((WindowManager) (BaseApplication.getApp().getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
        return 0;
    }


    /**
     * check the app is installed
     */
    public static boolean isAppInstalled(String packageName) {
        PackageInfo packageInfo;
        ApplicationInfo applicationInfo = null;
        try {
            packageInfo = BaseApplication.getApp().getPackageManager().getPackageInfo(packageName, 0);
            applicationInfo = BaseApplication.getApp().getPackageManager().getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo != null && applicationInfo != null && applicationInfo.enabled) {
            return true;
        } else {
            return false;
        }
    }

    public static int getTimeZoneOffset() {
        TimeZone timeZone = TimeZone.getDefault();
        return timeZone.getRawOffset() / 3600000;
    }

    /**
     * 获取当前时区
     */
    public static String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        String strTz = tz.getDisplayName(false, TimeZone.SHORT);
        return strTz;
    }

    public static int getTimeZoneOffsetInHour() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.getDefault());
        Date currentLocalTime = calendar.getTime();
        DateFormat date = new SimpleDateFormat("Z");
        String localTime = date.format(currentLocalTime);
        String plusOrMinus = localTime.substring(0, 1);
        int hourOffset = Integer.valueOf(localTime.substring(1, 3));
        int minuteOffset = Integer.valueOf(localTime.substring(3, 5));
        if (minuteOffset >= 30 && minuteOffset < 60) {
            hourOffset = hourOffset + 1;
        }
        if ("-".equals(plusOrMinus)) {
            hourOffset = 0 - hourOffset;
        }
        return hourOffset;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    public static String getDeviceId() {
        TelephonyManager manager = (TelephonyManager) BaseApplication.getApp().getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(BaseApplication.getApp(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        return manager.getDeviceId();
    }
}
