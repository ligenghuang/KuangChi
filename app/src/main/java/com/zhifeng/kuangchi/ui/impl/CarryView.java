package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.CarryListDto;

/**
  *
  * @ClassName:     提币明细
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 9:08
  * @Version:        1.0
 */

public interface CarryView extends BaseView {


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
     * @param carryListDto
     */
    void getCarryListSuccess(CarryListDto carryListDto);
}
