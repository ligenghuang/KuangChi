package com.zhifeng.kuangchi.ui.my;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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
import com.zhifeng.kuangchi.actions.MoblieLoginPwdAction;
import com.zhifeng.kuangchi.module.GeneralDto;
import com.zhifeng.kuangchi.module.post.MoblieLoginPwdPost;
import com.zhifeng.kuangchi.ui.impl.MoblieLoginPwdView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;
import com.zhifeng.kuangchi.util.data.MySp;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 修改登录密码
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/7 14:10
 * @Version: 1.0
 */

public class MoblieLoginPwdActivity extends UserBaseActivity<MoblieLoginPwdAction> implements MoblieLoginPwdView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_pwd_mobile)
    TextView tvPwdMobile;
    @BindView(R.id.et_login_code)
    EditText etLoginCode;
    @BindView(R.id.tv_login_get_code)
    TextView tvLoginGetCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_again_pwd)
    EditText etAgainPwd;

    MyCountDownTimer timer;

    String phone ;

    @Override
    public int intiLayout() {
        return R.layout.activity_moblie_login_pwd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected MoblieLoginPwdAction initAction() {
        return new MoblieLoginPwdAction(this, this);
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
                .addTag("MoblieLoginPwdActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_tab_22_2));
    }


    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        phone = getIntent().getStringExtra("phone");

        timer = new MyCountDownTimer(60000, 1000);
        tvPwdMobile.setText(ResUtil.getFormatString(R.string.my_tab_133,phone));
    }

    /**
     * 获取验证码
     */
    @Override
    public void getAuthCode() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getAuthCode(MySp.getUserPhone(mContext));
        }
    }

    /**
     * 获取验证码成功
     *
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
     * 修改登录密码
     *
     * @param pwdPost
     */
    @Override
    public void moblieLoginPwd(MoblieLoginPwdPost pwdPost) {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.moblieLoginPwd(pwdPost);
        }
    }

    /**
     * 修改登录密码成功
     *
     * @param generalDto
     */
    @Override
    public void moblieLoginPwdSuccess(GeneralDto generalDto) {
        loadDiss();
        showNormalToast(generalDto.getData());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
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


    @OnClick({R.id.tv_login_get_code,R.id.tv_pwd_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_get_code:
                //todo 获取验证码
                getAuthCode();
                break;
            case R.id.tv_pwd_submit:
                submit();
                break;
        }
    }

    /**
     * 提交数据前校验
     */
    private void submit() {
        MoblieLoginPwdPost pwdPost = new MoblieLoginPwdPost();
        pwdPost.setPhone(MySp.getUserPhone(mContext));
        //todo 判断是否输入验证码
        if (TextUtils.isEmpty(etLoginCode.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.login_tab_4));
            return;
        }

        pwdPost.setVerify_code(etLoginCode.getText().toString());

        //todo 判断是否输入支付密码
        if (TextUtils.isEmpty(etPwd.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.my_tab_105));
            return;
        }

        pwdPost.setPassword(etPwd.getText().toString());

        //todo 判断是否再次输入支付密码
        if (TextUtils.isEmpty(etAgainPwd.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.my_tab_107));
            return;
        }

        //todo 判断两次支付密码是否一致
        if (!etPwd.getText().toString().equals(etAgainPwd.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.my_tab_106));
            return;
        }

        pwdPost.setRepassword(etAgainPwd.getText().toString());
        moblieLoginPwd(pwdPost);
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
            tvLoginGetCode.setEnabled(false);
            tvLoginGetCode.setSelected(true);
            tvLoginGetCode.setText(FormatUtils.format(getString(R.string.login_tab_6), millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            tvLoginGetCode.setEnabled(true);
            tvLoginGetCode.setSelected(false);
            tvLoginGetCode.setText(R.string.login_tab_5);
        }
    }
/*****************************************计时器 end**************************************************/
}
