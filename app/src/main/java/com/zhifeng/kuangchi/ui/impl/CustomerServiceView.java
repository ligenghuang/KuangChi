package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.CustomerServiceListDto;

/**
  *
  * @ClassName:     客服对话列表页
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 11:07
  * @Version:        1.0
 */

public interface CustomerServiceView extends BaseView {
    /**
     * 获取客服对话列表
     */
    void getCustomerServiceList();
    void getCustomerServiceListSuccess(CustomerServiceListDto customerServiceListDto);

    /**
     * 发送消息
     * @param msg
     */
    void sendMessage(String msg);
    void sendMessageSuccess();
}
