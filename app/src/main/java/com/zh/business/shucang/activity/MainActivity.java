package com.zh.business.shucang.activity;

import com.zh.business.shucang.R;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityMainBinding;
import com.zh.business.shucang.fragment.IndexFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private IndexFragment indexFragment = IndexFragment.newInstance();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initUI() {
        mDelegate.loadMultipleRootFragment(binding.flytContainer.getId(), 0, indexFragment);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
