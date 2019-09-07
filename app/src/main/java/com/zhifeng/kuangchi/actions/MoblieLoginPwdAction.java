package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.data.MD5Utils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.AgencyListDto;
import com.zhifeng.kuangchi.module.GeneralDto;
import com.zhifeng.kuangchi.module.post.MoblieLoginPwdPost;
import com.zhifeng.kuangchi.module.post.SetPayPwdPost;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.MoblieLoginPwdView;
import com.zhifeng.kuangchi.util.config.MyApp;
import com.zhifeng.kuangchi.util.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     修改登录密码
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/7 14:22
  * @Version:        1.0
 */

public class MoblieLoginPwdAction extends BaseAction<MoblieLoginPwdView> {
    public MoblieLoginPwdAction(RxAppCompatActivity _rxAppCompatActivity,MoblieLoginPwdView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取验证码
     * @param phone 手机号码
     */
    public void getAuthCode(String phone){
        String temp = "sms_forget";
        String auth = MD5Utils.getMd5Value(phone+temp);
        post(WebUrlUtil.POST_GET_CODE, false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("phone",phone,"temp",temp,
                        "auth",auth,"type","1"),WebUrlUtil.POST_GET_CODE)));
    }

    /**
     * 修改登录密码
     * @param pwdPost
     */
    public void moblieLoginPwd(MoblieLoginPwdPost pwdPost){
        post(WebUrlUtil.POST_MOBLIE_LOGIN_PWD,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("password",pwdPost.getPassword(),"repassword",
                       pwdPost.getRepassword(),"phone",pwdPost.getPhone(),"verify_code",pwdPost.getVerify_code()),WebUrlUtil.POST_MOBLIE_LOGIN_PWD)));
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
                    case WebUrlUtil.POST_GET_CODE:
                        //todo 获取验证码
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 200){
                                //todo 获取验证码成功
                                view.getAuthCodeSuccess(generalDto.getData());
                                return;
                            }
                            view.onError(generalDto.getMsg(),action.getErrorType());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_MOBLIE_LOGIN_PWD:
//                        //todo 修改登录密码
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<GeneralDto>() {
                            }.getType());
                            if (generalDto.getStatus() == 200){
                                //todo 修改登录密码成功
                                view.moblieLoginPwdSuccess(generalDto);
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
