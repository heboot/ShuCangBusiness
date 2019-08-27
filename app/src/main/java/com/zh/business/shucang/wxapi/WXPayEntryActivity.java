package com.zh.business.shucang.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.waw.hr.mutils.event.UserEvent;
import com.waw.hr.mutils.rxbus.RxBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //注册API
        api = WXAPIFactory.createWXAPI(this, "wx37c1c72fd81bd8bf", false);
        api.registerApp("wx37c1c72fd81bd8bf");
//第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            if (!api.handleIntent(getIntent(), this)) {
                finish();
            }
//            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        RxBus.getInstance().post(new UserEvent.WxPayEvent(baseResp.errCode));
//        switch (baseResp.errCode) {
//            case 0:
//
//                break;
//            case -1:
//                ToastUtils.showShort(MyApplication.getContext(),"支付失败");
//                PayListenerUtils.getInstance(this).addError(PayUtils.PAY_TYPE_WX);
//                break;
//            case -2:
//                ToastUtils.showShort(MyApplication.getContext(),"取消支付");
//                PayListenerUtils.getInstance(this).addCancel(PayUtils.PAY_TYPE_WX);
//                break;
//            default:
//                ToastUtils.showShort(MyApplication.getContext(),"支付失败");
//                PayListenerUtils.getInstance(this).addError(PayUtils.PAY_TYPE_WX);
//                break;
//        }
        finish();
    }
}
