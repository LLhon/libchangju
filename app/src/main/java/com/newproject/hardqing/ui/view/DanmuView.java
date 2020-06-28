package com.newproject.hardqing.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.newproject.hardqing.R;
import com.newproject.hardqing.constant.UrlConst;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DanmuView extends FrameLayout {
    private static final String TAG = "DanmuView";
    private static final long DEFAULT_ANIM_DURATION = 9000; //默认每个动画的播放时长
    private static final long DEFAULT_QUERY_DURATION = 3000; //遍历弹幕的默认间隔
    private LinkedList<View> mViews = new LinkedList<>();//弹幕队列
    private boolean isQuerying;
    private int mWidth;//弹幕的宽度
    private int mHeight;//弹幕的高度
    private Handler mUIHandler = new Handler();
    private boolean TopDirectionFixed;//弹幕顶部的方向是否固定
    private Handler mQueryHandler;
    private int mTopGravity = Gravity.CENTER_VERTICAL;//顶部方向固定时的默认对齐方式

    public void setHeight(int height) {
        mHeight = height;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public void setTopGravity(int gravity) {
        this.mTopGravity = gravity;
    }

    public void add(List<Danmu> danmuList) {
        for (int i = 0; i < danmuList.size(); i++) {
            Danmu danmu = danmuList.get(i);
            addDanmuToQueue(danmu);
        }
    }

    public void add(Danmu danmu) {
        addDanmuToQueue(danmu);
    }

    public DanmuView(Context context) {
        this(context, null);
    }

    public DanmuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DanmuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        HandlerThread thread = new HandlerThread("query");
        thread.start();
        //循环取出弹幕显示
        mQueryHandler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                final View view = mViews.poll();
                if (null != view) {
                    mUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            //添加弹幕
                            showDanmu(view);
                        }
                    });
                }
                sendEmptyMessageDelayed(0, DEFAULT_QUERY_DURATION);
            }
        };
    }

    /**
     * 将要展示的弹幕添加到队列中
     */
    private void addDanmuToQueue(Danmu danmu) {
        if (null != danmu) {
            final View view = View.inflate(getContext(), R.layout.item_danmu, null);
            TextView usernameTv = (TextView) view.findViewById(R.id.danmu_user_name);
            TextView infoTv = (TextView) view.findViewById(R.id.danmu_content);
            CircleImageView headerIv = (CircleImageView) view.findViewById(R.id.danmu_user_avaster);
            usernameTv.setText(danmu.getUserName());//昵称
            infoTv.setText(danmu.getInfo());//信息
            Glide.with(getContext()).//头像
                    load(UrlConst.PICTURE_ADDRESS + danmu.getHeaderUrl()).
                    apply(RequestOptions.circleCropTransform()).into(headerIv);
            view.measure(0, 0);
            AnimatorSet animatorSet = new AnimatorSet();//组合动画
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(headerIv, "scaleX", 0.2f, 1.1f, 0.2f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(headerIv, "scaleY", 0.2f, 1.1f, 0.2f);
            scaleX.setRepeatCount(ValueAnimator.INFINITE);//永久循环
            scaleY.setRepeatCount(ValueAnimator.INFINITE);
            animatorSet.setDuration(1500);//时间
            animatorSet.play(scaleX).with(scaleY);//两个动画同时开始
            animatorSet.start();//开始
            //添加弹幕到队列中
            mViews.offerLast(view);
        }
    }

    /**
     * 播放弹幕
     *
     * @param topDirectionFixed 弹幕顶部的方向是否固定
     */
    public void startPlay(boolean topDirectionFixed) {
        this.TopDirectionFixed = topDirectionFixed;
        if (mWidth == 0 || mHeight == 0) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressLint("NewApi")
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    if (mWidth == 0) mWidth = getWidth() - getPaddingLeft() - getPaddingRight();
                    if (mHeight == 0) mHeight = getHeight() - getPaddingTop() - getPaddingBottom();
                    if (!isQuerying) {
                        mQueryHandler.sendEmptyMessage(0);
                    }
                }
            });
        } else {
            if (!isQuerying) {
                mQueryHandler.sendEmptyMessage(0);
            }
        }
    }

    /**
     * 显示弹幕,包括动画的执行
     */
    private void showDanmu(final View view) {
        isQuerying = true;
        Log.d(TAG, "mWidth:" + mWidth + " mHeight:" + mHeight);
        final LayoutParams lp = new LayoutParams(view.getMeasuredWidth(), view.getMeasuredHeight());
        lp.leftMargin = mWidth;
        if (TopDirectionFixed) {
            lp.gravity = mTopGravity | Gravity.LEFT;
        } else {
            lp.gravity = Gravity.LEFT | Gravity.TOP;
            lp.topMargin = getRandomTopMargin(view);
        }
        view.setLayoutParams(lp);
        view.setTag(lp.topMargin);
        //设置item水平滚动的动画
        ValueAnimator animator = ValueAnimator.ofInt(mWidth, -view.getMeasuredWidth());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lp.leftMargin = (int) animation.getAnimatedValue();
                view.setLayoutParams(lp);
            }
        });
        addView(view);//显示弹幕
        animator.setDuration(DEFAULT_ANIM_DURATION);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();//开启动画
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.clearAnimation();
                existMarginValues.remove(view.getTag());//移除已使用过的顶部边距
                removeView(view);//移除弹幕
                animation.cancel();
            }
        });
    }

    //记录当前仍在显示状态的弹幕的垂直方向位置（避免重复）
    private Set<Integer> existMarginValues = new HashSet<>();
    private int linesCount;
    private int range = 10;

    private int getRandomTopMargin(View view) {
        //计算可用的行数
        linesCount = mHeight / view.getMeasuredHeight();
        if (linesCount <= 1) {
            linesCount = 1;
        }
        Log.d(TAG, "linesCount:" + linesCount);
        //检查重叠
        while (true) {
            int randomIndex = (int) (Math.random() * linesCount);
            int marginValue = randomIndex * (mHeight / linesCount);
            //边界检查
            if (marginValue > mHeight - view.getMeasuredHeight()) {
                marginValue = mHeight - view.getMeasuredHeight() - range;
            }
            if (marginValue == 0) {
                marginValue = range;
            }
            if (!existMarginValues.contains(marginValue)) {
                existMarginValues.add(marginValue);
                Log.d(TAG, "marginValue:" + marginValue);
                return marginValue;
            }
        }
    }
}