package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;

/**
 * 用户协议
 */
public interface AgreementView extends BaseView {

    /**
     * 获取用户协议
     */
    void getAgreement();

    void getAgreementSuccess(String data);
}
