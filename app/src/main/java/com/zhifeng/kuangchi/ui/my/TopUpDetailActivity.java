package com.zhifeng.kuangchi.ui.my;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.CarryAction;
import com.zhifeng.kuangchi.actions.TopUpAction;
import com.zhifeng.kuangchi.adapter.CarryListAdapter;
import com.zhifeng.kuangchi.adapter.PutListAdapter;
import com.zhifeng.kuangchi.module.CarryListDto;
import com.zhifeng.kuangchi.module.TopUpListDto;
import com.zhifeng.kuangchi.ui.impl.CarryView;
import com.zhifeng.kuangchi.ui.impl.TopUpView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
 * @ClassName: 充值明细
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/30 22:10
 * @Version: 1.0
 */

public class TopUpDetailActivity extends UserBaseActivity<TopUpAction> implements TopUpView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swiperefeshlayout)
    SmartRefreshLayout refreshLayout;

    PutListAdapter carryListAdapter;

    int page = 1;
    boolean isRefresh = true;
    //是否加载更多
    boolean isSlect = true;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;

    MyCountDownTimer topUpTimer;

    @Override
    public int intiLayout() {
        return R.layout.activity_carry_2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected TopUpAction initAction() {
        return new TopUpAction(this, this);
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
                .statusBarDarkFont(true)
                .addTag("TopUpDetailActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_tab_156));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        topUpTimer = new MyCountDownTimer(3600000,1000);

//        //初始化适配器
        carryListAdapter = new PutListAdapter(mContext);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(carryListAdapter);

        refreshLayout.autoRefresh();
//        refreshCarryList();

        loadView();
    }

    @Override
    protected void loadView() {
        super.loadView();
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //todo 上拉加载更多
                loadMoreCarryList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //todo 下拉刷新
                refreshCarryList();
            }
        });
    }

    /**
     * 刷新数据
     */
    @Override
    public void refreshCarryList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            isRefresh = true;
            page = 1;
            baseAction.getCarryList(page);
        } else {
            loadDiss();
            refreshLayout.finishRefresh();
        }
    }

    /**
     * 加载更多数据
     */
    @Override
    public void loadMoreCarryList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            isRefresh = false;
            page++;
            baseAction.getCarryList(page);
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    /**
     * 加载数据成功
     *
     * @param carryListDto
     */
    @Override
    public void getCarryListSuccess(TopUpListDto carryListDto) {
        loadDiss();
        if (topUpTimer != null){
            topUpTimer.cancel();
        }
        topUpTimer.start();
        refreshLayout.finishRefresh();//关闭刷新
        refreshLayout.finishLoadMore();//关闭加载更多
        TopUpListDto.DataBeanX dataBean = carryListDto.getData();
        if (dataBean.getData().size() != 0) {
            recyclerview.setVisibility(View.VISIBLE);
            tvNodata.setVisibility(View.GONE);
            isSlect = page < dataBean.getLast_page();
            loadSwapTab();
            if (isRefresh) {
                //刷新数据成功
                carryListAdapter.refresh(dataBean.getData());
            } else {
                //加载更多数据成功
                carryListAdapter.loadMore(dataBean.getData());
            }
        } else {
            //todo 2019/8/30 没数据 添加空布局
            if (isRefresh){
                recyclerview.setVisibility(View.GONE);
                tvNodata.setVisibility(View.VISIBLE);
            }
        }

    }

    /**
     * 失败
     *
     * @param message
     * @param code
     */
    @Override
    public void onError(String message, int code) {
        loadDiss();
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
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
        if (topUpTimer != null){
            topUpTimer.cancel();
        }
        baseAction.toUnregister();
    }

    /**
     * tab变换 加载更多的显示方式
     */
    public void loadSwapTab() {

        if (!isSlect) {
            L.e("xx", "设置为没有加载更多....");
            refreshLayout.finishLoadMoreWithNoMoreData();
            refreshLayout.setNoMoreData(true);
        } else {
            L.e("xx", "设置为可以加载更多....");
            refreshLayout.setNoMoreData(false);
        }

    }

    class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub

        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            refreshCarryList();
        }
    }
}
