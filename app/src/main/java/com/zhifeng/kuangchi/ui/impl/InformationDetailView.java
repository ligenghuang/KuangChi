package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.InfoDetailDto;

/**
 * 资讯详情页
 */
public interface InformationDetailView extends BaseView {
    /**
     * 获取详情页数据
     */
    void getInfoDetail();
    void getInfoDetailSuccess(InfoDetailDto infoDetailDto);
}
