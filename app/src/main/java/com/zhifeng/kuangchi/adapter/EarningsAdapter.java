package com.zhifeng.kuangchi.adapter;

import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.module.EarningsListDto;
import com.zhifeng.kuangchi.util.data.DynamicTimeFormat;

import java.text.ParseException;
import java.util.Date;

/**
  *
  * @ClassName:     收益明细列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 22:03
  * @Version:        1.0
 */

public class EarningsAdapter extends BaseRecyclerAdapter<EarningsListDto.DataBeanX.DataBean> {
    public EarningsAdapter() {
        super(R.layout.layout_item_earnings);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, EarningsListDto.DataBeanX.DataBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_balance, ResUtil.getFormatString(R.string.my_tab_55,model.getBalance()));//金额
        holder.text(R.id.tv_item_note,model.getNote());//描述
        try {
            long data = (long) model.getCreate_time()*(long)1000;
            L.e("lgh_time", "time  = " + DynamicTimeFormat.longToString(data, "yyyy/MM/dd HH:mm"));
            L.e("lgh_time", "time  = " + data);
            holder.text(R.id.tv_item_time, DynamicTimeFormat.longToString(data,"yyyy/MM/dd HH:mm"));//时间
        }catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
