package com.zh.business.shucang.utils;

import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zh.business.shucang.MAPP;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PayUtils {

    private static PayUtils payUtils;

    private static IWXAPI msgApi = null;

    public static PayUtils getInstance() {
        if (payUtils == null) {
            payUtils = new PayUtils();
        }
        return payUtils;
    }

    public void wxPay(Map<String, String> map) {
        msgApi = WXAPIFactory.createWXAPI(MAPP.mapp, null);
        // 将该app注册到微信

        msgApi.registerApp("wx37c1c72fd81bd8bf");

        PayReq request = new PayReq();
        request.appId = (String) map.get("appId");
//        request.partnerId = (String) map.get("appId");
//        request.prepayId = (String) map.get("appId");
        request.partnerId = "1519790121";//商户ID
        request.prepayId = (String) map.get("package").substring(10,  map.get("package").length());
        request.packageValue = (String) map.get("package");
        request.nonceStr = (String) map.get("nonceStr");
        request.timeStamp = (String) map.get("timeStamp");
        request.sign = (String) map.get("paySign");
        msgApi.sendReq(request);


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
