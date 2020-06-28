package com.newproject.hardqing.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.newproject.hardqing.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */
public class AnimationUtils {

    private LinearLayout llSubtitles;
    private TextView tvSubtitles;
    private float mWidth;
    private float mScreenWidth;
    private final float endPosition;
    private boolean isShowing = false;
    private List<String> data = new ArrayList<>();

    public boolean isShowing() {
        return isShowing;
    }

    private Context mContext;

    public void setShowing(boolean showing) {
        isShowing = showing;
    }

    public AnimationUtils(LinearLayout subtitles, float width, float screenWidth, TextView tvSubtitles, Context context) {
        llSubtitles = subtitles;
        mWidth = width;
        mScreenWidth = screenWidth;
        endPosition = (screenWidth - width) / 2;
        this.tvSubtitles = tvSubtitles;
        this.mContext = context;

    }


    public void startAnimation() {
        handler7.sendEmptyMessage(0x222);
    }

    public void addData(String content) {
        data.add(content);
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        llSubtitles.measure(w, h);
        mWidth = llSubtitles.getMeasuredWidth();
    }


    //第一个动画
    public synchronized void startFristAnimation(LinearLayout subtitles, float startPosition, final float endPosition) {
        final PropertyValuesHolder anim1 = PropertyValuesHolder.ofFloat("translationX", startPosition, endPosition);
        PropertyValuesHolder anim2 = PropertyValuesHolder.ofFloat("alpha", 1f);
        ObjectAnimator subtitlesAnimator = ObjectAnimator.ofPropertyValuesHolder(subtitles, anim1, anim2).setDuration(1500);
        subtitlesAnimator.setInterpolator(new AccelerateInterpolator());
        subtitlesAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                llSubtitles.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                handler7.sendEmptyMessage(0x111);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        subtitlesAnimator.start();

    }

    Handler handler7 = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0x111:
                    startSecondAnimation(llSubtitles, endPosition, -mScreenWidth);
                    break;
                case 0x222:
                    if (data.size() >= 1) {
                        //初始化数据
                        String content = data.get(0);
                        tvSubtitles.setText(content);
                        llSubtitles.setBackgroundResource(R.mipmap.hengfu);
                        data.remove(0);
                        startFristAnimation(llSubtitles, mWidth, (mScreenWidth - mWidth) / 2);
                    } else {
                        listener.showCompelete();
                    }
                    break;
            }
            return false;
        }
    });


    //第二个动画
    public synchronized void startSecondAnimation(LinearLayout subtitles, float startPosition, float endPosition) {
        PropertyValuesHolder anim1 = PropertyValuesHolder.ofFloat("translationX", startPosition, endPosition);
        PropertyValuesHolder anim2 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);

        ObjectAnimator subtitlesAnimator = ObjectAnimator.ofPropertyValuesHolder(subtitles, anim1, anim2).setDuration(1500);
        subtitlesAnimator.setInterpolator(new AccelerateInterpolator());
        subtitlesAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                llSubtitles.setVisibility(View.GONE);
                //移除已播放的数据

                handler7.sendEmptyMessage(0x222);

            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        subtitlesAnimator.start();
    }

    public interface AnimationListener {

        void showCompelete();

    }

    AnimationListener listener;

    public void setAnimationListener(AnimationListener listener) {
        this.listener = listener;
    }


}
