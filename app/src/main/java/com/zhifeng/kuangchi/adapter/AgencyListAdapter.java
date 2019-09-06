package com.zhifeng.kuangchi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.module.AgencyListDto;
import com.zhifeng.kuangchi.ui.my.LowerListActivity;

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
        holder.text(R.id.tv_item_name_id,model.getRealname()+"/"+model.getId());
        holder.text(R.id.tv_item_lower,model.getLevel()+"");
        TextView lever = holder.itemView.findViewById(R.id.tv_item_lower);
        setLever(lever,model.getLevel());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 跳转至舰队长代理页面
                Intent intent = new Intent(context, LowerListActivity.class);
                intent.putExtra("id",model.getId()+"");
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
