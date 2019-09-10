package com.zhifeng.kuangchi.ui.found;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.charts.LineChart;
import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.data.ResUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.FoundAction;
import com.zhifeng.kuangchi.module.KLineDto;
import com.zhifeng.kuangchi.ui.impl.FoundView;
import com.zhifeng.kuangchi.ui.my.AgencyListActivity;
import com.zhifeng.kuangchi.ui.my.ArningsActivity;
import com.zhifeng.kuangchi.ui.my.CarryActivity;
import com.zhifeng.kuangchi.ui.my.EntrustListActivity;
import com.zhifeng.kuangchi.util.base.UserBaseFragment;
import com.zhifeng.kuangchi.util.data.MySp;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发现fragment
 */
public class FoundFragment extends UserBaseFragment<FoundAction> implements FoundView {
    View view;
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.tv_lamb)
    TextView tvLamb;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_home_bouns_day)
    TextView tvHomeBounsDay;
    @BindView(R.id.tv_home_bouns)
    TextView tvHomeBouns;
    @BindView(R.id.tv_home_bouns_day_f)
    TextView tvHomeBounsDayF;
    @BindView(R.id.tv_home_bouns_f)
    TextView tvHomeBounsF;


    @Override
    protected FoundAction initAction() {
        return new FoundAction((RxAppCompatActivity) getActivity(), this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getContext();
        mActivity = activity;
    }


    @Override
    protected void initialize() {

        refreshLayout.setEnableLoadMore(false);
        loadView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_found, container, false);
        ButterKnife.bind(this, view);
        binding();
        mImmersionBar.setStatusBarView(getActivity(), topView);
        return view;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            getService();
            tvHomeBouns.setText(MySp.getBouns(mContext));
            tvHomeBounsDay.setText(MySp.getBounsDay(mContext));
        }
    }

    @Override
    protected void loadView() {
        super.loadView();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getService();
            }
        });
    }

    /**
     * 获取存储服务器群组
     */
    @Override
    public void getService() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getService();
        }
    }


    /**
     * 获取存储服务器群组成功
     *
     * @param serviceDto
     */
    @Override
    public void getServiceSuccess(KLineDto serviceDto) {
        refreshLayout.finishRefresh();
        DecimalFormat df = new DecimalFormat("#0.00000000");
        tvLamb.setText(ResUtil.getFormatString(R.string.found_tab_5, df.format(serviceDto.getData().getTick().getClose())));
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
        tvLamb.setText(ResUtil.getFormatString(R.string.found_tab_5, "0"));
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

    @OnClick({R.id.tv_user_entrust, R.id.tv_user_agency, R.id.tv_user_earnings, R.id.tv_usere_mention_money})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_user_entrust:
                //todo 委托明细
                jumpActivityNotFinish(mContext, EntrustListActivity.class);
                break;
            case R.id.tv_user_agency:
                //todo 代理明细
                jumpActivityNotFinish(mContext, AgencyListActivity.class);
                break;
            case R.id.tv_user_earnings:
                //todo 收益明细
                jumpActivityNotFinish(mContext, ArningsActivity.class);
                break;
            case R.id.tv_usere_mention_money:
                //todo 提币明细
                jumpActivityNotFinish(mContext, CarryActivity.class);
                break;
        }
    }
}
