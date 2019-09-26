package com.zhifeng.kuangchi.ui;

import android.os.Process;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.gyf.barlibrary.ImmersionBar;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.base.BaseActivity;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.BaseAction;
import com.zhifeng.kuangchi.ui.login.LoginActivity;
import com.zhifeng.kuangchi.ui.login.LoginOrRegisteredActivity;
import com.zhifeng.kuangchi.ui.my.IdCardActivity;
import com.zhifeng.kuangchi.ui.my.SecurityPwdActivity;
import com.zhifeng.kuangchi.ui.my.SetPayPwdActivity;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;
import com.zhifeng.kuangchi.util.data.MySp;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartPageActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int intiLayout() {
        return R.layout.activity_start_page;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(intiLayout());
        ButterKnife.bind(this);

        ImmersionBar.with(this)
                .titleBar(toolbar, false)
                .transparentBar()
                .statusBarDarkFont(true)
                .init();
        init();
    }

    @Override
    protected void init() {
        super.init();

        mActicity = this;
        mContext = this;

        MySp.setFound(mContext,0);
        //todo 引导页
//        if (!MySp.getFirst(getApplicationContext())) {
//            jumpActivityNotFinish(this, GuidePageActivity.class);
//            finish();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }
        List<String> needRequestPermissionList = findDeniedPermissions(needPermissions);
        if (null != needRequestPermissionList && needRequestPermissionList.size() > 0) {
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //todo 判断是否登录和是否认证跳转不同页面
                    Class classA = null;
                    if (!MySp.iSLoginLive(mContext)){
                        classA = LoginOrRegisteredActivity.class;
                    }else {
                        if (MySp.getUserNameapi(mContext) == 1 && MySp.getUserPayPwd(mContext) == 1){
                            classA = MainActivity.class;
                        }else  if (MySp.getUserNameapi(mContext) == 0){
                            classA = IdCardActivity.class;
                            showNormalToast(ResUtil.getString(R.string.my_tab_164));
                        }else if (MySp.getUserPayPwd(mContext) == 0){
                            classA = SetPayPwdActivity.class;
                            showNormalToast(ResUtil.getString(R.string.my_tab_165));
                        }
                    }
                    Intent intent = new Intent(mContext, classA);
                    intent.putExtra("isLogin",true);
                    intent.putExtra("phone",MySp.getUserPhone(mContext));
                    startActivity(intent);
                    isNeedAnim = false;
                    finish();
                }
            }, 2000);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Process.killProcess(Process.myPid());
    }

}
