package com.newproject.hardqing.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast Unified management
 */
public class ToastUtil {

	public static boolean isShow = true;

	public static void showShort(Context context, CharSequence message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void showShort(Context context, int message) {
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void showLong(Context context, CharSequence message) {
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	public static void showLong(Context context, int message) {
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
}