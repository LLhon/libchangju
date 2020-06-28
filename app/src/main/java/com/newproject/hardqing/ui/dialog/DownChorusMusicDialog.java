package com.newproject.hardqing.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.newproject.hardqing.R;
import com.newproject.hardqing.base.BaseDialogFragment;
import com.newproject.hardqing.entity.RefreshToken;

/**
 * @author LLhon
 * @description 合唱音乐的下载进度条dialog
 * @date 2019/8/21 11:44
 */
public class DownChorusMusicDialog extends BaseDialogFragment {

    ProgressBar mProgressBar;
    TextView mTvProgress;

    public static final String TAG = "DownChorusMusicDialog";

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_down_chorus_music;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressBar = view.findViewById(R.id.progressBar);
        mTvProgress = view.findViewById(R.id.tv_progress);
    }

    public void setProgress(int progress, int secondProgress, String message) {
        if (mProgressBar != null && mTvProgress != null) {
            mProgressBar.setProgress(progress);
            mProgressBar.setSecondaryProgress(secondProgress);
            mTvProgress.setText(message);
        }
    }

    public void setProgressText(String tip) {
        if (mTvProgress != null) {
            mTvProgress.setText(tip);
        }
    }

    public void setMaxProgress(int maxProgress) {
        mProgressBar.setMax(maxProgress);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void refresh(RefreshToken event) {

    }
}
