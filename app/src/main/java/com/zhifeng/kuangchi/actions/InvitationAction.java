package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.InvitationInfoDto;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.InvitationView;
import com.zhifeng.kuangchi.util.config.MyApp;
import com.zhifeng.kuangchi.util.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     邀请链接
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 15:10
  * @Version:        1.0
 */

public class InvitationAction extends BaseAction<InvitationView> {
    public InvitationAction(RxAppCompatActivity _rxAppCompatActivity,InvitationView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取邀请链接
     *
     */
    public void getInvitationInfo(){
        post(WebUrlUtil.POST_USER_WEM,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()))
                        ,WebUrlUtil.POST_USER_WEM)));
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
                    case WebUrlUtil.POST_USER_WEM:
//                        //todo 获取邀请链接
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            InvitationInfoDto invitationInfoDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<InvitationInfoDto>() {
                            }.getType());
                            if (invitationInfoDto.getStatus() == 200){
                                //todo 获取邀请链接成功
                                view.getInvitationSuccess(invitationInfoDto);
                                return;
                            }
                            view.onError(invitationInfoDto.getMsg(),action.getErrorType());
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
