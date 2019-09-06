package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.GeneralDto;

/**
  *
  * @ClassName:     修改昵称头像
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/4 17:55
  * @Version:        1.0
 */

public interface ModifyUserInfoView extends BaseView {

    void ModifyUserName(String name);
    void ModifyUserNameSuccess(GeneralDto generalDto);

    void ModifyUserAvatar(String path);
    void ModifyUserAvatarSuccess(GeneralDto generalDto);
}
