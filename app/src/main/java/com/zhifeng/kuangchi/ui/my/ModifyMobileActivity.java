package com.zhifeng.kuangchi.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.lgh.huanglib.util.data.ValidateUtils;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.ModifyMobileAction;
import com.zhifeng.kuangchi.module.BaseDto;
import com.zhifeng.kuangchi.ui.impl.ModifyMobileView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;
/**
  *
  * @ClassName:     修改手机号  校验手机号
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 17:02
  * @Version:        1.0
 */

public class ModifyMobileActivity extends UserBaseActivity<ModifyMobileAction> implements ModifyMobileView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.tv_modify_next)
    TextView tvModifyNext;

    String phone;

    @Override
    public int intiLayout() {
        return R.layout.activity_modify_mobile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected ModifyMobileAction initAction() {
        return new ModifyMobileAction(this,this);
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
                .addTag("ModifyMobileActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_tab_24));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

    }


    @OnClick(R.id.tv_modify_next)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_modify_next:
                //todo 校验手机号
                check();
                break;
        }
    }

    /**
     * 判断手机号是否为空
     */
    private void check() {
        //todo 判断手机号码是否为空
        if (TextUtils.isEmpty(etMobile.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.my_tab_29));
            return;
        }
        //todo 判断手机号码格式是否正确
        if (!ValidateUtils.isPhone2(etMobile.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.login_tab_10));
            return ;
        }
        phone = etMobile.getText().toString();
        checkPhone(phone);
    }


    /**
     * 校验手机号
     */
    @Override
    public void checkPhone(String phone) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.checkPhone(phone);
        }
    }

    /**
     * 校验返回
     */
    @Override
    public void checkPhoneSuccess(BaseDto baseDto) {
        loadDiss();
        switch (baseDto.getStatus()){
            case 200:
                //todo 成功 跳转至填写验证码页面
                Intent intent = new Intent(mContext,MobileAuthCodeActivity.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
                break;
            default:
                //todo 失败
                showNormalToast(baseDto.getMsg());
                break;
        }
    }

    /**
     * 请求失败
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
