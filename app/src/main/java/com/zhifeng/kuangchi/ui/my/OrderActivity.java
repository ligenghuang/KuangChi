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
import butterknife.OnTouch;

/**
 * @ClassName: 我的订单
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/31 14:32
 * @Version: 1.0
 */

public class OrderActivity extends UserBaseActivity {

    public static int Position = 0;
    private static final int POIONTONE = 0;
    private static final int POIONTTWO = 1;
    private static final int POIONTTHREE = 2;
    private static final int POIONTFOUR = 3;
    private MyFragmentPagerAdapter fragmentPagerAdapter;
    private int fragmentSize = 4;
    private ArrayList<Fragment> fragments;

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_order_status_1)
    TextView tvOrderStatus1;
    @BindView(R.id.tv_order_status_2)
    TextView tvOrderStatus2;
    @BindView(R.id.tv_order_status_3)
    TextView tvOrderStatus3;
    @BindView(R.id.tv_order_status_4)
    TextView tvOrderStatus4;
    @BindView(R.id.my_pager)
    CustomViewPager myPager;

    @Override
    public int intiLayout() {
        return R.layout.activity_order;
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
                .addTag("OrderActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_tab_5));
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
            OrderFragment fragment = new OrderFragment().newInstance(i);
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
        myPager.setPagingEnabled(false);
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

    @OnTouch({R.id.tv_order_status_1, R.id.tv_order_status_2, R.id.tv_order_status_3,
            R.id.tv_order_status_4})
    public boolean onTouch(View v) {
        int i = v.getId();

        switch (i) {
            case R.id.tv_order_status_1:
                Position = POIONTONE;
                break;
            case R.id.tv_order_status_2:
                Position = POIONTTWO;
                break;
            case R.id.tv_order_status_3:
                Position = POIONTTHREE;
                break;
            case R.id.tv_order_status_4:
                Position = POIONTFOUR;
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
        tvOrderStatus1.setSelected(false);
        tvOrderStatus2.setSelected(false);
        tvOrderStatus3.setSelected(false);
        tvOrderStatus4.setSelected(false);
        switch (position) {
            case 0:
                tvOrderStatus1.setSelected(true);
                break;
            case 1:
                tvOrderStatus2.setSelected(true);
                break;
            case 2:
                tvOrderStatus3.setSelected(true);
                break;
            case 3:
                tvOrderStatus4.setSelected(true);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Position = 0;
    }
}
