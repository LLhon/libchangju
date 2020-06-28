package com.newproject.hardqing.base;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.gyf.barlibrary.ImmersionBar;
import com.maning.mndialoglibrary.MProgressDialog;
import com.newproject.hardqing.R;
import com.newproject.hardqing.entity.RefreshToken;
import com.newproject.hardqing.util.AppManager;
import com.newproject.hardqing.util.http.HttpUtil;
import com.yinglan.keyboard.HideUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by Administrator on 2016/6/2.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public ImmersionBar mImmersionBar;
    private InputMethodManager imm;
    public String TAG;

    protected MProgressDialog mMProgressDialog;

    public void configDialogDefault() {
        //新建一个Dialog
        mMProgressDialog = new MProgressDialog.Builder(this)
                .isCanceledOnTouchOutside(true)
                //全屏背景窗体的颜色
                .setBackgroundWindowColor(getResources().getColor(R.color.lucency))
                //View背景的颜色
                .setBackgroundViewColor(getResources().getColor(R.color.colorDialogProgressRimColor))
                .setCornerRadius(20)
                .setStrokeColor(getResources().getColor(R.color.colorDialogProgressRimColor))
                .setStrokeWidth(0)
                .setProgressColor(getResources().getColor(R.color.theme_color))
                .setProgressWidth(3)
                .setProgressRimColor(Color.WHITE)
                .setProgressRimWidth(4)
                .setTextColor(getResources().getColor(R.color.theme_color))
                .build();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
//初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        AppManager.getAppManager().addActivity(this);
        TAG = getClass().getName();
        //友盟错误统计
        configDialogDefault();
        initView();
        mPageName = getPackageName();
        initData();
        HideUtil.init(this);
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }


    public abstract int getLayout();

    public abstract void initView();

    public abstract void initData();


    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }


    IntentFilter intentFilter;

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }


    }

    private String mPageName;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        HttpUtil.cancel(TAG);
        AppManager.getAppManager().finishActivity(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        this.imm = null;
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        if (mMProgressDialog != null) {
            mMProgressDialog.dismiss();
        }
        super.onDestroy();
    }


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.imm != null)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleSomethingElse(RefreshToken event) {
        int code = event.getCode();
        if (code == 200) {

        } else if (code == 100) {

        }
    }


}
