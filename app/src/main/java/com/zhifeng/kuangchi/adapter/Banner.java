package com.zhifeng.kuangchi.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.lgh.huanglib.util.config.GlideApp;
import com.zhifeng.kuangchi.R;

import cn.bingoogolapple.bgabanner.BGABanner;

public class Banner implements BGABanner.Adapter<ImageView, String> {


    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
        GlideApp.with(itemView.getContext()).load(model).dontAnimate().placeholder(R.mipmap.banner).error(R.mipmap.banner)
                .into(itemView);
    }

}
