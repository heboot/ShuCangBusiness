package com.zh.business.shucang.adapter.order;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.GoodsBean;
import com.zh.business.shucang.R;
import com.zh.business.shucang.databinding.ItemGoodsOrderBinding;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.List;

public class OrderGoodsAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

    private final String TAG = OrderGoodsAdapter.class.getName();

    public OrderGoodsAdapter(List data) {
        super(R.layout.item_goods_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean goodsBean) {
        ItemGoodsOrderBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvNumTip.setText(goodsBean.getNum() == 0?"x1":"x"+goodsBean.getNum());
        binding.tvPrice.setText(goodsBean.getPrice());
        binding.tvTitle.setText(goodsBean.getName());
        binding.getRoot().setOnClickListener((v) -> {
            IntentUtils.toGoodsDetailActivity(goodsBean.getId());
        });
    }


}
