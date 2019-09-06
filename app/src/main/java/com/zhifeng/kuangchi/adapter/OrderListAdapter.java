package com.zhifeng.kuangchi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.cusview.magicIndicator.buildins.commonnavigator.titles.CommonPagerTitleView;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.module.OrderListDto;
import com.zhifeng.kuangchi.ui.home.CustomerServiceActivity;
import com.zhifeng.kuangchi.util.data.DynamicTimeFormat;

/**
  *
  * @ClassName:     我的订单列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 15:26
  * @Version:        1.0
 */

public class OrderListAdapter extends BaseRecyclerAdapter<OrderListDto.DataBean.CarryListBean> {
    Context context;

    public OrderListAdapter(Context context) {
        super(R.layout.layout_item_order);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, OrderListDto.DataBean.CarryListBean model, int position) {
        holder.setIsRecyclable(false);
        long time = model.getAdd_time() * (long)1000;
        holder.text(R.id.tv_item_order_time, DynamicTimeFormat.LongToString2(time));//时间
        holder.text(R.id.tv_item_order_name,model.getGoods_name());//名称
        holder.text(R.id.tv_item_order_pay_type, ResUtil.getFormatString(R.string.my_tab_84,model.getPay_type()+""));//todo 支付方式
        holder.text(R.id.tv_item_order_t_num,ResUtil.getFormatString(R.string.my_tab_85,model.getT_num()));//存储空间
        holder.text(R.id.tv_item_order_money,"￥"+model.getOrder_amount());//金额

        ImageView imageView = holder.itemView.findViewById(R.id.iv_item_order_img);
        GlideUtil.setImage(context,"",imageView,R.mipmap.goods_img);//todo 商品图片

        TextView type = holder.itemView.findViewById(R.id.tv_item_order_type);
        setType(type,model.getEntrust_status());
        holder.itemView.findViewById(R.id.tv_item_order_to_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CustomerServiceActivity.class);
                context.startActivity(intent);
            }
        });
    }

    private void setType(TextView type, int entrust_status) {
        String text = "待审核";
        switch (entrust_status){
            case 0:
                text = "待审核";
                break;
            case 1:
                text = "审核通过";
                break;
            case 2:
                text = "审核不通过";
                break;
        }
        type.setText(text);
    }
}
