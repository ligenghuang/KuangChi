package com.zhifeng.kuangchi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.module.AgencyListDto;
import com.zhifeng.kuangchi.ui.my.LowerListActivity;
import com.zhifeng.kuangchi.util.data.DynamicTimeFormat;

/**
  *
  * @ClassName:     代理明细列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 18:34
  * @Version:        1.0
 */

public class AgencyListAdapter extends BaseRecyclerAdapter<AgencyListDto.DataBeanX.UserFirstBean.DataBean>{
    Context context;
    public AgencyListAdapter(Context context) {
        super(R.layout.layout_item_agency);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, AgencyListDto.DataBeanX.UserFirstBean.DataBean model, int position) {
        holder.setIsRecyclable(false);
        String phone = model.getMobile();
        holder.text(R.id.tv_item_phone,phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        holder.text(R.id.tv_item_name_id,"ID:"+model.getId());
        long data = (long) model.getCreatetime()*(long)1000;
        holder.text(R.id.tv_item_time, DynamicTimeFormat.LongToString2(data));
      if (model.getT_time() == 0){
          holder.text(R.id.tv_item_buy_time,"未激活");
      }else {
          long timeData = (long) model.getT_time()*(long)1000;
          holder.text(R.id.tv_item_buy_time,DynamicTimeFormat.LongToString2(timeData));
      }
        TextView lever = holder.itemView.findViewById(R.id.tv_item_lower);
        setLever(lever,model.getLevel());

        TextView tvVip = holder.itemView.findViewById(R.id.tv_item_vip);
        tvVip.setText(ResUtil.getString(model.getIs_vip() == 1?R.string.my_tab_135:R.string.my_tab_136));
        TextView tvNameApi = holder.itemView.findViewById(R.id.tv_item_nameapi);
        tvNameApi.setText(ResUtil.getString(model.getIs_vip() == 1?R.string.my_tab_145:R.string.my_tab_146));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 跳转至舰队长代理页面
                Intent intent = new Intent(context, LowerListActivity.class);
                intent.putExtra("id",model.getId()+"");
                intent.putExtra("lever",model.getLevel());
                context.startActivity(intent);
            }
        });

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
