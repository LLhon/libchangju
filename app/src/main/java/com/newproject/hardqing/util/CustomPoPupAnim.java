package com.newproject.hardqing.util;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.mrwang.imageframe.ImageFrameHandler;
import com.newproject.hardqing.R;
import com.newproject.hardqing.base.BaseApplication;
import com.newproject.hardqing.constant.PreConst;
import com.newproject.hardqing.constant.UrlConst;
import com.newproject.hardqing.listener.MyAnimationListener;
import com.newproject.hardqing.listener.MyOnImgeLoadListener;
import com.newproject.hardqing.ui.receivecmd.GiftEntity;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/6/14.
 */
public class CustomPoPupAnim {


    public CustomPoPupAnim() {

    }

    public static void enterAnim(View view) {
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.0000000000001f, 1f);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);

        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.0000000000001f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY).setDuration(450).start();

    }

    //顶部平移显示
    public static ScaleAnimation showEnterAnim() {
        ScaleAnimation translateAnimation = new ScaleAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        translateAnimation.setDuration(450);
        return translateAnimation;
    }

    //顶部平移显示
    public static ScaleAnimation hideExitAnim() {
        ScaleAnimation translateAnimation = new ScaleAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        translateAnimation.setDuration(450);
        return translateAnimation;
    }

    public static void exitAnim(View view) {
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.0000000000001f);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);

        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.0000000000001f);
        ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY).setDuration(450).start();
    }


    public static Animation enterPwAnim(int start, int end, int durationMillis) {
        Animation translateAnimation = new TranslateAnimation(0, 0, start, end);
        translateAnimation.setDuration(durationMillis);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    //顶部平移显示
    public static void black(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);
        view.startAnimation(alphaAnimation);
    }

    public static void light(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);
        view.startAnimation(alphaAnimation);
    }


    public static void showRotate(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 8, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(0);
        view.startAnimation(rotateAnimation);
    }

    public static void hideRotate(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(8, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(0);
        view.startAnimation(rotateAnimation);
    }

    //顶部平移显示
    public static TranslateAnimation showActionTop() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        translateAnimation.setDuration(650);
        return translateAnimation;
    }

    //顶部平移隐藏
    public static TranslateAnimation hideActionTop() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        translateAnimation.setDuration(650);
        return translateAnimation;
    }

    //左边平移显示
    public static TranslateAnimation showActionLeft() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        translateAnimation.setDuration(500);
        return translateAnimation;
    }

    //左边平移隐藏
    public static TranslateAnimation hideActionLeft() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        translateAnimation.setDuration(500);
        return translateAnimation;
    }

    //底部平移显示
    public static TranslateAnimation showActionBottom() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        translateAnimation.setDuration(500);
        return translateAnimation;
    }

    //底部平移显示
    public static TranslateAnimation hideActionBottom() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f);
        translateAnimation.setDuration(500);
        return translateAnimation;
    }


    //右边平移显示
    public static TranslateAnimation showActionRight() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        translateAnimation.setDuration(500);

        return translateAnimation;
    }

    //右边平移显示
    public static TranslateAnimation hideActionRight() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        translateAnimation.setDuration(500);
        return translateAnimation;
    }

    public static void showAnim(View view, String direction, boolean isVisibility) {
        if (isVisibility) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
        switch (direction) {
            case PreConst.LEFT:
                if (isVisibility) {
                    view.startAnimation(showActionLeft());
                } else {
                    view.startAnimation(hideActionLeft());
                }
                break;
            case PreConst.RIGHT:
                if (isVisibility) {
                    view.startAnimation(showActionRight());
                } else {
                    view.startAnimation(hideActionRight());
                }
                break;
            case PreConst.TOP:
                if (isVisibility) {
                    view.startAnimation(showActionTop());
                } else {
                    view.startAnimation(hideActionTop());
                }
                break;
            case PreConst.BOTTOM:
                if (isVisibility) {
                    view.startAnimation(showActionBottom());
                } else {
                    view.startAnimation(hideActionBottom());
                }
                break;
        }
    }


    public static final int COUNTDOWN_DELAY = 1000;

    public static ScaleAnimation scaleAnimtion(final TextView mCountDownTxtv) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0f, 1.0f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(COUNTDOWN_DELAY);
        scaleAnimation.setFillAfter(false);
        scaleAnimation.setAnimationListener(new MyAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);
                mCountDownTxtv.setVisibility(View.GONE);
            }
        });
        return scaleAnimation;
    }

    public static void startScaleAnimation(TextView mCountDownTxtv) {
        if (mCountDownTxtv == null) {
            return;
        }
        mCountDownTxtv.startAnimation(scaleAnimtion(mCountDownTxtv));
    }


    public static ImageFrameHandler loadRes(int count, String title, int fps,
                                            boolean loop, final MyOnImgeLoadListener listener) {
        final int[] resIds = new int[count];
        Resources res = BaseApplication.getApp().getResources();
        final String packageName = BaseApplication.getApp().getPackageName();
        for (int i = 0; i < resIds.length; i++) {
            String resName = title + (i + 1);
            int imageResId = res.getIdentifier(resName, "drawable", packageName);
            resIds[i] = imageResId;
        }
        return new ImageFrameHandler.ResourceHandlerBuilder(BaseApplication.getApp().getResources(), resIds)
                .setFps(fps)
                .setLoop(loop)
                .openLruCache(false)
                .setOnImageLoaderListener(new ImageFrameHandler.OnImageLoadListener() {
                    @Override
                    public void onImageLoad(BitmapDrawable bitmapDrawable) {

                    }

                    @Override
                    public void onPlayFinish() {
                        if (listener != null) {
                            listener.finish();
                        }
                    }
                })
                .build();

    }

    public static void toBlack(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.to_black);//设置透明度
        view.startAnimation(animation);
    }

    public static void toLight(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.to_light);//设置透明度
        view.startAnimation(animation);
    }

    private List<GiftEntity> data = new ArrayList<>();

    public void addData(GiftEntity appearanceBean) {
        data.add(appearanceBean);
    }

    public interface AnimListener {

        void onFinish();

    }


    public static void loadSvga(final SVGAImageView svgaAnim, String url, final AnimListener animListener) {

        SVGAParser parser = new SVGAParser(BaseApplication.getApp());
        svgaAnim.setLoops(1);
        svgaAnim.setClearsAfterStop(true);

        try { // new URL needs try catch.
            parser.decodeFromURL(new URL(url), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    svgaAnim.setVideoItem(videoItem);
                    svgaAnim.startAnimation();
                    svgaAnim.setCallback(new SVGACallback() {
                        @Override
                        public void onPause() {

                        }

                        @Override
                        public void onFinished() {
                            if (animListener != null) {
                                animListener.onFinish();
                            }
                        }

                        @Override
                        public void onRepeat() {

                        }

                        @Override
                        public void onStep(int i, double v) {

                        }
                    });

                }

                @Override
                public void onError() {

                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public static void loadSvgaAnim(final SVGAImageView svgaAnim, String url, final AnimListener animListener) {
        SVGAParser parser = new SVGAParser(BaseApplication.getApp());
        svgaAnim.setLoops(1);
        svgaAnim.setClearsAfterStop(true);
        try {
            parser.parse(url, new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    Log.e("TAG", "onComplete: ");
                    SVGADrawable drawable = new SVGADrawable(videoItem, new SVGADynamicEntity());
                    svgaAnim.setImageDrawable(drawable);
                    svgaAnim.startAnimation();
                    svgaAnim.setCallback(new SVGACallback() {
                        @Override
                        public void onPause() {

                        }

                        @Override
                        public void onFinished() {
                            if (animListener != null) {
                                animListener.onFinish();
                            }
                        }

                        @Override
                        public void onRepeat() {

                        }

                        @Override
                        public void onStep(int i, double v) {

                        }
                    });
                }
                @Override
                public void onError() {
                    Log.e("TAG", "onError: ");
                }
            });
        }catch (Exception e){
            System.out.print(true);
        }
    }

    public static void loadSvga(final SVGAImageView svgaAnim, String url) {
        Log.e("TAG", "loadSvga: ");

        SVGAParser parser = new SVGAParser(BaseApplication.getApp());
        svgaAnim.setLoops(0);
        try {
            parser.parse(url, new SVGAParser.ParseCompletion() {

                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    Log.e("TAG", "onComplete: ");
                    SVGADrawable drawable = new SVGADrawable(videoItem, new SVGADynamicEntity());
                    svgaAnim.setImageDrawable(drawable);
                    svgaAnim.startAnimation();
                }

                @Override
                public void onError() {
                    Log.e("TAG", "onError: ");
                }
            });
        } catch (Exception e) {
            System.out.print(true);
        }
    }

    //播放模板svga
    public static void loadSvgaTem(final SVGAImageView svgaAnim, String url) {

        SVGAParser parser = new SVGAParser(BaseApplication.getApp());
        svgaAnim.setLoops(0);
        svgaAnim.setClearsAfterStop(true);
        try { // new URL needs try catch.
            parser.decodeFromURL(new URL(url), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    svgaAnim.setVideoItem(videoItem);
                    svgaAnim.startAnimation();
                }

                @Override
                public void onError() {

                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public interface DownGiftPathListener {
        void path(String path);
    }


    public static void down(String url, final DownGiftPathListener downGiftPathListener) {
        final File file = new File(PreConst.music_dir + getFileName(url));
        if (file.exists()) {
            downGiftPathListener.path(file.getAbsolutePath());
        } else {
            DownloadUtils downloadUtils = new DownloadUtils();
            downloadUtils.download(url,
                    PreConst.music_dir + getFileName(url));
//2. 下载完毕后通过“GifDrawable”进行显示
            downloadUtils.setOnDownloadListener(new DownloadUtils.OnDownloadListener() {
                @Override
                public void onDownloadUpdate(int percent) {
                }

                @Override
                public void onDownloadError(Exception e) {
                }

                @Override
                public void onDownloadConnect(int filesize) {
                }

                //下载完毕后进行显示
                @Override
                public void onDownloadComplete(Object result) {
                    downGiftPathListener.path(file.getAbsolutePath());
                }
            });
        }
    }

    public static String getFileName(String downUrl) {
        if (TextUtils.isEmpty(downUrl)) {
            return "";
        }
        return downUrl.substring(downUrl.lastIndexOf("/") + 1, downUrl.length() - 4) + ".gif";
    }


    public static int[] getLandsCakeRes(Context context) {
        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.landscape_cake_xin_bg);
        int len = typedArray.length();
        int[] resId = new int[len];
        for (int i = 0; i < len; i++) {
            resId[i] = typedArray.getResourceId(i, -1);
        }
        typedArray.recycle();
        return resId;
    }

    public static int[] getLightRes(Context context) {
        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.candle_light);
        int len = typedArray.length();
        int[] resId = new int[len];
        for (int i = 0; i < len; i++) {
            resId[i] = typedArray.getResourceId(i, -1);
        }
        typedArray.recycle();
        return resId;
    }
}
