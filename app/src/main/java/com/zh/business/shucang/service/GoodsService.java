package com.zh.business.shucang.service;

import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.R;
import com.zh.business.shucang.activity.goods.GoodsDetailActivity;
import com.zh.business.shucang.http.HttpObserver;
import com.zh.business.shucang.utils.SignUtils;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GoodsService {

    private QMUITipDialog tipDialog;

    public void addShopCart(String goodsId) {
        Map params = SignUtils.getNormalParams();
        params.put(MKey.ID, goodsId);
        HttpClient.Builder.getServer().addCar(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(MAPP.mapp.getCurrentActivity(), baseBean.getMsg(), true);
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(MAPP.mapp.getCurrentActivity(), baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}
