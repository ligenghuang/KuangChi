package com.zhifeng.kuangchi.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.CarryDetailAction;
import com.zhifeng.kuangchi.module.CarryDetailDto;
import com.zhifeng.kuangchi.ui.impl.CarryDetailView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;
import com.zhifeng.kuangchi.util.data.DynamicTimeFormat;

import java.lang.ref.WeakReference;

import butterknife.BindView;

/**
 * @ClassName: 提币明细详情页(缺少提现方式)
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/31 9:51
 * @Version: 1.0
 */

public class CarryDetailActivity extends UserBaseActivity<CarryDetailAction> implements CarryDetailView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_carry_detail_time)
    TextView tvCarryDetailTime;
    @BindView(R.id.tv_carry_detail_money)
    TextView tvCarryDetailMoney;
    @BindView(R.id.tv_carry_detail_type)
    TextView tvCarryDetailType;
    @BindView(R.id.tv_carry_detail_address)
    TextView tvCarryDetailAddress;
    @BindView(R.id.tv_carry_detail_status)
    TextView tvCarryDetailStatus;

    int id;
    @BindView(R.id.tv_carry_detail_account)
    TextView tvCarryDetailAccount;

    @Override
    public int intiLayout() {
        return R.layout.activity_carry_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected CarryDetailAction initAction() {
        return new CarryDetailAction(this, this);
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
                .addTag("CarryDetailActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_tab_59));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        id = getIntent().getIntExtra("id", 0);
        getCarryDetail();
    }

    /**
     * 获取提币详情
     */
    @Override
    public void getCarryDetail() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getCarryDetail(id + "");
        }
    }

    /**
     * 获取提币详情成功
     *
     * @param carryDetailDto
     */
    @Override
    public void getCarryDetailSuccess(CarryDetailDto carryDetailDto) {
        loadDiss();
        CarryDetailDto.DataBean dataBean = carryDetailDto.getData();
        tvCarryDetailAccount.setText(dataBean.getMoney()+"");//金额
        long data = (long) dataBean.getAdd_time()*(long)1000;
        tvCarryDetailTime.setText(DynamicTimeFormat.LongToString(data));//时间
        tvCarryDetailMoney.setText(dataBean.getInput_money()+"");//提币金额
        tvCarryDetailAddress.setText(dataBean.getAddress());//地址
        String status = getStatus(dataBean.getStatus());//获取状态
        tvCarryDetailStatus.setText(status);
        String coinType = getCoinType(dataBean.getCoin_type());
        tvCarryDetailType.setText(coinType);
    }

    /**
     * 4:usdt  5:lamb 6:btc 7:eth 8:filecoin
     * @param coin_type
     * @return
     */
    private String getCoinType(int coin_type) {
        String text = "usdt";
        switch (coin_type){
            case 4:
                text = "usdt";
                break;
            case 5:
                text = "lamb";
                break;
            case 6:
                text = "btc";
                break;
            case 7:
                text = "eth";
                break;
            case 8:
                text = "filecoin";
                break;
        }
        return text;
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
     * 获取状态
     * @param status
     * -1不通过审批，1待审批，2通过审批’
     * @return
     */
    private String getStatus(int status) {
        String str = "";
        switch (status){
            case 1:
                str = "待审核";
                break;
            case 2:
                str = "通过审核";
                break;
            default:
                str = "未通过审核";
                break;
        }
        return str;
    }
}