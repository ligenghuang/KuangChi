package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.AgencyListDto;
import com.zhifeng.kuangchi.module.LowerListDto;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.AgencyListView;
import com.zhifeng.kuangchi.ui.impl.LowerListView;
import com.zhifeng.kuangchi.util.config.MyApp;
import com.zhifeng.kuangchi.util.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     舰队长代理明细
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 20:25
  * @Version:        1.0
 */

public class LowerListAction extends BaseAction<LowerListView> {
    public LowerListAction(RxAppCompatActivity _rxAppCompatActivity, LowerListView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取舰队长代理明细列表
     * @param page
     */
    public void getLowerList(int page,String id){
        post(WebUrlUtil.POST_MINERESAGENT_LOWER_LISET,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("page",page,"id",
                        id),WebUrlUtil.POST_MINERESAGENT_LOWER_LISET)));
    }

    /**
     * 获取舰队长代理明细列表
     * @param page
     */
    public void getLowerList2(int page,String id){
        post(WebUrlUtil.POST_AGENT_NUM_LIST,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("page",page,"level",
                        id,"token",MySp.getAccessToken(MyApp.getContext())),WebUrlUtil.POST_AGENT_NUM_LIST)));
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
                    case WebUrlUtil.POST_MINERESAGENT_LOWER_LISET:
                    case WebUrlUtil.POST_AGENT_NUM_LIST:
//                        //todo 获取舰队长代理明细列表
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            LowerListDto lowerListDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<LowerListDto>() {
                            }.getType());
                            if (lowerListDto.getStatus() == 200){
                                //todo 获取舰队长代理明细列表成功
                                view.getLowerListSuccess(lowerListDto);
                                return;
                            }
                            view.onError(lowerListDto.getMsg(),action.getErrorType());
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
