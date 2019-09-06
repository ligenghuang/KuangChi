package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.OrderListDto;

/**
  *
  * @ClassName:     我的订单
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 14:42
  * @Version:        1.0
 */

public interface OrderView extends BaseView {
    /**
     * 刷新数据
     */
    void refreshOrderList();
    /**
     * 加载更多数据
     */
    void loadMoreOrderList();

    /**
     *
     * @param orderListDto
     */
    void getOrderListSuccess(OrderListDto orderListDto);
}
