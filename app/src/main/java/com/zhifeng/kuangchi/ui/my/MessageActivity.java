package com.zhifeng.kuangchi.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.MessageAction;
import com.zhifeng.kuangchi.adapter.MessageListAdapter;
import com.zhifeng.kuangchi.module.MessageListDto;
import com.zhifeng.kuangchi.ui.impl.MessageView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
 * @ClassName: 消息中心
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/30 15:31
 * @Version: 1.0
 */

public class MessageActivity extends UserBaseActivity<MessageAction> implements MessageView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    MessageListAdapter messageListAdapter;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;

    @Override
    public int intiLayout() {
        return R.layout.activity_message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }


    @Override
    protected MessageAction initAction() {
        return new MessageAction(this, this);
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
                .addTag("MessageActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_tab_11));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        messageListAdapter = new MessageListAdapter();
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(messageListAdapter);

        getMessageList();
    }


    /**
     * 获取消息中心数据
     */
    @Override
    public void getMessageList() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getMessageList();
        }
    }

    /**
     * 获取消息中心数据成功
     *
     * @param messageListDto
     */
    @Override
    public void getMessageListSuccess(MessageListDto messageListDto) {
        loadDiss();
        if (messageListDto.getData().size()!= 0) {
            recyclerview.setVisibility(View.VISIBLE);
            tvNodata.setVisibility(View.GONE);
            messageListAdapter.refresh(messageListDto.getData());
        }else {
            recyclerview.setVisibility(View.GONE);
            tvNodata.setVisibility(View.VISIBLE);
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
        showNormalToast(message);
        recyclerview.setVisibility(View.GONE);
        tvNodata.setVisibility(View.VISIBLE);
        tvNodata.setText(message);
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
