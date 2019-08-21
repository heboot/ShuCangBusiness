package com.zh.business.shucang.utils;

import android.content.Intent;

import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.bean.AddressBean;
import com.waw.hr.mutils.bean.ImmediatelyBean;
import com.waw.hr.mutils.bean.OrderSubBean;
import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.activity.goods.GoodsDetailActivity;
import com.zh.business.shucang.activity.goods.GoodsListActivity;
import com.zh.business.shucang.activity.order.OrderDetailActivity;
import com.zh.business.shucang.activity.user.AddAddressActivity;
import com.zh.business.shucang.activity.user.AddressListActivity;
import com.zh.business.shucang.common.AddressListType;
import com.zh.business.shucang.common.NewAddressType;
import com.zh.business.shucang.common.OrderDetailType;

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

    public static void toOrderDetailActivity(OrderDetailType orderDetailType, ImmediatelyBean immediatelyBean){
        intent = new Intent(MAPP.mapp.getCurrentActivity(), OrderDetailActivity.class);
        intent.putExtra(MKey.TYPE,orderDetailType);
        immediatelyBean.getGoods().setNum(1);
        intent.putExtra(MKey.DATA,immediatelyBean);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void toOrderDetailActivity(OrderDetailType orderDetailType, OrderSubBean orderSubBean){
        intent = new Intent(MAPP.mapp.getCurrentActivity(), OrderDetailActivity.class);
        intent.putExtra(MKey.TYPE,orderDetailType);
        intent.putExtra(MKey.DATA,orderSubBean);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void toAddressListActivity(AddressListType addressListType){
        intent = new Intent(MAPP.mapp.getCurrentActivity(), AddressListActivity.class);
        intent.putExtra(MKey.TYPE,addressListType);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void toNewAddressActivity(NewAddressType newAddressType){
        intent = new Intent(MAPP.mapp.getCurrentActivity(), AddAddressActivity.class);
        intent.putExtra(MKey.TYPE,newAddressType);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }

    public static void toNewAddressActivity(NewAddressType newAddressType, AddressBean addressBean){
        intent = new Intent(MAPP.mapp.getCurrentActivity(), AddAddressActivity.class);
        intent.putExtra(MKey.TYPE,newAddressType);
        intent.putExtra(MKey.DATA,addressBean);
        MAPP.mapp.getCurrentActivity().startActivity(intent);
    }



}
