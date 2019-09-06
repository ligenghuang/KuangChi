package com.zhifeng.kuangchi.actions;


import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.data.IDCardUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.GeneralDto;
import com.zhifeng.kuangchi.module.IdCardDto;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.IdCardView;
import com.zhifeng.kuangchi.util.config.MyApp;
import com.zhifeng.kuangchi.util.data.DynamicTimeFormat;
import com.zhifeng.kuangchi.util.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     身份认证
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 15:34
  * @Version:        1.0
 */

public class IdCardAction extends BaseAction<IdCardView> {
    public IdCardAction(RxAppCompatActivity _rxAppCompatActivity,IdCardView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 身份·认证
     * @param name
     */
    public void setIdCard(String name,String idNum){
        try {
            String str = DynamicTimeFormat.base64(idNum, "UTF-8");
            post(WebUrlUtil.POST_USER_API,false, service -> manager.runHttp(
                    service.PostData(CollectionsUtils.generateMap("idNum",str,"name",name,"token",MySp.getAccessToken(MyApp.getContext())
                    ),WebUrlUtil.POST_USER_API)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
                    case WebUrlUtil.POST_USER_API:
////                        //todo 身份认证
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            try{
                                IdCardDto idCardDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<IdCardDto>() {
                                }.getType());
                                if (idCardDto.getStatus() == 200){
                                    //todo 身份认证成功
                                    view.setIdCardSuccess();
                                    return;
                                }
                                view.onError(idCardDto.getMsg(),action.getErrorType());
                            }catch (JsonSyntaxException e){
                                GeneralDto generalDto = new Gson().fromJson(action.getUserData().toString(),new TypeToken<GeneralDto>(){}.getType());
                                view.onError(generalDto.getData(),action.getErrorType());
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
