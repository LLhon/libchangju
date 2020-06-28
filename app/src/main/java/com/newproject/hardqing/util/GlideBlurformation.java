package com.newproject.hardqing.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class GlideBlurformation extends BitmapTransformation {

    private Context context;
    private int code;

    public GlideBlurformation(Context context, int code) {
        this.context = context;
        this.code = code;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        if(code == 1){
            return BlurBitmapUtil.instance().blurBitmap(context, toTransform, 8, outWidth, outHeight);
        }else {
            return BlurBitmapUtil.instance().blurBitmap2(context, toTransform, 8, outWidth, outHeight);
        }
    }


    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}