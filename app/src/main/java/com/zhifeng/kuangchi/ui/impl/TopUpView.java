package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.CarryListDto;
import com.zhifeng.kuangchi.module.TopUpListDto;

/**
  *
  * @ClassName:     充值明细
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 9:08
  * @Version:        1.0
 */

public interface TopUpView extends BaseView {


    /**
     * 刷新数据
     */
    void refreshCarryList();
    /**
     * 加载更多数据
     */
    void loadMoreCarryList();

    /**
     *
     * @param topUpListDto
     */
    void getCarryListSuccess(TopUpListDto topUpListDto);
}
