package com.zh.business.shucang.activity.user;

import android.view.View;

import com.zh.business.shucang.R;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityAddAddressBinding;

public class AddAddressActivity extends BaseActivity<ActivityAddAddressBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.tvTitle.setText("新增收货地址");
        setBackVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
