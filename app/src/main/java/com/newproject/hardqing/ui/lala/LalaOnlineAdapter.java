package com.newproject.hardqing.ui.lala;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newproject.hardqing.R;
import com.newproject.hardqing.constant.UrlConst;
import com.newproject.hardqing.util.GlideUtil;

import java.util.List;

public class LalaOnlineAdapter extends BaseQuickAdapter<LalaOnlineBean.DataBean, BaseViewHolder> {
    private Context mContext;
    private final RequestOptions rp;

    public LalaOnlineAdapter(@Nullable List<LalaOnlineBean.DataBean> data, Context context) {
        super(R.layout.item_lala_online, data);
        rp = GlideUtil.createRp(R.mipmap.pic_3);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LalaOnlineBean.DataBean item) {
        GlideUtil.setImage((ImageView) helper.getView(R.id.iv_avatar), mContext, UrlConst.PICTURE_ADDRESS + item.getAvatar(), rp);
    }
}
