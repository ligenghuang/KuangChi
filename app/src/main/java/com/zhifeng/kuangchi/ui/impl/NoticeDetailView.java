package com.zhifeng.kuangchi.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.kuangchi.module.NoticeDetailDto;

/**
 * 公告详情页
 */
public interface NoticeDetailView extends BaseView {
    /**
     * 获取公告详情数据
     */
    void getNoticeDetail();
    void getNoticeDetailSuccess(NoticeDetailDto noticeDetailDto);
}
