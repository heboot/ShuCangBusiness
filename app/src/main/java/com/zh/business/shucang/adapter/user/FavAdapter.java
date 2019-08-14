package com.zh.business.shucang.adapter.user;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.shopcart.ShopCartAdapter;
import com.zh.business.shucang.databinding.ItemAddressBinding;
import com.zh.business.shucang.databinding.ItemFavBinding;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.List;

public class FavAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private final String TAG = ShopCartAdapter.class.getName();


    public FavAdapter(List data) {
        super(R.layout.item_fav, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String s) {
        ItemFavBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.getRoot().setOnClickListener((v)->{
            IntentUtils.toGoodsDetailActivity();
        });
    }
}
