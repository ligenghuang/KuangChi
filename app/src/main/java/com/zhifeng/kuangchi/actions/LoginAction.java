package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.data.MD5Utils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.BaseDto;
import com.zhifeng.kuangchi.module.GeneralDto;
import com.zhifeng.kuangchi.module.LoginDto;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.LoginView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * 登录页面
 */
public class LoginAction extends BaseAction<LoginView> {
    public LoginAction(RxAppCompatActivity _rxAppCompatActivity,LoginView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取验证码
     * @param phone 手机号码
     * @param type 0：注册 sms_reg;1：登录:sms_login
     */
    public void getCode(String phone,int type){
        String temp = type == 0 ? "sms_reg" : "sms_login";
        String auth = MD5Utils.getMd5Value(phone+temp);
        post(WebUrlUtil.POST_GET_CODE, false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("phone",phone,"temp",temp,
                        "auth",auth,"type","1"),WebUrlUtil.POST_GET_CODE)));
    }

    /**
     * 登录
     * @param phone 手机号码
     * @param verify_code 验证码
     */
    public void Login(String phone,String verify_code){
        Map<Object,Object> map = new HashMap<>();
        map = CollectionsUtils.generateMap("phone",phone,"password",verify_code);
        Map<Object, Object> finalMap = map;
        post(WebUrlUtil.POST_LOGIN, false, service -> manager.runHttp(
                service.PostData(finalMap,WebUrlUtil.POST_LOGIN)));
    }

    /**
     * 注册
     * @param phone
     * @param verify_code
     * @param inviteCode
     */
    public void Registered(String phone,String verify_code,String inviteCode,String password){
        Map<Object,Object> map = new HashMap<>();
        map = CollectionsUtils.generateMap("phone",phone,"verify_code",verify_code,"inviteCode",inviteCode,"password",password);
        Map<Object, Object> finalMap = map;
        post(WebUrlUtil.POST_LOGIN, false, service -> manager.runHttp(
                service.PostData(finalMap,WebUrlUtil.POST_LOGIN)));
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
                               view.getCodeSuccess(generalDto.getData());
                               return;
                           }
                            view.onError(generalDto.getMsg(),action.getErrorType());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                    case WebUrlUtil.POST_LOGIN:
                        //todo 登录或注册
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                           try{
                               LoginDto loginDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<LoginDto>() {
                               }.getType());
                               if (loginDto.getStatus() == 200){
                                   //todo 获取验证码成功
                                   view.loginOrRegisteredSuccess(loginDto);
                                   return;
                               }
                               view.onError(loginDto.getMsg(),action.getErrorType());
                           }catch (JsonSyntaxException e){
                               BaseDto baseDto = new Gson().fromJson(action.getUserData().toString(),new TypeToken<BaseDto>(){}.getType());
                               view.onError(baseDto.getMsg(),action.getErrorType());
                           }
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
