package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.SecurityInfoDto;

/**
  *
  * @ClassName:     安全中心
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 16:24
  * @Version:        1.0
 */

public interface SecurityView extends BaseView {

    /**
     * 获取安全中心信息
     */
    void getSecurityInfo();
    void getSecurityInfoSuccess(SecurityInfoDto securityInfoDto);
}
