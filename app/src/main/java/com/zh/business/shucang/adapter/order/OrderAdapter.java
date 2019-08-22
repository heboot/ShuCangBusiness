package com.zh.business.shucang.adapter.order;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.GoodsBean;
import com.waw.hr.mutils.bean.GoodsDetailBean;
import com.waw.hr.mutils.bean.OrderListBean;
import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.R;
import com.zh.business.shucang.activity.order.MyOrderActivity;
import com.zh.business.shucang.adapter.shopcart.ShopCartAdapter;
import com.zh.business.shucang.common.OrderDetailType;
import com.zh.business.shucang.common.OrderType;
import com.zh.business.shucang.databinding.ItemFavBinding;
import com.zh.business.shucang.databinding.ItemOrderBinding;
import com.zh.business.shucang.fragment.OrderFragment;
import com.zh.business.shucang.utils.ImageUtils;
import com.zh.business.shucang.utils.IntentUtils;

import java.lang.ref.WeakReference;
import java.util.List;

public class OrderAdapter extends BaseQuickAdapter<OrderListBean, BaseViewHolder> {

    private final String TAG = OrderAdapter.class.getName();

    private WeakReference<OrderFragment> weakReference;

    public OrderAdapter(List data,WeakReference<OrderFragment> weakReference) {
        super(R.layout.item_order, data);
        this.weakReference = weakReference;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean goodsBean) {
        ItemOrderBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvTotalPrice.setText(goodsBean.getTotal_price() + "");
        switch (goodsBean.getStatus()) {
            case OrderType.WAIT:
                binding.tvCancel.setVisibility(View.VISIBLE);
                binding.tvStatus.setText("待支付");
                break;
            case OrderType.PROGRESS:
                binding.tvStatus.setText("配送中");
                binding.tvAction.setText("确认收货");
                binding.tvCancel.setVisibility(View.GONE);
                break;
            case OrderType.FINISH:
                binding.tvStatus.setText("已完成");
                binding.tvAction.setVisibility(View.GONE);
                binding.tvCancel.setVisibility(View.GONE);
                break;
        }
        binding.rvList.setLayoutManager(new LinearLayoutManager(MAPP.mapp, RecyclerView.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        binding.tvCancel.setOnClickListener((v)->{
            weakReference.get().showCancelDialog(goodsBean.getId());
        });

        binding.rvList.setAdapter(new OrderItemAdapter(goodsBean.getDeal()));


        binding.getRoot().setOnClickListener((v)->{
            IntentUtils.toOrderDetailActivity(OrderDetailType.MY_ORDER,goodsBean.getId());
        });
    }
}
