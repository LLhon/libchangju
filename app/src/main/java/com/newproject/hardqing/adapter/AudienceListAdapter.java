package com.newproject.hardqing.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newproject.hardqing.R;
import com.newproject.hardqing.ui.receivecmd.RandomLuckyEntity;

import java.util.List;


public class AudienceListAdapter extends BaseQuickAdapter<RandomLuckyEntity.UsersBean, BaseViewHolder> {
    public AudienceListAdapter(int layoutResId, @Nullable List<RandomLuckyEntity.UsersBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RandomLuckyEntity.UsersBean item) {
        helper.setText(R.id.tv_extract,item.getUsername());
        //自视图的点击事件
        helper.addOnClickListener(R.id.tv2_extract);
        switch (item.getStatus()){
            case 2:
                helper.setText(R.id.tv2_extract,"连麦中");
                helper.setTextColor(R.id.tv2_extract,Color.parseColor("#4EFF00"));
                break;
            case 3:
                helper.setText(R.id.tv2_extract,"已参与");
                helper.setTextColor(R.id.tv2_extract,Color.parseColor("#4EFF00"));
                break;
            case 4:
                helper.setText(R.id.tv2_extract,"已拒绝");
                helper.setTextColor(R.id.tv2_extract,Color.parseColor("#4EFF00"));
                break;
            case 5:
                helper.setText(R.id.tv2_extract,"已退出");
                helper.setTextColor(R.id.tv2_extract,Color.parseColor("#4EFF00"));
                break;
            default:
                helper.setText(R.id.tv2_extract, "连麦");
                break;
        }
    }
}
