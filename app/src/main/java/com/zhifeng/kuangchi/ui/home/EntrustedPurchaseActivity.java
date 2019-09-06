package com.zhifeng.kuangchi.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.zhifeng.kuangchi.BuildConfig;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.EntrustedPurchaseAction;
import com.zhifeng.kuangchi.adapter.CoinListAdapter;
import com.zhifeng.kuangchi.module.BalanceDto;
import com.zhifeng.kuangchi.module.GeneralDto;
import com.zhifeng.kuangchi.module.post.EntrustedPurchasePost;
import com.zhifeng.kuangchi.ui.impl.EntrustedPurchaseView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;
import com.zhifeng.kuangchi.util.dialog.PicturesDialog;
import com.zhifeng.kuangchi.util.imageloader.GlideImageLoader;
import com.zhifeng.kuangchi.util.photo.PicUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @ClassName: 委托购买
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/2 17:07
 * @Version: 1.0
 */

public class EntrustedPurchaseActivity extends UserBaseActivity<EntrustedPurchaseAction> implements EntrustedPurchaseView {

    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    public static final int REQUEST_CODE_TAKE = 102;
    public static final int REQUEST_CODE_ALBUM = 103;
    public static int REQUEST_SELECT_TYPE = -1;//选择的类型
    private ArrayList<ImageItem> selImageList = new ArrayList<>(); //当前选择的所有图片
    ArrayList<ImageItem> images = null;

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.iv_coin)
    ImageView ivCoin;
    @BindView(R.id.et_coin_other)
    EditText etCoinOther;
    @BindView(R.id.ll_other)
    LinearLayout llOther;
    @BindView(R.id.et_coin_address)
    EditText etCoinAddress;
    @BindView(R.id.iv_order_img)
    ImageView ivOrderImg;
    CoinListAdapter coinListAdapter;

    int sku_id;
    int cart_number;
    String coinType;
    int pay_type;
    boolean isImg = false;

    boolean isOther = false;

    @Override
    public int intiLayout() {
        return R.layout.activity_entrusted_purchase;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected EntrustedPurchaseAction initAction() {
        return new EntrustedPurchaseAction(this, this);
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
                .addTag("EntrustedPurchaseActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.home_tab_24));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        sku_id = getIntent().getIntExtra("sku_id",0);
        cart_number = getIntent().getIntExtra("cart_number",0);

        coinListAdapter = new CoinListAdapter();
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(coinListAdapter);

        getBalance();
        initImagePicker();
        loadView();
    }

    @Override
    protected void loadView() {
        super.loadView();
        //todo 列表item点击事件监听
        coinListAdapter.setOnClickListener(new CoinListAdapter.OnClickListener() {
            @Override
            public void onClick(String address, int id,int coinType) {
                isOther = false;
                setOther();
                List<BalanceDto.DataBean.CoinAddressBean> list = coinListAdapter.getAllData();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setClick(list.get(i).getId() == id);
                }
                coinListAdapter.notifyDataSetChanged();
                etCoinAddress.setText(address);//todo 地址
            }
        });
    }

    /**
     *是否选择其他
     */
    private void setOther() {
        if(isOther){
            ivCoin.setImageResource(R.mipmap.icon_coin_y);
            List<BalanceDto.DataBean.CoinAddressBean> list = coinListAdapter.getAllData();
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setClick(false);
            }
            coinListAdapter.notifyDataSetChanged();
        }else {
            ivCoin.setImageResource(0);
        }
    }

    /**
     * 获取币类型
     */
    @Override
    public void getBalance() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getBalance();
        }
    }

    @Override
    public void getBalanceSuccess(BalanceDto balanceDto) {
        loadDiss();
        BalanceDto.DataBean dataBean = balanceDto.getData();

        List<BalanceDto.DataBean.CoinAddressBean> list = balanceDto.getData().getCoin_address();
        list.get(0).setClick(true);
        coinListAdapter.refresh(list);
        pay_type = list.get(0).getPay_type();
        etCoinAddress.setText(list.get(0).getAddress());//todo 地址
    }

    /**
     * 委托支付
     * @param entrustedPurchasePost
     */
    @Override
    public void EntrustedPurchase(EntrustedPurchasePost entrustedPurchasePost) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.EntrustedPurchase(entrustedPurchasePost);
        }
    }

    /**
     * 委托支付成功
     * @param generalDto
     */
    @Override
    public void EntrustedPurchaseSuccess(GeneralDto generalDto) {
        loadDiss();
        showNormalToast(generalDto.getMsg());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    /**
     * 未实名认证  购买失败
     */
    @Override
    public void EntrustedPurchaseError(String msg) {
        loadDiss();
        showNormalToast(msg);
    }

    /**
     * 失败
     * @param message
     * @param code
     */
    @Override
    public void onError(String message, int code) {
        loadDiss();
        showNormalToast(message);
    }

    @OnClick({R.id.iv_order_img, R.id.tv_confirm,R.id.ll_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_order_img:
                //上传凭证
                showSelectDiaLog();
//                takeUserGally();
                break;
            case R.id.tv_confirm:
                //todo 确认提交
                confirm();
                break;
            case R.id.ll_other:
                //todo 选择其他
                isOther = true;
                setOther();
                break;
        }
    }

    /**
     * 提交前的校验
     */
    private void confirm()  {
        EntrustedPurchasePost entrustedPurchasePost = new EntrustedPurchasePost();
        entrustedPurchasePost.setSku_id(sku_id);//sku_id
        entrustedPurchasePost.setCart_number(cart_number);//商品数量
        entrustedPurchasePost.setPay_type(pay_type);

        try {
            if (!isImg){
                showNormalToast(ResUtil.getString(R.string.home_tab_25));
                return;
            }

            entrustedPurchasePost.setProof_pic("data:image/gif;base64,"+PicUtils.bitmapToString(images.get(0).path));
            EntrustedPurchase(entrustedPurchasePost);
        } catch (Exception e) {
            e.printStackTrace();
        }


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
     * 选择图片
     */
    private void showSelectDiaLog() {
        PicturesDialog dialog = new PicturesDialog(this, R.style.MY_AlertDialog);
        dialog.setOnClickListener(new PicturesDialog.OnClickListener() {
            @Override
            public void onCamera() {
                takePhoto();
            }

            @Override
            public void onPhoto() {
                takeUserGally();
            }
        });
        dialog.show();
    }

    private void takePhoto() {
        /**
         * 0.4.7 目前直接调起相机不支持裁剪，如果开启裁剪后不会返回图片，请注意，后续版本会解决
         *
         * 但是当前直接依赖的版本已经解决，考虑到版本改动很少，所以这次没有上传到远程仓库
         *
         * 如果实在有所需要，请直接下载源码引用。
         */
        REQUEST_SELECT_TYPE = REQUEST_CODE_TAKE;
        //打开选择,本次允许选择的数量
        ImagePicker.getInstance().setSelectLimit(1);
        Intent intent = new Intent(mContext, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    /**
     * 打开相册
     */
    private void takeUserGally() {
        //打开选择,本次允许选择的数量

        REQUEST_SELECT_TYPE = REQUEST_CODE_ALBUM;
        ImagePicker.getInstance().setSelectLimit(1);
        Intent intent1 = new Intent(mContext, ImageGridActivity.class);
        /* 如果需要进入选择的时候显示已经选中的图片，
         * 详情请查看ImagePickerActivity
         * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
        startActivityForResult(intent1, REQUEST_CODE_SELECT);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setMultiMode(false);
        imagePicker.setSaveRectangle(true);
        imagePicker.setSelectLimit(1);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(400);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(400);                         //保存文件的高度。单位像素
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            L.e("xx", "添加图片返回....");
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                L.e("xx", REQUEST_SELECT_TYPE + "返回的图片 数量 " + images.size());
                switch (REQUEST_SELECT_TYPE) {
                    case REQUEST_CODE_ALBUM:
                    case REQUEST_CODE_TAKE:
                        if (images != null) {
                            selImageList.addAll(images);

                            if (CheckNetwork.checkNetwork2(mContext)) {
                                File imgUri = new File(images.get(0).path);
                                Uri dataUri = FileProvider.getUriForFile
                                        (this, BuildConfig.APPLICATION_ID + ".android7.fileprovider", imgUri);
                                int zoomSacle = 3;
                                try {
                                    // 当图片大小大于512kb至少缩小两倍
                                    if (imgUri.length() / 1024 > 512) {
                                        zoomSacle = zoomSacle * 10;
                                    }
//
//                                    //todo  显示图片
                                    L.e("lgh", "images.get(0).path  = " + images.get(0).path);
                                    isImg = true;
                                    GlideUtil.setImage(mContext,images.get(0).path,ivOrderImg,0);
                                    L.e("lgh_img","img  = "+images.get(0).path);
                                } catch (Exception e) {
                                    loadError(ResUtil.getString(R.string.my_tab_120), mContext);
                                }
                            }
                        }
                        break;
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            L.e("xx", "预览图片返回....");
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {

            }
        }

    }

    /**********************************修改头像 end*********************************************/
}
