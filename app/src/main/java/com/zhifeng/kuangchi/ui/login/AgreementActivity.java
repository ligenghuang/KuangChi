package com.zhifeng.kuangchi.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.cusview.richtxtview.XRichText;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.AgreementAction;
import com.zhifeng.kuangchi.ui.impl.AgreementView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
 * 用户协议
 */
public class AgreementActivity extends UserBaseActivity<AgreementAction> implements AgreementView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.xrichtext)
    XRichText xrichtext;

    @Override
    public int intiLayout() {
        return R.layout.activity_agreement;
    }

    @Override
    protected AgreementAction initAction() {
        return new AgreementAction(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
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
                .addTag("AgreementActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.login_tab_13));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        getAgreement();
    }

    /**
     * 获取用户协议
     */
    @Override
    public void getAgreement() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getAgreement();
        }
    }

    /**
     * 获取用户协议成功
     *
     * @param data
     */
    @Override
    public void getAgreementSuccess(String data) {
        loadDiss();
        xrichtext.text(data);
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
