package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.BalanceDto;
import com.zhifeng.kuangchi.module.CoinRateDto;
import com.zhifeng.kuangchi.module.GeneralDto;
import com.zhifeng.kuangchi.module.post.GetCoinPost;
import com.zhifeng.kuangchi.module.post.PutCoinPost;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.BalanceView;
import com.zhifeng.kuangchi.util.config.MyApp;
import com.zhifeng.kuangchi.util.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     我的余额
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 11:32
  * @Version:        1.0
 */

public class BalanceAction extends BaseAction<BalanceView>{
    public BalanceAction(RxAppCompatActivity _rxAppCompatActivity,BalanceView view) {
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
     * 提币
     * @param getCoinPost
     */
    public void getCoin(GetCoinPost getCoinPost){
        post(WebUrlUtil.POST_GET_COIN,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext())
                        ,"coin_type",getCoinPost.getCoin_type(),"money",getCoinPost.getMoney(),"address",getCoinPost.getAddress()
                        ,"input_money",getCoinPost.getInput_money(),"password",getCoinPost.getPassword())
                        ,WebUrlUtil.POST_GET_COIN)));
    }

    /**
     * 充币
     * @param putCoinPost
     */
    public void putCoin(PutCoinPost putCoinPost){
        post(WebUrlUtil.POST_PUT_COIN,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext())
                        ,"coin_type",putCoinPost.getCoin_type(),"money",putCoinPost.getMoney(),"address",putCoinPost.getAddress()
                        ,"input_money",putCoinPost.getInput_money(),"proof_pic",putCoinPost.getProof_pic())
                        ,WebUrlUtil.POST_PUT_COIN)));
    }

    /**
     * 获取币转换率
     * @param coinType
     */
    public void getRate(String coinType){ ;
        post(WebUrlUtil.POST_COIN_RATE,false,service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token",MySp.getAccessToken(MyApp.getContext()),
                        "coin_type",coinType),WebUrlUtil.POST_COIN_RATE)
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
                    case WebUrlUtil.POST_COIN_RATE:
                        //todo 获取币转换率
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            CoinRateDto coinRateDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<CoinRateDto>() {
                            }.getType());
                            if (coinRateDto.getStatus() == 200){
                                //todo 获取币转换率成功
                                view.getRateSuccess(coinRateDto);
                                return;
                            }
                            view.onError(coinRateDto.getMsg(),action.getErrorType());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_GET_COIN:
                        //todo 提币
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 200){
                                //todo 提币成功
                                view.getCoinSuccess(generalDto);
                                return;
                            }
                            view.onError(generalDto.getMsg(),action.getErrorType());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_PUT_COIN:
                        //todo 充币
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 200){
                                //todo 充币成功
                                view.putCoinSuccess(generalDto);
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
