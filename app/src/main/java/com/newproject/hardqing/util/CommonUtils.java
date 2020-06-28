/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.newproject.hardqing.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;
import com.newproject.hardqing.base.BaseApplication;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class CommonUtils {
    private static final String TAG = "CommonUtils";
    private static Toast toast;
    private static ProgressDialog dialog;

    /**
     * check if network avalable
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();
            }
        }

        return false;
    }

    /**
     * check if sdcard exist
     *
     * @return
     */
    public static boolean isSdcardExist() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }


    public static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    /**
     * get top activity
     * @param context
     * @return
     */
    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null)
            return runningTaskInfos.get(0).topActivity.getClassName();
        else
            return "";
    }

    /**
     * set initial letter of according user's nickname( username if no nickname)
     *
     * @param
     * @param user
     */
//    public static void setUserInitialLetter(User user) {
//        final String DefaultLetter = "#";
//        String letter = DefaultLetter;
//        if (!TextUtils.isEmpty(user.getNick())) {
//            letter = Pinyin.toPinyin(user.getNick().toCharArray()[0]);
//            user.setInitialLetter(letter.toUpperCase().substring(0, 1));
//            if (isNumeric(user.getInitialLetter()) || !check(user.getInitialLetter())) {
//                user.setInitialLetter("#");
//            }
//            return;
//        }
//        if (letter == DefaultLetter && !TextUtils.isEmpty(user.getUsername())) {
//            letter = Pinyin.toPinyin(user.getUsername().toCharArray()[0]);
//        }
//        user.setInitialLetter(letter.substring(0, 1));
//        if (isNumeric(user.getInitialLetter()) || !check(user.getInitialLetter())) {
//            user.setInitialLetter("#");
//        }
//    }

    /**
     * set initial letter of according user's nickname( username if no nickname)
     *
     * @param
     * @param user
     */
//    public static void setServiceInitialLetter(ServiceUser user) {
//        final String DefaultLetter = "#";
//        String letter = DefaultLetter;
//        if (!TextUtils.isEmpty(user.getNick())) {
//            letter = Pinyin.toPinyin(user.getNick().toCharArray()[0]);
//            user.setInitialLetter(letter.toUpperCase().substring(0, 1));
//            if (!check(user.getInitialLetter()) || isNumeric(user.getInitialLetter())) {
//                user.setInitialLetter(DefaultLetter);
//            }
//            return;
//        }
//        if (letter == DefaultLetter && !TextUtils.isEmpty(user.getUsername())) {
//            letter = Pinyin.toPinyin(user.getUsername().toCharArray()[0]);
//        }
//        user.setInitialLetter(letter.substring(0, 1));
//        if (!check(user.getInitialLetter()) || isNumeric(user.getInitialLetter())) {
//            user.setInitialLetter(DefaultLetter);
//        }
//    }

    /**
     * set initial letter of according user's nickname( username if no nickname)
     *
     * @param
     * @param user
     */
//    public static void setContactsInfoInitialLetter(ContactInfo user) {
//        final String DefaultLetter = "#";
//        String letter = DefaultLetter;
//        if (!TextUtils.isEmpty(user.getName())) {
//            letter = Pinyin.toPinyin(user.getName().toCharArray()[0]);
//            user.setLetter(letter.toUpperCase().substring(0, 1));
//            if (isNumeric(user.getLetter()) || !check(user.getLetter())) {
//                user.setLetter("#");
//            }
//            return;
//        }
//        if (letter == DefaultLetter && !TextUtils.isEmpty(user.getName())) {
//            letter = Pinyin.toPinyin(user.getName().toCharArray()[0]);
//        }
//        user.setLetter(letter.substring(0, 1));
//        if (isNumeric(user.getLetter()) || !check(user.getLetter())) {
//            user.setLetter("#");
//        }
//    }


    /**
     * set initial letter of according user's nickname( username if no nickname)
     *
     * @param
     * @param
     */
//    public static void setUserTeamLetter(User user) {
//        final String DefaultLetter = "0";
//        String letter = DefaultLetter;
//        String info = user.getUserInfo();
//        JSONObject userJson = JSONObject.parseObject(info);
//        String teamId = userJson.getString("teamId");
//        if (!TextUtils.isEmpty(teamId)) {
//            letter = teamId;
//            user.setInitialLetter(letter);
//            return;
//        }
//    }

//    public static String getDuration(Context context, String rel_time, String now_time) {
//
//        if (TextUtils.isEmpty(now_time)) {
//            if (!TextUtils.isEmpty(rel_time)) {
//                String showTime = rel_time.substring(0, rel_time.lastIndexOf(":"));
//
//                return showTime;
//            }
//
//            return "时间错误";
//        }
//
//        String backStr = "";
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        Date d1 = null;
//        Date d2 = null;
//
//        try {
//            d1 = format.parse(rel_time);
//            d2 = format.parse(now_time);
//
//            // 毫秒ms
//            long diff = d2.getTime() - d1.getTime();
//
//            long diffMinutes = diff / (60 * 1000) % 60;
//            long diffHours = diff / (60 * 60 * 1000) % 24;
//            long diffDays = diff / (24 * 60 * 60 * 1000);
//
//            if (diffDays != 0) {
//                if (diffDays < 30) {
//                    if (1 < diffDays && diffDays < 2) {
//                        backStr = context.getString(R.string.yesterday);
//                    } else if (1 < diffDays && diffDays < 2) {
//                        backStr = context.getString(R.string.The_day_before_yesterday);
//                    } else {
//                        backStr = String.valueOf(diffDays) + context.getString(R.string.Days_ago);
//                    }
//                } else {
//                    backStr = context.getString(R.string.long_long_ago);
//                }
//
//            } else if (diffHours != 0) {
//                backStr = String.valueOf(diffHours) + context.getString(R.string.An_hour_ago);
//
//            } else if (diffMinutes != 0) {
//                backStr = String.valueOf(diffMinutes) + context.getString(R.string.minutes_ago);
//
//            } else {
//
//                backStr = context.getString(R.string.just);
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return backStr;
//
//    }


    public static int getSupportSoftInputHeight(Activity activity) {
        Rect r = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        int screenHeight = activity.getWindow().getDecorView().getRootView().getHeight();
        int softInputHeight = screenHeight - r.bottom;
        if (Build.VERSION.SDK_INT >= 20) {
            // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
            softInputHeight = softInputHeight - getSoftButtonsBarHeight(activity);
            Log.d("observeSoftKeyboard---9", String.valueOf(getSoftButtonsBarHeight(activity)));

        }
        if (softInputHeight < 0) {
            Log.w("EmotionInputDetector", "Warning: value of softInputHeight is below zero!");
        }

        return softInputHeight;
    }


    private static int getSoftButtonsBarHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        }
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }


    //键盘显示监听
    public static void observeSoftKeyboard(final Activity activity, final OnSoftKeyboardChangeListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            int previousKeyboardHeight = -1;

            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int displayHeight = rect.bottom - rect.top;
                int height = decorView.getHeight();
                int keyboardHeight = height - rect.bottom;

                if (Build.VERSION.SDK_INT >= 20) {
                    // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
                    keyboardHeight = keyboardHeight - getSoftButtonsBarHeight(activity);

                }

                if (previousKeyboardHeight != keyboardHeight) {
                    boolean hide = (double) displayHeight / height > 0.8;
                    listener.onSoftKeyBoardChange(keyboardHeight, !hide, this);
                }

                previousKeyboardHeight = height;

            }
        });
    }
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public interface OnSoftKeyboardChangeListener {
        void onSoftKeyBoardChange(int softKeybardHeight, boolean visible,
            ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener);
    }

//    public static User Json2User(JSONObject userJson) {
//        User user = new User(userJson.getString(HTConstant.JSON_KEY_HXID));
//        user.setNick(userJson.getString(HTConstant.JSON_KEY_NICK));
//        user.setAvatar(userJson.getString(HTConstant.JSON_KEY_AVATAR));
//        user.setUserInfo(userJson.toJSONString());
//        CommonUtils.setUserInitialLetter(user);
//        return user;
//    }
//
//    public static JSONObject User2Json(User user) {
//        JSONObject jsonObject = new JSONObject();
//        String userInfo = user.getUserInfo();
//        try {
//            if (userInfo != null) {
//
//                jsonObject = JSONObject.parseObject(userInfo);
//            }
//        } catch (JSONException e) {
//
//            Log.d("JSONUtil----->>", "User2Json error");
//        }
//
//        return jsonObject;
//
//    }

    public static boolean isChinese(String str) {

        char[] chars = str.toCharArray();
        boolean isGB2312 = false;
        for (int i = 0; i < chars.length; i++) {
            byte[] bytes = ("" + chars[i]).getBytes();
            if (bytes.length == 2) {
                int[] ints = new int[2];
                ints[0] = bytes[0] & 0xff;
                ints[1] = bytes[1] & 0xff;
                if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 && ints[1] <= 0xFE) {
                    isGB2312 = true;
                    break;
                }
            }
        }
        return isGB2312;
    }

    public static boolean test(String s) {
        char c = s.charAt(0);
        int i = (int) c;
        if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean check(String fstrData) {
        char c = fstrData.charAt(0);
        if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 短吐司
     *
     * @param context
     * @param msg
     */
    public static void showToastShort(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 短吐司
     *
     * @param context
     * @param msg
     */
    public static void showToastShort(Context context, int msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 长吐司
     *
     * @param context
     * @param msg
     */
    public static void showToastLong(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 长吐司
     *
     * @param context
     * @param msg
     */
    public static void showToastLong(Context context, int msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 弹窗
     *
     * @param
     * @param
     * @param
     * @param
     */
//    public static void showAlertDialog(Activity context, String title, String content, final OnDialogClickListener listener) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        View dialogView = View.inflate(context, R.layout.layout_alert_dialog_delete, null);
//        TextView tv_delete_people = (TextView) dialogView.findViewById(R.id.tv_delete_people);
//        View view_line_dialog = dialogView.findViewById(R.id.view_line_dialog);
//        TextView tv_delete_title = (TextView) dialogView.findViewById(R.id.tv_delete_title);
//        TextView tv_cancle = (TextView) dialogView.findViewById(R.id.tv_cancle);
//        TextView tv_ok = (TextView) dialogView.findViewById(R.id.tv_ok);
//        tv_delete_title.setText(title);
//        tv_delete_people.setText(content);
//        builder.setView(dialogView);
//        final AlertDialog dialog = builder.show();
//        tv_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                listener.onPriformClock();
//            }
//        });
//        tv_cancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                listener.onCancleClock();
//            }
//        });
//    }

    public interface OnDialogClickListener {
        void onPriformClock();

        void onCancleClock();
    }

    public static void showDialogNumal(Context context, String msg) {
        dialog = new ProgressDialog(context);
        dialog.setMessage(msg);
        dialog.show();
    }

    public static void cencelDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 更新魔方的后台信息
     * @param
     * @param
     * @param
     */
//    public static void upDateRedAvatarUrl(Activity activity,String nick, String avatar) {
//        if (TextUtils.isEmpty(nick)) {
//            nick = HTApp.getInstance().getUsername();
//        }
//        if (TextUtils.isEmpty(avatar)) {
//            avatar = HTApp.getInstance().getUserAvatar();
//        }
//        JrmfRpClient.updateUserInfo(activity,HTApp.getInstance().getUsername(), HTApp.getInstance().getThirdToken(), nick, avatar, new OkHttpModelCallBack<BaseModel>() {
//            @Override
//            public void onSuccess(BaseModel baseModel) {
//                boolean success = baseModel.isSuccess();
//                if (success) {
//                    Log.d(TAG, "----更新魔方红包信息成功");
//                } else {
//                    Log.d(TAG, "----更新魔方红包信息失败");
//                }
//            }
//
//            @Override
//            public void onFail(String s) {
//                Log.d(TAG, "----更新魔方红包信息失败");
//            }
//        });
//    }

    public interface onUpdateListener {
        void success();

        void failed();
    }

    public static int getColor(int resid) {
        return getResoure().getColor(resid);
    }

    public static Resources getResoure() {
        return BaseApplication.getApp().getResources();
    }

    /**
     * 生成随机字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        //1.  定义一个字符串（A-Z，a-z，0-9）即62个数字字母；
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //2.  由Random生成随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //3.  长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            //从62个的数字或字母中选择
            int number = random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }
}
