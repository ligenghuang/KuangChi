package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.HomeDataDto;
import com.zhifeng.kuangchi.module.KLineDto;
import com.zhifeng.kuangchi.module.ServiceDto;

public interface FoundView extends BaseView {

    /**
     * 存储服务器群组
     */
    void getService();
    void getServiceSuccess(KLineDto serviceDto);

    /**
     * 验证密码
     * @param pwd
     */
    void verifyPassword(String pwd);
    void verifyPasswordSuccess();
    void verifyPasswordError(String msg);

    void getHomeDataSuccess(HomeDataDto homeDataDto);
}
