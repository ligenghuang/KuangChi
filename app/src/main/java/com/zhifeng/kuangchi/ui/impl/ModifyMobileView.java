package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.BaseDto;

/**
  *
  * @ClassName:     修改手机号
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 16:53
  * @Version:        1.0
 */

public interface ModifyMobileView  extends BaseView {

    /**
     * 校验手机号
     */
    void checkPhone(String phone);
    void checkPhoneSuccess(BaseDto baseDto);
}
