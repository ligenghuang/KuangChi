package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.EntrustListDto;

/**
  *
  * @ClassName:     委托明细
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 18:26
  * @Version:        1.0
 */

public interface EntrustListView extends BaseView {

    /**
     * 刷新数据
     */
    void refreshEntrustList();
    /**
     * 加载更多数据
     */
    void loadMoreEntrustList();

    /**
     *
     * @param entrustListDto
     */
    void getEntrustListSuccess(EntrustListDto entrustListDto);
}
