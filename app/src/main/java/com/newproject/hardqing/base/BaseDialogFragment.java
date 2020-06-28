package com.newproject.hardqing.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.gyf.barlibrary.ImmersionBar;
import com.newproject.hardqing.R;
import com.newproject.hardqing.entity.RefreshToken;
import com.newproject.hardqing.util.http.HttpUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * DialogFragment 实现沉浸式的基类 Created by geyifeng on 2017/8/26.
 */

public abstract class BaseDialogFragment extends DialogFragment {
    protected String TAG;
    protected Activity mActivity;
    protected View mRootView;
    protected ImmersionBar mImmersionBar;
    protected Window mWindow;
    protected int mWidth;  //屏幕宽度
    protected int mHeight;  //屏幕高度

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        dialog.setCanceledOnTouchOutside(true);  //点击外部消失
        mWindow = dialog.getWindow();
        //测量宽高
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(dm);
            mWidth = dm.widthPixels;
            mHeight = dm.heightPixels;
        } else {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            mWidth = metrics.widthPixels;
            mHeight = metrics.heightPixels;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutId(), container, false);

        return mRootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TAG = getClass().getName();
        if (isImmersionBarEnabled())
            initImmersionBar();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initData();
        initView();
        setListener();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        HttpUtil.cancel(TAG);
        if (mImmersionBar != null)
            mImmersionBar.destroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * Sets layout id.
     *
     * @return the layout id
     */
    protected abstract int setLayoutId();

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this, getDialog());
        mImmersionBar
                .keyboardEnable(true)
                .init();
    }


    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * view与数据绑定
     */
    protected void initView() {

    }

    /**
     * 设置监听
     */
    protected void setListener() {

    }

    /**
     * 找到activity的控件
     *
     * @param <T> the type parameter
     * @param id  the id
     * @return the t
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findActivityViewById(@IdRes int id) {
        return (T) mActivity.findViewById(id);
    }

    private boolean isShow = false;


    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
            isShow = true;
        } catch (Exception e) {

        }
    }

    public boolean isShow() {
        return isShow;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        try {
            super.onDismiss(dialog);
            isShow = false;
        } catch (Exception e) {

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleSomethingElse(RefreshToken event) {
        int code = event.getCode();
        if (code == 200) {
            refresh(event);
        }
    }

    public abstract void refresh(RefreshToken event);
}
