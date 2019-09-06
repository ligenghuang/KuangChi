package com.zhifeng.kuangchi.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lgh.huanglib.util.base.ActivityStack;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.BaseAction;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginOrRegisteredActivity extends UserBaseActivity {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    public int intiLayout() {
        return R.layout.activity_login_or_registered;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected void initTitlebar() {
        super.initTitlebar();
        mImmersionBar
                .statusBarView(R.id.top_view)
                .keyboardEnable(true)
                .statusBarDarkFont(true, 0.2f)
                .addTag("LoginOrRegisteredActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

    }

    @Override
    protected BaseAction initAction() {
        return null;
    }


    @OnClick({R.id.tv_login_to_login,R.id.tv_login_to_registered})
    void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_login_to_login:
                //todo 登录
                jumpLoginActivity(1);
                break;
            case R.id.tv_login_to_registered:
                //TODO 注册
                jumpLoginActivity(0);
                break;
        }
    }

    /**
     * 跳转页面
     * @param type
     */
    private void jumpLoginActivity(int type){
        Intent intent = new Intent(mActicity,LoginActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }

}
