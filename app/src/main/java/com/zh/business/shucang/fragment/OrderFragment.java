package com.zh.business.shucang.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.order.OrderAdapter;
import com.zh.business.shucang.base.BaseFragment;
import com.zh.business.shucang.databinding.FragmentMyOrderBinding;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends BaseFragment<FragmentMyOrderBinding> {

    private OrderAdapter orderAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_order;
    }

    @Override
    public void initUI() {
        binding.rvList.setLayoutManager(new LinearLayoutManager(_mActivity, RecyclerView.VERTICAL, false));
    }

    @Override
    public void initData() {
        List<String> datas = new ArrayList<>();
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        orderAdapter = new OrderAdapter(datas);
        binding.rvList.setAdapter(orderAdapter);
    }

    @Override
    public void initListener() {

    }
}
