package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.EarningsListDto;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.EarningsView;
import com.zhifeng.kuangchi.util.config.MyApp;
import com.zhifeng.kuangchi.util.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     收益明细
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/8/30 21:11
  * @Version:        1.0
 */

public class EarningsAction extends BaseAction<EarningsView> {
    public EarningsAction(RxAppCompatActivity _rxAppCompatActivity,EarningsView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     * 获取收益明细列表
     * @param type
     */
    public void getEarningsList(int type,int page){
        String source_type = getType(type);
        post(WebUrlUtil.POST_ENTRUST_LOG,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("page",page,"source_type",source_type,"token",
                        MySp.getAccessToken(MyApp.getContext())),WebUrlUtil.POST_ENTRUST_LOG)));
    }

    /**
     *
     * @param type
     * 明细类型
     * all:全部,
     * all_share:分享奖励,
     * all_node:节点奖励,
     * all_Infinite:无限奖励,
     * day_release:每日释放,
//     * 7:提币,
     * 11:释放
     * @return
     */
    private String getType(int type){
        String source_type = "all";
        switch (type){
            case 0:
                source_type = "all";
                break;
            case 1:
                source_type = "all_share";
                break;
            case 2:
                source_type = "all_node";
                break;
            case 3:
                source_type = "all_Infinite";
                break;
            case 4:
                source_type = "day_release";
                break;
            case 5:
                source_type = "12";
                break;
//            case 6:
//                source_type = "11";
//                break;
        }
        return source_type;
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
                    case WebUrlUtil.POST_ENTRUST_LOG:
//                        //todo 获取收益明细列表
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            EarningsListDto earningsListDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<EarningsListDto>() {
                            }.getType());
                            if (earningsListDto.getStatus() == 200){
                                //todo 获取收益明细列表成功
                                view.getEarningsListSuccess(earningsListDto);
                                return;
                            }
                            view.onError(earningsListDto.getMsg(),action.getErrorType());
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
