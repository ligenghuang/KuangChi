package com.zhifeng.kuangchi.ui.my;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.base.MyFragmentPagerAdapter;
import com.lgh.huanglib.util.cusview.CustomViewPager;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.BaseAction;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

/**
 * @ClassName: 收益明细
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/30 20:35
 * @Version: 1.0
 */

public class ArningsActivity extends UserBaseActivity {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_state_1)
    TextView tvState1;
    @BindView(R.id.tv_state_2)
    TextView tvState2;
    @BindView(R.id.tv_state_3)
    TextView tvState3;
    @BindView(R.id.tv_state_4)
    TextView tvState4;
    @BindView(R.id.tv_state_5)
    TextView tvState5;
    @BindView(R.id.tv_state_6)
    TextView tvState6;
    @BindView(R.id.tv_state_7)
    TextView tvState7;
    @BindView(R.id.my_pager)
    CustomViewPager myPager;

    public static int Position = 0;
    private MyFragmentPagerAdapter fragmentPagerAdapter;
    private int fragmentSize = 7;
    private ArrayList<Fragment> fragments;
    private final int POIONTONE = 0;
    private final int POIONTTWO = 1;
    private final int POIONTTHREE = 2;
    private final int POIONTFOUR = 3;
    private final int POIONTFIVE = 4;
    private final int POIONTSIX = 5;
    private final int POIONTSEVEN = 6;


    @Override
    public int intiLayout() {
        return R.layout.activity_arnings;
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
                .addTag("ArningsActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_tab_3));
    }

    @Override
    protected BaseAction initAction() {
        return null;
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        initViewPager();
    }

    private void initViewPager() {
        fragments = new ArrayList<Fragment>();

        for (int i = 0; i < fragmentSize; i++) {
            EarningsFragment fragment = new EarningsFragment().newInstance(i);
            fragments.add(fragment);
        }
        fragmentPagerAdapter = new MyFragmentPagerAdapter(
                getSupportFragmentManager(), fragments);

        myPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                L.e("xx", "onPageSelected " + position);

                Position = position;
                setSelectedLin(position);
            }
        });

        fragmentPagerAdapter.setFragments(fragments);
        myPager.setPagingEnabled(true);
        setSelectedLin(Position);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myPager.setAdapter(fragmentPagerAdapter);
                myPager.setCurrentItem(Position);
                myPager.setOffscreenPageLimit(1);

            }
        }, 500);
    }

    @OnTouch({R.id.tv_state_1, R.id.tv_state_2, R.id.tv_state_3,
            R.id.tv_state_4, R.id.tv_state_5,R.id.tv_state_6, R.id.tv_state_7})
    public boolean onTouch(View v) {
        int i = v.getId();

        switch (i) {
            case R.id.tv_state_1:
                Position = POIONTONE;
                break;
            case R.id.tv_state_2:
                Position = POIONTTWO;
                break;
            case R.id.tv_state_3:
                Position = POIONTTHREE;
                break;
            case R.id.tv_state_4:
                Position = POIONTFOUR;
                break;
            case R.id.tv_state_5:
                Position = POIONTFIVE;
                break;
            case R.id.tv_state_6:
                Position = POIONTSIX;
                break;
            case R.id.tv_state_7:
                Position = POIONTSEVEN;
                break;
        }

        // TODO: 2018/10/19 false  禁止滚动
        myPager.setCurrentItem(Position, false);
        return false;
    }

    /**
     * 选择
     *
     * @param position
     */
    private void setSelectedLin(int position) {
        tvState1.setSelected(false);
        tvState2.setSelected(false);
        tvState3.setSelected(false);
        tvState4.setSelected(false);
        tvState5.setSelected(false);
        tvState6.setSelected(false);
        tvState7.setSelected(false);
        switch (position) {
            case 0:
                tvState1.setSelected(true);
                break;
            case 1:
                tvState2.setSelected(true);
                break;
            case 2:
                tvState3.setSelected(true);
                break;
            case 3:
                tvState4.setSelected(true);
                break;
            case 4:
                tvState5.setSelected(true);
                break;
            case 5:
                tvState6.setSelected(true);
                break;
            case 6:
                tvState7.setSelected(true);
                break;
            default:
                break;
        }
    }
}
