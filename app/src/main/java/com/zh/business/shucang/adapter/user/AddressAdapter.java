package com.zh.business.shucang.adapter.user;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.shopcart.ShopCartAdapter;
import com.zh.business.shucang.databinding.ItemAddressBinding;

import java.util.List;

public class AddressAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private final String TAG = ShopCartAdapter.class.getName();


    public AddressAdapter(List data) {
        super(R.layout.item_address, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String s) {
        ItemAddressBinding binding = DataBindingUtil.bind(helper.itemView);

    }
}
