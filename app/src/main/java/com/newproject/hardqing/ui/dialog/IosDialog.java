package com.newproject.hardqing.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.newproject.hardqing.R;

public class IosDialog extends Dialog {

    public static final String TAG = "EnterLiveDialog";

    private Context mContext;

    public IosDialog(@NonNull Context context) {
        super(context, R.style.Translucent_NoTitle);
        this.mContext = context.getApplicationContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public boolean isShow;

    @Override
    public void dismiss() {
        super.dismiss();
        isShow = false;
    }

    public boolean isShow() {
        return isShow;
    }

    @Override
    public void show() {
        super.show();
        isShow = true;
    }


    public void setTitle(String title) {
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(title);
    }
}
