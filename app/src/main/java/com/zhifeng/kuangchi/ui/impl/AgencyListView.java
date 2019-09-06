package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.AgencyListDto;

/**
  *
  * @ClassName:     代理明细
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 20:19
  * @Version:        1.0
 */

public interface AgencyListView extends BaseView {

    /**
     * 刷新数据
     */
    void refreshAgencyList();
    /**
     * 加载更多数据
     */
    void loadMoreAgencyList();

    /**
     *
     * @param agencyListDto
     */
    void getAgencyListSuccess(AgencyListDto agencyListDto);

}
