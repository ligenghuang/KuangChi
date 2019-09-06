package com.zhifeng.kuangchi.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.hjq.toast.ToastUtils;
import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.util.imageloader.GlideImageLoader;
import com.zhifeng.kuangchi.util.photo.PicUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
  *
  * @ClassName:     充币上传凭证
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 16:45
  * @Version:        1.0
 */

public class PutDialog extends Dialog {

    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    public static final int REQUEST_CODE_TAKE = 102;
    public static final int REQUEST_CODE_ALBUM = 103;
    public static int REQUEST_SELECT_TYPE = -1;//选择的类型
    private ArrayList<ImageItem> selImageList = new ArrayList<>(); //当前选择的所有图片
    ArrayList<ImageItem> images = null;

    Context mContext;
    Activity activity;

    @BindView(R.id.iv_dialog_img)
    ImageView ivDialogImg;

    String url = "";
    OnClickListener onClickListener;

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public PutDialog(@NonNull Context context, int themeResId,Activity activity) {
        super(context, themeResId);
        this.mContext = context;
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_put);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        Window win = this.getWindow();
        win.setGravity(Gravity.CENTER);
    }

    @OnClick({R.id.tv_dialog_close, R.id.tv_dialog_confirm,R.id.iv_dialog_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog_close:
                //todo 取消
                dismiss();
                break;
            case R.id.tv_dialog_confirm:
                //todo 确定
                confirm();
                break;
            case R.id.iv_dialog_img:
                //todo 选择图片
                onClickListener.TakeUserGally();
                break;
        }
    }

    /**
     * 确定上传
     */
    private void confirm(){
        if (TextUtils.isEmpty(url)){
            Toast.makeText(mContext, ResUtil.getString(R.string.home_tab_25), Toast.LENGTH_SHORT).show();
            return;
        }else {
            try {
                onClickListener.onClick(PicUtils.bitmapToString(url));
                L.e("lgh_base64","base64 = "+PicUtils.bitmapToString(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 显示图片
     * @param url
     */
    public void setImg(String url){
        GlideUtil.setImage(mContext,url,ivDialogImg,0);
        this.url = url;
        L.e("lgh_img","img  = "+url);
    }



    public interface OnClickListener  {
        void onClick(String base64);
        void TakeUserGally();
    }
}
