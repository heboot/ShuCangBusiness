package com.zh.business.shucang.utils;

import android.content.Intent;

import com.waw.hr.mutils.MKey;
import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.activity.goods.GoodsDetailActivity;
import com.zh.business.shucang.activity.goods.GoodsListActivity;
import com.zh.business.shucang.activity.order.OrderDetailActivity;
import com.zh.business.shucang.activity.user.AddressListActivity;
import com.zh.business.shucang.common.AddressListType;

public class IntentUtils {

    private static Intent intent;


    public static void doIntent(Class cls){
        intent = new Intent(MAPP.mapp.getCurrentActivity(),cls);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void toGoodsListActivity(){
        intent = new Intent(MAPP.mapp.getCurrentActivity(), GoodsListActivity.class);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void toGoodsDetailActivity(int id){
        intent = new Intent(MAPP.mapp.getCurrentActivity(), GoodsDetailActivity.class);
        intent.putExtra(MKey.ID,String.valueOf(id));
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }
    public static void toGoodsDetailActivity( ){
        intent = new Intent(MAPP.mapp.getCurrentActivity(), GoodsDetailActivity.class);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }
    public static void toOrderDetailActivity(){
        intent = new Intent(MAPP.mapp.getCurrentActivity(), OrderDetailActivity.class);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void toAddressListActivity(AddressListType addressListType){
        intent = new Intent(MAPP.mapp.getCurrentActivity(), AddressListActivity.class);
        intent.putExtra(MKey.TYPE,addressListType);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

}
