package com.zhifeng.kuangchi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.module.CarryListDto;
import com.zhifeng.kuangchi.module.TopUpListDto;
import com.zhifeng.kuangchi.ui.my.CarryDetailActivity;
import com.zhifeng.kuangchi.util.data.DynamicTimeFormat;

/**
  *
  * @ClassName:     充值明细列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 9:24
  * @Version:        1.0
 */

public class PutListAdapter extends BaseRecyclerAdapter<TopUpListDto.DataBeanX.DataBean> {
    Context context;
    public PutListAdapter(Context context) {
        super(R.layout.layout_item_carry_2);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, TopUpListDto.DataBeanX.DataBean model, int position) {
        holder.setIsRecyclable(false);
        long data = (long) model.getAdd_time()*(long)1000;
        holder.text(R.id.tv_item_time, DynamicTimeFormat.LongToString(data));
        holder.text(R.id.tv_item_money,""+model.getMoney());
        String text = "USDT";//4:usdt 5:lamb
        switch (model.getCoin_type()){
            case 4:
                text = "USDT";
                break;
            case 5:
                text = "LAMB";
                break;
        }
        holder.text(R.id.tv_item_coin,text);

        holder.itemView.findViewById(R.id.tv_item_remark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 跳转至提币详情页
                Intent intent = new Intent(context, CarryDetailActivity.class);
                intent.putExtra("id",model.getId());
                intent.putExtra("type",1);
                context.startActivity(intent);
            }
        });
    }
}
