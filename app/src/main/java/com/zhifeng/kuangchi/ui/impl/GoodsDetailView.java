package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.BalanceDto;
import com.zhifeng.kuangchi.module.GoodsDetailDto;

/**
  *
  * @ClassName:     商品详情
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 17:03
  * @Version:        1.0
 */

public interface GoodsDetailView extends BaseView {

    /**
     * 获取商品详情
     */
    void getGoodsDetail();
    void getGoodsDetailSuccess(GoodsDetailDto goodsDetailDto);
}
