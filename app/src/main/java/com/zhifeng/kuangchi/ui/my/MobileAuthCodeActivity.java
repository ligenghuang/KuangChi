package com.zhifeng.kuangchi.ui.my;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.FormatUtils;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.MobileAuthCodeAction;
import com.zhifeng.kuangchi.module.GeneralDto;
import com.zhifeng.kuangchi.ui.MainActivity;
import com.zhifeng.kuangchi.ui.impl.MobileAuthCodeView;
import com.zhifeng.kuangchi.ui.login.LoginActivity;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 校验手机号成功后填写验证码
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/30 17:15
 * @Version: 1.0
 */

public class MobileAuthCodeActivity extends UserBaseActivity<MobileAuthCodeAction> implements MobileAuthCodeView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.et_auth_code)
    EditText etAuthCode;
    @BindView(R.id.tv_get_auth_code)
    TextView tvGetAuthCode;

    String phone;
    String code;

    MyCountDownTimer timer;

    @Override
    public int intiLayout() {
        return R.layout.activity_mobile_auth_code;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected MobileAuthCodeAction initAction() {
        return new MobileAuthCodeAction(this, this);
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

        phone = getIntent().getStringExtra("phone");
        tvMobile.setText(ResUtil.getFormatString(R.string.my_tab_34,phone));
        timer = new MyCountDownTimer(60000,1000);
        getAuthCode();

    }

    /**
     * 获取验证码
     */
    @Override
    public void getAuthCode() {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.getCode(phone);
        }
    }

    /**
     * 获取验证码成功
     * @param generalDto
     */
    @Override
    public void getAuthCodeSuccess(String generalDto) {
        loadDiss();
        showNormalToast(generalDto);
        //todo 启动计时器
        if (timer != null) {
            timer.cancel();
        }
        timer.start();
    }

    /**
     * 换绑手机号
     */
    @Override
    public void ModifyMobile() {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.ModifyMobile(phone,code);
        }
    }

    /**
     * 换绑手机号成功
     * @param generalDto
     */
    @Override
    public void ModifyMobileSuccess(GeneralDto generalDto) {
        loadDiss();
        showNormalToast(generalDto.getData());
        finish();
        ActivityStack.getInstance().exitIsNotHaveMain(MainActivity.class,SecurityActivity.class);
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

    /**
     * 监听点击事件
     * @param view
     */
    @OnClick({R.id.tv_get_auth_code, R.id.tv_modify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_auth_code:
                //todo 获取验证码
                getAuthCode();
                break;
            case R.id.tv_modify:
                //todo 确定
                modify();
                break;
        }
    }

    /**
     * 判断验证码是否为空 请求接口
     */
    private void modify() {
        //todo 判断是否输入验证码
        if (TextUtils.isEmpty(etAuthCode.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.login_tab_4));
            return;
        }

        //TODO 判断验证码格式
        if (etAuthCode.getText().length() != 6){
            showNormalToast(ResUtil.getString(R.string.login_tab_11));
            return;
        }
        code = etAuthCode.getText().toString();
        //请求接口
        ModifyMobile();
    }


    @Override
    protected void onResume() {
        super.onResume();
        baseAction.toRegister();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
        baseAction.toUnregister();
    }

    /**************************************计时器 start*******************************************/
    class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub

        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            tvGetAuthCode.setEnabled(false);
            tvGetAuthCode.setSelected(true);
            tvGetAuthCode.setText(FormatUtils.format(getString(R.string.login_tab_6), millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            tvGetAuthCode.setEnabled(true);
            tvGetAuthCode.setSelected(false);
            tvGetAuthCode.setText(R.string.login_tab_5);
        }
    }
/*****************************************计时器 end**************************************************/

}
