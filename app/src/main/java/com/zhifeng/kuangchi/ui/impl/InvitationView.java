package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.InvitationInfoDto;

/**
  *
  * @ClassName:     邀请链接
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 15:09
  * @Version:        1.0
 */

public interface InvitationView extends BaseView {

    void getInvitation();
    void getInvitationSuccess(InvitationInfoDto invitationInfoDto);
}
