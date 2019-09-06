package com.zhifeng.kuangchi.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.BaseAction;
import com.zhifeng.kuangchi.actions.SecurityAction;
import com.zhifeng.kuangchi.module.SecurityInfoDto;
import com.zhifeng.kuangchi.ui.MainActivity;
import com.zhifeng.kuangchi.ui.impl.SecurityView;
import com.zhifeng.kuangchi.ui.login.LoginActivity;
import com.zhifeng.kuangchi.ui.login.LoginOrRegisteredActivity;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;
import com.zhifeng.kuangchi.util.data.MySp;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName: 安全中心
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/30 15:51
 * @Version: 1.0
 */

public class SecurityActivity extends UserBaseActivity<SecurityAction> implements SecurityView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_security_name)
    TextView tvSecurityName;
    @BindView(R.id.tv_security_phone)
    TextView tvSecurityPhone;

    boolean isFirst = true;
    String phone;

    @Override
    public int intiLayout() {
        return R.layout.activity_security;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected SecurityAction initAction() {
        return new SecurityAction(this,this);
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
                .addTag("SecurityActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_tab_10));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        loadDialog();

    }

    @Override
    protected void onResume() {
        super.onResume();
        baseAction.toRegister();
        //页面显示时加载数据
        getSecurityInfo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }

    /**
     * 获取安全中心信息数据
     */
    @Override
    public void getSecurityInfo() {
        if (CheckNetwork.checkNetwork2(mContext)){
            baseAction.getSecurityInfo();
        }
    }

    /**
     * 获取安全中心信息数据成功
     * @param securityInfoDto
     */
    @Override
    public void getSecurityInfoSuccess(SecurityInfoDto securityInfoDto) {
        loadDiss();
        SecurityInfoDto.DataBean dataBean = securityInfoDto.getData();
        tvSecurityPhone.setText(dataBean.getPhone());
        tvSecurityName.setText(dataBean.getRealname());
        isFirst = TextUtils.isEmpty(dataBean.getPwd());
        phone = dataBean.getPhone();
        MySp.setUserNameapi(mContext,dataBean.getIs_nameapi());
        MySp.setUserVip(mContext,dataBean.getIs_vip());
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


    @OnClick({R.id.tv_security_phone, R.id.tv_security_pwd, R.id.tv_security_user, R.id.tv_security_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_security_phone:
                //todo  修改手机号
                jumpActivityNotFinish(mContext,ModifyMobileActivity.class);
                break;
            case R.id.tv_security_pwd:
                //todo 支付密码
                Intent intent = new Intent(mContext,SecurityPwdActivity.class);
                intent.putExtra("first",isFirst);
                intent.putExtra("phone",phone);
                startActivity(intent);
                break;
            case R.id.tv_security_user:
                //todo 切换账号
                jumpActivityNotFinish(mContext,SecurityUserActivity.class);
                break;
            case R.id.tv_security_logout:
                //todo 退出登录
                MySp.clearAllSP(mContext);
                jumpActivity(mContext, LoginActivity.class);
                MainActivity.Position = 0;
                ActivityStack.getInstance().exitIsNotHaveMain( LoginActivity.class, MainActivity.class);
                break;
        }
    }
}
