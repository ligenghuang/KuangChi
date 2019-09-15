package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.HomeDataDto;
import com.zhifeng.kuangchi.module.UpdateDto;

/**
 * 首页
 */
public interface HomeView extends BaseView {

    /**
     * 获取首页数据
     */
    void getHomeData();

    void getHomeDataSuccess(HomeDataDto homeDataDto);

    void updata();
    void updataSuccessful(UpdateDto updateDto);
}
