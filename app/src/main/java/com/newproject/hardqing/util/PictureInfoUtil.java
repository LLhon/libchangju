package com.newproject.hardqing.util;

import android.support.annotation.IntDef;

/**
 * 图片信息管理
 *
 * @author Created by jz on 2017/3/29 17:36
 */
public class PictureInfoUtil {

    private static volatile PictureInfoUtil instance;

    public static PictureInfoUtil get() {
        if (instance == null) {
            synchronized (PictureInfoUtil.class) {
                if (instance == null) {
                    instance = new PictureInfoUtil();
                }
            }
        }
        return instance;
    }

    public static final int OPAQUE = 0, TRANSPARENT = 1;

    @IntDef({OPAQUE, TRANSPARENT})
    private @interface PictureType {
    }

    private final String mFileName = "cakebg";
    private final String mLandsFileName = "landscape_cakebg";
    private final String mTreeName = "tree";
    private final String mLandsTreeName = "landscape_tree";
    private final String mLightName = "light";

    private final String[] mPaths;
    private final String[] mLandsPaths;
    private final String[] mTrees;
    private final String[] mLandsTrees;
    private final String[] lights;

    private final long mDuration;
    private final long mTreeDuration;
    private final long mLightDuration;

    public String[] getLights() {
        return lights;
    }

    public long getmLightDuration() {
        return mLightDuration;
    }

    public String[] getmTrees() {
        return mTrees;
    }

    public String[] getmLandsTrees() {
        return mLandsTrees;
    }

    public long getmTreeDuration() {
        return mTreeDuration;
    }

    private int mType = OPAQUE;

    private PictureInfoUtil() {
        int count = 24;
        int treeCount = 88;
        int lightCount = 12;
        mPaths = new String[count];
        mLandsPaths = new String[count];
        mTrees = new String[treeCount];
        lights = new String[lightCount];
        mLandsTrees = new String[treeCount];
        for (int i = 0; i < count; i++) {
            mPaths[i] = String.format("%s/cake_bg_%s.png", mFileName, i + 1 + "");
        }
        for (int i = 0; i < count; i++) {
            mLandsPaths[i] = String.format("%s/cake_xin_bg_%s.png", mLandsFileName, i + 1 + "");
        }
        mDuration = count * 1000 / 12;
        for (int i = 0; i < treeCount; i++) {
            mTrees[i] = String.format("%s/tree_%s.png", mTreeName, i + 1 + "");
        }
        for (int i = 0; i < treeCount; i++) {
            mLandsTrees[i] = String.format("%s/tree_xin_%s.png", mLandsTreeName, i + 1 + "");
        }
        mTreeDuration = 10000;
        for (int i = 0; i < lightCount; i++) {
            lights[i] = String.format("%s/candle_light_%s.png", mLightName, i + 1 + "");
        }
        mLightDuration = lightCount * 1000 / 12;
    }




    private static String getIndex(int max, int i) {
        return String.format("%0" + String.valueOf(max).length() + "d", i);
    }

    public void setType(@PictureType int type) {
        this.mType = type;
    }

    public int getType() {
        return mType;
    }

    public long getDuration() {
        return mDuration;
    }

    public String[] getmPaths() {
        return mPaths;
    }
    public String[] getLandsPahts() {
        return mLandsPaths;
    }
}
