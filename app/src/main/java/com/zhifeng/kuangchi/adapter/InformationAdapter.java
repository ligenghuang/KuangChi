package com.zhifeng.kuangchi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.cusview.richtxtview.XRichText;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.module.HomeDataDto;
import com.zhifeng.kuangchi.ui.home.InformationDetailActivity;
import com.zhifeng.kuangchi.ui.home.NoticeDetailActivity;

/**
 * 公告列表 适配器
 */
public class InformationAdapter extends BaseRecyclerAdapter<HomeDataDto.DataBean.AnnounceBean>{
    Context context;

    public InformationAdapter(Context context) {
        super(R.layout.layout_item_genaral);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, HomeDataDto.DataBean.AnnounceBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_list_title,model.getTitle());

        ImageView imageView = holder.itemView.findViewById(R.id.iv_item_img);
        GlideUtil.setImage(context,model.getPicture(),imageView,R.mipmap.icon_info);

//        XRichText xRichText = holder.itemView.findViewById(R.id.xrichtext);
//        xRichText.text(model.getDesc());
//        xRichText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //todo 跳转至资讯详情页
//                Intent intent = new Intent(context, InformationDetailActivity.class);
//                intent.putExtra("id",model.getId()+"");
//                context.startActivity(intent);
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 跳转至资讯详情页
                Intent intent = new Intent(context, InformationDetailActivity.class);
                intent.putExtra("id",model.getId()+"");
                context.startActivity(intent);
            }
        });
    }
}
