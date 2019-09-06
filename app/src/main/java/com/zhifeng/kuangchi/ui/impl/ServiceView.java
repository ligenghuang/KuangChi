package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.ServiceDto;

import java.util.Map;

/**
  *
  * @ClassName:     存储服务器群组
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 18:17
  * @Version:        1.0
 */

public interface ServiceView extends BaseView {
    /**
     * 存储服务器群组
     */
    void getService();
    void getServiceSuccess(ServiceDto serviceDto, Map<String,String> map);
}
