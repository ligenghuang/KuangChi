package com.zhifeng.kuangchi.ui.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.data.ResUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.zhifeng.kuangchi.BuildConfig;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.BalanceAction;
import com.zhifeng.kuangchi.adapter.CoinListAdapter;
import com.zhifeng.kuangchi.module.BalanceDto;
import com.zhifeng.kuangchi.module.CoinRateDto;
import com.zhifeng.kuangchi.module.GeneralDto;
import com.zhifeng.kuangchi.module.post.GetCoinPost;
import com.zhifeng.kuangchi.module.post.PutCoinPost;
import com.zhifeng.kuangchi.ui.impl.BalanceView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;
import com.zhifeng.kuangchi.util.dialog.PicturesDialog;
import com.zhifeng.kuangchi.util.dialog.PutDialog;
import com.zhifeng.kuangchi.util.dialog.PwdDialog;
import com.zhifeng.kuangchi.util.imageloader.GlideImageLoader;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * @ClassName: 我的余额
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/31 11:10
 * @Version: 1.0
 */

public class BalanceActivity extends UserBaseActivity<BalanceAction> implements BalanceView {

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
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_balance_put)
    TextView tvBalancePut;
    @BindView(R.id.tv_balance_get)
    TextView tvBalanceGet;
    @BindView(R.id.tv_balance_type)
    TextView tvBalanceType;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_balance_lines)
    TextView tvBalanceLines;
    @BindView(R.id.ll_balance_lines)
    LinearLayout llBalanceLines;
    @BindView(R.id.et_balance)
    EditText etBalance;
    @BindView(R.id.tv_balance_address)
    TextView tvBalanceAddress;
    @BindView(R.id.tv_balance_money)
    TextView tvBalanceMoney;
    @BindView(R.id.ll_balance_money)
    LinearLayout llBalanceMoney;
    @BindView(R.id.tv_type)
    TextView typeTv;


    public static int Type = 0;
    private final int POIONTONE = 0;
    private final int POIONTTWO = 1;

    CoinListAdapter coinListAdapter;

    double rate;
    double money = 0;
    double inputMoney = 0;
    String coin_type;
    String Address;

    PwdDialog pwdDialog;
    PutDialog putDialog;


    @Override
    public int intiLayout() {
        return R.layout.activity_balance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected BalanceAction initAction() {
        return new BalanceAction(this, this);
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
                .addTag("BalanceActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_tab_7));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        coinListAdapter = new CoinListAdapter();
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(coinListAdapter);

        initImagePicker();
        getBalance();
        loadView();
    }

    /**
     * 选择图片
     */
    public void showSelectDiaLog() {
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

    /**
     * 相机
     */
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
    protected void loadView() {
        super.loadView();

        etBalance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(etBalance.getText().toString())){
                    //todo 根据转换率计算金额
                    money = Double.parseDouble(etBalance.getText().toString());
                    inputMoney = money * rate;
                    L.e("lgh_rate","money  = "+inputMoney);
                    L.e("lgh_rate","rate  = "+rate);
                    llBalanceMoney.setVisibility(View.VISIBLE);
                    tvBalanceMoney.setText(inputMoney+"");
                }else{
                    llBalanceMoney.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        //todo 列表item点击事件监听
        coinListAdapter.setOnClickListener(new CoinListAdapter.OnClickListener() {
            @Override
            public void onClick(String address, int id,int coinType) {
                List<BalanceDto.DataBean.CoinAddressBean> list = coinListAdapter.getAllData();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setClick(list.get(i).getId() == id);
                }
                coinListAdapter.notifyDataSetChanged();
                tvBalanceAddress.setText(address);//todo 地址
                //todo 获取转换率
                getRate(coinType+"");
                coin_type = coinType+"";
                Address = address;
            }
        });
    }

    @OnClick(R.id.tv_balance_submit)
    void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_balance_submit:
                //todo 提交
                if (Type == POIONTONE){
                    //todo 充币
                    PutCoin();
                }else if (Type == POIONTTWO){
                   //todo 提币
                    GetCoin();
                }
                break;
        }
    }

    /**
     * 充币校验
     */
    private void PutCoin() {
        if (money == 0){
            showNormalToast(ResUtil.getString(R.string.my_tab_112_1));
            return;
        }
        putDialog = new PutDialog(mContext, R.style.MY_AlertDialog,mActicity);
        putDialog.setOnClickListener(new PutDialog.OnClickListener() {
            @Override
            public void onClick(String base64) {
                PutCoinPost putCoinPost = new PutCoinPost();
                putCoinPost.setAddress(Address);
                putCoinPost.setCoin_type(coin_type);
                putCoinPost.setInput_money(inputMoney);
                putCoinPost.setMoney(money+"");
                putCoinPost.setProof_pic("data:image/gif;base64,"+base64);
                putCoin(putCoinPost);
            }

            @Override
            public void TakeUserGally() {
                showSelectDiaLog();
            }
        });
        putDialog.show();
    }

    /**
     * 提币校验
     */
    private void GetCoin() {
        if (money == 0){
            showNormalToast(ResUtil.getString(R.string.my_tab_112));
            return;
        }
        pwdDialog = new PwdDialog(mContext, R.style.MY_AlertDialog);
        pwdDialog.setOnFinishInput(new PwdDialog.OnFinishInput() {
            @Override
            public void inputFinish(String password) {
                GetCoinPost getCoinPost = new GetCoinPost();
                getCoinPost.setAddress(Address);//地址
                getCoinPost.setCoin_type(coin_type);//币类型
                getCoinPost.setMoney(money+"");//币数量
                getCoinPost.setInput_money(inputMoney);//输入金额
                getCoinPost.setPassword(password);//密码
                //todo 请求接口
                getCoin(getCoinPost);
            }
        });

        pwdDialog.show();

    }


    /**
     * 获取我的余额
     */
    @Override
    public void getBalance() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getBalance();
        }
    }

    /**
     * 获取我的余额成功
     */
    @Override
    public void getBalanceSuccess(BalanceDto balanceDto) {
        loadDiss();
        BalanceDto.DataBean dataBean = balanceDto.getData();
        tvBalance.setText(dataBean.getRemainder_money());//余额
        tvBalanceLines.setText(dataBean.getWithdrawal_lines());//单日交易额

        Type = POIONTONE;
        setSelectedLin(Type);

        List<BalanceDto.DataBean.CoinAddressBean> list = balanceDto.getData().getCoin_address();
        list.get(0).setClick(true);
        coinListAdapter.refresh(list);
        coin_type = list.get(0).getPay_type()+"";
        Address = list.get(0).getAddress();
        getRate(coin_type);
        tvBalanceAddress.setText(Address);

    }

    /**
     * 提币
     * @param getCoinPost
     */
    @Override
    public void getCoin(GetCoinPost getCoinPost) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.getCoin(getCoinPost);
        }
    }

    /**
     * 提币成功
     */
    @Override
    public void getCoinSuccess(GeneralDto generalDto) {
        loadDiss();
        etBalance.setText("");
        llBalanceMoney.setVisibility(View.GONE);
        if (pwdDialog != null){
            pwdDialog.dismiss();
        }
    }

    /**
     * 充币
     * @param putCoinPost
     */
    @Override
    public void putCoin(PutCoinPost putCoinPost) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.putCoin(putCoinPost);
        }
    }

    /**
     * 充币成功
     */
    @Override
    public void putCoinSuccess(GeneralDto generalDto) {
        loadDiss();
        etBalance.setText("");
        llBalanceMoney.setVisibility(View.GONE);
        if (putDialog != null){
            putDialog.dismiss();
        }
    }

    /**
     * 获取币转换率
     * @param coinType
     */
    @Override
    public void getRate(String coinType) {
        if (CheckNetwork.checkNetwork2(mContext)){
            baseAction.getRate(coinType);
        }
    }

    /**
     * 获取币转换率成功
     * @param coinRateDto
     */
    @Override
    public void getRateSuccess(CoinRateDto coinRateDto) {
        rate = Double.valueOf(coinRateDto.getData().getRate());
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
     * 点击事件监听
     *
     * @param view
     */
    @OnClick(R.id.tv_balance_submit)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_balance_submit:
                switch (Type) {
                    case POIONTONE:
                        //todo 充币
                        break;
                    case POIONTTWO:
                        //todo 提币
                        break;
                }
                break;
        }
    }


    @OnTouch({R.id.tv_balance_put, R.id.tv_balance_get})
    public boolean onTouch(View v) {
        int i = v.getId();

        switch (i) {
            case R.id.tv_balance_put:
                Type = POIONTONE;
                break;
            case R.id.tv_balance_get:
                Type = POIONTTWO;
                break;
        }
        setSelectedLin(Type);
        return false;
    }

    /**
     * 选择
     *
     * @param position
     */
    private void setSelectedLin(int position) {
        tvBalancePut.setSelected(false);
        tvBalanceGet.setSelected(false);
        llBalanceLines.setVisibility(View.GONE);
        String text = ResUtil.getString(R.string.my_tab_66);
        String text1 = ResUtil.getString(R.string.my_tab_69_2);
        switch (position) {
            case 0:
                //todo 充币
                tvBalancePut.setSelected(true);
                llBalanceLines.setVisibility(View.VISIBLE);
                break;
            case 1:
                //todo 提币
                tvBalanceGet.setSelected(true);
                text = ResUtil.getString(R.string.my_tab_67);
                text1 = ResUtil.getString(R.string.my_tab_69);
                break;

            default:
                break;
        }
        tvBalanceType.setText(text);
        typeTv.setText(text1);
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
//                                    GlideUtil.setImageCircle(mContext,images.get(0).path,isPortrait?userPortaitIv:userCertificateIv,0);
                                   if (putDialog != null){
                                       putDialog.setImg(images.get(0).path);
                                   }
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

    /**********************************上传凭证 end*********************************************/
}
