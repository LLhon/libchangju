package com.newproject.hardqing.ai_effects;


import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/*
 * 获取、设置控件信息
 */
public class WidgetController {

    /*
     * 获取控件宽
     */
    public static int getWidth(View view)
    {
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return (view.getMeasuredWidth());
    }
    /*
     * 获取控件高
     */
    public static int getHeight(View view)
    {
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return (view.getMeasuredHeight());
    }

    /*
     * 设置控件所在的位置X，并且不改变宽高，
     * X为绝对位置，此时Y可能归0
     */
    public static void setRelativeLayoutX(View view,int x)
    {
        MarginLayoutParams margin=new MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x,margin.topMargin, x+margin.width, margin.bottomMargin);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }
    /*
     * 设置控件所在的位置Y，并且不改变宽高，
     * Y为绝对位置，此时X可能归0
     */
    public static void setRelativeLayoutY(View view,int y)
    {
        MarginLayoutParams margin=new MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(margin.leftMargin,y, margin.rightMargin, y+margin.height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }
    /*
     * 设置控件所在的位置YY，并且不改变宽高，
     * XY为绝对位置
     */
    public static void setRelativeLayout(View view,int x,int y)
    {
        MarginLayoutParams margin=new MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x,y, x+margin.width, y+margin.height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }
    /*
     * 设置控件所在的位置XY，并且不改变宽高，
     * XY为绝对位置
     */
    public static void setFrameLayoutXY(View view,int x,int y)
    {
        MarginLayoutParams margin=new MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x,y, 0,0);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 设置View的大小；
     * @param view
     * @param width
     * @param height
     */
    public static void setFrameSize(View view, int width, int height){
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width,height);
        view.setLayoutParams(layoutParams);
    }



    public static void setMargins (View v, int l, int t) {
        MarginLayoutParams p = (MarginLayoutParams) v.getLayoutParams();
        p.setMargins(l, t, 0, 0);
        v.requestLayout();
    }

}
