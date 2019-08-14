package com.zh.business.shucang.activity.login;

import android.view.View;

import com.zh.business.shucang.R;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityRegisterBinding;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("注册");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
