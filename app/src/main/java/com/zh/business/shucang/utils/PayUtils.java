package com.zh.business.shucang.utils;

import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.waw.hr.mutils.LogUtil;
import com.zh.business.shucang.MAPP;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PayUtils {

    private static PayUtils payUtils;


    public static PayUtils getInstance() {
        if (payUtils == null) {
            payUtils = new PayUtils();
        }
        return payUtils;
    }

    public void wxPay(Map<String, String> map) {
        PayReq request = new PayReq();
        request.appId = map.get("appId");
        request.partnerId = "1541100021";//商户ID
        request.prepayId = (String) map.get("package").substring(10);
        LogUtil.e("支付ID", request.prepayId);
        request.packageValue = "Sign=WXPay";
        request.nonceStr = map.get("nonceStr");
        request.timeStamp = map.get("timeStamp");
//        request.sign = map.get("paySign");
        //开始将6个字段进行数据封装
        List<WXModel> list = new LinkedList<>();
        list.add(new WXModel("appid", map.get("appId")));
        list.add(new WXModel("noncestr", map.get("nonceStr")));
        list.add(new WXModel("package", "Sign=WXPay"));
        list.add(new WXModel("partnerid", "1541100021"));
        list.add(new WXModel("prepayid", map.get("package").substring(10)));
        list.add(new WXModel("timestamp", map.get("timeStamp")));

        LogUtil.e("自己测试", genAppSign(list));

        request.sign = "WXPay";
//...发起请求即可


        MAPP.getWxapi().sendReq(request);


    }


    /**
     * 生成签名
     */
    private String genAppSign(List<WXModel> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).getKey());
            sb.append('=');
            sb.append(list.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append("wx37c1c72fd81bd8bf");
        String appSign = null;
        try {
            appSign = String.valueOf(MessageDigest.getInstance("MD5").digest(sb.toString().getBytes())).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return appSign;
    }


    public void aliPay(String orderInfo, Handler mHandler) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(orderInfo);
            }
        }).subscribeOn(Schedulers.io()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                PayTask alipay = new PayTask(MAPP.mapp.getCurrentActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = 555;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

}
