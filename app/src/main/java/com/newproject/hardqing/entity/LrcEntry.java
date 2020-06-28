package com.newproject.hardqing.entity;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;

public class LrcEntry implements Comparable<LrcEntry> {

    private long time;
    private String text;
    private String secondText;
    private StaticLayout staticLayout;
    /**
     * 歌词距离视图顶部的距离
     */
    private float offset = Float.MIN_VALUE;
    public static final int GRAVITY_CENTER = 0;
    public static final int GRAVITY_LEFT = 1;
    public static final int GRAVITY_RIGHT = 2;

    public LrcEntry(long time, String text) {
        this.time = time;
        this.text = text;
    }

    public LrcEntry(long time, String text, String secondText) {
        this.time = time;
        this.text = text;
        this.secondText = secondText;
    }

    public void init(TextPaint paint, int width, int gravity) {
        Layout.Alignment align;
        switch (gravity) {
            case GRAVITY_LEFT:
                align = Layout.Alignment.ALIGN_NORMAL;
                break;

            default:
            case GRAVITY_CENTER:
                align = Layout.Alignment.ALIGN_CENTER;
                break;

            case GRAVITY_RIGHT:
                align = Layout.Alignment.ALIGN_OPPOSITE;
                break;
        }
        staticLayout = new StaticLayout(getShowText(), paint, width, align, 1f, 0f, false);

        offset = Float.MIN_VALUE;
    }

    public long getTime() {
        return time;
    }

    public StaticLayout getStaticLayout() {
        return staticLayout;
    }

    public int getHeight() {
        if (staticLayout == null) {
            return 0;
        }
        return staticLayout.getHeight();
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public String getText() {
        return text;
    }

    public void setSecondText(String secondText) {
        this.secondText = secondText;
    }

    private String getShowText() {
        if (!TextUtils.isEmpty(secondText)) {
            return text + "\n" + secondText;
        } else {
            return text;
        }
    }

    @Override
    public int compareTo(LrcEntry entry) {
        if (entry == null) {
            return -1;
        }
        return (int) (time - entry.getTime());
    }
}

