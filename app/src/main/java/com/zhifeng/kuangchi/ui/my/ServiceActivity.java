package com.zhifeng.kuangchi.ui.my;

import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.JsonUtils;
import com.lgh.huanglib.util.data.ResUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.ServiceAction;
import com.zhifeng.kuangchi.adapter.ServiceAdapter;
import com.zhifeng.kuangchi.module.ServiceDto;
import com.zhifeng.kuangchi.ui.impl.ServiceView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;
import com.zhifeng.kuangchi.util.data.DynamicTimeFormat;
import com.zhifeng.kuangchi.util.json.GetJsonDataUtil;
import com.zhifeng.kuangchi.util.json.JsonMap;
import com.zhifeng.kuangchi.util.view.LineChartView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @ClassName: 存储服务器群组
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/31 18:15
 * @Version: 1.0
 */

public class ServiceActivity extends UserBaseActivity<ServiceAction> implements ServiceView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_service_name)
    TextView tvServiceName;
    @BindView(R.id.tv_miner_id)
    TextView tvMinerId;
    @BindView(R.id.tv_user_money)
    TextView tvUserMoney;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_t_num)
    TextView tvTNum;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.linechart)
    LineChart mLineChart;

    ServiceAdapter serviceAdapter;

    @Override
    public int intiLayout() {
        return R.layout.activity_service;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected ServiceAction initAction() {
        return new ServiceAction(this, this);
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
                .addTag("ServiceActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_tab_6));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        serviceAdapter = new ServiceAdapter();
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(serviceAdapter);



        getService();
    }

    /**
     * 获取存储服务器群组
     */
    @Override
    public void getService() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getService();
        }
    }

    /**
     * 获取存储服务器群组成功
     *
     * @param serviceDto
     */
    @Override
    public void getServiceSuccess(ServiceDto serviceDto, Map<String,String> mMap ) {
        loadDiss();
        ServiceDto.DataBean dataBean = serviceDto.getData();
        serviceAdapter.refresh(dataBean.getMiner_info());

        ServiceDto.DataBean.CurrencyBean currencyBean = dataBean.getCurrency();
        tvMinerId.setText(ResUtil.getFormatString(R.string.my_tab_128, currencyBean.getCurrency_address()));
        tvAddress.setText(ResUtil.getFormatString(R.string.my_tab_130, currencyBean.getCurrency_address_url()));
        tvUserMoney.setText(ResUtil.getFormatString(R.string.my_tab_129, dataBean.getUser_money()));
        tvTNum.setText(ResUtil.getFormatString(R.string.my_tab_131, dataBean.getMiner_nums() + ""));
        List<Entry> entries = new ArrayList<>();
        List<String> mList = new ArrayList<>();

            if (!mMap.isEmpty()) {
                //设置数据
                Iterator kv = mMap.entrySet().iterator();
                int size = mMap.size();
                for (int i = 0; i < size; i++) {
                    Map.Entry entry = (Map.Entry) kv.next();
                    String key = entry.getKey().toString();
                    String value = entry.getValue().toString();
                    entries.add(new Entry(i + 1, Float.parseFloat(value)));
                    long date = 0;
                    try {
                        date = DynamicTimeFormat.stringToLong(key, "yyyy-MM-dd HH:mm:ss");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    mList.add(DynamicTimeFormat.LongToString3(date));
                    L.e("lgh_data", "data  = " + value);
                    L.e("lgh_data", "data  = " + entries.get(i).toString());
                }



            } else {
                for (int i = 0; i < 5; i++) {
                    entries.add(new Entry(i + 1, i*10));
                    mList.add("");
                }

            }

        //显示边界
        mLineChart.setDrawBorders(false);
        //去除网格
        mLineChart.setDrawGridBackground(false);
        Description description = new Description();
        description.setEnabled(false);
        mLineChart.setDescription(description);

        Legend legend = mLineChart.getLegend();
        legend.setDrawInside(false);
        legend.setEnabled(false);
        LineDataSet lineDataSet = new LineDataSet(entries, "T");
        lineDataSet.setColor(getResources().getColor(R.color.color_fad117));
        lineDataSet.setCircleColor(getResources().getColor(R.color.color_fad117));
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        //设置折线图填充
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(getResources().getColor(R.color.color_88fad117));
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        //设置曲线展示为圆滑曲线（如果不设置则默认折线）
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //设置是否显示点的坐标值
        lineDataSet.setDrawValues(false);

        LineData data = new LineData(lineDataSet);
        mLineChart.setData(data);
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);
        //设置X轴坐标之间的最小间隔（X轴可设置可缩放）
        xAxis.setGranularity(1f);
        //是否可以拖动
        mLineChart.setDragEnabled(true);
        //触摸事件
        mLineChart.setTouchEnabled(true);
        Matrix matrix = new Matrix();
        float sx = (mList.size() * 1.0f) / 5.0f;
        //x轴缩放sx倍
        matrix.postScale(sx, 1f);
        //在图表动画显示之前进行缩放
        mLineChart.getViewPortHandler().refresh(matrix, mLineChart, false);
//        x、y轴动画
        mLineChart.animateX(1000);
        mLineChart.animateY(1000);
        mLineChart.setScaleEnabled(false);
        xAxis.setLabelCount(10, false);
        xAxis.setTextSize(7f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                L.e("TAG", "----->getFormattedValue: " + value);
                //设置 xAxis.setGranularity(1);后 value是从0开始的，每次加1，
                int v = ((int) value) - 1;
                if (v <= mList.size() && v >= 0) {
                    String st = mList.get(v);
                    String tim1 = "";
                    tim1 = st;
                    return tim1;
                } else {
                    return null;
                }
            }
        });
        //Y轴
        YAxis left = mLineChart.getAxisLeft();
        YAxis right = mLineChart.getAxisRight();
        //隐藏右边的Y轴
        right.setEnabled(false);
        left.setAxisLineColor(Color.WHITE);
        left.setTextColor(Color.WHITE);
        //去除x y轴网格
        xAxis.setDrawGridLines(false);
        right.setDrawGridLines(false);
        left.setDrawGridLines(false);

        L.e("lgh_data", "data  = " + entries.size());
        mLineChart.setData(data);
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
    protected void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }

    @Override
    protected void onResume() {
        super.onResume();
        baseAction.toRegister();
    }
}
