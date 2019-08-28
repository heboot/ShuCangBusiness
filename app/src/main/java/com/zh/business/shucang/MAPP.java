package com.zh.business.shucang;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class MAPP extends Application {

    public static MAPP mapp;

    private Activity currentActivity;

    public static IWXAPI wxapi;

    @Override
    public void onCreate() {
        super.onCreate();
        mapp = this;
        regToWx();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                setCurrentActivity(activity);
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    private void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    /**
     * 注册到微信
     */
    private void regToWx() {
        wxapi = WXAPIFactory.createWXAPI(this.getApplicationContext(),
                "wx37c1c72fd81bd8bf", true);
        wxapi.registerApp("wx37c1c72fd81bd8bf");
    }

    public static IWXAPI getWxapi() {
        return wxapi;
    }

    public static void setWxapi(IWXAPI wxapi) {
        MAPP.wxapi = wxapi;
    }
}
