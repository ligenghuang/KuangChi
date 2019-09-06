package com.zhifeng.kuangchi.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.BaseAction;
import com.zhifeng.kuangchi.actions.IdCardAction;
import com.zhifeng.kuangchi.ui.impl.IdCardView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;
import com.zhifeng.kuangchi.util.data.MySp;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 身份认证
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/31 14:17
 * @Version: 1.0
 */

public class IdCardActivity extends UserBaseActivity<IdCardAction> implements IdCardView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_id_card_name)
    EditText etIdCardName;
    @BindView(R.id.et_id_card_num)
    EditText etIdCardNum;

    @Override
    public int intiLayout() {
        return R.layout.activity_id_card;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected IdCardAction initAction() {
        return new IdCardAction(this,this);
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
                .addTag("IdCardActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_tab_8));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;
    }

    /**
     * 点击事件监听
     */
    @OnClick(R.id.tv_id_card_activation)
    public void onViewClicked() {
        //todo 判断真实姓名是否为空
        if (TextUtils.isEmpty(etIdCardName.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.my_tab_116));
            return;
        }

        //todo 判断证件号是否为空
        if (TextUtils.isEmpty(etIdCardNum.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.my_tab_117));
            return;
        }

        setIdCard(etIdCardName.getText().toString(),etIdCardNum.getText().toString());
    }

    /**
     * 身份认证
     * @param name
     * @param idNum
     */
    @Override
    public void setIdCard(String name ,String idNum) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.setIdCard(name,idNum);
        }
    }

    /**
     * 身份认证成功
     */
    @Override
    public void setIdCardSuccess() {
        loadDiss();
        showNormalToast(ResUtil.getString(R.string.my_tab_118));
        MySp.setUserNameapi(mContext,1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
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
    protected void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }

    @Override
    protected void onResume() {
        super.onResume();
        baseAction.toRegister();
    }
}
