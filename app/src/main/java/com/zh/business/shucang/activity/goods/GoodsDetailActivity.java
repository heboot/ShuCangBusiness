package com.zh.business.shucang.activity.goods;

import android.view.View;

import com.zh.business.shucang.R;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityGoodsDetailBinding;

public class GoodsDetailActivity extends BaseActivity<ActivityGoodsDetailBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.tvTitle.setText("商品详情");
        setBackVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
