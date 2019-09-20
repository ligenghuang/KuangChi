package com.zhifeng.kuangchi.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
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
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.AgencyListAction;
import com.zhifeng.kuangchi.adapter.AgencyListAdapter;
import com.zhifeng.kuangchi.module.AgencyListDto;
import com.zhifeng.kuangchi.ui.impl.AgencyListView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 代理明细(未完成)
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/30 19:55
 * @Version: 1.0
 */

public class AgencyListActivity extends UserBaseActivity<AgencyListAction> implements AgencyListView {


    int page = 1;
    boolean isRefresh = true;
    //是否加载更多
    boolean isSlect = true;
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_agency_realname_lever)
    TextView tvAgencyRealnameLever;
    @BindView(R.id.tv_agency_level_count_1)
    TextView tvAgencyLevelCount1;
    @BindView(R.id.tv_agency_level_count_2)
    TextView tvAgencyLevelCount2;
    @BindView(R.id.tv_agency_level_count_3)
    TextView tvAgencyLevelCount3;
    @BindView(R.id.tv_agency_level_count_4)
    TextView tvAgencyLevelCount4;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    AgencyListAdapter agencyListAdapter;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.classicsheader)
    ClassicsHeader classicsheader;
    @BindView(R.id.classicsfooter)
    ClassicsFooter classicsfooter;
    @BindView(R.id.tv_t_num)
    TextView tvTNum;


    @Override
    public int intiLayout() {
        return R.layout.activity_agency_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected AgencyListAction initAction() {
        return new AgencyListAction(this, this);
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
                .addTag("AgencyListActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_tab_2));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        //初始化适配器
        agencyListAdapter = new AgencyListAdapter(mContext);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(agencyListAdapter);
//
        loadDialog();
        refreshAgencyList();
        loadView();
    }

    @Override
    protected void loadView() {
        super.loadView();
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreAgencyList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshAgencyList();
            }
        });
    }

    /**
     * 刷新代理明细数据
     */
    @Override
    public void refreshAgencyList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            isRefresh = true;
            page = 1;
            baseAction.getAgencyList(page);
        } else {
            loadDiss();
            refreshLayout.finishRefresh();
        }
    }

    /**
     * 加载更多
     */
    @Override
    public void loadMoreAgencyList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            isRefresh = false;
            page++;
            baseAction.getAgencyList(page);
        } else {
            loadDiss();
            refreshLayout.finishLoadMore();
        }
    }

    /**
     * 获取数据成功
     *
     * @param agencyListDto
     */
    @Override
    public void getAgencyListSuccess(AgencyListDto agencyListDto) {
        loadDiss();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        AgencyListDto.DataBeanX dataBeanX = agencyListDto.getData();
        AgencyListDto.DataBeanX.UserFirstBean dataBean = dataBeanX.getUser_first();
        if (dataBean.getData().size() != 0) {
            recyclerview.setVisibility(View.VISIBLE);
            tvNodata.setVisibility(View.GONE);
            isSlect = page < dataBean.getCurrent_page();
            loadSwapTab();
            if (isRefresh) {
                //刷新数据成功
                agencyListAdapter.refresh(dataBean.getData());
            } else {
                //加载更多数据成功
                agencyListAdapter.loadMore(dataBean.getData());
            }
        } else {
            //todo 2019/8/30 没数据 添加空布局
            if (isRefresh) {
                tvNodata.setVisibility(View.VISIBLE);
                recyclerview.setVisibility(View.GONE);
            }
        }
        tvTNum.setText(ResUtil.getFormatString(R.string.my_tab_162,dataBeanX.getT_num()));
        tvAgencyRealnameLever.setText(ResUtil.getFormatString(R.string.my_tab_39, dataBeanX.getTeam_count() + ""));
        tvAgencyLevelCount1.setText(ResUtil.getFormatString(R.string.my_tab_44, dataBeanX.getLevel_count_1() + ""));//舵手
        tvAgencyLevelCount2.setText(ResUtil.getFormatString(R.string.my_tab_44, dataBeanX.getLevel_count_2() + ""));//大副
        tvAgencyLevelCount3.setText(ResUtil.getFormatString(R.string.my_tab_44, dataBeanX.getLevel_count_3() + ""));//舰长
        tvAgencyLevelCount4.setText(ResUtil.getFormatString(R.string.my_tab_44, dataBeanX.getLevel_count_4() + ""));//舰队长
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
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
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

    @OnClick({R.id.tv_agency_level_count_1, R.id.tv_agency_level_count_2, R.id.tv_agency_level_count_3, R.id.tv_agency_level_count_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_agency_level_count_1:
                jumpActivity(1);
                break;
            case R.id.tv_agency_level_count_2:
                jumpActivity(2);
                break;
            case R.id.tv_agency_level_count_3:
                jumpActivity(3);
                break;
            case R.id.tv_agency_level_count_4:
                jumpActivity(4);
                break;
        }
    }

    private void jumpActivity(int i){
        Intent intent = new Intent(mContext, LowerListActivity.class);
        intent.putExtra("isLever",true);
        intent.putExtra("lever",i);
        startActivity(intent);
    }
}
