package com.zh.business.shucang.utils;

import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.zh.business.shucang.MAPP;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PayUtils {

    private static PayUtils payUtils;

    public static PayUtils getInstance(){
        if(payUtils == null){
              payUtils = new PayUtils();
        }
        return payUtils;
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
