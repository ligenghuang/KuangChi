package com.zhifeng.kuangchi.adapter;

import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.module.MessageListDto;

/**
  *
  * @ClassName:     消息列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 21:20
  * @Version:        1.0
 */

public class MessageListAdapter extends BaseRecyclerAdapter<MessageListDto.DataBean>{
    public MessageListAdapter() {
        super(R.layout.layout_item_message);
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, MessageListDto.DataBean model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_item_message_title,model.getTitle());
        holder.text(R.id.tv_item_description,model.getDescription());
        holder.text(R.id.tv_item_time,model.getTime());
    }
}
