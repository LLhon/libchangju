package com.newproject.hardqing.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.newproject.hardqing.R;
import com.newproject.hardqing.listener.CommitListener;

/**
 * Created by Administrator on 2017/7/18.
 */
public class InitDialog extends Dialog {

    TextView tvConfirm;
    TextView tvCancel;

    private Context context;

    public InitDialog(Context context) {
        super(context, R.style.Translucent_NoTitle);
        this.context = context;
    }

    private View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
    }

    private CommitListener commitListener;

    public void setListener(CommitListener commitListener) {
        this.commitListener = commitListener;
    }

    private void init() {
        layout = LayoutInflater.from(context).inflate(R.layout.dialog_init, new FrameLayout(context), false);
        setContentView(layout);
        tvConfirm = layout.findViewById(R.id.tv_confirm);
        tvCancel = layout.findViewById(R.id.tv_cancel);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commitListener != null) {
                    dismiss();
                    commitListener.onClick();
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
