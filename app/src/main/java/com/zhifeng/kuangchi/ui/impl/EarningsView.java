package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.EarningsListDto;
/**
  *
  * @ClassName:     收益明细
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 21:11
  * @Version:        1.0
 */

public interface EarningsView extends BaseView {

    void getEarningsList();
    void moreEarningsList();
    void getEarningsListSuccess(EarningsListDto earningsListDto);
}
