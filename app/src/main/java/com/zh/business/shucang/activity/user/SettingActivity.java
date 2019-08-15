package com.zh.business.shucang.activity.user;

import android.view.View;

import com.zh.business.shucang.R;
import com.zh.business.shucang.activity.login.LoginActivity;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivitySettingBinding;
import com.zh.business.shucang.utils.IntentUtils;

public class SettingActivity extends BaseActivity<ActivitySettingBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("设置");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        binding.tvLogout.setOnClickListener((v)->{
            IntentUtils.doIntent( LoginActivity.class);
        });
        binding.tvAlterPwd.setOnClickListener((v)->{

        });
    }
}
