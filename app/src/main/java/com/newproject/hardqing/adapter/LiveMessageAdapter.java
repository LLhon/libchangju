package com.newproject.hardqing.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newproject.hardqing.R;
import com.newproject.hardqing.constant.UrlConst;
import com.newproject.hardqing.entity.BaPingChatMessage;
import com.newproject.hardqing.ui.receivecmd.SendMsgEntity;
import com.newproject.hardqing.util.GlideUtil;
import com.newproject.hardqing.util.GsonConverter;
import com.newproject.hardqing.view.SocketReciveView;

import java.util.List;
import java.util.Random;

public class LiveMessageAdapter extends BaseQuickAdapter<SendMsgEntity, BaseViewHolder> {
    private RequestOptions rp;
    private RequestOptions mImageRequestOptions;

    private Context context;

    private SocketReciveView mSocketReciveView;


    public LiveMessageAdapter(@Nullable List<SendMsgEntity> data, Context context, SocketReciveView socketReciveView) {
        super(R.layout.live_message_layout, data);
        this.context = context;
        rp = GlideUtil.createRp(R.mipmap.logo);
        mImageRequestOptions = GlideUtil.createRp(R.drawable.shape_chat_message_bg);
        this.mSocketReciveView = socketReciveView;
    }

    @Override
    protected void convert(BaseViewHolder helper, SendMsgEntity item) {
        String itemContent = item.getContent();
        if (!TextUtils.isEmpty(itemContent) && GsonConverter.isJson(itemContent)) {
            final BaPingChatMessage baPingChatMessage = GsonConverter.fromJson(itemContent, BaPingChatMessage.class);
            if (baPingChatMessage == null) {
                helper.getView(R.id.rl_image).setVisibility(View.GONE);
                helper.getView(R.id.rl_text).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_message, item.getUsername() + ": " + itemContent);
                GlideUtil.setImage((ImageView) helper.getView(R.id.civ_icon), context, UrlConst.PICTURE_ADDRESS + item.getAvatar(), rp);
            } else {
                switch (baPingChatMessage.getType()) {
                    case 3:
                        helper.getView(R.id.rl_image).setVisibility(View.GONE);
                        helper.getView(R.id.iv_red_envelpopes).setVisibility(View.VISIBLE);
                        helper.getView(R.id.rl_text).setVisibility(View.VISIBLE);
                        helper.setText(R.id.tv_message, item.getUsername() + ": " + baPingChatMessage.getMessageValue());
                        GlideUtil.setImage((ImageView) helper.getView(R.id.civ_icon), context, item.getAvatar(), rp);
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    default:
                        helper.getView(R.id.rl_text).setVisibility(View.GONE);
                        helper.getView(R.id.rl_image).setVisibility(View.VISIBLE);
                        helper.setText(R.id.ba_message, item.getUsername() + ": ");
                        TextView textView = helper.getView(R.id.tv_show_time);
                        if (textView != null) {
                            textView.setText(baPingChatMessage.getTime() + "s");
                        }
                        GlideUtil.setImage((ImageView) helper.getView(R.id.civ_avatar), context, item.getAvatar(), rp);
                        ImageView imageView = helper.getView(R.id.iv_image);
                        if (imageView != null) {
                            String picUrl = baPingChatMessage.getUri();
                            String[] picUrlList = picUrl.split(",");
                            if (picUrlList == null || picUrlList.length < 1) {
                                helper.getView(R.id.rl_image).setVisibility(View.GONE);
                                return;
                            } else {
                                GlideUtil.setImage(imageView, context, picUrlList[0], mImageRequestOptions);
                            }
                        }
                }
            }
        } else {
            helper.getView(R.id.rl_image).setVisibility(View.GONE);
            helper.getView(R.id.iv_red_envelpopes).setVisibility(View.GONE);
            helper.getView(R.id.rl_text).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_message, item.getUsername() + ": " + itemContent);
            GlideUtil.setImage((ImageView) helper.getView(R.id.civ_icon), context, UrlConst.PICTURE_ADDRESS + item.getAvatar(), rp);
        }
        helper.setTextColor(R.id.tv_message, Color.rgb(new Random().nextInt(256),
            new Random().nextInt(256), new Random().nextInt(256)));
    }

}
