package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.BaseDto;
import com.zhifeng.kuangchi.module.GeneralDto;
import com.zhifeng.kuangchi.module.MessageListDto;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.MessageView;
import com.zhifeng.kuangchi.util.config.MyApp;
import com.zhifeng.kuangchi.util.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     消息中心
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 15:40
  * @Version:        1.0
 */

public class MessageAction extends BaseAction<MessageView> {
    public MessageAction(RxAppCompatActivity _rxAppCompatActivity,MessageView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取消息中心数据
     *
     */
    public void getMessageList(){
        post(WebUrlUtil.POST_MESSAGE_LIST,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token", MySp.getAccessToken(MyApp.getContext()))
                        ,WebUrlUtil.POST_MESSAGE_LIST)));
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
                    case WebUrlUtil.POST_MESSAGE_LIST:
                        //todo 获取消息中心数据
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                           try{
                               MessageListDto messageListDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<MessageListDto>() {
                               }.getType());
                               if (messageListDto.getStatus() == 200){
                                   //todo 获取消息中心数据成功
                                   view.getMessageListSuccess(messageListDto);
                                   return;
                               }
                               view.onError(messageListDto.getMsg(),action.getErrorType());
                           }catch (JsonSyntaxException E){
                               BaseDto generalDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<BaseDto>() {
                               }.getType());
                               view.onError(generalDto.getMsg(),action.getErrorType());
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
