package com.zhifeng.kuangchi.ui.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Process;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.FormatUtils;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.SecurityPwdAction;
import com.zhifeng.kuangchi.module.post.SetPayPwdPost;
import com.zhifeng.kuangchi.ui.MainActivity;
import com.zhifeng.kuangchi.ui.impl.SecurityPwdView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;
import com.zhifeng.kuangchi.util.data.MySp;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
  *
  * @ClassName:     设置密码
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/26 14:26
  * @Version:        1.0
 */

public class SetPayPwdActivity extends UserBaseActivity<SecurityPwdAction> implements SecurityPwdView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ll_tab_2)
    LinearLayout llTab2;
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

    @BindView(R.id.tv_pwd_submit)
    TextView tvPwdSubmit;

    MyCountDownTimer timer;

    String phone ;
    int type = 0;
    boolean isLogin;

    @Override
    public int intiLayout() {
        return R.layout.activity_set_pay_pwd;
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
                .statusBarDarkFont(true)
                .addTag("SetPayPwdActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin){
                    showNormalToast(ResUtil.getString(R.string.my_tab_165));
                }else {
                    finish();
                }
            }
        });
        fTitleTv.setText(ResUtil.getString(R.string.my_tab_95_1));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        isLogin = getIntent().getBooleanExtra("isLogin",false);
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
     * 设置支付密码
     */
    @Override
    public void setPayPwd(SetPayPwdPost setPayPwdPost) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.setPayPwd(setPayPwdPost);
        }
    }

    /**
     * 设置支付密码成功
     * @param generalDto
     */
    @Override
    public void setPayPwdSuccess(String generalDto) {
        loadDiss();
        showNormalToast(generalDto);
        MySp.setUserPayPwd(mContext, 1);
        if (isLogin){
            Intent intent2 = new Intent(mContext, MainActivity.class);
            intent2.putExtra("isLogin", true);
            startActivity(intent2);
            finish();
            ActivityStack.getInstance().exitIsNotHaveMain(MainActivity.class);
        }else {
            finish();
        }

    }

    @Override
    public void forgetPayPwd(SetPayPwdPost setPayPwdPost) {

    }

    @Override
    public void forgetPayPwdSuccess(String generalDto) {

    }

    @Override
    public void editPayPwd(String oldPwd, String newPwd) {

    }

    @Override
    public void editPayPwdSuccess(String generalDto) {

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
        if (timer != null) {
            timer.cancel();
        }
        baseAction.toUnregister();
    }

    @OnClick({R.id.tv_pwd_submit,R.id.tv_login_get_code})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.tv_pwd_submit:
                //todo 设置支付密码
                setFirstPayPwd();
                break;
            case R.id.tv_login_get_code:
                //todo 获取验证码
                getAuthCode();
                break;
        }
    }

    /**
     * 设置支付密码校验 并请求接口
     */
    private void setFirstPayPwd() {
        SetPayPwdPost payPwdPost = new SetPayPwdPost();
        payPwdPost.setPhone(MySp.getUserPhone(mContext));
        //todo 判断是否输入验证码
        if (TextUtils.isEmpty(etLoginCode.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.login_tab_4));
            return;
        }

        //TODO 判断验证码格式
        if (etLoginCode.getText().length() != 6){
            showNormalToast(ResUtil.getString(R.string.login_tab_11));
            return;
        }

        payPwdPost.setVerify_code(etLoginCode.getText().toString());

        //todo 判断是否输入支付密码
        if (TextUtils.isEmpty(etPwd.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.my_tab_105));
            return;
        }

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
        payPwdPost.setPassword(etPwd.getText().toString());

        //todo 设置支付密码
        setPayPwd(payPwdPost);

    }

    @Override
    protected SecurityPwdAction initAction() {
        return new SecurityPwdAction(this,this);
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

@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    // TODO Auto-generated method stub

    switch (keyCode) {
        case KeyEvent.KEYCODE_BACK:

            if (isLogin){
                showNormalToast(ResUtil.getString(R.string.my_tab_165));
                return true;
            }else {
                finish();
            }
            break;
    }
    return super.onKeyDown(keyCode, event);
}

}
