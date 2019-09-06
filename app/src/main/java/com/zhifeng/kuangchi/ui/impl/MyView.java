package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.MyInfoDto;

/**
  *
  * @ClassName:    我的fragment
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 12:26
  * @Version:        1.0
 */
public interface MyView extends BaseView {
    /**
     * 获取我的信息
     */
    void getMyInfo();
    void getMyInfoSuccess(MyInfoDto infoDto);
    void getMyInfoError();
}
