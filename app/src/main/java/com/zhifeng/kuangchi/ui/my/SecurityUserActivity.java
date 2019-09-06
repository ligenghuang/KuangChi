package com.zhifeng.kuangchi.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.config.GlideUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.BaseAction;
import com.zhifeng.kuangchi.adapter.UserListAdapter;
import com.zhifeng.kuangchi.module.AgencyListDto;
import com.zhifeng.kuangchi.module.UserDto;
import com.zhifeng.kuangchi.ui.MainActivity;
import com.zhifeng.kuangchi.ui.login.LoginActivity;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;
import com.zhifeng.kuangchi.util.data.MySp;

import java.lang.ref.WeakReference;
import java.util.List;

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
    //    @BindView(R.id.iv_avatar)
//    ImageView ivAvatar;
//    @BindView(R.id.tv_name)
//    TextView tvName;
    @BindView(R.id.tv_security)
    TextView tvSecurity;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    UserListAdapter userListAdapter;

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


        userListAdapter = new UserListAdapter(mContext);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(userListAdapter);

        String json = MySp.getUserList(mContext);
        List<UserDto> list = new Gson().fromJson(json, new TypeToken<List<UserDto>>() {
        }.getType());
        userListAdapter.refresh(list);
        loadView();
//        tvSecurity.setVisibility();

//        tvName.setText(MySp.getUserName(mContext));//用户名称
//        GlideUtil.setImageCircle(mContext, MySp.getUserImg(mContext), ivAvatar, R.mipmap.icon_avatar);//头像
    }

    @Override
    protected void loadView() {
        super.loadView();
        userListAdapter.setOnClickListener(new UserListAdapter.OnClickListener() {
            @Override
            public void OnClick(UserDto dto) {
                MySp.setAccessToken(mContext,dto.getToken());
                MySp.setUserName(mContext,dto.getName());
                MySp.setUserImg(mContext,dto.getImg());
                MySp.setUserVip(mContext,dto.getIs_vip());
                MainActivity.isLogin2 = true;
                finish();
            }
        });
    }

    @OnClick({ R.id.tv_security})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_security:
                //换个账号登录
                Intent intent = new Intent(mActicity, LoginActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
        }
    }
}
