package com.zh.business.shucang.utils;

import android.content.Intent;

import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.activity.goods.GoodsListActivity;

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



}
