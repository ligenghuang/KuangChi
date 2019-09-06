package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.AgencyListDto;
import com.zhifeng.kuangchi.module.LowerListDto;

/**
  *
  * @ClassName:     舰队长代理明细
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 20:19
  * @Version:        1.0
 */

public interface LowerListView extends BaseView {

    /**
     * 刷新数据
     */
    void refreshLowerList();
    /**
     * 加载更多数据
     */
    void loadMoreLowerList();

    /**
     *
     * @param lowerListDto
     */
    void getLowerListSuccess(LowerListDto lowerListDto);

}
