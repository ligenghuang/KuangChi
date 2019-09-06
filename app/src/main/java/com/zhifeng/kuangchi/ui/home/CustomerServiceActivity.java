package com.zhifeng.kuangchi.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.BaseAction;
import com.zhifeng.kuangchi.actions.CustomerServiceAction;
import com.zhifeng.kuangchi.adapter.CustomerServiceListAdapter;
import com.zhifeng.kuangchi.module.CustomerServiceListDto;
import com.zhifeng.kuangchi.ui.impl.CustomerServiceView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

import static com.lgh.huanglib.util.cusview.magicIndicator.ScrollState.SCROLL_STATE_IDLE;

/**
 * @ClassName: 客服对话列表页
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/2 10:20
 * @Version: 1.0
 */

public class CustomerServiceActivity extends UserBaseActivity<CustomerServiceAction> implements CustomerServiceView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.edit_direct)
    EditText editDirect;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.send_ll)
    LinearLayout sendLl;
    @BindView(R.id.rl_inputdlg_view)
    RelativeLayout rlInputdlgView;

    CustomerServiceListAdapter customerServiceListAdapter;

    boolean isSend = false;

    @Override
    public int intiLayout() {
        return R.layout.activity_customer_service;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected CustomerServiceAction initAction() {
        return new CustomerServiceAction(this,this);
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
                .addTag("CustomerServiceActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.home_tab_15));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        //todo 2019/09/02 禁止列表刷新加载
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);

        customerServiceListAdapter = new CustomerServiceListAdapter(mContext);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(customerServiceListAdapter);

        loadDialog();
        getCustomerServiceList();
    }

    @Override
    protected void loadView() {
        super.loadView();

        //滑动监听
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != SCROLL_STATE_IDLE) {
                    hideInput();

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!isSend) {
                    hideInput();
                }
                return false;
            }
        });

        //布局缩小时滑到最后一项
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5,
                                       int i6, int i7) {
                if (i3 < i7) {
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //todo 滑动至列表最后一项
                            recyclerView.scrollToPosition(customerServiceListAdapter.getAllData().size() - 1);
                        }
                    }, 100);
                }
            }
        });
    }

    @OnClick(R.id.tv_send)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_send:
                //todo 发送消息
                send();
                break;
        }
    }

    /**
     * 发送消息前校验
     */
    private void send() {
        //todo 判断输入框是否为空
        if (TextUtils.isEmpty(editDirect.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.home_tab_17));
            return;
        }

        sendMessage(editDirect.getText().toString());
        editDirect.setText("");
    }

    /**
     * 获取客服对话列表
     */
    @Override
    public void getCustomerServiceList() {
        if (CheckNetwork.checkNetwork2(mContext)){
            baseAction.getCustomerService();
        }else {
            loadDiss();
        }
    }

    /**
     * 获取客服对话列表成功
     * @param customerServiceListDto
     */
    @Override
    public void getCustomerServiceListSuccess(CustomerServiceListDto customerServiceListDto) {
        loadDiss();
        customerServiceListAdapter.refresh(customerServiceListDto.getData());
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //todo 滑动至列表最后一项
                recyclerView.scrollToPosition(customerServiceListAdapter.getAllData().size() - 1);
            }
        }, 100);

        isSend = false;
    }

    /**
     * 发送消息
     * @param msg
     */
    @Override
    public void sendMessage(String msg) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            isSend = true;
            baseAction.sendMessage(msg);
        }
    }

    /**
     * 发送成功 获取列表数据
     */
    @Override
    public void sendMessageSuccess() {
        getCustomerServiceList();
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
