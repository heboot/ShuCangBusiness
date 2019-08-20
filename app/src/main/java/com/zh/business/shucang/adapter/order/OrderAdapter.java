package com.zh.business.shucang.adapter.order;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.GoodsBean;
import com.waw.hr.mutils.bean.GoodsDetailBean;
import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.shopcart.ShopCartAdapter;
import com.zh.business.shucang.databinding.ItemFavBinding;
import com.zh.business.shucang.databinding.ItemOrderBinding;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.List;

public class OrderAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

    private final String TAG = OrderAdapter.class.getName();


    public OrderAdapter(List data) {
        super(R.layout.item_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean goodsBean) {
        ItemOrderBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.getRoot().setOnClickListener((v)->{
            IntentUtils.toGoodsDetailActivity(goodsBean.getId());
        });
    }
}
