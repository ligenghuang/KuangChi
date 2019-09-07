package com.zhifeng.kuangchi.adapter;

import android.view.View;
import android.widget.TextView;

import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.module.GoodsDetailDto;
/**
  *
  * @ClassName:     规格列表
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 22:00
  * @Version:        1.0
 */

public class SpecListAdapter extends BaseRecyclerAdapter<GoodsDetailDto.DataBean.SpecBean.GoodsSkuBean> {

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public SpecListAdapter() {
        super(R.layout.layout_item_spec);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, GoodsDetailDto.DataBean.SpecBean.GoodsSkuBean model, int position) {
        holder.setIsRecyclable(false);
        TextView textView = holder.itemView.findViewById(R.id.tv_item_spec);
        textView.setText(model.getName());

        textView.setSelected(model.isClick());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.OnClick(model.getInventory(),model.getSku_id(),model.getPrice(),model.getName());
            }
        });
    }

    public interface OnClickListener{
        void OnClick(int Inventory,int Sku_id,String price,String tNum);
    }
}
