package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.AgencyListDto;
import com.zhifeng.kuangchi.module.GeneralDto;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.ModifyUserInfoView;
import com.zhifeng.kuangchi.util.config.MyApp;
import com.zhifeng.kuangchi.util.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     修改昵称头像
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/4 17:54
  * @Version:        1.0
 */

public class ModifyUserInfoAction extends BaseAction<ModifyUserInfoView> {
    public ModifyUserInfoAction(RxAppCompatActivity _rxAppCompatActivity,ModifyUserInfoView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 修改头像
     * @param path
     */
    public void ModifyUserAvatar(String path){
        post(WebUrlUtil.POST_SHANG_AVATAR,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("avatar",path,"token",
                        MySp.getAccessToken(MyApp.getContext())),WebUrlUtil.POST_SHANG_AVATAR)));
    }

    /**
     * 修改昵称
     * @param path
     */
    public void ModifyUserName(String path){
        post(WebUrlUtil.POST_CHANGE_NAME,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("realname",path,"token",
                        MySp.getAccessToken(MyApp.getContext())),WebUrlUtil.POST_CHANGE_NAME)));
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
                    case WebUrlUtil.POST_SHANG_AVATAR:
//                        //todo 修改头像
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 200){
                                //todo 修改头像成功
                                view.ModifyUserAvatarSuccess(generalDto);
                                return;
                            }
                            view.onError(generalDto.getMsg(),action.getErrorType());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_CHANGE_NAME:
//                        //todo 修改昵称
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 200){
                                //todo 修改昵称成功
                                view.ModifyUserNameSuccess(generalDto);
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
