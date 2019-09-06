package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
/**
  *
  * @ClassName:     身份认证
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 15:31
  * @Version:        1.0
 */

public interface IdCardView extends BaseView {

    void setIdCard(String name ,String idNum);
    void setIdCardSuccess();
}
