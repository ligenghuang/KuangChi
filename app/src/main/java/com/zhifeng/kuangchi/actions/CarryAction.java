package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.CarryListDto;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.CarryView;
import com.zhifeng.kuangchi.util.config.MyApp;
import com.zhifeng.kuangchi.util.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     提币明细
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/31 9:11
  * @Version:        1.0
 */

public class CarryAction extends BaseAction<CarryView> {
    public CarryAction(RxAppCompatActivity _rxAppCompatActivity,CarryView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取提币明细列表
     * @param page
     */
    public void getCarryList(int page){
        post(WebUrlUtil.POST_MINERS_CARRY,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("page",page,"token",
                        MySp.getAccessToken(MyApp.getContext())),WebUrlUtil.POST_MINERS_CARRY)));
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
                    case WebUrlUtil.POST_MINERS_CARRY:
//                        //todo 获取提币明细列表
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            CarryListDto carryListDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<CarryListDto>() {
                            }.getType());
                            if (carryListDto.getStatus() == 200){
                                //todo 获取提币明细列表成功
                                view.getCarryListSuccess(carryListDto);
                                return;
                            }
                            view.onError(carryListDto.getMsg(),action.getErrorType());
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
