package com.example.http;


import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.AddressBean;
import com.waw.hr.mutils.bean.ClassifyBean;
import com.waw.hr.mutils.bean.GoodsBean;
import com.waw.hr.mutils.bean.GoodsDetailBean;
import com.waw.hr.mutils.bean.ImmediatelyBean;
import com.waw.hr.mutils.bean.IndexBean;
import com.waw.hr.mutils.bean.OrderInfoBean;
import com.waw.hr.mutils.bean.OrderListBean;
import com.waw.hr.mutils.bean.OrderSubBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @POST("api/login")
    Observable<BaseBean<String>> login(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("api/pwdfind")
    Observable<BaseBean<List>> pwdfind(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("api/code")
    Observable<BaseBean<String>> code(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/register")
    Observable<BaseBean<String>> register(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/login")
    Observable<BaseBean<String>> mobilelogin(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/resetpwd")
    Observable<BaseBean<String>> resetpwd(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/addressedit")
    Observable<BaseBean<Object>> addressedit(@Header("token") String token,@FieldMap Map<String, Object> params);

    @Multipart
    @POST("api/editIcon")
    Observable<BaseBean<String>> editIcon(@Header("token") String token,@Part MultipartBody.Part img);


    @FormUrlEncoded
    @POST("api/addressadd")
    Observable<BaseBean<Object>> addressadd(@Header("token") String token, @FieldMap Map<String, Object> params);

    @GET("api/delAddress")
    Observable<BaseBean<Object>> delAddress(@Header("token") String token, @QueryMap Map<String, Object> params);


    @GET("api/defultaddr")
    Observable<BaseBean<Object>> defultaddr(@Header("token") String token, @QueryMap Map<String, Object> params);

    @GET("api/myOrder")
    Observable<BaseBean<List<OrderListBean>>> myOrder(@Header("token") String token, @QueryMap Map<String, Object> params);

    @GET("api/cancelOrder")
    Observable<BaseBean<Object>> cancelOrder(@Header("token") String token, @QueryMap Map<String, Object> params);


    @GET("api/member")
    Observable<BaseBean<Map>> member(@Header("token") String token);

    @GET("api/info")
    Observable<BaseBean<String>> info();

    @GET("api/editName")
    Observable<BaseBean<Map>> editName(@Header("token") String token,@QueryMap Map<String, Object> params);

    @GET("api/affirmOrder")
    Observable<BaseBean<Object>> affirmOrder(@Header("token") String token,@QueryMap Map<String, Object> params);

    @GET("api/address")
    Observable<BaseBean<List<AddressBean>>> address(@Header("token") String token);

    @FormUrlEncoded
    @POST("api/cartPay")
    Observable<BaseBean<Object>> cartPay(@Header("token") String token,@FieldMap Map<String, Object> params);

    @GET("api/payBtn")
    Observable<BaseBean<Object>> payBtn(@Header("token") String token, @QueryMap Map<String, Object> params);

    @GET("api/orderSub")
    Observable<BaseBean<OrderSubBean>> orderSub(@Header("token") String token, @QueryMap Map<String, Object> params);

    @GET("api/index")
    Observable<BaseBean<IndexBean>> index();

    @GET("api/classList")
    Observable<BaseBean<List<ClassifyBean.ClassBean>>> classList();

    @GET("api/cartList")
    Observable<BaseBean<List<GoodsBean>>> cartList(@Header("token") String token);

    @GET("api/search")
    Observable<BaseBean<List<GoodsBean>>> search(@QueryMap Map<String, Object> params);

    @GET("api/classInfo")
    Observable<BaseBean<ClassifyBean>> classInfo(@QueryMap Map<String, Object> params);

    @GET("api/goodsInfo")
    Observable<BaseBean<GoodsDetailBean>> goodsInfo(@Header("token") String token,@QueryMap Map<String, Object> params);

    @GET("api/cartDel")
    Observable<BaseBean<Object>> cartItemDel(@Header("token") String token, @QueryMap Map<String, Object> params);

    @GET("api/cartItem")
    Observable<BaseBean<Object>> cartItem(@Header("token") String token, @QueryMap Map<String, Object> params);


    @GET("api/collect")
    Observable<BaseBean<Object>> collect(@Header("token") String token, @QueryMap Map<String, Object> params);

    @GET("api/myCollect")
    Observable<BaseBean<List<GoodsBean>>> myCollect(@Header("token") String token, @QueryMap Map<String, Object> params);


    @GET("api/immediately")
    Observable<BaseBean<OrderSubBean>> immediately(@Header("token") String token, @QueryMap Map<String, Object> params);

    @GET("api/orderInfo")
    Observable<BaseBean<OrderInfoBean>> orderInfo(@Header("token") String token, @QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/immediPay")
    Observable<BaseBean<Object>> immediPay(@Header("token") String token, @FieldMap Map<String, Object> params);

    @GET("api/addCar")
    Observable<BaseBean<Object>> addCar(@Header("token") String token, @QueryMap Map<String, Object> params);

}