package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.BaseDto;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.ModifyMobileView;
import com.zhifeng.kuangchi.util.config.MyApp;
import com.zhifeng.kuangchi.util.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

import io.reactivex.Observable;

/**
  *
  * @ClassName:     修改手机号
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 16:53
  * @Version:        1.0
 */

public class ModifyMobileAction extends BaseAction<ModifyMobileView> {
    public ModifyMobileAction(RxAppCompatActivity _rxAppCompatActivity,ModifyMobileView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 校验手机号码
     * @param phone
     */
    public void checkPhone(String phone){
        post(WebUrlUtil.POST_CHECK_NEW_PHONE,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext())
                        ,"phone",phone)
                        ,WebUrlUtil.POST_CHECK_NEW_PHONE)));
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
                    case WebUrlUtil.POST_CHECK_NEW_PHONE:
                        //todo 校验手机号码
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            BaseDto baseDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<BaseDto>() {
                            }.getType());
                           view.checkPhoneSuccess(baseDto);
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
