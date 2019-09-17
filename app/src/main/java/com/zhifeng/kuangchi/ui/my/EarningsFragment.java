package com.zhifeng.kuangchi.ui.my;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.EarningsAction;
import com.zhifeng.kuangchi.adapter.EarningsAdapter;
import com.zhifeng.kuangchi.module.EarningsListDto;
import com.zhifeng.kuangchi.ui.impl.EarningsView;
import com.zhifeng.kuangchi.util.base.UserBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName: 收益明细fragment
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/30 22:05
 * @Version: 1.0
 */

public class EarningsFragment extends UserBaseFragment<EarningsAction> implements EarningsView {
    View view;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    EarningsAdapter earningsAdapter;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private int state;
    boolean isRefresh = true;
    int page =1;
    //是否加载更多
    boolean isSlect = true;

    @Override
    protected EarningsAction initAction() {
        return new EarningsAction((RxAppCompatActivity) getActivity(), this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getContext();
        mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            state = bundle.getInt("state");
        }
    }

    @Override
    protected void initialize() {
        //初始化适配器
        earningsAdapter = new EarningsAdapter();
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(earningsAdapter);
        loadView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_earnings, container, false);
        ButterKnife.bind(this, view);
        binding();
        return view;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            getEarningsList();
        }
    }

    public static EarningsFragment newInstance(int state) {
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        EarningsFragment testFm = new EarningsFragment();
        testFm.setArguments(bundle);
        return testFm;
    }

    @Override
    protected void loadView() {
        super.loadView();
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                moreEarningsList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getEarningsList();
            }
        });
    }

    /**
     * 收益明细
     */
    @Override
    public void getEarningsList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            isRefresh = true;
            page = 1;
            baseAction.getEarningsList(state,page);
        }else {
            loadDiss();
            refreshLayout.finishRefresh();
        }
    }

    @Override
    public void moreEarningsList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            isRefresh = false;
            page++;
            baseAction.getEarningsList(state,page);
        }else {
            loadDiss();
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void getEarningsListSuccess(EarningsListDto earningsListDto) {
        loadDiss();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        EarningsListDto.DataBeanX dataBean = earningsListDto.getData();
        if (earningsListDto.getData().getData().size() != 0) {
            recyclerview.setVisibility(View.VISIBLE);
            tvNodata.setVisibility(View.GONE);
            isSlect = page < dataBean.getCurrent_page();
            loadSwapTab();
            if (isRefresh) {
                //刷新数据成功
                earningsAdapter.refresh(dataBean.getData());
            } else {
                //加载更多数据成功
                earningsAdapter.loadMore(dataBean.getData());
            }
        } else {
            //todo 2019/8/30 没数据 添加空布局
            if (isRefresh) {
                tvNodata.setVisibility(View.VISIBLE);
                recyclerview.setVisibility(View.GONE);
            }
            isSlect =false;
            loadSwapTab();
        }
    }

    @Override
    public void onError(String message, int code) {
        showToast(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        baseAction.toRegister();
    }

    @Override
    public void onPause() {
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
