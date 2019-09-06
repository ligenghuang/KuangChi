package com.zhifeng.kuangchi.ui.my;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
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

    private int state;

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

    /**
     * 收益明细
     */
    @Override
    public void getEarningsList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getEarningsList(state);
        }
    }

    @Override
    public void getEarningsListSuccess(EarningsListDto earningsListDto) {
        if (earningsListDto.getData().getData().size() != 0) {
            recyclerview.setVisibility(View.VISIBLE);
            tvNodata.setVisibility(View.GONE);
            earningsAdapter.refresh(earningsListDto.getData().getData());
        }else {
            recyclerview.setVisibility(View.GONE);
            tvNodata.setVisibility(View.VISIBLE);
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
}
