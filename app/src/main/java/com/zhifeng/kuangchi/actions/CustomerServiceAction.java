package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.CustomerServiceListDto;
import com.zhifeng.kuangchi.module.GeneralDto;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.CustomerServiceView;
import com.zhifeng.kuangchi.util.config.MyApp;
import com.zhifeng.kuangchi.util.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     客服对话列表页
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 11:07
  * @Version:        1.0
 */

public class CustomerServiceAction extends BaseAction<CustomerServiceView> {
    public CustomerServiceAction(RxAppCompatActivity _rxAppCompatActivity,CustomerServiceView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }


    /**
     * 获取客服对话列表
     */
    public void getCustomerService(){
        post(WebUrlUtil.POST_CUSTOMER_INDEX,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext())),
                        WebUrlUtil.POST_CUSTOMER_INDEX)));
    }

    /**
     * 发送消息
     * @param msg
     */
    public void sendMessage(String msg){
        post(WebUrlUtil.POST_CUSTOMER_SEND,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()),
                        "content",msg), WebUrlUtil.POST_CUSTOMER_SEND)));
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
                    case WebUrlUtil.POST_CUSTOMER_INDEX:
////                        //todo 获取客服对话列表
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            CustomerServiceListDto customerServiceListDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<CustomerServiceListDto>() {
                            }.getType());
                            if (customerServiceListDto.getStatus() == 200){
                                //todo 获取客服对话列表成功
                                view.getCustomerServiceListSuccess(customerServiceListDto);
                                return;
                            }
                            view.onError(customerServiceListDto.getMsg(),action.getErrorType());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_CUSTOMER_SEND:
                        //todo 发送消息
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 200){
                                //todo 发送消息成功
                                view.sendMessageSuccess();
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
