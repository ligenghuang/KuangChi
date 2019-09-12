package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.CarryDetailDto;
import com.zhifeng.kuangchi.module.PutDetailDto;

/**
  *
  * @ClassName:     提币详情页
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 10:11
  * @Version:        1.0
 */

public interface CarryDetailView extends BaseView {
    /**
     * 获取提币详情
     */
    void getCarryDetail();
    void getCarryDetailSuccess(CarryDetailDto carryDetailDto);

    void getPutDetail();
    void getPutDetailSuccess(PutDetailDto putDetailDto);
}
