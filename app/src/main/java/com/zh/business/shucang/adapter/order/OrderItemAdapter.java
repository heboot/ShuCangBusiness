package com.zh.business.shucang.adapter.order;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.GoodsBean;
import com.waw.hr.mutils.bean.OrderListBean;
import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.R;
import com.zh.business.shucang.databinding.ItemOrderBinding;
import com.zh.business.shucang.databinding.ItemOrderChildBinding;
import com.zh.business.shucang.utils.ImageUtils;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.List;

public class OrderItemAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

    private final String TAG = OrderItemAdapter.class.getName();


    public OrderItemAdapter(List data) {
        super(R.layout.item_order_child, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean goodsBean) {
        ItemOrderChildBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvNumTip.setText(goodsBean.getNum() == 0 ? "x1" : "x" + goodsBean.getNum());
        binding.tvPrice.setText(goodsBean.getPrice());
        binding.tvTitle.setText(goodsBean.getName());
        ImageUtils.showImage(goodsBean.getCover_image(),binding.ivImg);

    }
}
