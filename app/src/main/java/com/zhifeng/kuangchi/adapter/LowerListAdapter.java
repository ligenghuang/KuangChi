package com.zhifeng.kuangchi.adapter;

import android.widget.TextView;

import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.module.LowerListDto;
import com.zhifeng.kuangchi.util.data.DynamicTimeFormat;

/**
  *
  * @ClassName:     下级代理列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 19:27
  * @Version:        1.0
 */

public class LowerListAdapter extends BaseRecyclerAdapter<LowerListDto.DataBeanX.DataBean> {
    public LowerListAdapter() {
        super(R.layout.layout_item_lower);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, LowerListDto.DataBeanX.DataBean model, int position) {
        holder.setIsRecyclable(false);
        String phone = model.getMobile();
        holder.text(R.id.tv_item_phone,phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        holder.text(R.id.tv_item_name_id,"ID:"+model.getId());
        long data = (long) model.getCreatetime()*(long)1000;
        holder.text(R.id.tv_item_time, DynamicTimeFormat.LongToString2(data));
        TextView lever = holder.itemView.findViewById(R.id.tv_item_lower);
        setLever(lever,model.getLevel());

        TextView tvVip = holder.itemView.findViewById(R.id.tv_item_vip);
        tvVip.setText(ResUtil.getString(model.getIs_vip() == 1?R.string.my_tab_135:R.string.my_tab_136));
        TextView tvNameApi = holder.itemView.findViewById(R.id.tv_item_nameapi);
        tvNameApi.setText(ResUtil.getString(model.getIs_vip() == 1?R.string.my_tab_145:R.string.my_tab_146));
    }

    /**
     *
     * @param tvLever
     * @param level
     */
    private void setLever(TextView tvLever, int level) {
        String text = "普通用户";
        switch (level){
            case 0:
                text = "普通用户";
                break;
            case 1:
                text = "星月";
                break;
            case 2:
                text = "星光";
                break;
            case 3:
                text = "星宝";
                break;
            case 4:
                text = "星河";
                break;
            case 5:
                text = "级差等级一";
                break;
            case 6:
                text = "级差等级二";
                break;
            case 7:
                text = "级差等级三";
                break;
        }
        tvLever.setText(text);
    }
}
