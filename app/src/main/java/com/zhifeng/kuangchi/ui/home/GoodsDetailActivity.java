package com.zhifeng.kuangchi.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.base.MyFragmentPagerAdapter;
import com.lgh.huanglib.util.config.GlideApp;
import com.lgh.huanglib.util.cusview.CustomViewPager;
import com.lgh.huanglib.util.cusview.richtxtview.ImageLoader;
import com.lgh.huanglib.util.cusview.richtxtview.XRichText;
import com.lgh.huanglib.util.data.ResUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.BaseAction;
import com.zhifeng.kuangchi.actions.GoodsDetailAction;
import com.zhifeng.kuangchi.adapter.Banner;
import com.zhifeng.kuangchi.adapter.SpecListAdapter;
import com.zhifeng.kuangchi.module.GoodsDetailDto;
import com.zhifeng.kuangchi.module.HomeDataDto;
import com.zhifeng.kuangchi.ui.impl.GoodsDetailView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * @ClassName: 商品详情页
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/31 15:55
 * @Version: 1.0
 */

public class GoodsDetailActivity extends UserBaseActivity<GoodsDetailAction> implements GoodsDetailView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.banner_main)
    BGABanner bannerMain;
    @BindView(R.id.tv_goods_detail_name)
    TextView tvGoodsDetailName;
    @BindView(R.id.tv_goods_detail_money)
    TextView tvGoodsDetailMoney;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.tv_goods_detail_subtract)
    TextView tvGoodsDetailSubtract;
    @BindView(R.id.et_goods_detail_num)
    TextView tvGoodsDetailNum;
    @BindView(R.id.tv_goods_detail_add)
    TextView tvGoodsDetailAdd;

    @BindView(R.id.tv_goods_detail_price)
    TextView tvGoodsDetailPrice;
    @BindView(R.id.tv_type_1)
    TextView tvType1;
    @BindView(R.id.tv_type_2)
    TextView tvType2;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_goods_detail_entrust)
    TextView tvGoodsDetailEntrust;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.xrichtext)
    XRichText xRichText;

    int id;
    double price;
    int num;//数量
    int inventory;//库存
    int sku_id;

    /**
     * 轮播图所需参数
     */
    Banner banner;

    List<String> imgs = new ArrayList<>();
    List<String> tips = new ArrayList<>();
    List<String> url = new ArrayList<>();

    public static int Position = 0;
    private static final int POIONTONE = 0;
    private static final int POIONTTWO = 1;
    private ArrayList<Fragment> fragments;
    private MyFragmentPagerAdapter fragmentPagerAdapter;
    private int fragmentSize = 2;

    SpecListAdapter specListAdapter;
    String content;
    String content_param;

    @Override
    public int intiLayout() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected GoodsDetailAction initAction() {
        return new GoodsDetailAction(this,this);
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
                .addTag("GoodsDetailActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        id = getIntent().getIntExtra("goods_id",0);

        //轮播图
        banner = new Banner();
        bannerMain.setAdapter(banner);

        refreshLayout.setEnableLoadMore(false);//禁止上拉加载更多

        specListAdapter = new SpecListAdapter();
        recyclerview.setLayoutManager(new GridLayoutManager(mContext,3));
        recyclerview.setAdapter(specListAdapter);

        refreshLayout.autoRefresh();
        tvGoodsDetailEntrust.setVisibility(View.GONE);
        loadView();
    }

    @Override
    protected void loadView() {
        super.loadView();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //todo 刷新数据
                getGoodsDetail();
            }
        });

        specListAdapter.setOnClickListener(new SpecListAdapter.OnClickListener() {
            @Override
            public void OnClick(int Inventory, int Sku_id,String Price) {
                List<GoodsDetailDto.DataBean.SpecBean.GoodsSkuBean> list = specListAdapter.getAllData();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setClick(list.get(i).getSku_id() == Sku_id);
                }
                specListAdapter.notifyDataSetChanged();
                sku_id = Sku_id;
                inventory = Inventory;
                price = Double.parseDouble(Price);
                tvGoodsDetailMoney.setText("￥"+price);//单价
                double total = num * price;
                tvGoodsDetailPrice.setText("￥"+total);
            }
        });
    }

    @OnClick({R.id.iv_back,R.id.tv_goods_detail_entrust,
    R.id.tv_goods_detail_subtract,R.id.tv_goods_detail_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                //todo 返回
                finish();
                break;
            case R.id.tv_goods_detail_entrust:
                //todo 跳转至支付页
                Intent intent = new Intent(mContext,EntrustedPurchaseActivity.class);
                intent.putExtra("sku_id",sku_id);
                intent.putExtra("cart_number",num);
                startActivity(intent);
                break;
            case R.id.tv_goods_detail_subtract:
                //todo 减
                //todo 判断数量是否小于等于最小库存
                if (num <= 1){
                    showNormalToast(ResUtil.getString(R.string.home_tab_26));
                    return;
                }
                num--;
                setNum();
                break;
            case R.id.tv_goods_detail_add:
                //todo 加
                //todo 判断数量是否达到最大库存
                if (num >= inventory){
                    showNormalToast(ResUtil.getString(R.string.home_tab_27));
                    return;
                }
                num++;
                setNum();

                break;
        }
    }

    private void setNum() {
        double  total = price * num;
        tvGoodsDetailNum.setText(num+"");
        tvGoodsDetailPrice.setText("￥"+total);
    }

    /**
     * 获取商品详情
     */
    @Override
    public void getGoodsDetail() {
        if (CheckNetwork.checkNetwork2(mContext)){
            baseAction.getGoodsDetail(id+"");
        }else {
            refreshLayout.finishRefresh();
        }
    }

    /**
     * 获取商品详情成功
     * @param goodsDetailDto
     */
    @Override
    public void getGoodsDetailSuccess(GoodsDetailDto goodsDetailDto) {
        loadDiss();
        refreshLayout.finishRefresh();
        GoodsDetailDto.DataBean dataBean = goodsDetailDto.getData();
        setBanner(dataBean.getImg());//设置图片轮播
        tvGoodsDetailName.setText(dataBean.getGoods_name());//商品名称
        tvGoodsDetailMoney.setText("￥"+dataBean.getPrice());//单价
        tvGoodsDetailPrice.setText("￥"+dataBean.getPrice());
        price = Double.parseDouble(dataBean.getPrice());
        num = 1;
        initViewPager(dataBean.getContent(),dataBean.getContent_param());//设置产品描述和基数

        GoodsDetailDto.DataBean.SpecBean specBean = dataBean.getSpec();
        setSpec(specBean);
        tvGoodsDetailEntrust.setVisibility(View.VISIBLE);
    }

    /**
     * 获取规格
     * @param specBean
     */
    private void setSpec(GoodsDetailDto.DataBean.SpecBean specBean) {
      try{
          List<GoodsDetailDto.DataBean.SpecBean.GoodsSkuBean> goodsSkuBeans = specBean.getGoods_sku();
          for (int i = 0; i <goodsSkuBeans.size() ; i++) {
              GoodsDetailDto.DataBean.SpecBean.GoodsSkuBean goodsSkuBean = goodsSkuBeans.get(i);
              String str = goodsSkuBean.getSku_attr();
              str = str.substring(str.indexOf("\"")+1 , str.indexOf("\"")+2);
              L.e("lgh_str","str  = "+str);
              List<GoodsDetailDto.DataBean.SpecBean.SpecAttrBean> list = specBean.getSpec_attr();
              for (int j = 0; j <list.size() ; j++) {
                  if (list.get(j).getSpec_id() == Integer.parseInt(str)){
                      List<GoodsDetailDto.DataBean.SpecBean.SpecAttrBean.ResBean> resBeans = list.get(j).getRes();
                      for (int k = 0; k < resBeans.size() ; k++) {
                          if (resBeans.get(k).getAttr_id()==Integer.parseInt(goodsSkuBean.getSku_attr1().substring(0 , goodsSkuBean.getSku_attr1().indexOf(",")))){
                              goodsSkuBean.setName(resBeans.get(k).getAttr_name());
                              break;
                          }
                      }
                      break;
                  }
              }
          }

          goodsSkuBeans.get(0).setClick(true);
          sku_id = goodsSkuBeans.get(0).getSku_id();
          inventory = goodsSkuBeans.get(0).getInventory();
          price = Double.parseDouble(goodsSkuBeans.get(0).getPrice());
          specListAdapter.refresh(goodsSkuBeans);

          tvGoodsDetailMoney.setText("￥"+price);//单价
          tvGoodsDetailPrice.setText("￥"+price);
      }catch (Exception e){

      }
    }

    /**
     * 失败
     * @param message
     * @param code
     */
    @Override
    public void onError(String message, int code) {
        loadDiss();
        refreshLayout.finishRefresh();
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
     * 设置图片轮播
     *
     * @param banners
     */
    private void setBanner(List<GoodsDetailDto.DataBean.ImgBean> banners) {
        //设置轮播图
        if (banners.size() != 0) {
            bannerMain.setVisibility(View.VISIBLE);
            imgs = new ArrayList<>();
            tips = new ArrayList<>();
            url = new ArrayList<>();
            for (int i = 0; i < banners.size(); i++) {
                GoodsDetailDto.DataBean.ImgBean bannersBean = banners.get(i);
                imgs.add(bannersBean.getPicture());
                tips.add("");
                url.add(bannersBean.getPicture());
            }
            bannerMain.setAutoPlayAble(true);
            bannerMain.setData(imgs, tips);
            bannerMain.startAutoPlay();
        }
    }

    /**
     * 初始化ViewPager
     * @param content 产品描述
     * @param content_param 基数
     */
    private void initViewPager(String content,String content_param) {
        setXRichText(content);
        this.content = content;
        this.content_param = content_param;
        fragments = new ArrayList<Fragment>();
        for (int i = 0; i < fragmentSize; i++) {
            switch (i) {
                case POIONTONE://产品描述
                    GoodesDetailFragment goodesDetailFragment_1 = new GoodesDetailFragment(content);
                    if (Position != POIONTONE) {
                        goodesDetailFragment_1.setUserVisibleHint(false);//
                    }

                    fragments.add(goodesDetailFragment_1);
                    break;
                case POIONTTWO://基数
                    GoodesDetailFragment goodesDetailFragment_2 = new GoodesDetailFragment(content_param);
                    if (Position != POIONTTWO) {
                        goodesDetailFragment_2.setUserVisibleHint(false);//
                    }

                    fragments.add(goodesDetailFragment_2);
                    break;
                default:
                    break;
            }
        }

        fragmentPagerAdapter = new MyFragmentPagerAdapter(
                getSupportFragmentManager(), fragments);

        fragmentPagerAdapter.setFragments(fragments);
        setSelectedLin(Position);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewPager.setAdapter(fragmentPagerAdapter);
                viewPager.setCurrentItem(Position, false);
                viewPager.setOffscreenPageLimit(fragmentSize);


            }
        }, 500);

    }

    @OnTouch({R.id.tv_type_1, R.id.tv_type_2})
    public boolean onTouch(View v) {
        switch (v.getId()) {
            case R.id.tv_type_1:
                Position = POIONTONE;
                break;
            case R.id.tv_type_2:
                Position = POIONTTWO;
                break;
            default:
                break;
        }
        setSelectedLin(Position);
        viewPager.setCurrentItem(Position, false);
        return false;
    }

    /**
     * 选择
     *
     * @param position
     */
    private void setSelectedLin(int position) {
        tvType1.setSelected(false);
        tvType2.setSelected(false);
        switch (position) {
            case 0:
                tvType1.setSelected(true);
                setXRichText(content);
                break;
            case 1:
                tvType2.setSelected(true);
                setXRichText(content_param);
                break;
            default:
                break;
        }
    }


    private void setXRichText(String text){
        try {
            xRichText

                    .callback(new XRichText.BaseClickCallback() {
                        @Override
                        public boolean onLinkClick(String url) {
                            return true;
                        }

                        @Override
                        public void onImageClick(List<String> urlList, int position) {
                            super.onImageClick(urlList, position);
                            //todo 图片点击事件
                        }

                        @Override
                        public void onFix(XRichText.ImageHolder holder) {
                            super.onFix(holder);
                            //仅仅是文本的话不会进这边 holder.getPosition()
//                                choseRlLoadingMusic.setVisibility(View.VISIBLE);
                            L.e("XRichText", "w " + holder.getWidth() + " h " + holder.getHeight());
                            holder.setStyle(XRichText.Style.CENTER);
                            L.e("XRichText2", "w " + holder.getWidth() + " h " + holder.getHeight());
                            //设置宽高
                        }

                    })
                    .imageDownloader(new ImageLoader() {
                        @Override
                        public Bitmap getBitmap(String url) throws IOException {
                            L.e("lgh", "url  = " + url);
                            try {
                                Bitmap myBitmap = GlideApp.with(mContext)
                                        .asBitmap() //必须
                                        .load(url)
                                        .placeholder(R.mipmap.icon_info)
                                        .error(R.mipmap.icon_info)
                                        .submit()
                                        .get();
                                int screen_width = getWindowManager().getDefaultDisplay().getWidth();
                                int image_width = myBitmap.getWidth();
                                int image_height = myBitmap.getHeight();
                                int widget_height = screen_width * image_height / image_width;
                                myBitmap = Bitmap.createScaledBitmap(myBitmap, screen_width, widget_height, false);
//
                                return myBitmap;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return BitmapFactory.decodeResource(getResources(), R.mipmap.icon_info);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                                return BitmapFactory.decodeResource(getResources(), R.mipmap.icon_info);
                            }
                        }
                    })
                    .text(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
