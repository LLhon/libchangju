package com.newproject.hardqing.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.newproject.hardqing.R;
import com.newproject.hardqing.util.DensityUtil;


public class XiTongDialog {

    public static void showDialog(Context context, String title, String message, String positive) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 设置参数
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {// 积极

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });
        builder.create().show();
    }

    public static void showDialog(Context context, String title, String positive, String negative, final XiTongDialog.CallXBack callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 设置参数
        builder.setTitle(title)
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {// 积极

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        callback.onSuccess();
                    }
                })
                .setNegativeButton(negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    public interface CallXBack {

        void onSuccess();

    }

    public interface CallBack<T> {

        void onFailure(@Nullable String msg);

        void onSuccess(@Nullable String msg);

    }

    public static void showXinDialog(Context context, String title, String positive, String negative
            , final XiTongDialog.CallBack<String> callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 设置参数
        builder.setTitle(title)
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {// 积极

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        callback.onSuccess("");
                    }
                })
                .setNegativeButton(negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builder.create().show();
    }


    public static void showXinDialog(Context context, String title, String message, String positive, String negative
            , final XiTongDialog.CallBack<String> callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 设置参数
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {// 积极

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        callback.onSuccess("");
                    }
                })
                .setNegativeButton(negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builder.create().show();
    }


    public static IosDialog showSystemDialog(Context context, String title, final XiTongDialog.CallBack<String> callback) {
        final IosDialog payLiveDialog = new IosDialog(context);
        payLiveDialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(context).inflate(R.layout.music_exist_dialog, new FrameLayout(context));
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvCancle = view.findViewById(R.id.tv_cancle);
//        tvCancle.setVisibility(View.GONE);
        tvTitle.setText(title);
        view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payLiveDialog.dismiss();
                callback.onFailure("");
            }
        });
        view.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payLiveDialog.dismiss();
                callback.onSuccess("");
            }
        });
        payLiveDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        payLiveDialog.setContentView(view);
        if (payLiveDialog.getWindow() != null) {
            WindowManager.LayoutParams lp = payLiveDialog.getWindow().getAttributes();
            lp.width = DensityUtil.dip2px(context, 275);
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            payLiveDialog.getWindow().setAttributes(lp);
        }
        return payLiveDialog;
    }
}
