package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.OrderListDto;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.OrderView;
import com.zhifeng.kuangchi.util.config.MyApp;
import com.zhifeng.kuangchi.util.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     我的订单
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 14:44
  * @Version:        1.0
 */

public class OrderAction extends BaseAction<OrderView>{
    public OrderAction(RxAppCompatActivity _rxAppCompatActivity,OrderView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取我的订单列表
     * @param type
     * @param page
     */
    public void getOrderList(int type,int page){
        String order_status = getStatus(type);
        post(WebUrlUtil.POST_ORDER_LIST,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("order_status",order_status,"page",page+"","token", MySp.getAccessToken(MyApp.getContext())),
                        WebUrlUtil.POST_ORDER_LIST)));
    }

    /**
     * 获取订单列表
     * @param type
     * @return
     */
    private String getStatus(int type) {
        String order_status = "all";
        switch (type){
            case 1:
                order_status = "0";
                break;
            case 2:
                order_status = "1";
                break;
            case 3:
                order_status = "2";
                break;
            default:
                order_status = "all";
                break;
        }
        return order_status;
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
                    case WebUrlUtil.POST_ORDER_LIST:
                        //todo 获取我的订单列表
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            OrderListDto orderListDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<OrderListDto>() {
                            }.getType());
                            if (orderListDto.getStatus() == 200){
                                //todo 获取我的订单列表成功
                                view.getOrderListSuccess(orderListDto);
                                return;
                            }
                            view.onError(orderListDto.getMsg(),action.getErrorType());
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
