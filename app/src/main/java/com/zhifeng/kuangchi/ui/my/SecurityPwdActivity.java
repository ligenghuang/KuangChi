package com.zhifeng.kuangchi.ui.my;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.FormatUtils;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.SecurityPwdAction;
import com.zhifeng.kuangchi.module.post.SetPayPwdPost;
import com.zhifeng.kuangchi.ui.impl.SecurityPwdView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;
import com.zhifeng.kuangchi.util.data.MySp;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 支付密码
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/31 20:21
 * @Version: 1.0
 */

public class SecurityPwdActivity extends UserBaseActivity<SecurityPwdAction> implements SecurityPwdView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_tab_1)
    TextView tvTab1;
    @BindView(R.id.tv_tab_2)
    TextView tvTab2;

    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.ll_tab_1)
    LinearLayout llTab1;

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

    boolean isFirst = true;
    String phone ;
    int type = 0;

    @Override
    public int intiLayout() {
        return R.layout.activity_security_pwd;
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
                .addTag("SecurityPwdActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_tab_22));
    }

    @Override
    protected SecurityPwdAction initAction() {
        return new SecurityPwdAction(this, this);
    }


    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        isFirst = getIntent().getBooleanExtra("first",true);
        phone = getIntent().getStringExtra("phone");
        type = isFirst ? 1:0;
        setType();

        timer = new MyCountDownTimer(60000, 1000);
        tvPwdMobile.setText(ResUtil.getFormatString(R.string.my_tab_133,phone));
    }

    /**
     * 设置布局
     */
    private void setType() {
        llTab1.setVisibility(type == 0?View.VISIBLE :View.GONE);
        llTab2.setVisibility(type == 1?View.VISIBLE :View.GONE);
        tvTab1.setSelected(type == 0);
        tvTab2.setSelected(type == 1);
        tvPwdSubmit.setText(ResUtil.getString(type == 0?R.string.my_tab_101:R.string.my_tab_102));
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
        finish();
    }

    /**
     * 重置支付密码
     */
    @Override
    public void forgetPayPwd(SetPayPwdPost setPayPwdPost) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.forgetPayPwd(setPayPwdPost);
        }
    }

    /**
     * 重置支付密码成功
     * @param generalDto
     */
    @Override
    public void forgetPayPwdSuccess(String generalDto) {
        loadDiss();
        showNormalToast(generalDto);
        finish();
    }

    /**
     * 修改支付密码
     */
    @Override
    public void editPayPwd(String oldPwd,String newPwd) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.editPayPwd(oldPwd,newPwd);
        }
    }

    /**
     * 修改支付密码
     * @param generalDto
     */
    @Override
    public void editPayPwdSuccess(String generalDto) {
        loadDiss();
        showNormalToast(generalDto);
        finish();
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

    @OnClick({R.id.tv_tab_1, R.id.tv_tab_2, R.id.tv_login_get_code,R.id.tv_pwd_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tab_1:
                //todo 修改支付密码
               if (type == 1){
                   if (isFirst){
                       //todo 未设置支付密码
                       showNormalToast(ResUtil.getString(R.string.my_tab_104));
                       return;
                   }

                   type = 0;
                   setType();
               }
                break;
            case R.id.tv_tab_2:
                //todo 忘记支付密码
                if (type != 1){
                    type = 1;
                    setType();
                }
                break;
            case R.id.tv_login_get_code:
                //todo 获取验证码
                getAuthCode();
                break;
            case R.id.tv_pwd_submit:
                if (isFirst){
                    //todo 设置支付密码
                    setFirstPayPwd(0);
                }else {
                    switch (type){
                        case 0:
                            //todo 修改支付密码
                            EditPayPwd();
                            break;
                        case 1:
                            //todo 重置支付密码
                            setFirstPayPwd(1);
                            break;
                    }
                }
                break;
        }
    }

    /**
     * 修改支付密码校验
     */
    private void EditPayPwd() {
        //todo 判断是否输入原密码
        if (TextUtils.isEmpty(etOldPwd.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.my_tab_108));
            return;
        }

        //todo 判断是否输入新密码
        if (TextUtils.isEmpty(etNewPwd.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.my_tab_109));
            return;
        }

        editPayPwd(etOldPwd.getText().toString(),etNewPwd.getText().toString());

    }

    /**
     * 设置支付密码与重置支付密码校验 并请求接口
     */
    private void setFirstPayPwd(int i) {
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

        if (i == 0){
            //todo 设置支付密码
            setPayPwd(payPwdPost);
        }else {
            //todo 重置支付密码
            forgetPayPwd(payPwdPost);
        }

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
