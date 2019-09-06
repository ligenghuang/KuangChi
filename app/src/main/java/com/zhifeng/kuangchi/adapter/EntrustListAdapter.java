package com.zhifeng.kuangchi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.module.EntrustListDto;
import com.zhifeng.kuangchi.module.HomeDataDto;
import com.zhifeng.kuangchi.ui.home.NoticeDetailActivity;
import com.zhifeng.kuangchi.util.data.DynamicTimeFormat;

import java.text.ParseException;

/**
  *
  * @ClassName:     委托明细列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 18:51
  * @Version:        1.0
 */

public class EntrustListAdapter extends BaseRecyclerAdapter<EntrustListDto.DataBean.EntrustListBean>{


    public EntrustListAdapter() {
        super(R.layout.layout_item_enteust);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder,EntrustListDto.DataBean.EntrustListBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_enteust_name,model.getRealname());//委托人
        try {
            long date = DynamicTimeFormat.stringToLong(model.getCreate_time(),"yyyy-MM-dd HH:mm:ss");
            holder.text(R.id.tv_enteust_add_time, DynamicTimeFormat.LongToString4(date));//日期
            holder.text(R.id.tv_enteust_num,model.getT_num()+"T");//数量
//        holder.text(R.id.tv_enteust_type,"");//状态
            TextView type = holder.itemView.findViewById(R.id.tv_enteust_type);
            setType(type,model.getEntrust_status());
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void setType(TextView type, int entrust_status) {
        String text = "未审核";
        switch (entrust_status){
            case 0:
                text = "未审核";
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
