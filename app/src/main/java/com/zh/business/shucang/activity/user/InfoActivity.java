package com.zh.business.shucang.activity.user;

import com.zh.business.shucang.R;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityInfoBinding;

public class InfoActivity extends BaseActivity<ActivityInfoBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_info;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.tvTitle.setText("用户资料");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
