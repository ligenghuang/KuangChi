package com.zhifeng.kuangchi.ui.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.config.GlideUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.superluo.textbannerlibrary.ITextBannerItemClickListener;
import com.superluo.textbannerlibrary.TextBannerView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.HomeAction;
import com.zhifeng.kuangchi.adapter.AnnounceAdapter;
import com.zhifeng.kuangchi.adapter.Banner;
import com.zhifeng.kuangchi.adapter.InformationAdapter;
import com.zhifeng.kuangchi.module.HomeDataDto;
import com.zhifeng.kuangchi.module.UpdateDto;
import com.zhifeng.kuangchi.ui.MainActivity;
import com.zhifeng.kuangchi.ui.impl.HomeView;
import com.zhifeng.kuangchi.ui.login.LoginOrRegisteredActivity;
import com.zhifeng.kuangchi.util.base.UserBaseFragment;
import com.zhifeng.kuangchi.util.data.MySp;
import com.zhifeng.kuangchi.util.update.AllDialogShowStrategy;
import com.zhifeng.kuangchi.util.update.NotificationDownloadCreator;
import com.zhifeng.kuangchi.util.update.NotificationForODownloadCreator;
import com.zhifeng.kuangchi.util.update.NotificationInstallCreator;
import com.zhifeng.kuangchi.util.update.ToastCallback;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.base.DownloadCallback;
import org.lzh.framework.updatepluginlib.base.DownloadNotifier;
import org.lzh.framework.updatepluginlib.flow.Launcher;
import org.lzh.framework.updatepluginlib.model.Update;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import cn.bingoogolapple.bgabanner.BGABanner;

import static org.greenrobot.greendao.async.AsyncOperation.OperationType.Update;

/**
 * 首页
 */
public class HomeFragment extends UserBaseFragment<HomeAction> implements HomeView {
    View view;
    @BindView(R.id.top_view)
    View topView;

    public static int Position = 0;
    private static final int POIONTONE = 0;
    private static final int POIONTTWO = 1;

    ToastCallback callback;
    //轮播图
    @BindView(R.id.banner_main)
    BGABanner banner_main;
    @BindView(R.id.tv_banner)
    TextBannerView tvBanner;
//    @BindView(R.id.tv_home_bouns_day)
//    TextView tvHomeBounsDay;
//    @BindView(R.id.tv_home_bouns)
//    TextView tvHomeBouns;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_home_buy)
    TextView tvHomeBuy;
    @BindView(R.id.tv_announce)
    TextView tvAnnounce;
    @BindView(R.id.tv_recommend_goods)
    TextView tvRecommendGoods;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.recyclerview_1)
    RecyclerView recyclerview_1;
    @BindView(R.id.recyclerview_2)
    RecyclerView recyclerview_2;

    @BindView(R.id.tv_message_num)
    TextView tvMessageNum;

    AnnounceAdapter announceAdapter;
    InformationAdapter InfoAdapter;

    Banner banner;

    List<String> imgs = new ArrayList<>();
    List<String> tips = new ArrayList<>();
    List<String> url = new ArrayList<>();
    List<String> types = new ArrayList<>();

    boolean isFirst = true;

    int goodsId;

    List<HomeDataDto.DataBean.AnnounceBean> announceBeans = new ArrayList<>();


    @Override
    protected HomeAction initAction() {
        return new HomeAction((RxAppCompatActivity) getActivity(), this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getContext();
        mActivity = activity;
    }

    @Override
    protected void initialize() {
        //轮播图
        banner = new Banner();
        banner_main.setAdapter(banner);
        //初始化公告列表
        announceAdapter = new AnnounceAdapter(mContext);
        recyclerview_2.setNestedScrollingEnabled(false);
        recyclerview_2.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview_2.setAdapter(announceAdapter);

        //初始化资讯列表
        InfoAdapter = new InformationAdapter(mContext);
        recyclerview_1.setNestedScrollingEnabled(false);
        recyclerview_1.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview_1.setAdapter(InfoAdapter);

        //关闭上拉加载更多
        refreshLayout.setEnableLoadMore(false);

        loadView();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        binding();
        mImmersionBar.setStatusBarView(getActivity(), topView);
        return view;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            updata();
            if (isFirst) {
                loadDialog();
                isFirst = false;

            }
            if (MainActivity.isLogin2) {
                getHomeData();
                MainActivity.isLogin2 = false;
            }
        }
    }

    @Override
    protected void loadView() {
        super.loadView();
        //todo 下拉刷新监听
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                updata();
                getHomeData();
            }
        });
        //todo 公告列表点击事件
        tvBanner.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                L.e("lgh_item","position   = "+position);
                Intent intent = new Intent(mContext, NoticeDetailActivity.class);
                intent.putExtra("id",announceBeans.get(position).getId()+"");
                startActivity(intent);
            }
        });
        banner_main.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
                L.e("lghl", url.get(position));
                banner_main.stopAutoPlay();
               Intent intent = new Intent(mContext,BannerActivity.class);
               intent.putExtra("title",types.get(position));
               intent.putExtra("url",url.get(position));
               startActivity(intent);
            }
        });
    }

    /**
     * 获取首页数据
     */
    @Override
    public void getHomeData() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getHomeData();
        } else {
            loadDiss();
        }
    }

    /**
     * 获取首页数据成功
     *
     * @param homeDataDto
     */
    @Override
    public void getHomeDataSuccess(HomeDataDto homeDataDto) {
        loadDiss();
        refreshLayout.finishRefresh();
        banner_main.setFocusable(true);
        banner_main.setFocusableInTouchMode(true);
        HomeDataDto.DataBean dataBean = homeDataDto.getData();
        getAnnounceList(dataBean.getAnnounce());//设置公告轮播
        setBanner(dataBean.getBanners());//设置图片轮播
        MySp.setBounsDay(mContext,dataBean.getDay_bouns()+"");//今日收益
        MySp.setBouns(mContext,dataBean.getBouns()+"");//总收益
//        L.e("lgh_img", dataBean.getGoods_gift().getImg());
        GlideUtil.setImage(mActivity, dataBean.getGoods_gift().getImg(), ivImg,R.mipmap.icon_i);// 商品图片
        goodsId = dataBean.getGoods_gift().getGoods_id();//商品id
        setSelectedLin(Position);
        setData(dataBean.getAnnounce());
        if (dataBean.getNot_read() != 0){
            String num = "";
            if (dataBean.getNot_read() > 99){
                num = "...";
            }else {
                num = dataBean.getNot_read()+"";
            }

            tvMessageNum.setText(num);
            tvMessageNum.setVisibility(View.VISIBLE);
        }else {
            tvMessageNum.setVisibility(View.GONE);
        }
    }

    @Override
    public void updata() {
        if (CheckNetwork.checkNetwork2(mContext)){
            baseAction.updata();
        }
    }

    @Override
    public void updataSuccessful(UpdateDto updateDto) {
        String downloadUrl = updateDto.getData().getUrl();
        int versionNum = updateDto.getData().getAndroid();
        L.e("updataSuccessful","versionNum = "+versionNum);
        PackageManager manager = mContext.getPackageManager();
        PackageInfo info = null;
        int code = 0;
        try {
            info = manager.getPackageInfo(mContext.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        L.e("updataSuccessful","getSDKVersionNumber() = "+getSDKVersionNumber());
        if (versionNum > code) {
            //todo 检测更新
            showUpdata(downloadUrl, versionNum);
        }
    }

    /**
     * 区分公告和资讯
     * @param announce
     */
    private void setData(List<HomeDataDto.DataBean.AnnounceBean> announce) {
        List<HomeDataDto.DataBean.AnnounceBean> list_1 = new ArrayList<>();
        List<HomeDataDto.DataBean.AnnounceBean> list_2 = new ArrayList<>();
        for (int i = 0; i < announce.size(); i++) {
           switch (announce.get(i).getType()){
               case 1:
                   //公告
                   list_1.add(announce.get(i));
                   break;
               case 2:
                   //资讯
                   list_2.add(announce.get(i));
                   break;
           }
        }
        announceAdapter.refresh(list_1);//公告列表
        InfoAdapter.refresh(list_2);//资讯列表
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
    }

    @Override
    public void onPause() {
        super.onPause();
        baseAction.toUnregister();
    }

    /**
     * 获取公告列表
     */
    private void getAnnounceList(List<HomeDataDto.DataBean.AnnounceBean> list) {
        if (list.size() != 0) {
            List<String> strings = new ArrayList<>();
            announceBeans = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
               if (list.get(i).getType() == 1){
                   strings.add(list.get(i).getTitle());
                   announceBeans.add(list.get(i));
               }
            }
            tvBanner.setDatas(strings);

        }

    }

    /**
     * 设置图片轮播
     *
     * @param banners
     */
    private void setBanner(List<HomeDataDto.DataBean.BannersBean> banners) {
        //设置轮播图
        if (banners.size() != 0) {
            banner_main.setVisibility(View.VISIBLE);
            imgs = new ArrayList<>();
            tips = new ArrayList<>();
            url = new ArrayList<>();
            types = new ArrayList<>();
            for (int i = 0; i < banners.size(); i++) {
                HomeDataDto.DataBean.BannersBean bannersBean = banners.get(i);
                imgs.add(bannersBean.getPicture());
                tips.add("");
                url.add(bannersBean.getUrl());
                types.add(bannersBean.getTitle());
            }
            banner_main.setAutoPlayAble(true);
            banner_main.setData(imgs, tips);
            banner_main.startAutoPlay();
        }
    }


    @OnTouch({R.id.tv_announce, R.id.tv_recommend_goods})
    public boolean onTouch(View v) {
        switch (v.getId()) {
            case R.id.tv_announce:
                Position = POIONTONE;
                break;
            case R.id.tv_recommend_goods:
                Position = POIONTTWO;
                break;
            default:
                break;
        }
        setSelectedLin(Position);
        return false;
    }

    /**
     * 选择
     *
     * @param position
     */
    private void setSelectedLin(int position) {
        tvAnnounce.setSelected(false);
        recyclerview_1.setVisibility(View.GONE);
        tvRecommendGoods.setSelected(false);
        recyclerview_2.setVisibility(View.GONE);
        //设置状态栏黑色字体与图标
//        QMUIStatusBarHelper.setStatusBarLightMode(this);
        switch (position) {
            case 0:
                tvAnnounce.setSelected(true);
                recyclerview_1.setVisibility(View.VISIBLE);
                break;
            case 1:
                tvRecommendGoods.setSelected(true);
                recyclerview_2.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.iv_img,R.id.ll_customer_service,R.id.tv_home_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_img:
            case R.id.tv_home_buy:
                //todo 判断是否已经登录
                if (!MySp.iSLoginLive(mContext)){
                    jumpActivityNotFinish(mContext, LoginOrRegisteredActivity.class);
                    return;
                }

                //todo 跳转至商品详情页
                Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                intent.putExtra("goods_id", goodsId);
                startActivity(intent);
                break;
            case R.id.ll_customer_service:
                //todo 判断是否已经登录
                if (!MySp.iSLoginLive(mContext)){
                    jumpActivityNotFinish(mContext, LoginOrRegisteredActivity.class);
                    return;
                }
                tvMessageNum.setVisibility(View.GONE);
                //todo 跳转至客服对话列表页
                jumpActivityNotFinish(mContext,CustomerServiceActivity.class);
                break;
        }
    }


    /**
     * 更新 弹窗
     */
    private void showUpdata( String downloadUrl, int versionNum) {

        Dialog dialog = new Dialog(getActivity(), R.style.MY_AlertDialog);
        dialog.setCanceledOnTouchOutside(false);
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_updata, null);
        TextView tv_updata = inflate.findViewById(R.id.tv_dialog_confirm);
        tv_updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 立即更新
                loadDown(downloadUrl, versionNum);
                tv_updata.setText("正在下载，请稍等");
                tv_updata.setEnabled(false);
            }
        });
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }


    public void loadDown(String downloadUrl, int versionNum) {
        UpdateBuilder builder = UpdateBuilder.create();
        // 配置toast通知的回调
        builder.setDownloadCallback(callback);
        builder.setCheckCallback(callback);
        builder.setUpdateStrategy(new AllDialogShowStrategy());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            builder.setDownloadNotifier(new NotificationForODownloadCreator());
        } else {

            builder.setDownloadNotifier(new NotificationDownloadCreator());
        }
        builder.setInstallNotifier(new NotificationInstallCreator());


        Update update = new Update();
        // 此apk包的下载地址
//        update.setUpdateUrl("https://raw.githubusercontent.com/yjfnypeu/UpdatePlugin/master/update_plugin.apk");
        update.setUpdateUrl(downloadUrl);
        // 此apk包的版本号
        update.setVersionCode(versionNum);
        // 此apk包的版本名称
        update.setVersionName("v"+versionNum);
        // 此apk包的更新内容
        update.setUpdateContent("");
        // 此apk包是否为强制更新
        update.setForced(true);
        // 是否显示忽略此次版本更新按钮
        update.setIgnore(false);
        update.setMd5(null);

        Launcher.getInstance().launchDownload(update, builder);
    }


}
