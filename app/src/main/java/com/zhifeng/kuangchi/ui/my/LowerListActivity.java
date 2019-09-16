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
import com.zhifeng.kuangchi.actions.LowerListAction;
import com.zhifeng.kuangchi.adapter.LowerListAdapter;
import com.zhifeng.kuangchi.module.LowerListDto;
import com.zhifeng.kuangchi.ui.impl.LowerListView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
 * @ClassName: 舰队长代理列表
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/31 19:11
 * @Version: 1.0
 */

public class LowerListActivity extends UserBaseActivity<LowerListAction> implements LowerListView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    LowerListAdapter lowerListAdapter;

    int page = 1;
    boolean isRefresh = true;
    //是否加载更多
    boolean isSlect = true;

    String id;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;

    int lever;
    boolean isLever = false;

    @Override
    public int intiLayout() {
        return R.layout.activity_lower_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected LowerListAction initAction() {
        return new LowerListAction(this, this);
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
                .addTag("LowerListActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        lever = getIntent().getIntExtra("lever",0);
        isLever = getIntent().getBooleanExtra("isLever",false);
        if (isLever) {
            setfTitle();
        }else {
            fTitleTv.setText(ResUtil.getFormatString(R.string.my_tab_89_1));
        }
//        setfTitle();

    }

    /**
     * 设置标题
     */
    private void setfTitle() {
        String text = "普通用户";
        switch (lever){
            case 0:
                text = "普通用户";
                break;
            case 1:
                text = "星月";
                break;
            case 2:
                text = "星光";
                break;
            case 3:
                text = "星宝";
                break;
            case 4:
                text = "星河";
                break;
            case 5:
                text = "级差等级一";
                break;
            case 6:
                text = "级差等级二";
                break;
            case 7:
                text = "级差等级三";
                break;
        }
        fTitleTv.setText(ResUtil.getFormatString(R.string.my_tab_89,text));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        id = getIntent().getStringExtra("id");

        lowerListAdapter = new LowerListAdapter();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(lowerListAdapter);

        refreshLayout.autoRefresh();
        loadView();
    }

    @Override
    protected void loadView() {
        super.loadView();
        //事件监听
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //todo 加载更多
                loadMoreLowerList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //todo 刷新数据
                refreshLowerList();
            }
        });
    }

    /**
     * 刷新数据
     */
    @Override
    public void refreshLowerList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            isRefresh = true;
            page = 1;
            if (isLever){
                baseAction.getLowerList2(page, lever+"");
            }else {
                baseAction.getLowerList(page, id);
            }

        } else {
            refreshLayout.finishRefresh();
        }
    }

    /**
     * 加载更多数据
     */
    @Override
    public void loadMoreLowerList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            isRefresh = false;
            page++;
            if (isLever){
                baseAction.getLowerList2(page, lever+"");
            }else {
                baseAction.getLowerList(page, id);
            }
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    /**
     * 获取数据成功
     *
     * @param lowerListDto
     */
    @Override
    public void getLowerListSuccess(LowerListDto lowerListDto) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        LowerListDto.DataBeanX dataBeanX = lowerListDto.getData();
        if (dataBeanX.getData().size() != 0) {
            recyclerview.setVisibility(View.VISIBLE);
            tvNodata.setVisibility(View.GONE);
            isSlect = page < dataBeanX.getCurrent_page();
            loadSwapTab();
            if (isRefresh) {
                //刷新数据成功
                lowerListAdapter.refresh(dataBeanX.getData());
            } else {
                //加载更多数据成功
                lowerListAdapter.loadMore(dataBeanX.getData());
            }
        } else {
            //todo 2019/8/30 没数据 添加空布局
            if (isRefresh){
                recyclerview.setVisibility(View.GONE);
                tvNodata.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onError(String message, int code) {
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
