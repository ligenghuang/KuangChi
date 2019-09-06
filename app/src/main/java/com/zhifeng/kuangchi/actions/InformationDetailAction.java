package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.HomeDataDto;
import com.zhifeng.kuangchi.module.InfoDetailDto;
import com.zhifeng.kuangchi.module.NoticeDetailDto;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.InformationDetailView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * 资讯详情页
 */
public class InformationDetailAction extends BaseAction<InformationDetailView> {
    public InformationDetailAction(RxAppCompatActivity _rxAppCompatActivity,InformationDetailView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取详情页数据
     * @param id
     */
    public void getInfoDetail(String id){
        post(WebUrlUtil.POST_GET_ANNOUNCE_DETAIL,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("id",id),WebUrlUtil.POST_GET_ANNOUNCE_DETAIL)));
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
                    case WebUrlUtil.POST_GET_ANNOUNCE_DETAIL:
                        //todo 获取资讯数据
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            InfoDetailDto infoDetailDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<InfoDetailDto>() {
                            }.getType());
                            if (infoDetailDto.getStatus() == 200){
                                //todo 获取资讯数据成功
                                view.getInfoDetailSuccess(infoDetailDto);
                                return;
                            }
                            view.onError(infoDetailDto.getMsg(),action.getErrorType());
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
