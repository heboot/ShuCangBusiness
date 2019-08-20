package com.zh.business.shucang.activity;

import com.zh.business.shucang.R;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityMainBinding;
import com.zh.business.shucang.fragment.ClassifyFragment;
import com.zh.business.shucang.fragment.IndexFragment;
import com.zh.business.shucang.fragment.MyFragment;
import com.zh.business.shucang.fragment.ShopCartFragment;
import com.zh.business.shucang.utils.LocationUtils;

import me.yokeyword.fragmentation.ISupportFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private IndexFragment indexFragment = IndexFragment.newInstance();

    private ClassifyFragment classifyFragment = ClassifyFragment.newInstance();

    private ShopCartFragment shopCartFragment = ShopCartFragment.newInstance();

    private MyFragment myFragment = MyFragment.newInstance();

    private ISupportFragment currentFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initUI() {
        mDelegate.loadMultipleRootFragment(binding.flytContainer.getId(), 0, indexFragment, classifyFragment,shopCartFragment,myFragment);
        binding.ivIndex.setBackgroundResource(R.mipmap.icon_index_fouse);
        binding.ivClassify.setBackgroundResource(R.mipmap.icon_classify_normal);
        binding.ivShopcart.setBackgroundResource(R.mipmap.icon_shopcart_normal);
        binding.ivMy.setBackgroundResource(R.mipmap.icon_my_normal);
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
            binding.ivIndex.setBackgroundResource(R.mipmap.icon_index_fouse);
            binding.ivClassify.setBackgroundResource(R.mipmap.icon_classify_normal);
            binding.ivShopcart.setBackgroundResource(R.mipmap.icon_shopcart_normal);
            binding.ivMy.setBackgroundResource(R.mipmap.icon_my_normal);
        });
        binding.llytClassify.setOnClickListener((v) -> {
            mDelegate.showHideFragment(classifyFragment, currentFragment);
            currentFragment = classifyFragment;
            binding.ivClassify.setBackgroundResource(R.mipmap.icon_classify_fouse);
            binding.ivIndex.setBackgroundResource(R.mipmap.icon_index_normal);
            binding.ivShopcart.setBackgroundResource(R.mipmap.icon_shopcart_normal);
            binding.ivMy.setBackgroundResource(R.mipmap.icon_my_normal);
        });
        binding.llytShopcart.setOnClickListener((v) -> {
            mDelegate.showHideFragment(shopCartFragment, currentFragment);
            currentFragment = shopCartFragment;
            binding.ivClassify.setBackgroundResource(R.mipmap.icon_classify_normal);
            binding.ivIndex.setBackgroundResource(R.mipmap.icon_index_normal);
            binding.ivShopcart.setBackgroundResource(R.mipmap.icon_shopcart_fouse);
            binding.ivMy.setBackgroundResource(R.mipmap.icon_my_normal);
        });
        binding.llytMy.setOnClickListener((v) -> {
            mDelegate.showHideFragment(myFragment, currentFragment);
            currentFragment = myFragment;
            binding.ivClassify.setBackgroundResource(R.mipmap.icon_classify_normal);
            binding.ivIndex.setBackgroundResource(R.mipmap.icon_index_normal);
            binding.ivShopcart.setBackgroundResource(R.mipmap.icon_shopcart_normal);
            binding.ivMy.setBackgroundResource(R.mipmap.icon_my_fouse);
        });
    }
}
