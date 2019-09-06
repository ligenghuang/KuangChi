package com.zhifeng.kuangchi.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.config.GlideUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.BaseAction;
import com.zhifeng.kuangchi.ui.MainActivity;
import com.zhifeng.kuangchi.ui.login.LoginActivity;
import com.zhifeng.kuangchi.ui.login.LoginOrRegisteredActivity;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;
import com.zhifeng.kuangchi.util.data.MySp;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 切换账号
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/31 19:41
 * @Version: 1.0
 */

public class SecurityUserActivity extends UserBaseActivity {

    @BindView(R.id.top_view)
    View topView;
//    @BindView(R.id.f_right_tv)
//    TextView fRightTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_security)
    TextView tvSecurity;

    @Override
    public int intiLayout() {
        return R.layout.activity_security_user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected BaseAction initAction() {
        return null;
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
                .addTag("SecurityUserActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        tvName.setText(MySp.getUserName(mContext));//用户名称
        GlideUtil.setImageCircle(mContext,MySp.getUserImg(mContext),ivAvatar,R.mipmap.icon_avatar);//头像
    }

    @OnClick({R.id.ll_user, R.id.tv_security})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_user:
                //返回
                finish();
                break;
            case R.id.tv_security:
                //换个账号登录
                Intent intent = new Intent(mActicity, LoginActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
                break;
        }
    }
}
