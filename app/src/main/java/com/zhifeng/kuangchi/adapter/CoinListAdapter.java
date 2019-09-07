package com.zhifeng.kuangchi.adapter;

import android.view.View;
import android.widget.ImageView;

import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.module.BalanceDto;
/**
  *
  * @ClassName:     币列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 12:21
  * @Version:        1.0
 */

public class CoinListAdapter extends BaseRecyclerAdapter<BalanceDto.DataBean.CoinAddressBean>{

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CoinListAdapter() {
        super(R.layout.layout_item_coin);
    }



    @Override
    protected void onBindViewHolder(SmartViewHolder holder, BalanceDto.DataBean.CoinAddressBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_coin_name,model.getCoin_name());

        ImageView imageView = holder.itemView.findViewById(R.id.iv_coin);
        imageView.setImageResource(model.isClick()?R.mipmap.icon_coin_y:0);
        ImageView img = holder.itemView.findViewById(R.id.iv_coin_img);
        int res = R.mipmap.filecoin;
        switch (model.getCoin_name()){
            case "FILECOIN":
                img.setImageResource(R.mipmap.filecoin);
                break;
            case "LAMB":
                img.setImageResource(R.mipmap.icon_coin);
                res = R.mipmap.icon_coin;
                break;
            case "USDT":
                img.setImageResource(R.mipmap.usdt);
                res = R.mipmap.usdt;
                break;
            case "BTC":
                img.setImageResource(R.mipmap.btc);
                break;
            case "ETH":
                img.setImageResource(R.mipmap.eth);
                break;
        }

        int finalRes = res;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(model.getAddress(),model.getId(),model.getPay_type(), finalRes,model.getUser_money(),1);
            }
        });
    }

    public interface OnClickListener{
        void onClick(String address,int id,int coinType,int res,double user_money,double Rate);
    }
}
