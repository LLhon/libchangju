package com.newproject.hardqing.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.newproject.hardqing.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class GlideUtil {


    /**
     *
     */
    public static RequestOptions createRp(int resource) {
        RequestOptions options = new RequestOptions().centerCrop().priority(Priority.HIGH).diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop().error(resource);
        return options.centerCrop();
    }


    public static RequestOptions createRp(int resource, int placeId) {
        return new RequestOptions().priority(Priority.HIGH).placeholder(placeId).error(resource).centerCrop();
    }

    public static void setImage(ImageView view, Context context, String url, RequestOptions op) {
        Glide.with(context).load(url).apply(op).into(view);
    }

    public static void setImage(ImageView view, Context context, int url) {
        Glide.with(context).load(url).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)).into(view);
    }

    public static void setImage(ImageView view, Context context, String url) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).into(view);
    }

    public static void setImage(ImageView view, Context context, int url, RequestOptions op) {
        Glide.with(context).load(url).apply(op).into(view);
    }


    public interface BitmapCallBack {
        void showBitmap(Bitmap bitmap);
    }

    public static void loadBitmap(Context mContext, String url, final BitmapCallBack callBack) {
        if (mContext == null) return;
        Glide.with(mContext)
                .asBitmap()
                .load(url)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).error(R.mipmap.pic_33))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                        if (callBack != null) {
                            callBack.showBitmap(resource);
                        }
                    }
                });
    }

    public static void loadBitmap(Context mContext, int url, final BitmapCallBack callBack) {
        if (mContext == null) return;
        Glide.with(mContext)
                .asBitmap()
                .load(url)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).error(R.mipmap.pic_33))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                        if (callBack != null) {
                            callBack.showBitmap(resource);
                        }
                    }
                });
    }

    public static void setBlurredImage(final Context mContext, final ImageView iv, String url, final int inSampleSize) {
        loadBitmap(mContext, url, new BitmapCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void showBitmap(Bitmap bitmap) {
                if (iv != null) {
                    iv.setImageDrawable(createBlurredImageFromBitmap(bitmap, mContext, inSampleSize));
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Drawable createBlurredImageFromBitmap(Bitmap bitmap, Context context, int inSampleSize) {

        RenderScript rs = RenderScript.create(context);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
        byte[] imageInByte = stream.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);
        Bitmap blurTemplate = BitmapFactory.decodeStream(bis, null, options);

        final Allocation input = Allocation.createFromBitmap(rs, blurTemplate);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(10f);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(blurTemplate);

        return new BitmapDrawable(context.getResources(), blurTemplate);
    }


}
