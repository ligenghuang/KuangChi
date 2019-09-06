package com.zhifeng.kuangchi.ui.my;

import android.os.Bundle;
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
import com.zhifeng.kuangchi.actions.EntrustListAction;
import com.zhifeng.kuangchi.adapter.EntrustListAdapter;
import com.zhifeng.kuangchi.module.EntrustListDto;
import com.zhifeng.kuangchi.ui.impl.EntrustListView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
 * @ClassName: 委托明细
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/30 18:09
 * @Version: 1.0
 */

public class EntrustListActivity extends UserBaseActivity<EntrustListAction> implements EntrustListView {

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

    EntrustListAdapter entrustListAdapter;

    int page = 1;
    boolean isRefresh = true;
    //是否加载更多
    boolean isSlect = true;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;

    @Override
    public int intiLayout() {
        return R.layout.activity_entrust_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected EntrustListAction initAction() {
        return new EntrustListAction(this, this);
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
                .addTag("EntrustListActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_tab_1));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        //初始化适配器
        entrustListAdapter = new EntrustListAdapter();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(entrustListAdapter);

        loadDialog();
        refreshEntrustList();

        loadView();
    }

    @Override
    protected void loadView() {
        super.loadView();
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //todo 上拉加载更多
                loadMoreEntrustList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //todo 下拉刷新
                refreshEntrustList();
            }
        });
    }

    /**
     * 刷新数据
     */
    @Override
    public void refreshEntrustList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            isRefresh = true;
            page = 1;
            baseAction.getEntrustList(page);
        } else {
            loadDiss();
            refreshLayout.finishRefresh();
        }
    }

    /**
     * 加载更多数据
     */
    @Override
    public void loadMoreEntrustList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            isRefresh = false;
            page++;
            baseAction.getEntrustList(page);
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    /**
     * 获取数据成功
     *
     * @param entrustListDto
     */
    @Override
    public void getEntrustListSuccess(EntrustListDto entrustListDto) {
        loadDiss();
        refreshLayout.finishRefresh();//关闭刷新
        refreshLayout.finishLoadMore();//关闭加载更多
        EntrustListDto.DataBean dataBean = entrustListDto.getData();
        if (dataBean.getEntrust_list().size() != 0) {
            recyclerview.setVisibility(View.VISIBLE);
            tvNodata.setVisibility(View.GONE);
            isSlect = page < dataBean.getCurrent_page();
            loadSwapTab();
            if (isRefresh) {
                //刷新数据成功
                entrustListAdapter.refresh(dataBean.getEntrust_list());
            } else {
                //加载更多数据成功
                entrustListAdapter.loadMore(dataBean.getEntrust_list());
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
}
