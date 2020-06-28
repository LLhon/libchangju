package com.newproject.hardqing.util;

import android.util.Log;

/**
 * 日志工具类
 * Created by Administrator on 2016/5/19 0019.
 */
public class LogUtil {

	public static final boolean isDebug = true;
	public static final String TAG = "LogUtil";


	public static void w(String msg) {
		if (isDebug) {
			Log.w(TAG,msg);
		}
	}
	public static void e(String msg) {
		if (isDebug) {
			Log.e(TAG,msg);
		}
	}
	public static void i(Object msg) {
		if (isDebug) {
			Log.i(TAG, "i日志："+ String.valueOf(msg).trim());
		}
		return;
	}
	public static void i(String TAG, Object msg) {
		if (isDebug) 
			return;
		Log.i(TAG, "i日志"+ String.valueOf(msg).trim());
	}
	public static void i_json(String msg) {
		if (isDebug) 
			return;
		Log.i(TAG, "json字串第一段："+msg.substring(0, msg.indexOf("},{")+1).trim());
	}

	public static void d(String TAG, Object msg) {
		if (isDebug){
			Log.d(TAG, "" + String.valueOf(msg).trim());
		}
	}



}
