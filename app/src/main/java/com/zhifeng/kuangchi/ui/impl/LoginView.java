package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.LoginDto;

/**
 * 登录页面
 */
public interface LoginView extends BaseView {

    /**
     * 获取验证码
     */
    void getCodeSuccess(String msg);

    /**
     * 登录或注册
     */
    void loginOrRegisteredSuccess(LoginDto loginDto);
}
