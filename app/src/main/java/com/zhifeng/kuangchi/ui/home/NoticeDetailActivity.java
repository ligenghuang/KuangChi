package com.zhifeng.kuangchi.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.cusview.richtxtview.XRichText;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.NoticeDetailAction;
import com.zhifeng.kuangchi.module.NoticeDetailDto;
import com.zhifeng.kuangchi.ui.impl.NoticeDetailView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
  *
  * @ClassName:     公告详情页
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 10:59
  * @Version:        1.0
 */
public class NoticeDetailActivity extends UserBaseActivity<NoticeDetailAction> implements NoticeDetailView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @BindView(R.id.xrichtext)
    XRichText xrichtext;

    String id;

    @Override
    public int intiLayout() {
        return R.layout.activity_notice_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected NoticeDetailAction initAction() {
        return new NoticeDetailAction(this,this);
    }

    /**
     * 初始化标题栏
     */
    @Override
    protected void initTitlebar() {
        super.initTitlebar();
        mImmersionBar
                .statusBarView(R.id.top_view)
                .keyboardEnable(true)
                .statusBarDarkFont(true, 0.2f)
                .addTag("NoticeDetailActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.home_tab_10));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        id = getIntent().getStringExtra("id");

        getNoticeDetail();
    }

    /**
     * 获取公告详情页
     */
    @Override
    public void getNoticeDetail() {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            L.e("Rx","id  = "+id);
            baseAction.getNoticeDetail(id);
        }

    }

    /**
     * 获取公告详情页数据成功
     * @param noticeDetailDto
     */
    @Override
    public void getNoticeDetailSuccess(NoticeDetailDto noticeDetailDto) {
        loadDiss();
        NoticeDetailDto.DataBean dataBean = noticeDetailDto.getData();
//        tvDetailTitle.setText(dataBean.getTitle());
        xrichtext.text(dataBean.getDesc());
    }

    /**
     * 失败
     * @param message
     * @param code
     */
    @Override
    public void onError(String message, int code) {
        loadDiss();
        showNormalToast(message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        baseAction.toRegister();
    }

    @Override
    protected void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }
}
