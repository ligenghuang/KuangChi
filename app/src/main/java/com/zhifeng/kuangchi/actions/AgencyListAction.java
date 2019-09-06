package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.AgencyListDto;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.AgencyListView;
import com.zhifeng.kuangchi.util.config.MyApp;
import com.zhifeng.kuangchi.util.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
/**
  *
  * @ClassName:     代理明细
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 20:25
  * @Version:        1.0
 */

public class AgencyListAction extends BaseAction<AgencyListView> {
    public AgencyListAction(RxAppCompatActivity _rxAppCompatActivity,AgencyListView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取代理明细列表
     * @param page
     */
    public void getAgencyList(int page){
        post(WebUrlUtil.POST_MINERS_AGENT_LIST,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("page",page,"token",
                        MySp.getAccessToken(MyApp.getContext())),WebUrlUtil.POST_MINERS_AGENT_LIST)));
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
                    case WebUrlUtil.POST_MINERS_AGENT_LIST:
//                        //todo 获取代理明细列表
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            AgencyListDto agencyListDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<AgencyListDto>() {
                            }.getType());
                            if (agencyListDto.getStatus() == 200){
                                //todo 获取代理明细列表成功
                                view.getAgencyListSuccess(agencyListDto);
                                return;
                            }
                            view.onError(agencyListDto.getMsg(),action.getErrorType());
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
