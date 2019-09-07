package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.GeneralDto;
import com.zhifeng.kuangchi.module.post.MoblieLoginPwdPost;

/**
  *
  * @ClassName:     修改登录密码
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/7 14:23
  * @Version:        1.0
 */

public interface MoblieLoginPwdView extends BaseView {

    /**
     * 获取验证码成功
     */
    void getAuthCode();
    void getAuthCodeSuccess(String generalDto);

    /**
     * 修改登录密码
     * @param pwdPost
     */
    void moblieLoginPwd(MoblieLoginPwdPost pwdPost);
    void moblieLoginPwdSuccess(GeneralDto generalDto);
}
