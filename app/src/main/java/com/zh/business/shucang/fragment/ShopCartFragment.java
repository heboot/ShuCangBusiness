package com.zh.business.shucang.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.shopcart.ShopCartAdapter;
import com.zh.business.shucang.base.BaseFragment;
import com.zh.business.shucang.databinding.ActivityShopCartBinding;

import java.util.ArrayList;
import java.util.List;

public class ShopCartFragment extends BaseFragment<ActivityShopCartBinding> {

    private ShopCartAdapter shopCartAdapter;

    public static ShopCartFragment newInstance() {
        Bundle args = new Bundle();
        ShopCartFragment fragment = new ShopCartFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_cart;
    }

    @Override
    public void initUI() {
        binding.rvList.setLayoutManager(new LinearLayoutManager(_mActivity, RecyclerView.VERTICAL, false));
    }

    @Override
    public void initData() {
        List<String> datas = new ArrayList();
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
//        int imgWidth = (QMUIDisplayHelper.getScreenWidth(MAPP.mapp)  - MAPP.mapp.getResources().getDimensionPixelSize(R.dimen.x45))/2;
//        LogUtil.e(TAG, imgWidth + "");
        shopCartAdapter = new ShopCartAdapter(datas);
        binding.rvList.setAdapter(shopCartAdapter);
    }

    @Override
    public void initListener() {

    }
}
