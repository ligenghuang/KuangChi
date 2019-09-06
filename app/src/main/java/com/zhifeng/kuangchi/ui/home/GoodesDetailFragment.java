package com.zhifeng.kuangchi.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.cusview.richtxtview.XRichText;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.BaseAction;
import com.zhifeng.kuangchi.util.base.UserBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName: 商品详情页 产品描述和基数fragment
 * @Description:
 * @Author: lgh
 * @CreateDate: 2019/8/31 18:04
 * @Version: 1.0
 */

public class GoodesDetailFragment extends UserBaseFragment {
    View view;
    @BindView(R.id.xrichtext)
    XRichText xrichtext;

    String text;

    public GoodesDetailFragment(String text) {
        this.text = text;
    }

    @Override
    protected BaseAction initAction() {
        return null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getContext();
        mActivity = activity;
    }

    @Override
    protected void initialize() {
        xrichtext.text(text);
        L.e("lgh_text","text == "+text);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goods_detail, container, false);
        ButterKnife.bind(this, view);
        binding();
        mImmersionBar.statusBarDarkFont(true);
        return view;
    }
}
