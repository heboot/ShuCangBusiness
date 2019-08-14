package com.zh.business.shucang.adapter.index;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zh.business.shucang.R;
import com.zh.business.shucang.databinding.ItemAdBinding;
import com.zh.business.shucang.databinding.ItemOrderBinding;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.List;

public class ADAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private final String TAG = ADAdapter.class.getName();


    public ADAdapter(List data) {
        super(R.layout.item_ad, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String s) {
        ItemAdBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.getRoot().setOnClickListener((v)->{
            IntentUtils.toGoodsListActivity();
        });
    }
}
