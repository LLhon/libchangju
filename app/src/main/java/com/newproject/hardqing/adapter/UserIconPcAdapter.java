package com.newproject.hardqing.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newproject.hardqing.R;
import com.newproject.hardqing.constant.UrlConst;
import com.newproject.hardqing.entity.RoomUserBean;
import com.newproject.hardqing.util.GlideUtil;

import java.util.List;

//这个滑动列表 里的用户是一个个加进去 还是怎么样
public class UserIconPcAdapter extends BaseQuickAdapter<RoomUserBean.DataBean, BaseViewHolder> {
    private final RequestOptions rp;
    public UserIconPcAdapter(@Nullable List<RoomUserBean.DataBean> data) {
        super(R.layout.user_iconpc_layout, data);
        rp = GlideUtil.createRp(R.mipmap.pic_3);

    }

    @Override
    protected void convert(BaseViewHolder helper, RoomUserBean.DataBean item) {
        GlideUtil.setImage((ImageView) helper.getView(R.id.civ_icon_one), mContext, UrlConst.PICTURE_ADDRESS + item.getAvatar(),rp);
       /*    switch (helper.getAdapterPosition() % 4) {
            case 0:

                break;
         case 1:
                view = helper.getView(R.id.civ_icon_two);
                view.setImageResource(item);
                break;
            case 2:
                view = helper.getView(R.id.civ_icon_three);
                view.setImageResource(item);
                break;
            case 3:
                view = helper.getView(R.id.civ_icon_four);
                view.setImageResource(item);
                break;
        }*/
    }
}
