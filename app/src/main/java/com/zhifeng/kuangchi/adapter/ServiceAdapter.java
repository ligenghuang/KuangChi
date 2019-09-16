package com.zhifeng.kuangchi.adapter;

import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.module.ServiceDto;
import com.zhifeng.kuangchi.util.data.DynamicTimeFormat;

import java.text.DecimalFormat;

/**
  *
  * @ClassName:     存储服务器群组列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 20:55
  * @Version:        1.0
 */

public class ServiceAdapter extends BaseRecyclerAdapter<ServiceDto.DataBean.MinerInfoBean> {
    public ServiceAdapter() {
        super(R.layout.layout_item_service);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, ServiceDto.DataBean.MinerInfoBean model, int position) {
        holder.setIsRecyclable(false);
//        holder.text(R.id.tv_item_miner_id,model.getMiner_id());
        String minSuanli = Math.round(Double.valueOf(model.getMinimum_suanli()))+"";
        String minSuanyi = Math.round(Double.valueOf(model.getMinimum_shouyi()))+"";
        String maxSuanli = Math.ceil(Double.valueOf(model.getMaximum_suanli()))+"";
        String maxSuanyi = Math.ceil(Double.valueOf(model.getMaximum_shouyi()))+"";
        holder.text(R.id.tv_item_maximum_suanli,model.getT_num()+"");
        holder.text(R.id.tv_item_shouyi,"0~3");
        long time = model.getCreatetime() * (long)1000;
        holder.text(R.id.tv_item_time, DynamicTimeFormat.LongToString2(time));
//        holder.text(R.id.tv_item_day_nums,model.getDay_nums()+"");
    }
}
