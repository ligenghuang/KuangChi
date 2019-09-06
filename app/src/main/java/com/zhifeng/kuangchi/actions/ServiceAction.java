package com.zhifeng.kuangchi.actions;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lgh.huanglib.actions.Action;
import com.lgh.huanglib.net.CollectionsUtils;
import com.lgh.huanglib.util.L;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhifeng.kuangchi.module.ServiceDto;
import com.zhifeng.kuangchi.net.WebUrlUtil;
import com.zhifeng.kuangchi.ui.impl.ServiceView;
import com.zhifeng.kuangchi.util.config.MyApp;
import com.zhifeng.kuangchi.util.data.MySp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
  *
  * @ClassName:     存储服务器群组
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 18:18
  * @Version:        1.0
 */

public class ServiceAction extends BaseAction<ServiceView> {
    public ServiceAction(RxAppCompatActivity _rxAppCompatActivity,ServiceView view) {
        super(_rxAppCompatActivity);
        attachView(view);
    }

    /**
     *
     * 获取存储服务器群组
     */
    public void getService(){
        post(WebUrlUtil.POST_MINERS_INDEX,false, service -> manager.runHttp(
                service.PostData(CollectionsUtils.generateMap("token",MySp.getAccessToken(MyApp.getContext())),WebUrlUtil.POST_MINERS_INDEX)));
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
                    case WebUrlUtil.POST_MINERS_INDEX:
//                        //todo 获取存储服务器群组
                        if (aBoolean) {
                            L.e("xx", "输出返回结果 " + action.getUserData().toString());
                            ServiceDto serviceDto = new Gson().fromJson(action.getUserData().toString(), new TypeToken<ServiceDto>() {
                            }.getType());
                            if (serviceDto.getStatus() == 200){
                                //todo 获取存储服务器群组成功
                                JSONObject jsonObject = new JSONObject(action.getUserData().toString());
                                JSONObject datajson = jsonObject.getJSONObject("data");
                                JSONArray json = datajson.getJSONArray("line_data"); // 首先把字符串转成 JSONArray  对象
                                Map<String,String> map = new HashMap<>();
                                for (int i=0;i<json.length();i++){
                                    JSONObject jsons=json.getJSONObject(i);
                                    //通过key方法得到遍历器
                                    Iterator iter = jsons.keys();
                                    while (iter.hasNext()){
                                        String time= (String) iter.next();//取key
                                        String value=jsons.getString(time);//取value
                                        map.put(time,value);
                                    }
                                }
                                view.getServiceSuccess(serviceDto,map);
                                return;
                            }
                            view.onError(serviceDto.getMsg(),action.getErrorType());
                            return;
                        }
                        view.onError(msg,action.getErrorType());
                        break;
                }

            }

        });

    }

    private static HashMap<String, String> toHashMap(JSONObject jsonObject)
    {
        HashMap<String, String> data = new HashMap<String, String>();
        Iterator it = jsonObject.keys();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext())
        {
            String key = String.valueOf(it.next());
            String value = "0";
            try {
                value = (String) jsonObject.get(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            data.put(key, value);
        }
        return data;
    }

    public void toRegister() {

        register(this);
    }

    public void toUnregister() {

        unregister(this);
    }
}
