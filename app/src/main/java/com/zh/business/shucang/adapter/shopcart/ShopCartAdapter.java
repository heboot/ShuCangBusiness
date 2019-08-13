package com.zh.business.shucang.adapter.shopcart;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.index.IndexGoodsAdapter;
import com.zh.business.shucang.databinding.ItemMainHotBinding;
import com.zh.business.shucang.databinding.ItemShopCartBinding;

import java.util.List;

public class ShopCartAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private final String TAG = ShopCartAdapter.class.getName();


    public ShopCartAdapter(List data) {
        super(R.layout.item_shop_cart, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String s) {
        ItemShopCartBinding binding = DataBindingUtil.bind(helper.itemView);

    }
}
