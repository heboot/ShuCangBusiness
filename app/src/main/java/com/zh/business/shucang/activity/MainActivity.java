package com.zh.business.shucang.activity;

import com.zh.business.shucang.R;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityMainBinding;
import com.zh.business.shucang.fragment.ClassifyFragment;
import com.zh.business.shucang.fragment.IndexFragment;

import me.yokeyword.fragmentation.ISupportFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private IndexFragment indexFragment = IndexFragment.newInstance();

    private ClassifyFragment classifyFragment = ClassifyFragment.newInstance();

    private ISupportFragment currentFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initUI() {
        mDelegate.loadMultipleRootFragment(binding.flytContainer.getId(), 0, indexFragment, classifyFragment);
    }

    @Override
    public void initData() {
        currentFragment = indexFragment;
    }

    @Override
    public void initListener() {
        binding.llytIndex.setOnClickListener((v) -> {
            mDelegate.showHideFragment(indexFragment, currentFragment);
            currentFragment = indexFragment;
        });
        binding.llytClassify.setOnClickListener((v) -> {
            mDelegate.showHideFragment(classifyFragment, currentFragment);
            currentFragment = classifyFragment;
        });
    }
}
