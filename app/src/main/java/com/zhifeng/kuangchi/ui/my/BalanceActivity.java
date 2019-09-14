package com.zhifeng.kuangchi.ui.my;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.FormatUtils;
import com.lgh.huanglib.util.data.ResUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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
import com.zhifeng.kuangchi.util.data.MySp;
import com.zhifeng.kuangchi.util.dialog.PicturesDialog;
import com.zhifeng.kuangchi.util.imageloader.GlideImageLoader;
import com.zhifeng.kuangchi.util.photo.PicUtils;

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
    @BindView(R.id.et_balance_pay_pwd)
    EditText etBalancePayPwd;
    @BindView(R.id.et_balance_pay_code)
    EditText etBalancePayCode;
    @BindView(R.id.tv_login_get_code)
    TextView tvLoginGetCode;
    @BindView(R.id.ll_get_coin)
    LinearLayout llGetCoin;
    @BindView(R.id.tv_balance_submit)
    TextView tvBalanceSubmit;
    @BindView(R.id.iv_put_coin)
    ImageView ivPutCoin;
    @BindView(R.id.iv_coin)
    ImageView ivCoin;
    @BindView(R.id.iv_coin_2)
    ImageView ivCoin2;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
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
    EditText tvBalanceAddress;
    @BindView(R.id.tv_balance_money)
    TextView tvBalanceMoney;
    @BindView(R.id.ll_balance_money)
    LinearLayout llBalanceMoney;
    @BindView(R.id.tv_type)
    TextView typeTv;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;


    public static int Type = 0;
    private final int POIONTONE = 0;
    private final int POIONTTWO = 1;

    CoinListAdapter coinListAdapter;
    List<BalanceDto.DataBean.CoinAddressBean>  putList = new ArrayList<>();
    List<BalanceDto.DataBean.CoinAddressBean>  getList = new ArrayList<>();

    double rate;
    double money = 0;
    double num = 0;
    double inputMoney = 0;
    String coin_type;
    String Address;
    String name;

    boolean isPic = false;

    //获取验证码倒计时
    private MyCountDownTimer timer;


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
        refreshLayout.setEnableLoadMore(false);
        Type = POIONTONE;
        coinListAdapter = new CoinListAdapter();
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(coinListAdapter);
        timer = new MyCountDownTimer(60000, 1000);
        initImagePicker();
        loadDialog();
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

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getBalance();
            }
        });

        etBalance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(etBalance.getText().toString())) {
                    //todo 根据转换率计算金额
                    money = Double.parseDouble(etBalance.getText().toString());

                    inputMoney = money;
                    L.e("lgh_rate", "money  = " + inputMoney);
                    L.e("lgh_rate", "rate  = " + rate);
//                    llBalanceMoney.setVisibility(View.VISIBLE);
//                    tvBalanceMoney.setText(inputMoney + "");
                } else {
//                    tvBalanceMoney.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        //todo 列表item点击事件监听
        coinListAdapter.setOnClickListener(new CoinListAdapter.OnClickListener() {
            @Override
            public void onClick(String address, int id, int coinType, int res, double user_money, double Rate,String Name) {
                List<BalanceDto.DataBean.CoinAddressBean> list = coinListAdapter.getAllData();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setClick(list.get(i).getId() == id);
                }
                coinListAdapter.notifyDataSetChanged();
                if (Type == POIONTONE){
                    tvBalanceAddress.setText(address);//todo 地址
                }
                //todo 获取转换率
//                getRate(coinType + "");
                rate = Rate;
                coin_type = coinType + "";
                Address = address;
                ivCoin.setImageResource(res);
                ivCoin2.setImageResource(res);
                tvBalance.setText(user_money + "");
                tvBalanceLines.setText(Rate + "");//单日交易额
                name = Name;
            }
        });
    }

    @OnClick({R.id.tv_balance_submit, R.id.tv_login_get_code, R.id.iv_put_coin,R.id.tv_balance_address})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_balance_submit:
                //todo 提交
                if (Type == POIONTONE) {
                    //todo 充币
                    putBalanceCoin();
                } else if (Type == POIONTTWO) {
                    //todo 提币
                    GetBalanceCoin();
                }
                break;
            case R.id.tv_login_get_code:
                //todo 获取验证码
                getAuthCode();
                break;
            case R.id.iv_put_coin:
                //todo 上传凭证
                showSelectDiaLog();
                break;
            case R.id.tv_balance_address:
                if (Type == POIONTONE){
                    Copy();
                }
                break;
        }
    }

    /**
     * 提币
     */
    private void GetBalanceCoin() {

        if (money == 0) {
            showNormalToast(ResUtil.getString(R.string.my_tab_112_1));
            return;
        }

        switch (name) {
            case "USDT":
                if (money < 50) {
                    showNormalToast("USDT提币数量≥50个");
                }
                break;
            case "LAMB":
                if (money < 100) {
                    showNormalToast("lambda提币数量≥100个");
                }
                break;
        }

        if (TextUtils.isEmpty(tvBalanceAddress.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.my_tab_155));
            return;
        }


        if (TextUtils.isEmpty(etBalancePayPwd.getText().toString())) {
            showNormalToast(ResUtil.getString(R.string.my_tab_149));
            return;
        }
        String pwd = etBalancePayPwd.getText().toString();

        if (TextUtils.isEmpty(etBalancePayCode.getText().toString())) {
            showNormalToast(ResUtil.getString(R.string.my_tab_151));
            return;
        }

        String code = etBalancePayCode.getText().toString();


        GetCoinPost getCoinPost = new GetCoinPost();
        getCoinPost.setAddress(tvBalanceAddress.getText().toString());//地址
        getCoinPost.setCoin_type(coin_type);//币类型
        getCoinPost.setMoney(money + "");//币数量
        getCoinPost.setInput_money(inputMoney);//输入金额
        getCoinPost.setPassword(pwd);//密码
        getCoinPost.setVerify_code(code);//验证码
        //todo 请求接口
        getCoin(getCoinPost);
    }

    /**
     * 充币
     */
    private void putBalanceCoin() {
        if (money == 0) {
            showNormalToast(ResUtil.getString(R.string.my_tab_112_1));
            return;
        }
        //todo 请上传凭证
        if (!isPic) {
            showNormalToast(ResUtil.getString(R.string.home_tab_25));
            return;
        }
        //todo 图片路径
        String base64 = PicUtils.bitmapToString(images.get(0).path);

        PutCoinPost putCoinPost = new PutCoinPost();
        putCoinPost.setAddress(Address);
        putCoinPost.setCoin_type(coin_type);
        putCoinPost.setInput_money(inputMoney);
        putCoinPost.setMoney(money + "");
        putCoinPost.setProof_pic("data:image/gif;base64," + base64);
        putCoin(putCoinPost);
    }


    /**
     * 获取我的余额
     */
    @Override
    public void getBalance() {
        if (CheckNetwork.checkNetwork2(mContext)) {

            baseAction.getBalance();
        }
    }

    /**
     * 获取我的余额成功
     */
    @Override
    public void getBalanceSuccess(BalanceDto balanceDto) {
        loadDiss();
        refreshLayout.finishRefresh();
        BalanceDto.DataBean dataBean = balanceDto.getData();
        tvBalance.setText(dataBean.getRemainder_money());//余额
        getList = new ArrayList<>();
        putList = new ArrayList<>();
        getList = balanceDto.getData().getCoin_address();

        for (int i = 0; i < balanceDto.getData().getCoin_address().size(); i++) {
            if (balanceDto.getData().getCoin_address().get(i).getPay_type() != 5){
                putList.add(balanceDto.getData().getCoin_address().get(i));
            }
        }

        setSelectedLin(Type);

    }

    private void setList(List<BalanceDto.DataBean.CoinAddressBean> list){
//        List<BalanceDto.DataBean.CoinAddressBean> list = balanceDto.getData().getCoin_address();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setClick(i==0);
        }
        coinListAdapter.refresh(list);
        coin_type = list.get(0).getPay_type() + "";
        Address = list.get(0).getAddress();
//        getRate(coin_type);

        if (Type == POIONTONE){
            tvBalanceAddress.setText(Address);
        }
        tvBalance.setText(list.get(0).getUser_money() + "");
        tvBalanceLines.setText(list.get(0).getHeigt_limit() + "");//单日交易额
        name = list.get(0).getCoin_name();
        switch (list.get(0).getCoin_name()) {
            case "LAMB":
                ivCoin.setImageResource(R.mipmap.icon_coin);
                ivCoin2.setImageResource(R.mipmap.icon_coin);
                rate = list.get(0).getTo_usdt();
                break;
            case "USDT":
                ivCoin.setImageResource(R.mipmap.usdt);
                ivCoin2.setImageResource(R.mipmap.usdt);
                rate = 1;
                break;
        }
    }

    /**
     * 获取验证码
     */
    @Override
    public void getAuthCode() {
        if (CheckNetwork.checkNetwork2(mContext)) {
            loadDialog();
            baseAction.getAuthCode(MySp.getUserPhone(mContext));
        }
    }

    /**
     * 获取验证码成功
     *
     * @param generalDto
     */
    @Override
    public void getAuthCodeSuccess(String generalDto) {
        loadDiss();
        showNormalToast(generalDto);
        //todo 启动计时器
        if (timer != null) {
            timer.cancel();
        }
        timer.start();
    }

    /**
     * 提币
     *
     * @param getCoinPost
     */
    @Override
    public void getCoin(GetCoinPost getCoinPost) {
        if (CheckNetwork.checkNetwork2(mContext)) {
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
        showNormalToast("提币成功");
        getBalance();
        etBalance.setText("");
        tvBalanceMoney.setText("");
        etBalancePayCode.setText("");
        etBalancePayPwd.setText("");

        if (timer != null) {
            timer.cancel();
        }
        tvLoginGetCode.setEnabled(true);
        tvLoginGetCode.setSelected(false);
        tvLoginGetCode.setText(R.string.login_tab_5);
    }

    /**
     * 充币
     *
     * @param putCoinPost
     */
    @Override
    public void putCoin(PutCoinPost putCoinPost) {
        if (CheckNetwork.checkNetwork2(mContext)) {
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
        showNormalToast("充币成功");
        getBalance();
        etBalance.setText("");
        tvBalanceMoney.setText("");
        ivPutCoin.setImageResource(R.mipmap.icon_add);
        isPic = false;
        images = new ArrayList<>();
    }

    /**
     * 获取币转换率
     *
     * @param coinType
     */
    @Override
    public void getRate(String coinType) {
        if (CheckNetwork.checkNetwork2(mContext)) {
            baseAction.getRate(coinType);
        }
    }

    /**
     * 获取币转换率成功
     *
     * @param coinRateDto
     */
    @Override
    public void getRateSuccess(CoinRateDto coinRateDto) {
        rate = Double.valueOf(coinRateDto.getData().getRate());
        name = coinRateDto.getData().getCoin_name();
        if (Type == POIONTTWO) {
            switch (coinRateDto.getData().getCoin_name()) {
                case "USDT":
                    num = 50;
                    showNormalToast("USDT提币数量≥50个");
                    break;
                case "LAMB":
                    num = 100;
                    showNormalToast("lambda提币数量≥100个");
                    break;
            }
        }
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
        llBalanceLines.setVisibility(View.VISIBLE);
        llGetCoin.setVisibility(View.GONE);
        ivPutCoin.setVisibility(View.VISIBLE);
        tvTime.setVisibility(View.GONE);
        String text = ResUtil.getString(R.string.my_tab_66);
        String text1 = ResUtil.getString(R.string.my_tab_69_2);
        tvAddress.setText(ResUtil.getString(R.string.my_tab_71));
        tvBalanceAddress.setFocusable(false);
        tvBalanceAddress.setFocusableInTouchMode(false);
        switch (position) {
            case 0:
                //todo 充币
                tvBalancePut.setSelected(true);
                llBalanceLines.setVisibility(View.GONE);
                tvTime.setVisibility(View.VISIBLE);
                setList(putList);
                break;
            case 1:
                //todo 提币
                tvBalanceGet.setSelected(true);
                llGetCoin.setVisibility(View.VISIBLE);
                ivPutCoin.setVisibility(View.GONE);
                tvBalanceAddress.setFocusableInTouchMode(true);
                tvBalanceAddress.setFocusable(true);
                tvBalanceAddress.requestFocus();
                tvBalanceAddress.setText("");

                tvAddress.setText(ResUtil.getString(R.string.my_tab_71_1));
                text = ResUtil.getString(R.string.my_tab_67);
                text1 = ResUtil.getString(R.string.my_tab_69);
                setList(getList);
                break;

            default:
                break;
        }
        tvBalanceType.setText(text);
        typeTv.setText(text1);
        coinListAdapter.setType(Type);
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
                                    GlideUtil.setImage(mContext, images.get(0).path, ivPutCoin, R.mipmap.icon_add);
                                    isPic = true;
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

    /**************************************计时器 start*******************************************/
    class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub

        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            tvLoginGetCode.setEnabled(false);
            tvLoginGetCode.setSelected(true);
            tvLoginGetCode.setText(FormatUtils.format(getString(R.string.login_tab_6), millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            tvLoginGetCode.setEnabled(true);
            tvLoginGetCode.setSelected(false);
            tvLoginGetCode.setText(R.string.login_tab_5);
        }
    }
/*****************************************计时器 end**************************************************/

    /**
     * 复制邀请码
     */
    private void Copy(){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", tvBalanceAddress.getText().toString());
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        //复制成功提示
        showNormalToast(ResUtil.getString(R.string.my_tab_18));
    }
}
