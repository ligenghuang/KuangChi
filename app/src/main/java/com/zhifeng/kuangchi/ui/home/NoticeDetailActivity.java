package com.zhifeng.kuangchi.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.lgh.huanglib.util.CheckNetwork;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.base.ActivityStack;
import com.lgh.huanglib.util.cusview.richtxtview.XRichText;
import com.lgh.huanglib.util.data.ResUtil;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.actions.NoticeDetailAction;
import com.zhifeng.kuangchi.module.NoticeDetailDto;
import com.zhifeng.kuangchi.ui.impl.NoticeDetailView;
import com.zhifeng.kuangchi.util.base.UserBaseActivity;
import com.zhifeng.kuangchi.util.view.X5LiveWebView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
  *
  * @ClassName:     公告详情页
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 10:59
  * @Version:        1.0
 */
public class NoticeDetailActivity extends UserBaseActivity<NoticeDetailAction> implements NoticeDetailView {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.f_title_tv)
    TextView fTitleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    X5LiveWebView webView;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar;

    private static final String HTML_REPLACE_TAG = "{XLHtmlContent}";
    private String mHtmlPlaceHolder = "<html><head><title></title><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0, user-scalable=no\"/></head><body>" + HTML_REPLACE_TAG + "</body></html>";


    String id;

    @Override
    public int intiLayout() {
        return R.layout.activity_information_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(new WeakReference<>(this));
        binding();
    }

    @Override
    protected NoticeDetailAction initAction() {
        return new NoticeDetailAction(this,this);
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
                .addTag("NoticeDetailActivity")  //给上面参数打标记，以后可以通过标记恢复
                .navigationBarWithKitkatEnable(false)
                .init();
        toolbar.setNavigationOnClickListener(view -> finish());
        fTitleTv.setText(ResUtil.getString(R.string.home_tab_10));
    }

    @Override
    protected void init() {
        super.init();
        mActicity = this;
        mContext = this;

        id = getIntent().getStringExtra("id");

        getNoticeDetail();
    }

    /**
     * 获取公告详情页
     */
    @Override
    public void getNoticeDetail() {
        if (CheckNetwork.checkNetwork2(mContext)){
            loadDialog();
            L.e("Rx","id  = "+id);
            baseAction.getNoticeDetail(id);
        }

    }

    /**
     * 获取公告详情页数据成功
     * @param noticeDetailDto
     */
    @Override
    public void getNoticeDetailSuccess(NoticeDetailDto noticeDetailDto) {
        loadDiss();
        NoticeDetailDto.DataBean dataBean = noticeDetailDto.getData();
//        tvDetailTitle.setText(dataBean.getTitle());
        String newContent = new String(mHtmlPlaceHolder);
        webView.loadData(newContent.replace(HTML_REPLACE_TAG, dataBean.getDesc()), "text/html; charset=UTF-8", null);

    }

    /**
     * 失败
     * @param message
     * @param code
     */
    @Override
    public void onError(String message, int code) {
        loadDiss();
//        showNormalToast(message);
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
    @Override
    protected void loadView() {

        webView.setWebChromeClient(webChromeClient);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);


            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
//                    if (url.contains("method=withdraw&partnerOrderNo"))
//                        payWv.loadUrl(url);
//                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadDiss();

            }


        });
    }


    protected void initView() {

        WebSettings webSetting = webView.getSettings();
        webSetting.setAllowFileAccess(true);//允许访问文件数据
        webSetting.setSupportZoom(true);
        webSetting.setTextZoom(30);
        /**
         * 设置布局方式：NARROW_COLUMNS 可能的话使所有列的宽度不超过屏幕宽度  ；NORMAL正常显示不做任何渲染；SINGLE_COLUMN 把所有内容放大webview等宽的一列中
         */
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSetting.setSupportZoom(false);//支持缩放
        webSetting.setBuiltInZoomControls(true);//设置支持缩放
        webSetting.setUseWideViewPort(true);//将图片调整到适合webview的大小 
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        if (Build.VERSION.SDK_INT >= 11)
            webSetting.setDisplayZoomControls(false);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        long time = System.currentTimeMillis();
//		if (mIntentUrl == null) {
//			mWebView.loadUrl(mHomeUrl);
//		} else {
//			mWebView.loadUrl(mIntentUrl.toString());
//		}
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView != null && webView.canGoBack()) {
                webView.goBack();
//                if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16)
//                    changGoForwardButton(mWebView);
                return true;
            } else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            progressBar.setProgress(i);
            if (progressBar != null && i != 100) {

                progressBar.setVisibility(View.VISIBLE);
            } else if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);
        }

        @Override
        public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                                   JsResult arg3) {
            return super.onJsConfirm(arg0, arg1, arg2, arg3);
        }

        IX5WebChromeClient.CustomViewCallback callback;

        // /////////////////////////////////////////////////////////
        //


        @Override
        public void onHideCustomView() {
            if (callback != null) {
                callback.onCustomViewHidden();
                callback = null;
            }
        }

        @Override
        public boolean onJsAlert(WebView arg0, String arg1, String arg2,
                                 JsResult arg3) {
            /**
             * 这里写入你自定义的window alert
             */
            return super.onJsAlert(null, arg1, arg2, arg3);
        }
        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            L.i("test", "openFileChooser 1");

        }

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsgs) {
            L.i("test", "openFileChooser 2");

        }

        // For Android  > 4.1.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            L.i("test", "openFileChooser 3");

        }

        // For Android  >= 5.0
        public boolean onShowFileChooser(WebView webView,
                                         ValueCallback<Uri[]> filePathCallback,
                                         FileChooserParams fileChooserParams) {
            L.i("test", "openFileChooser 4:" + filePathCallback.toString());

            return true;
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent == null || webView == null || intent.getData() == null)
            return;
        webView.loadUrl(intent.getData().toString());
    }

    @Override
    public void finish() {
        super.finish();
        webView.destroy();
    }

}
