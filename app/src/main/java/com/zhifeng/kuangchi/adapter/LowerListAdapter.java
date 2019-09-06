package com.zhifeng.kuangchi.adapter;

import android.widget.TextView;

import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.module.LowerListDto;
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
        holder.text(R.id.tv_item_name,model.getRealname());//名称
        holder.text(R.id.tv_item_id,model.getId()+"");//id
        TextView lever = holder.itemView.findViewById(R.id.tv_item_lever);
        setLever(lever,model.getLevel());
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
