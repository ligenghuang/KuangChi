package com.zhifeng.kuangchi.ui.found;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.charts.LineChart;
import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.data.ResUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.FoundAction;
import com.zhifeng.kuangchi.adapter.KeyBoardAdapter;
import com.zhifeng.kuangchi.module.KLineDto;
import com.zhifeng.kuangchi.ui.impl.FoundView;
import com.zhifeng.kuangchi.ui.my.AgencyListActivity;
import com.zhifeng.kuangchi.ui.my.ArningsActivity;
import com.zhifeng.kuangchi.ui.my.CarryActivity;
import com.zhifeng.kuangchi.ui.my.EntrustListActivity;
import com.zhifeng.kuangchi.ui.my.TopUpDetailActivity;
import com.zhifeng.kuangchi.util.base.UserBaseFragment;
import com.zhifeng.kuangchi.util.data.MySp;
import com.zhifeng.kuangchi.util.view.OnPasswordInputFinish;
import com.zhifeng.kuangchi.util.view.VirtualKeyboardView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发现fragment
 */
public class FoundFragment extends UserBaseFragment<FoundAction> implements FoundView {
    View view;
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.tv_lamb)
    TextView tvLamb;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_home_bouns_day)
    TextView tvHomeBounsDay;
    @BindView(R.id.tv_home_bouns)
    TextView tvHomeBouns;
    @BindView(R.id.tv_home_bouns_day_f)
    TextView tvHomeBounsDayF;
    @BindView(R.id.tv_home_bouns_f)
    TextView tvHomeBounsF;


    /*******************************密码输入界面****************************************/
    @BindView(R.id.ll_find_pwd)
    LinearLayout llFindPwd;
    @BindView(R.id.tv_pass1)
    TextView tvPass1;
    @BindView(R.id.img_pass1)
    ImageView imgPass1;
    @BindView(R.id.tv_pass2)
    TextView tvPass2;
    @BindView(R.id.img_pass2)
    ImageView imgPass2;
    @BindView(R.id.tv_pass3)
    TextView tvPass3;
    @BindView(R.id.img_pass3)
    ImageView imgPass3;
    @BindView(R.id.tv_pass4)
    TextView tvPass4;
    @BindView(R.id.img_pass4)
    ImageView imgPass4;
    @BindView(R.id.tv_pass5)
    TextView tvPass5;
    @BindView(R.id.img_pass5)
    ImageView imgPass5;
    @BindView(R.id.tv_pass6)
    TextView tvPass6;
    @BindView(R.id.img_pass6)
    ImageView imgPass6;
    @BindView(R.id.virtualKeyboardView)
    VirtualKeyboardView virtualKeyboardView;
    private TextView[] tvList;      //用数组保存6个TextView，为什么用数组？

    private ImageView[] imgList;      //用数组保存6个TextView，为什么用数组？

    private GridView gridView;

    private ArrayList<Map<String, String>> valueList;

    private int currentIndex = -1;    //用于记录当前输入密码格位置




    @Override
    protected FoundAction initAction() {
        return new FoundAction((RxAppCompatActivity) getActivity(), this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getContext();
        mActivity = activity;
    }


    @Override
    protected void initialize() {

        refreshLayout.setEnableLoadMore(false);
        gridView = virtualKeyboardView.getGridView();
        initPwdView();
        initValueList();
        setupView();
        loadView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_found, container, false);
        ButterKnife.bind(this, view);
        binding();
        mImmersionBar.setStatusBarView(getActivity(), topView);
        return view;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            if (MySp.getFound(mContext)==1){
                llFindPwd.setVisibility(View.GONE);
                getService();
                tvHomeBouns.setText(MySp.getBouns(mContext));
                tvHomeBounsDay.setText(MySp.getBounsDay(mContext));
                tvHomeBounsDayF.setText("0");
                tvHomeBounsF.setText("0");
            }else {
                llFindPwd.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    protected void loadView() {
        super.loadView();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (MySp.getFound(mContext)==1){
                    llFindPwd.setVisibility(View.GONE);
                    getService();
                    tvHomeBouns.setText(MySp.getBouns(mContext));
                    tvHomeBounsDay.setText(MySp.getBounsDay(mContext));
                    tvHomeBounsDayF.setText("0");
                    tvHomeBounsF.setText("0");
                }else {
                    llFindPwd.setVisibility(View.VISIBLE);
                }
            }
        });

        /**
         *  是否需要验证
         */
        setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(String password) {
                    verifyPassword(password);
//
            }
        });
    }

    /**
     * 获取存储服务器群组
     */
    @Override
    public void getService() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getService();
        }
    }


    /**
     * 获取存储服务器群组成功
     *
     * @param serviceDto
     */
    @Override
    public void getServiceSuccess(KLineDto serviceDto) {
        refreshLayout.finishRefresh();
        DecimalFormat df = new DecimalFormat("#0.00000000");
        tvLamb.setText(ResUtil.getFormatString(R.string.found_tab_5, df.format(serviceDto.getData().getTick().getClose())));
    }

    /**
     * 验证密码
     * @param pwd
     */
    @Override
    public void verifyPassword(String pwd) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.verifyPassword(pwd);
        }
    }

    /**
     * 验证密码
     */
    @Override
    public void verifyPasswordSuccess() {
        loadDiss();
        showToast(ResUtil.getString(R.string.found_tab_6));
        MySp.setFound(mContext,1);
        llFindPwd.setVisibility(View.GONE);
        getService();
        tvHomeBouns.setText(MySp.getBouns(mContext));
        tvHomeBounsDay.setText(MySp.getBounsDay(mContext));
        tvHomeBounsDayF.setText("0");
        tvHomeBounsF.setText("0");
    }


    /**
     * 验证失败
     * @param msg
     */
    @Override
    public void verifyPasswordError(String msg) {
        loadDiss();
        showToast(msg);
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
        tvLamb.setText(ResUtil.getFormatString(R.string.found_tab_5, "0"));
    }

    @Override
    public void onResume() {
        super.onResume();
        baseAction.toRegister();
    }

    @Override
    public void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }

    @OnClick({R.id.tv_user_entrust, R.id.tv_user_agency, R.id.tv_user_earnings, R.id.tv_usere_mention_money,R.id.tv_usere_put_money})
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
            case R.id.tv_usere_put_money:
                //todo 充值明细
                jumpActivityNotFinish(mContext, TopUpDetailActivity.class);
                break;
        }
    }

    /***************************************密码输入事件监听*****************************************************/
    // 这里，我们没有使用默认的数字键盘，因为第10个数字不显示.而是空白
    private void initValueList() {

        valueList = new ArrayList<>();

        // 初始化按钮上应该显示的数字
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<String, String>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", "");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "");
            }
            valueList.add(map);
        }
    }


    private void initPwdView() {

        tvList = new TextView[6];

        imgList = new ImageView[6];

        tvList[0] = tvPass1;
        tvList[1] = tvPass2;
        tvList[2] = tvPass3;
        tvList[3] = tvPass4;
        tvList[4] = tvPass5;
        tvList[5] = tvPass6;


        imgList[0] = imgPass1;
        imgList[1] = imgPass2;
        imgList[2] = imgPass3;
        imgList[3] = imgPass4;
        imgList[4] = imgPass5;
        imgList[5] = imgPass6;

    }

    private void setupView() {

        // 这里、重新为数字键盘gridView设置了Adapter
        KeyBoardAdapter keyBoardAdapter = new KeyBoardAdapter(mContext, valueList);
        gridView.setAdapter(keyBoardAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 11 && position != 9) {    //点击0~9按钮
                    if (currentIndex >= -1 && currentIndex < 5) {      //判断输入位置————要小心数组越界
                        ++currentIndex;
                        tvList[currentIndex].setText(valueList.get(position).get("name"));
                        tvList[currentIndex].setVisibility(View.INVISIBLE);
                        imgList[currentIndex].setVisibility(View.VISIBLE);
                    }
                } else {
                    if (position == 11) {      //点击退格键
                        if (currentIndex - 1 >= -1) {      //判断是否删除完毕————要小心数组越界
                            tvList[currentIndex].setText("");
                            tvList[currentIndex].setVisibility(View.VISIBLE);
                            imgList[currentIndex].setVisibility(View.INVISIBLE);
                            currentIndex--;
                        }
                    }
                }
            }
        });
    }

    //设置监听方法，在第6位输入完成后触发
    public void setOnFinishInput(final OnPasswordInputFinish pass) {


        tvList[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() == 1) {

                    String strPassword = "";     //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱

                    for (int i = 0; i < 6; i++) {
                        strPassword += tvList[i].getText().toString().trim();
                    }
                    pass.inputFinish(strPassword);    //接口中要实现的方法，完成密码输入完成后的响应逻辑
                }
            }
        });
    }
}
