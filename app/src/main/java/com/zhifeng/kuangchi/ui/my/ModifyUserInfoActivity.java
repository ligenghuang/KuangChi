package com.zhifeng.kuangchi.ui.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

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
import com.zhifeng.kuangchi.actions.ModifyUserInfoAction;
import com.zhifeng.kuangchi.module.GeneralDto;
import com.zhifeng.kuangchi.ui.impl.ModifyUserInfoView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;
import com.zhifeng.kuangchi.util.dialog.PicturesDialog;
import com.zhifeng.kuangchi.util.imageloader.GlideImageLoader;
import com.zhifeng.kuangchi.util.photo.PicUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: 修改头像与昵称
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/9/4 17:42
 * @Version: 1.0
 */

public class ModifyUserInfoActivity extends UserBaseActivity<ModifyUserInfoAction> implements ModifyUserInfoView {

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
    @BindView(R.id.iv_user_img)
    ImageView ivUserImg;
    @BindView(R.id.tv_user_avatar)
    TextView tvUserAvatar;
    @BindView(R.id.et_modify_name)
    EditText etModifyName;

    String path;
    String newPath;
    String name;

    @Override
    public int intiLayout() {
        return R.layout.activity_modify_user_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected ModifyUserInfoAction initAction() {
        return new ModifyUserInfoAction(this,this);
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
                .addTag("ModifyUserInfoActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.my_tab_143));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        initImagePicker();

        name = getIntent().getStringExtra("name");
        path = getIntent().getStringExtra("avatar");
        etModifyName.setText(name);
        GlideUtil.setImageCircle(mContext,path,ivUserImg,R.mipmap.icon_avatar);
    }

    @OnClick({R.id.tv_confirm,R.id.iv_user_img,R.id.tv_user_avatar})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_confirm:
                //修改昵称
                confirm();
                break;
            case R.id.iv_user_img:
            case R.id.tv_user_avatar:
                showSelectDiaLog();
                break;
        }
    }

    /**
     * 提交校验
     */
    private void confirm() {
        if (TextUtils.isEmpty(etModifyName.getText().toString())){
            showNormalToast(ResUtil.getString(R.string.my_tab_144));
            return;
        }
        if (name.equals(etModifyName.getText().toString())){
            finish();
        }else {
            ModifyUserName(etModifyName.getText().toString());
        }

    }

    /**
     * 修改昵称
     * @param name
     */
    @Override
    public void ModifyUserName(String name) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.ModifyUserName(name);
        }
    }

    /**
     * 修改昵称成功
     * @param generalDto
     */
    @Override
    public void ModifyUserNameSuccess(GeneralDto generalDto) {
        loadDiss();
        showNormalToast(ResUtil.getString(R.string.my_tab_142));
        new Handler().postDelayed(new Runnable() {
            @Override
        public void run() {


            finish();
        }
    }, 2000);
    }

    /**
     * 修改头像
     * @param path
     */
    @Override
    public void ModifyUserAvatar(String path) {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            baseAction.ModifyUserAvatar(path);
        }
    }

    /**
     * 修改头像成功
     * @param generalDto
     */
    @Override
    public void ModifyUserAvatarSuccess(GeneralDto generalDto) {
        loadDiss();
        showNormalToast(ResUtil.getString(R.string.my_tab_141));
        GlideUtil.setImageCircle(mContext,newPath,ivUserImg,R.mipmap.icon_avatar);
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
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setMultiMode(false);
        imagePicker.setSaveRectangle(true);
        imagePicker.setSelectLimit(1);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
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
                                   newPath = images.get(0).path;
                                   ModifyUserAvatar("data:image/gif;base64,"+PicUtils.bitmapToString(newPath));
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
