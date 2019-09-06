package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.GeneralDto;

/**
  *
  * @ClassName:     校验手机号成功后填写验证码
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 17:28
  * @Version:        1.0
 */

public interface MobileAuthCodeView extends BaseView {
    /**
     * 获取验证码成功
     */
    void getAuthCode();
    void getAuthCodeSuccess(String generalDto);

    /**
     * 换绑手机号
     */
    void ModifyMobile();
    void ModifyMobileSuccess(GeneralDto generalDto);
}
