package com.example.http;


import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.ClassifyBean;
import com.waw.hr.mutils.bean.GoodsDetailBean;
import com.waw.hr.mutils.bean.IndexBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by jingbin on 16/11/21.
 * 网络请求类（一个接口一个方法）
 */
public interface HttpClient {

    class Builder {

        public static HttpClient getServer() {
            return HttpUtils.getInstance().getGuodongServer(HttpClient.class);
        }

        public static HttpClient getOtherServer() {
            return HttpUtils.getInstance().getGuodongServer(HttpClient.class);
        }
    }


    @FormUrlEncoded
    @POST("api/complete")
    Observable<BaseBean<Map>> editInfo(@Header("token") String token,@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/register")
    Observable<BaseBean<String>> register(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/login")
    Observable<BaseBean<String>> login(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("api/pwdfind")
    Observable<BaseBean<List>> pwdfind(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("api/code")
    Observable<BaseBean<String>> getcode(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/applyLimit")
    Observable<BaseBean<Object>> applyLimit(@Header("token") String token,@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("api/delayRefund")
    Observable<BaseBean<Object>> delayRefund(@Header("token") String token,@FieldMap Map<String, Object> params);


    @GET("api/index")
    Observable<BaseBean<IndexBean>> index();

    @GET("api/classList")
    Observable<BaseBean<List<ClassifyBean.ClassBean>>> classList();

    @GET("api/classInfo")
    Observable<BaseBean<ClassifyBean>> classInfo(@QueryMap Map<String, Object> params);

    @GET("api/goodsInfo")
    Observable<BaseBean<GoodsDetailBean>> goodsInfo(@QueryMap Map<String, Object> params);

    @GET("api/collect")
    Observable<BaseBean<Object>> collect(@QueryMap Map<String, Object> params);

    @GET("api/addCar")
    Observable<BaseBean<Object>> addCar(@Header("token") String token,@QueryMap Map<String, Object> params);
}