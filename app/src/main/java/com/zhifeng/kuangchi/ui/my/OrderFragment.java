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
import com.zhifeng.kuangchi.actions.OrderAction;
import com.zhifeng.kuangchi.adapter.OrderListAdapter;
import com.zhifeng.kuangchi.module.OrderListDto;
import com.zhifeng.kuangchi.ui.impl.OrderView;
import com.zhifeng.kuangchi.util.base.UserBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName: 订单fragment
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/31 14:41
 * @Version: 1.0
 */

public class OrderFragment extends UserBaseFragment<OrderAction> implements OrderView {

    View view;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    OrderListAdapter orderListAdapter;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;

    private int state;
    int page = 1;

    boolean isRefresh = true;
    //是否加载更多
    boolean isSlect = true;

    @Override
    protected OrderAction initAction() {
        return new OrderAction((RxAppCompatActivity) getActivity(), this);
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
        init();
        loadView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);
        binding();
        return view;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            L.e("Position", "onPageSelected " + state);
            L.e("Position", "OrderActivity.Position " + OrderActivity.Position);
          loadDialog();
           refreshOrderList();
        }
    }

    public static OrderFragment newInstance(int state) {
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        OrderFragment testFm = new OrderFragment();
        testFm.setArguments(bundle);
        return testFm;
    }

    @Override
    protected void init() {
        super.init();
        //初始化适配器
        orderListAdapter = new OrderListAdapter(mContext);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(orderListAdapter);
    }

    @Override
    protected void loadView() {
        super.loadView();
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //todo 加载更多
                loadMoreOrderList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //todo 刷新数据
                L.e("Position","Position  = "+isRefresh);
                refreshOrderList();
            }
        });
    }

    /**
     * 刷新数据
     */
    @Override
    public void refreshOrderList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            L.e("Position","Position  = "+isRefresh);
            isRefresh = true;
            page = 1;
            baseAction.getOrderList(state, page);
        } else {
            loadDiss();
            refreshLayout.finishRefresh();
        }
    }

    /**
     * 加载更多数据
     */
    @Override
    public void loadMoreOrderList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            isRefresh = false;
            page++;
            baseAction.getOrderList(state, page);
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    /**
     * 获取数据成功
     *
     * @param orderListDto
     */
    @Override
    public void getOrderListSuccess(OrderListDto orderListDto) {
        refreshLayout.finishRefresh();//关闭刷新
        refreshLayout.finishLoadMore();//关闭加载更多
        loadDiss();
        OrderListDto.DataBean dataBean = orderListDto.getData();
        if (dataBean.getCarry_list().size() != 0) {
            recyclerview.setVisibility(View.VISIBLE);
            tvNodata.setVisibility(View.GONE);
            isSlect = page < dataBean.getLast_page();
            loadSwapTab();
            if (isRefresh) {
                //刷新数据成功
                orderListAdapter.refresh(dataBean.getCarry_list());
            } else {
                //加载更多数据成功
                orderListAdapter.loadMore(dataBean.getCarry_list());
            }
        } else {
            //todo 2019/8/30 没数据 添加空布局
            if (isRefresh) {
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
    public void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }

    @Override
    public void onResume() {
        super.onResume();
        baseAction.toRegister();
    }

    /**
     * tab变换 加载更多的显示方式
     */
    public void loadSwapTab() {

        if (!isSlect) {
            L.e("xx", "设置为没有加载更多....");
            refreshLayout.finishLoadMoreWithNoMoreData();
            refreshLayout.finishLoadMore();
            refreshLayout.setNoMoreData(true);
        } else {
            L.e("xx", "设置为可以加载更多....");
            refreshLayout.setNoMoreData(false);
        }

    }
}
