package com.zh.business.shucang.adapter.order;

import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.GoodsBean;
import com.zh.business.shucang.R;
import com.zh.business.shucang.activity.order.OrderDetailActivity;
import com.zh.business.shucang.common.OrderDetailType;
import com.zh.business.shucang.databinding.ItemGoodsOrderBinding;
import com.zh.business.shucang.utils.IntentUtils;

import java.lang.ref.WeakReference;
import java.util.List;

public class OrderGoodsAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

    private final String TAG = OrderGoodsAdapter.class.getName();

    private OrderDetailType type;

    private WeakReference<OrderDetailActivity> weakReference;

    public OrderGoodsAdapter(List data, OrderDetailType type, WeakReference<OrderDetailActivity> weakReference) {
        super(R.layout.item_goods_order, data);
        this.type = type;
        this.weakReference = weakReference;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean goodsBean) {

        ItemGoodsOrderBinding binding = DataBindingUtil.bind(helper.itemView);
        if (type == OrderDetailType.BUY) {
            binding.vJia.setVisibility(View.VISIBLE);
            binding.vJian.setVisibility(View.VISIBLE);
            binding.tvNum.setVisibility(View.VISIBLE);
        } else {
            binding.vJia.setVisibility(View.GONE);
            binding.vJian.setVisibility(View.GONE);
            binding.tvNum.setVisibility(View.GONE);
        }

        binding.tvNumTip.setText(goodsBean.getNum() == 0 ? "x1" : "x" + goodsBean.getNum());
        binding.tvPrice.setText(goodsBean.getPrice());
        binding.tvTitle.setText(goodsBean.getName());


        binding.vJia.setOnClickListener((v) -> {
            goodsBean.setNum(goodsBean.getNum() + 1);
            binding.tvNum.setText(goodsBean.getNum()+"");
            binding.tvNumTip.setText("x" + goodsBean.getNum());
            weakReference.get().calculateSelectedGoodsPrice(goodsBean.getNum());
            notifyDataSetChanged();
        });

        binding.vJian.setOnClickListener((v) -> {
            if(goodsBean.getNum() == 1){
                return;
            }
            goodsBean.setNum(goodsBean.getNum()-1);
            binding.tvNum.setText(goodsBean.getNum()+"");
            binding.tvNumTip.setText("x" + goodsBean.getNum());
            weakReference.get().calculateSelectedGoodsPrice(goodsBean.getNum());   notifyDataSetChanged();
        });

        binding.getRoot().setOnClickListener((v) -> {
            IntentUtils.toGoodsDetailActivity(goodsBean.getId());
        });
    }


}
