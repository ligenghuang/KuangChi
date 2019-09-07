package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.BalanceDto;
import com.zhifeng.kuangchi.module.GeneralDto;
import com.zhifeng.kuangchi.module.post.EntrustedPurchasePost;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.EntrustedPurchaseView;
import com.zhifeng.kuangchi.util.config.MyApp;
import com.zhifeng.kuangchi.util.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
  *
  * @ClassName:     立即委托
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 17:27
  * @Version:        1.0
 */

public class EntrustedPurchaseAction extends BaseAction<EntrustedPurchaseView> {
    public EntrustedPurchaseAction(RxAppCompatActivity _rxAppCompatActivity,EntrustedPurchaseView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取我的余额
     */
    public void getBalance(){
        post(WebUrlUtil.POST_BALANCEE,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token",
                        MySp.getAccessToken(MyApp.getContext())),WebUrlUtil.POST_BALANCEE)));
    }

    /**
     * 委托购买
     * @param purchasePost
     */
    public void EntrustedPurchase(EntrustedPurchasePost purchasePost){
        L.e("lgh_post","post  = "+purchasePost.toString());
        post(WebUrlUtil.POST_ORDER_ENTRUST,false,service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token",MySp.getAccessToken(MyApp.getContext()),
                        "password",purchasePost.getPwsseord(),
                        "sku_id",purchasePost.getSku_id(),"cart_number",purchasePost.getCart_number(),
                        "pay_type",purchasePost.getPay_type()),WebUrlUtil.POST_ORDER_ENTRUST)
        ));
    }

    /**
     * sticky:表明优先接收最高级  threadMode = ThreadMode.MAIN：表明在主线程
     *
     * @param action
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MessageEvent(final Action action) {
        L.e("xx", "action   接收到数据更新....." + action.getIdentifying() + " action.getErrorType() : " + action.getErrorType());

        final String msg = action.getMsg(action);
        Observable.just(action.getErrorType())
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return (integer == 200);
                    }
                }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                // 输出返回结果
                L.e("xx", "输出返回结果 " + aBoolean);

                switch (action.getIdentifying()) {
                    case WebUrlUtil.POST_BALANCEE:
//                        //todo 获取我的余额
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            BalanceDto balanceDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<BalanceDto>() {
                            }.getType());
                            if (balanceDto.getStatus() == 200){
                                //todo 获取我的余额成功
                                view.getBalanceSuccess(balanceDto);
                                return;
                            }
                            view.onError(balanceDto.getMsg(),action.getErrorType());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_ORDER_ENTRUST:
                        //todo 委托购买
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 200){
                                //todo 委托购买成功
                                view.EntrustedPurchaseSuccess(generalDto);
                                return;
                            }else if (generalDto.getStatus() == 301){
                                view.EntrustedPurchaseError(generalDto.getMsg());
                                return;
                            }
                            view.onError(generalDto.getMsg(),action.getErrorType());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                }

            }

        });

    }

    public void toRegister() {

        register(this);
    }

    public void toUnregister() {

        unregister(this);
    }

}
