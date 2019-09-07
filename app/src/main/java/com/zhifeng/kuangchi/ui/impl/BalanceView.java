package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.BalanceDto;
import com.zhifeng.kuangchi.module.CoinRateDto;
import com.zhifeng.kuangchi.module.GeneralDto;
import com.zhifeng.kuangchi.module.post.GetCoinPost;
import com.zhifeng.kuangchi.module.post.PutCoinPost;

/**
  *
  * @ClassName:     我的余额
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 11:31
  * @Version:        1.0
 */

public interface BalanceView extends BaseView {
    /**
     * 获取我的余额
     */
    void getBalance();
    void getBalanceSuccess(BalanceDto balanceDto);

    /**
     * 获取验证码成功
     */
    void getAuthCode();
    void getAuthCodeSuccess(String generalDto);

    /**
     * 提币
     */
    void getCoin(GetCoinPost getCoinPost);
    void getCoinSuccess(GeneralDto generalDto);

    /**
     * 充币
     */
    void putCoin(PutCoinPost putCoinPost);
    void putCoinSuccess(GeneralDto generalDto);

    /**
     * 获取转换率
     * @param coinType
     */
    void getRate(String coinType);
    void getRateSuccess(CoinRateDto coinRateDto);
}
