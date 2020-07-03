package com.newproject.hardqing.adapter;

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
    }
}
