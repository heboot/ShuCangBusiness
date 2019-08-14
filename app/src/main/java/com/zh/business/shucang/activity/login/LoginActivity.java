package com.zh.business.shucang.activity.login;

import android.view.View;

import com.zh.business.shucang.R;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityLoginBinding;
import com.zh.business.shucang.utils.IntentUtils;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("登录");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        binding.tvRegister.setOnClickListener((v)->{
            IntentUtils.doIntent(RegisterActivity.class);
        });
    }
}
