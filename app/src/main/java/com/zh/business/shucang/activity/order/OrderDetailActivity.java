package com.zh.business.shucang.activity.order;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.order.OrderGoodsAdapter;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.common.AddressListType;
import com.zh.business.shucang.databinding.ActivityOrderDetailBinding;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends BaseActivity<ActivityOrderDetailBinding> {

    private OrderGoodsAdapter orderGoodsAdapter;

    private boolean isOpen = true;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("订单详情");
        binding.rvList.setFocusable(false);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        binding.rvList.setFocusable(false);
    }

    @Override
    public void initData() {
        List<String> datas = new ArrayList();
        datas.add("1");
        datas.add("1");
        datas.add("1");
        orderGoodsAdapter = new OrderGoodsAdapter(datas);
        binding.rvList.setAdapter(orderGoodsAdapter);
    }

    @Override
    public void initListener() {
        binding.tvGoodsList.setOnClickListener((v) -> {
            if (isOpen) {
                isOpen = false;
                binding.rvList.setVisibility(View.GONE);
                binding.vArrow.setBackgroundResource(R.mipmap.icon_arrow_down);
            } else {
                isOpen = true;
                binding.rvList.setVisibility(View.VISIBLE);
                binding.vArrow.setBackgroundResource(R.mipmap.icon_arrow_up);
            }
        });
        binding.tvAlterAddress.setOnClickListener((v)->{
            IntentUtils.toAddressListActivity(AddressListType.CHOOSE);
        });
    }
}
