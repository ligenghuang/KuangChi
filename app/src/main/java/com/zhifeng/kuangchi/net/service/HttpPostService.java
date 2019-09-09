package com.zhifeng.kuangchi.net.service;



import com.lgh.huanglib.retrofitlib.Api.BaseResultEntity;
import com.lgh.huanglib.retrofitlib.Api.BaseResultEntity2;
import com.zhifeng.kuangchi.net.WebUrlUtil;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
* 
* @author lgh
* created at 2019/5/13 0013 14:38
*/
public interface HttpPostService {

//
//    @GET(WebUrlUtil.GET_ABOUT)
//    Observable<BaseResultEntity> getAbout();
//
//    @GET(WebUrlUtil.GET_ABOUT)
//    Observable<String> getAboutString();
//
//    @GET(WebUrlUtil.GET_ABOUT)
//    Call<BaseResultEntity> getAboutByCall();
//
//    @GET(WebUrlUtil.URL_GET_MAIN)
//    Observable<BaseResultEntity> getHome();


    /**
     * POST请求
     * @param url
     * @return
     */
    @POST
    Observable<BaseResultEntity> PostData(@Url String url);
    @POST
    Observable<BaseResultEntity> PostData( @Body Map<Object, Object> body, @Url String url);

    /**
     * GET请求
     * @param url
     * @return
     */
    @GET
    Observable<BaseResultEntity> GetData(@Url String url);

    /**
     * 带id的get请求
     * @param id
     * @param url
     * @return
     */
    @GET
    Observable<BaseResultEntity> GetData(@Url String url,@Query("token")String id);

    /**
     * 带id的get请求
     * @param symbol=lambusdt
     * @param url
     * @return
     */
    @GET
    Observable<BaseResultEntity> GetKLine(@Url String url,@Query("symbol")String symbol);


}
