package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.BalanceDto;
import com.zhifeng.kuangchi.module.GeneralDto;
import com.zhifeng.kuangchi.module.post.EntrustedPurchasePost;

/**
  *
  * @ClassName:     委托购买
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 17:27
  * @Version:        1.0
 */

public interface EntrustedPurchaseView extends BaseView {

    /**
     * 获取我的余额 获取币类型
     */
    void getBalance();
    void getBalanceSuccess(BalanceDto balanceDto);

    /**
     * 委托购买
     */
    void EntrustedPurchase(EntrustedPurchasePost entrustedPurchasePost);
    void EntrustedPurchaseSuccess(GeneralDto generalDto);
    void EntrustedPurchaseError(String msg);
}
