package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.MessageListDto;

/**
  *
  * @ClassName:     消息中心
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 15:38
  * @Version:        1.0
 */

public interface MessageView extends BaseView {
    /**
     * 获取消息中心数据
     */
    void getMessageList();
    void getMessageListSuccess(MessageListDto messageListDto);
}
