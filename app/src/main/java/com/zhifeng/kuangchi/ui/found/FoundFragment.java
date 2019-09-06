package com.zhifeng.kuangchi.ui.found;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.FoundAction;
import com.zhifeng.kuangchi.module.MarketTrendDto;
import com.zhifeng.kuangchi.module.ServiceDto;
import com.zhifeng.kuangchi.ui.impl.FoundView;
import com.zhifeng.kuangchi.util.base.UserBaseFragment;
import com.zhifeng.kuangchi.util.json.GetJsonDataUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 发现fragment
 */
public class FoundFragment extends UserBaseFragment<FoundAction> implements FoundView {
    View view;
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.linechart)
    LineChart mLineChart;


    @Override
    protected FoundAction initAction() {
        return new FoundAction((RxAppCompatActivity) getActivity(),this);
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
            getService();
        }
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
     * @param serviceDto
     */
    @Override
    public void getServiceSuccess(ServiceDto serviceDto) {
        String json = new GetJsonDataUtil().getJson(mContext,"data.json");
        MarketTrendDto marketTrendDto = new Gson().fromJson(json, new TypeToken<MarketTrendDto>() {
        }.getType());

        //设置数据
        List<Entry> entries = new ArrayList<>();
        List<Entry> entries1 = new ArrayList<>();
        List<Entry> entries2 = new ArrayList<>();
        List<Entry> entries3 = new ArrayList<>();
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < marketTrendDto.getData().size(); i++) {
            entries.add(new Entry(i+1, marketTrendDto.getData().get(i).getOpen()));
            entries1.add(new Entry(i+1, marketTrendDto.getData().get(i).getLow()));
            entries2.add(new Entry(i+1, marketTrendDto.getData().get(i).getVol()));
            entries3.add(new Entry(i+1, marketTrendDto.getData().get(i).getHigh()));
            mList.add((i+1)+"");
            L.e("lgh_data","data  = "+marketTrendDto.getData().get(i).getOpen());
            L.e("lgh_data","data  = "+entries.get(i).toString());
        }


        //显示边界
//        mLineChart.setDrawBorders(true);
        //去除网格
        mLineChart.setDrawGridBackground(false);
        Description description = new Description();
        description.setEnabled(false);
        mLineChart.setDescription(description);

        Legend legend = mLineChart.getLegend();
        legend.setEnabled(false);
        LineDataSet lineDataSet = new LineDataSet(entries, "T");
        LineDataSet lineDataSet1 = new LineDataSet(entries1, "T");
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "T");
        LineDataSet lineDataSet3 = new LineDataSet(entries3, "T");
        setLineData(lineDataSet,getResources().getColor(R.color.color_fad117));
        setLineData(lineDataSet1,Color.WHITE);
        setLineData(lineDataSet2,Color.GREEN);
        setLineData(lineDataSet3,Color.BLUE);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);
        dataSets.add(lineDataSet3);
        //把要画的所有线(线的集合)添加到LineData里
        LineData lineData = new LineData(dataSets);
        //把最终的数据setData
        mLineChart.setData(lineData);
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(true);
        //设置X轴坐标之间的最小间隔（X轴可设置可缩放）
        xAxis.setGranularity(1);
        //是否可以拖动
        mLineChart.setDragEnabled(true);
        //触摸事件
        mLineChart.setTouchEnabled(true);
        Matrix matrix = new Matrix();
        float sx = (mList.size()*1.0f) / 5.0f;
        //x轴缩放sx倍
        matrix.postScale(sx, 1f);
        //在图表动画显示之前进行缩放
        mLineChart.getViewPortHandler().refresh(matrix, mLineChart, false);
//        xAxis.setLabelCount(mLineChart.getLabelFor());
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
                int v = ((int) value)-1;
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

        L.e("lgh_data","data  = "+entries.size());
    }

    private void setLineData(LineDataSet lineDataSet,int color) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(2f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        //设置折线图填充
        lineDataSet.setDrawFilled(false);
        //设置填充的颜色
//        lineDataSet.setFillColor(getResources().getColor(R.color.color_88fad117));
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        //设置曲线展示为圆滑曲线（如果不设置则默认折线）
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //设置是否显示点的坐标值
        lineDataSet.setDrawValues(false);
    }


    /**
     * 失败
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
    }

    @Override
    public void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }
}
