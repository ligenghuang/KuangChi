package com.zhifeng.kuangchi.ui.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.MyAction;
import com.zhifeng.kuangchi.module.MyInfoDto;
import com.zhifeng.kuangchi.module.UserDto;
import com.zhifeng.kuangchi.ui.MainActivity;
import com.zhifeng.kuangchi.ui.impl.MyView;
import com.zhifeng.kuangchi.ui.login.LoginActivity;
import com.zhifeng.kuangchi.util.base.UserBaseFragment;
import com.zhifeng.kuangchi.util.data.MySp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的fragment
 */
public class MyFragment extends UserBaseFragment<MyAction> implements MyView {
    View view;
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_uxer_id)
    TextView tvUxerId;
    @BindView(R.id.tv_user_createtime)
    TextView tvUserCreatetime;
    @BindView(R.id.tv_user_mobile)
    TextView tvUserMobile;
    @BindView(R.id.tv_user_address)
    TextView tvUserAddress;
    @BindView(R.id.tv_user_level)
    TextView tvUserLevel;

    boolean isFirst = true;
    @BindView(R.id.tv_user_is_vip)
    TextView tvUserIsVip;
    @BindView(R.id.tv_user_is_nameapi)
    TextView tvUserIsNameApi;

    String name;
    String avatar;

    @Override
    protected MyAction initAction() {
        return new MyAction((RxAppCompatActivity) getActivity(), this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getContext();
        mActivity = activity;
    }

    @Override
    protected void initialize() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);
        binding();
        mImmersionBar.setStatusBarView(getActivity(), topView);
        return view;
    }


    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
//            if (isFirst) {
//                loadDialog();
//                isFirst = false;
//            }
//            getMyInfo();
        }
    }

    /**
     * 获取我的信息
     */
    @Override
    public void getMyInfo() {
        baseAction.getMyInfo();
    }

    /**
     * 获取我的信息成功
     *
     * @param infoDto
     */
    @Override
    public void getMyInfoSuccess(MyInfoDto infoDto) {
        loadDiss();
        MyInfoDto.DataBean dataBean = infoDto.getData();
        GlideUtil.setImageCircle(mContext, dataBean.getAvatar(), ivAvatar, R.mipmap.icon_avatar);//todo 头像
        MySp.setUserImg(mContext, dataBean.getAvatar());//保存头像
        tvUserName.setText(dataBean.getRealname());//昵称
        MySp.setUserName(mContext, dataBean.getRealname());//保存昵称
        MySp.setUserId(mContext, dataBean.getId() + "");//保存id
        tvUxerId.setText(ResUtil.getFormatString(R.string.my_tab_12, dataBean.getId() + ""));//用户id
        tvUserCreatetime.setText(ResUtil.getFormatString(R.string.my_tab_13, dataBean.getReg_time()));//注册时间
        tvUserMobile.setText(ResUtil.getFormatString(R.string.my_tab_14, dataBean.getMobile()));//手机号
        MySp.setUserPhone(mContext, dataBean.getMobile());//保存手机号
        tvUserAddress.setText(ResUtil.getFormatString(R.string.my_tab_15, dataBean.getAddress()));//地址
        tvUserLevel.setText(dataBean.getLevel_name());//身份等级
        MySp.setUserNameapi(mContext, dataBean.getIs_nameapi());
        MySp.setUserVip(mContext, dataBean.getIs_vip());
        tvUserIsVip.setText(ResUtil.getString(dataBean.getIs_vip() == 1?R.string.my_tab_135:R.string.my_tab_136));
        tvUserIsNameApi.setText(ResUtil.getString(MySp.getUserNameapi(mContext) == 1 ?R.string.my_tab_145:R.string.my_tab_146));
        name = dataBean.getRealname();
        avatar = dataBean.getAvatar();

        if (MainActivity.isLogin){
            String json = MySp.getUserList(mContext);
            List<UserDto> list = new ArrayList<>();
            if (!TextUtils.isEmpty(json)){
                list = new Gson().fromJson(json, new TypeToken<List<UserDto>>() {
                }.getType());
            }
            UserDto userDto = new UserDto();
            userDto.setImg(dataBean.getAvatar());
            userDto.setName(dataBean.getRealname());
            userDto.setToken(MySp.getAccessToken(mContext));
            L.e("lgh_user","token  = "+MySp.getAccessToken(mContext));
            userDto.setIs_vip(dataBean.getIs_vip());
            userDto.setPhone(dataBean.getMobile());

            for (int i = 0; i <list.size() ; i++) {
                if(list.get(i).getPhone().equals(userDto.getPhone())){
                    list.set(i,userDto);
                    L.e("lgh_user","user  = "+userDto.getToken());
                    MySp.setUserList(mContext,new Gson().toJson(list));
                    MainActivity.isLogin = false;
                    return;
                }
            }
            if (list.size() >= 3){
                list.set(0,userDto);
            }else {
                list.add(userDto);
            }



            MySp.setUserList(mContext,new Gson().toJson(list));
            MainActivity.isLogin = false;
        }
    }

    /**
     * 获取用户信息失败
     */
    @Override
    public void getMyInfoError() {
        showToast(ResUtil.getString(R.string.my_tab_147));
        MySp.clearAllSP(mContext);
        MySp.setUserList(mContext,null);
        jumpActivityNotFinish(mContext,LoginActivity.class);
        ActivityStack.getInstance().exitIsNotHaveMain(LoginActivity.class);
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
        showToast(message);
    }


    @Override
    public void onResume() {
        super.onResume();
        baseAction.toRegister();
        if (MySp.iSLoginLive(mContext)){

            getMyInfo();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }

    /**
     * 点击事件监听
     *
     * @param view
     */
    @OnClick({R.id.tv_user_entrust, R.id.tv_user_agency,
            R.id.tv_user_earnings, R.id.tv_usere_mention_money,
            R.id.tv_user_order, R.id.ll_user_service,
            R.id.ll_user_balance, R.id.ll_user_id_card,
            R.id.ll_user_invitation, R.id.ll_user_security, R.id.ll_user_message,R.id.ll_user_avatarAndname})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_user_entrust:
                //todo 委托明细
                jumpActivityNotFinish(mContext, EntrustListActivity.class);
                break;
            case R.id.tv_user_agency:
                //todo 代理明细
                jumpActivityNotFinish(mContext, AgencyListActivity.class);
                break;
            case R.id.tv_user_earnings:
                //todo 收益明细
                jumpActivityNotFinish(mContext, ArningsActivity.class);
                break;
            case R.id.tv_usere_mention_money:
                //todo 提币明细
                jumpActivityNotFinish(mContext, CarryActivity.class);
                break;
            case R.id.tv_user_order:
                //todo 我的订单
                jumpActivityNotFinish(mContext, OrderActivity.class);
                break;
            case R.id.ll_user_service:
                //TODO 服务器
                jumpActivityNotFinish(mContext, ServiceActivity.class);
                break;
            case R.id.ll_user_balance:
                //TODO 我的余额
                jumpActivityNotFinish(mContext, BalanceActivity.class);
                break;
            case R.id.ll_user_id_card:
                //todo 身份认证
                if (MySp.getUserNameapi(mContext) == 1) {
                    //todo 已实名认证
                    showToast(ResUtil.getString(R.string.my_tab_134));
                    return;
                }
                jumpActivityNotFinish(mContext, IdCardActivity.class);
                break;
            case R.id.ll_user_invitation:
                //todo 邀请链接
                jumpActivityNotFinish(mContext, InvitationActivity.class);
                break;
            case R.id.ll_user_security:
                //todo 安全中心
                jumpActivityNotFinish(mContext, SecurityActivity.class);
                break;
            case R.id.ll_user_message:
                //TODO 消息中心
                jumpActivityNotFinish(mContext, MessageActivity.class);
                break;
            case R.id.ll_user_avatarAndname:
                Intent intent = new Intent(mContext,ModifyUserInfoActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("avatar",avatar);
                startActivity(intent);
                break;
        }
    }
}
