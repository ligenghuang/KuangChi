package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.GeneralDto;
import com.zhifeng.kuangchi.module.HomeDataDto;
import com.zhifeng.kuangchi.module.KLineDto;
import com.zhifeng.kuangchi.module.ServiceDto;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.FoundView;
import com.zhifeng.kuangchi.util.config.MyApp;
import com.zhifeng.kuangchi.util.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class FoundAction extends BaseAction<FoundView> {
    public FoundAction(RxAppCompatActivity _rxAppCompatActivity,FoundView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     *
     * 获取存储服务器群组
     */
    public void getService(){
        post(WebUrlUtil.POST_HISTORY_KLINE,false, service -> manager.runHttp(
                service.GetKLine(WebUrlUtil.POST_HISTORY_KLINE,"lambusdt")));
    }

    /**
     * 获取首页数据
     */
    public void getHomeData(){
        post(WebUrlUtil.POST_GET_HOME_INDEX,false, service -> manager.runHttp(
                service.GetData(WebUrlUtil.POST_GET_HOME_INDEX, MySp.getAccessToken(MyApp.getContext()))));
    }

    /***
     * 验证密码
     * @param pwd
     */
    public void verifyPassword(String pwd){
        post(WebUrlUtil.POST_VERIFY_PWD,false,service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()),"password",pwd),WebUrlUtil.POST_VERIFY_PWD)
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
                    case WebUrlUtil.POST_HISTORY_KLINE:
//                        //todo 获取行情
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            KLineDto kLineDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<KLineDto>() {
                            }.getType());
                            if (kLineDto.getStatus()==200){
                                //todo 获取行情成功
                                view.getServiceSuccess(kLineDto);
                                return;
                            }
                            view.onError(kLineDto.getMsg(),action.getErrorType());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_VERIFY_PWD:
                        //todo 验证密码
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus()==200){
                                //todo 验证密码成功
                                view.verifyPasswordSuccess();
                                return;
                            }
                            view.verifyPasswordError(generalDto.getMsg());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_GET_HOME_INDEX:
                        //todo 获取首页数据
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            try{
                                HomeDataDto homeDataDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<HomeDataDto>() {
                                }.getType());
                                if (homeDataDto.getStatus() == 200){
                                    //todo 获取首页数据成功
                                    view.getHomeDataSuccess(homeDataDto);
                                    return;
                                }
                                view.onError(homeDataDto.getMsg(),action.getErrorType());
                                return;
                            }catch (JsonSyntaxException e){
                                view.onError(msg,action.getErrorType());
                            }

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
