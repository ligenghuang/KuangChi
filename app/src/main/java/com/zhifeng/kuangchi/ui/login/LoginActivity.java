package com.zhifeng.kuangchi.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.FormatUtils;
import com.lgh.huanglib.util.data.ResUtil;
import com.lgh.huanglib.util.data.ValidateUtils;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.LoginAction;
import com.zhifeng.kuangchi.module.LoginDto;
import com.zhifeng.kuangchi.module.UserDto;
import com.zhifeng.kuangchi.ui.MainActivity;
import com.zhifeng.kuangchi.ui.impl.LoginView;
import com.zhifeng.kuangchi.ui.my.IdCardActivity;
import com.zhifeng.kuangchi.ui.my.SecurityPwdActivity;
import com.zhifeng.kuangchi.ui.my.SetPayPwdActivity;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;
import com.zhifeng.kuangchi.util.data.MySp;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录页面
 */
public class LoginActivity extends UserBaseActivity<LoginAction> implements LoginView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.f_right_tv)
    TextView fRightTv;
    @BindView(R.id.tv_login_phone_area_code)
    TextView tvLoginPhoneAreaCode;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.iv_login_phone_close)
    ImageView ivLoginPhoneClose;
    @BindView(R.id.et_login_code)
    EditText etLoginCode;
    @BindView(R.id.iv_login_code_close)
    ImageView ivLoginCodeClose;
    @BindView(R.id.tv_login_get_code)
    TextView tvLoginGetCode;
    @BindView(R.id.btn_login)
    TextView btnLogin;
    @BindView(R.id.iv_round_consult)
    ImageView ivRoundConsult;
    @BindView(R.id.tv_login_consult)
    TextView tvLoginConsult;
    @BindView(R.id.iv_login_weixin)
    ImageView ivLoginWeixin;
    @BindView(R.id.et_login_invitecode)
    EditText etLoginInvitecode;

    /**
     * type： 0：注册 sms_reg;1：登录:sms_login
     */
    int type = 0;

    /**
     * 手机号码
     */
    String phone;
    List<String> phones = new ArrayList<>();

    /**
     * 是否可以获取验证码
     */
    boolean isGetCode = false;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.iv_login_pwd_close)
    ImageView ivLoginPwdClose;
    @BindView(R.id.et_login_again_pwd)
    EditText etLoginAgainPwd;
    @BindView(R.id.iv_login_again_pwd_close)
    ImageView ivLoginAgainPwdClose;
    @BindView(R.id.ll_registered)
    LinearLayout llRegistered;
    @BindView(R.id.ll_login_consult)
    LinearLayout llLoginConsult;
    @BindView(R.id.tv_login_pwd)
    TextView tvLoginPwd;

    //获取验证码倒计时
    private MyCountDownTimer timer;

    /**
     * 是否阅读协议
     */
    boolean isReadAgreement = true;


    @Override
    public int intiLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginAction initAction() {
        return new LoginAction(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        type = getIntent().getIntExtra("type", 0);
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
                .addTag("LoginActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        setType(type);
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        String json = MySp.getUserList(mContext);
        if (!TextUtils.isEmpty(json)) {
            phones = new ArrayList<>();
            List<UserDto> list = new Gson().fromJson(json, new TypeToken<List<UserDto>>() {
            }.getType());
            for (int i = 0; i < list.size(); i++) {
                phones.add(list.get(i).getPhone());
            }
        }

        getTextChangedListener();
        timer = new MyCountDownTimer(60000, 1000);
    }

    /**
     * 输入框监听
     */
    private void getTextChangedListener() {
        /**
         * 监听手机号码输入框
         */
        etLoginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(etLoginPhone.getText().toString()) &&
                        !ValidateUtils.isPhone2(etLoginPhone.getText().toString())) {
                    //todo 手机号码为空或格式不正确，不可获取验证码
                    isGetCode = false;
                } else {
                    //todo 手机号码格式正确，可获取验证码
                    isGetCode = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /**
         * 监听验证码输入框
         */
       setEditText(etLoginCode,ivLoginCodeClose);
       setEditText(etLoginPhone,ivLoginPhoneClose);
       setEditText(etLoginAgainPwd,ivLoginAgainPwdClose);
       setEditText(etLoginPwd,ivLoginPwdClose);
    }

    private void setEditText(EditText e,ImageView i){
        //根据是否有焦点更新背景（这里只是演示使用，其实这种更换背景的效果可以通过Selector来实现）
        e.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    L.e("abc", "et1获取到焦点了。。。。。。");
                  i.setVisibility(View.VISIBLE);
                } else {
                    L.e("abc", "et1失去焦点了。。。。。。");
                   i.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.btn_login, R.id.tv_login_get_code, R.id.f_right_tv,
            R.id.tv_login_consult, R.id.ll_login_consult,
            R.id.iv_login_phone_close, R.id.iv_login_code_close,
            R.id.tv_login_pwd,R.id.iv_login_pwd_close,R.id.iv_login_again_pwd_close})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.f_right_tv:
//                //todo 切换登录或注册
                setType(type == 0? 1:0);
                break;
            case R.id.tv_login_get_code:
                //todo 获取验证码
                getCode();
                break;
            case R.id.btn_login:
                //todo 登录或注册
                loginOrRegistered();
                break;
            case R.id.ll_login_consult:
                //todo 勾选或取消勾选协议
                isReadAgreement = !isReadAgreement;
                ivRoundConsult.setImageResource(isReadAgreement ? R.mipmap.round_on : 0);
                break;
            case R.id.iv_login_phone_close:
                //todo 撤销输入框中的手机号码
                if (TextUtils.isEmpty(etLoginPhone.getText().toString())) {
                    return;
                }
                etLoginPhone.setText("");
                break;
            case R.id.iv_login_code_close:
                //todo 撤销输入框中的验证码
                if (TextUtils.isEmpty(etLoginCode.getText().toString())) {
                    return;
                }
                etLoginCode.setText("");
                break;
            case R.id.iv_login_pwd_close:
                //todo 撤销输入框中的密码
                if (TextUtils.isEmpty(etLoginPwd.getText().toString())) {
                    return;
                }
                etLoginPwd.setText("");
                break;
            case R.id.iv_login_again_pwd_close:
                //todo 撤销输入框中的密码
                if (TextUtils.isEmpty(etLoginAgainPwd.getText().toString())) {
                    return;
                }
                etLoginAgainPwd.setText("");
                break;
            case R.id.tv_login_consult:
                //todo 跳转至用户协议页面
                jumpActivityNotFinish(mContext, AgreementActivity.class);
                break;
            case R.id.tv_login_pwd:
                jumpActivityNotFinish(mContext, FindLoginPwdActivity.class);
                break;

        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        //todo 判断手机号码是否为空或格式不正确
        if (judgePhone()) {
            return;
        }
        phone = etLoginPhone.getText().toString();
        //todo 判断网络是否可用后请求接口
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getCode(phone, type);
        }
    }

    /**
     * 登录或注册
     */
    private void loginOrRegistered() {
        //todo 判断手机号码是否为空或格式不正确
        if (judgePhone()) {
            return;
        }
        phone = etLoginPhone.getText().toString();

        String code = "";
        if (TextUtils.isEmpty(etLoginPwd.getText().toString())) {
            showNormalToast(ResUtil.getString(R.string.login_tab_16));
            return;
        }
        String pwd = etLoginPwd.getText().toString();
        int invitecode = 0;
     if (type == 0){
         //todo 判断是否再次输入密码
         if (TextUtils.isEmpty(etLoginAgainPwd.getText().toString())) {
             showNormalToast(ResUtil.getString(R.string.login_tab_17));
             return;
         }
         //todo 两次密码是否一致
         if (etLoginAgainPwd.equals(pwd)){
             showNormalToast(ResUtil.getString(R.string.login_tab_17));
             return;
         }

         //todo 判断是否输入验证码
         if (TextUtils.isEmpty(etLoginCode.getText().toString())) {
             showNormalToast(ResUtil.getString(R.string.login_tab_4));
             return;
         }

         //TODO 判断验证码格式
         if (etLoginCode.getText().length() != 6) {
             showNormalToast(ResUtil.getString(R.string.login_tab_11));
             return;
         }
         code = etLoginCode.getText().toString();

         if (TextUtils.isEmpty(etLoginInvitecode.getText().toString())) {
             showNormalToast(ResUtil.getString(R.string.login_tab_14));
             return;
         }

     }


//        //TODO 判断是否勾选用户协议
//        if (!isReadAgreement) {
//            showNormalToast(ResUtil.getString(R.string.login_tab_12));
//            return;
//        }


        //todo 判断网络是否可用后请求接口
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
           switch (type){
               case 0:
                   //todo 注册
                   baseAction.Registered(phone, code, etLoginInvitecode.getText().toString(),pwd);
                   break;
               case 1:
                   //todo 登录
                   baseAction.Login(phone, pwd);
                   break;
           }
        }
    }

    /**
     * 获取验证码成功
     */
    @Override
    public void getCodeSuccess(String msg) {
        loadDiss();
        showNormalToast(msg);
        //todo 启动计时器
        if (timer != null) {
            timer.cancel();
        }
        timer.start();
    }


    /**
     * 登录或注册成功
     *
     * @param loginDto
     */
    @Override
    public void loginOrRegisteredSuccess(LoginDto loginDto) {
        loadDiss();
        MySp.clearAllSP(mContext);
        //todo 保存登录或注册返回的数据
        L.e("lgh_user","token  = "+loginDto.getData().getToken());
        MySp.setAccessToken(mContext, loginDto.getData().getToken());
        MySp.setUserPhone(mContext,loginDto.getData().getMobile());
        MySp.setUserPayPwd(mContext,loginDto.getData().getPwd_exists());//判断是否设置支付密码
        MySp.setUserNameapi(mContext,loginDto.getData().getIs_nameapi());//判断是否认证
        //todo 判断是否认证和是否设置支付密码跳转不同页面
        Class classA = null;
        boolean isMain = false;
        if (MySp.getUserNameapi(mContext) == 1 && MySp.getUserPayPwd(mContext) == 1){
            classA = MainActivity.class;
            isMain = true;
        }else  if (MySp.getUserNameapi(mContext) == 0){
            classA = IdCardActivity.class;
        }else if (MySp.getUserPayPwd(mContext) == 0){
            classA = SetPayPwdActivity.class;
        }
        Intent intent = new Intent(mContext, classA);
        intent.putExtra("isLogin",true);
        intent.putExtra("phone",MySp.getUserPhone(mContext));
        startActivity(intent);
        finish();
       if (isMain){
           ActivityStack.getInstance().exitIsNotHaveMain(MainActivity.class);
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

    /*****************************  部分多次调用的代码块*************************************/

    /**
     * 切换登录或注册
     *
     * @param type
     */
    private void setType(int type) {
        this.type = type;
        fRightTv.setText(ResUtil.getString(type == 0 ? R.string.login_tab_1 : R.string.login_tab_2));
        btnLogin.setText(ResUtil.getString(type == 0 ? R.string.login_tab_2 : R.string.login_tab_1));
        llRegistered.setVisibility(type == 0?View.VISIBLE :View.GONE);
        tvLoginPwd.setVisibility(type == 1?View.VISIBLE :View.GONE);
    }

    //todo 判断手机号码是否为空或格式不正确
    private boolean judgePhone() {
        //todo 判断手机号码是否为空
        if (TextUtils.isEmpty(etLoginPhone.getText().toString())) {
            showNormalToast(ResUtil.getString(R.string.login_tab_3));
            return true;
        }
        //todo 判断手机号码格式是否正确
        if (!ValidateUtils.isPhone2(etLoginPhone.getText().toString())) {
            showNormalToast(ResUtil.getString(R.string.login_tab_10));
            return true;
        }
//        //todo 判断账号是否已登录
//        if (phones.size() != 0){
//            for (int i = 0; i <phones.size() ; i++) {
//                if (phones.get(i).equals(etLoginPhone.getText().toString())){
//                    showNormalToast(ResUtil.getString(R.string.login_tab_15));
//                    return true;
//                }
//            }
//        }
        return false;
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
