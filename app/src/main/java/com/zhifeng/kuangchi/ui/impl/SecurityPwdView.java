package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.post.SetPayPwdPost;

/**
  *
  * @ClassName:     支付密码
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 9:17
  * @Version:        1.0
 */

public interface SecurityPwdView extends BaseView {
    /**
     * 获取验证码成功
     */
    void getAuthCode();
    void getAuthCodeSuccess(String generalDto);

    /**
     * 设置密码
     */
    void setPayPwd(SetPayPwdPost setPayPwdPost);
    void setPayPwdSuccess(String generalDto);

    /**
     * 忘记支付密码
     */
    void forgetPayPwd(SetPayPwdPost setPayPwdPost);
    void forgetPayPwdSuccess(String generalDto);

    /**
     * 修改支付密码
     */
    void editPayPwd(String oldPwd,String newPwd);
    void editPayPwdSuccess(String generalDto);
}
