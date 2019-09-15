package com.zhifeng.kuangchi.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.config.GlideUtil;
import com.lgh.huanglib.util.data.ResUtil;
import com.lzy.imagepicker.bean.ImageItem;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.util.photo.PicUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
  *
  * @ClassName:     版本更新
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 16:45
  * @Version:        1.0
 */

public class UpdetaDialog extends Dialog {

    Context mContext;
    Activity activity;

    OnClickListener onClickListener;

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public UpdetaDialog(@NonNull Context context, int themeResId, Activity activity) {
        super(context, themeResId);
        this.mContext = context;
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_updata);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        Window win = this.getWindow();
        win.setGravity(Gravity.CENTER);
    }

    @OnClick({R.id.tv_dialog_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog_confirm:
                //todo 确定
                confirm();
                break;
        }
    }

    /**
     * 确定下载
     */
    private void confirm(){
      onClickListener.onClick();
    }


    public interface OnClickListener  {
        void onClick();
    }
}
