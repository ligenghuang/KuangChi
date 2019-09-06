package com.zhifeng.kuangchi.ui.my;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.InvitationAction;
import com.zhifeng.kuangchi.module.InvitationInfoDto;
import com.zhifeng.kuangchi.ui.impl.InvitationView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 邀请链接页面
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/30 14:40
 * @Version: 1.0
 */

public class InvitationActivity extends UserBaseActivity<InvitationAction> implements InvitationView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_invitation_avatar)
    ImageView ivInvitationAvatar;
    @BindView(R.id.tv_invitation_realname)
    TextView tvInvitationRealname;
    @BindView(R.id.iv_invitation_qrcode)
    ImageView ivInvitationQrcode;
    @BindView(R.id.tv_invitation_code)
    TextView tvInvitationCode;
    @BindView(R.id.tv_invitation_copy)
    TextView tvInvitationCopy;
    @BindView(R.id.tv_invitation_address)
    TextView tvInvitationAddress;

    String code = "";

    @Override
    public int intiLayout() {
        return R.layout.activity_invitation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected InvitationAction initAction() {
        return new InvitationAction(this, this);
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
                .statusBarDarkFont(false)
                .addTag("InvitationActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_tab_9));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        getInvitation();
    }

    /**
     * 获取邀请链接
     */
    @Override
    public void getInvitation() {
        //todo 判断网络是否链接 并请求接口
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getInvitationInfo();
        }
    }

    /**
     * 获取邀请链接成功
     *
     * @param invitationInfoDto
     */
    @Override
    public void getInvitationSuccess(InvitationInfoDto invitationInfoDto) {
        loadDiss();
        InvitationInfoDto.DataBean dataBean = invitationInfoDto.getData();
        GlideUtil.setImageCircle(mContext,dataBean.getAvatar(),ivInvitationAvatar,R.mipmap.icon_avatar);//头像
        tvInvitationRealname.setText(dataBean.getRealname());//昵称
        GlideUtil.setImage(mContext,dataBean.getUrl(),ivInvitationQrcode);//二维码
        tvInvitationAddress.setText(dataBean.getAddress());//地址
        code = dataBean.getId();
        tvInvitationCode.setText(ResUtil.getFormatString(R.string.my_tab_16,dataBean.getId()));//邀请码
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

    /**
     * 监听点击事件
     * @param view
     */
    @OnClick(R.id.tv_invitation_copy)
    void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_invitation_copy:
                //todo 复制邀请码
                Copy();
                break;
        }
    }

    /**
     * 复制邀请码
     */
    private void Copy(){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", code);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        //复制成功提示
        showNormalToast(ResUtil.getString(R.string.my_tab_18));
    }
}
