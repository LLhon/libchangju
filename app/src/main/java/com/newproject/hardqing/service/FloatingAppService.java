package com.newproject.hardqing.service;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import com.blankj.utilcode.util.ScreenUtils;
import com.newproject.hardqing.R;
import com.newproject.hardqing.ui.LivePlayActivity;
import com.newproject.hardqing.util.CommonUtils;
import com.newproject.hardqing.util.ToastUtil;

/**
 * Created by LLhon
 */
public class FloatingAppService extends Service {

    public static boolean isStarted = false;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private ImageView mImageView;
    private Callback mCallback;
    private boolean mIsAppToBack;
    private float downX;
    private float downY;
    private float upX;
    private float upY;
    private boolean mClick = false;
    private int mSlop;
    private ValueAnimator mAnimator;
    private TimeInterpolator mInterpolator;

    @Override public void onCreate() {
        super.onCreate();
        isStarted = true;
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.END | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        layoutParams.width = 500;
        layoutParams.height = 100;
        layoutParams.x = 100;
        layoutParams.y = 100;
        mSlop = ViewConfiguration.get(getApplicationContext()).getScaledTouchSlop();
    }

    @Nullable @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ToastUtil.showShort(getApplicationContext(), "启动悬浮窗");
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showFloatingWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                return;
            }
        }
        mImageView = new ImageView(getApplicationContext());
        mImageView.setImageResource(R.drawable.icon_edit);
        mImageView.setOnClickListener(v -> {
            mIsAppToBack = !mIsAppToBack;
            if (mCallback != null) {
                mCallback.setAppToBack(mIsAppToBack);
            }
        });
        windowManager.addView(mImageView, layoutParams);

        mImageView.setOnTouchListener(new FloatingOnTouchListener());
    }

    private class FloatingOnTouchListener implements View.OnTouchListener {
        float lastX, lastY, changeX, changeY;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getRawX();
                    downY = event.getRawY();
                    lastX = event.getRawX();
                    lastY = event.getRawY();
                    cancelAnimator();
                    break;
                case MotionEvent.ACTION_MOVE:
                    changeX = event.getRawX() - lastX;
                    changeY = event.getRawY() - lastY;
                    layoutParams.x = (int) (layoutParams.x + changeX);
                    layoutParams.y = (int) (layoutParams.y + changeY);
                    windowManager.updateViewLayout(view, layoutParams);
                    lastX = event.getRawX();
                    lastY = event.getRawY();
                    break;
                case MotionEvent.ACTION_UP:
                    upX = event.getRawX();
                    upY = event.getRawY();
                    mClick = (Math.abs(upX - downX) > mSlop) || (Math.abs(upY - downY) > mSlop);
                    int startX = layoutParams.x;
                    int endX = (startX * 2 + view.getWidth() > ScreenUtils.getScreenWidth()) ?
                        ScreenUtils.getScreenWidth() - view.getWidth() : 0;
                    mAnimator = ObjectAnimator.ofInt(startX, endX);
                    mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int x = (int) animation.getAnimatedValue();
                            layoutParams.x = x;
                            windowManager.updateViewLayout(view, layoutParams);
                        }
                    });
                    startAnimator();
                    break;
                default:
                    break;
            }
            return mClick;
        }
    }

    private void startAnimator() {
        if (mInterpolator == null) {
            mInterpolator = new BounceInterpolator();
        }
        mAnimator.setInterpolator(mInterpolator);
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimator.removeAllUpdateListeners();
                mAnimator.removeAllListeners();
                mAnimator = null;
            }
        });
        mAnimator.setDuration(500).start();
    }

    private void cancelAnimator() {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }

    public class Binder extends android.os.Binder {

        public FloatingAppService getService() {
            return FloatingAppService.this;
        }

        public void dismiss() {
            windowManager.removeView(mImageView);
        }
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {

        void setAppToBack(boolean back);
    }
}
