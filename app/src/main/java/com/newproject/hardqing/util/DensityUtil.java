package com.newproject.hardqing.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class DensityUtil {
    /**
     * dp for px
     */
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue / scale + 0.5f);
    }

    /**
     * sp for px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px for sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static int getStatusHeight(Context context) {
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(final EditText editText, final Activity activity) {
        if (activity.getWindow() == null) {
            return;
        }
        activity.getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    if (editText != null) {
                        editText.requestFocus();
                        imm.showSoftInput(editText, 0);
                    }
                }
            }
        }, 0);
    }

    public static void hideKeyBorad( Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        //键盘是否打开
        boolean isOpen = imm.isActive();
        if (isOpen) {
            //关闭键盘
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
