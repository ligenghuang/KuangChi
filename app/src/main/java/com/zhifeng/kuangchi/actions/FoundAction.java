package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.KLineDto;
import com.zhifeng.kuangchi.module.ServiceDto;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.FoundView;

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
