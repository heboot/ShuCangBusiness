package com.zh.business.shucang.activity.order;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.zh.business.shucang.R;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.common.OrderType;
import com.zh.business.shucang.databinding.ActivityMyOrdersBinding;
import com.zh.business.shucang.fragment.OrderFragment;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends BaseActivity<ActivityMyOrdersBinding> {

    private ArrayList<Fragment> fragmentList = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_orders;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("我的订单");
    }

    @Override
    public void initData() {
        fragmentList.add(OrderFragment.newInstance(OrderType.ALL));
        fragmentList.add(OrderFragment.newInstance(OrderType.WAIT));
        fragmentList.add(OrderFragment.newInstance(OrderType.PROGRESS));
        fragmentList.add(OrderFragment.newInstance(OrderType.FINISH));
        String [] titles = {"全部","待付款","配送中","已完成"};
        binding.tab.setViewPager(binding.vpContainer,titles,this,fragmentList);

        /** 关联ViewPager,用于连适配器都不想自己实例化的情况 */
//        public void setViewPager(ViewPager vp, String[] titles, FragmentActivity fa, ArrayList<Fragment> fragments)
    }

    @Override
    public void initListener() {

    }
}
