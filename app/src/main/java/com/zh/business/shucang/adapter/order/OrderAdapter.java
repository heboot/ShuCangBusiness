package com.zh.business.shucang.adapter.order;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.shopcart.ShopCartAdapter;
import com.zh.business.shucang.databinding.ItemFavBinding;
import com.zh.business.shucang.databinding.ItemOrderBinding;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.List;

public class OrderAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private final String TAG = OrderAdapter.class.getName();


    public OrderAdapter(List data) {
        super(R.layout.item_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String s) {
        ItemOrderBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.getRoot().setOnClickListener((v)->{
            IntentUtils.toGoodsDetailActivity();
        });
    }
}
