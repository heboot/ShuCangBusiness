package com.zh.business.shucang.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.waw.hr.mutils.LogUtil;
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
        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq baseReq) {
        LogUtil.e("ceshi>>>", JSON.toJSONString(baseReq));
    }

    @Override
    public void onResp(BaseResp baseResp) {
        RxBus.getInstance().post(new UserEvent.WxPayEvent(baseResp.errCode));
        finish();
    }
}
