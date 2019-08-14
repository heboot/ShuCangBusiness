package com.zh.business.shucang.fragment;


import android.os.Bundle;

import com.zh.business.shucang.R;
import com.zh.business.shucang.activity.order.MyOrderActivity;
import com.zh.business.shucang.activity.user.AddAddressActivity;
import com.zh.business.shucang.activity.user.AddressListActivity;
import com.zh.business.shucang.activity.user.CommentActivity;
import com.zh.business.shucang.activity.user.FavActivity;
import com.zh.business.shucang.activity.user.HTMLActivity;
import com.zh.business.shucang.activity.user.InfoActivity;
import com.zh.business.shucang.activity.user.SettingActivity;
import com.zh.business.shucang.base.BaseFragment;
import com.zh.business.shucang.databinding.FragmentMyBinding;
import com.zh.business.shucang.utils.IntentUtils;

public class MyFragment extends BaseFragment<FragmentMyBinding> {

    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initUI() {

        binding.includeToolbar.tvTitle.setText("会员中心");

        binding.includeOrder.tvTitle.setText("我的全部订单");
        binding.includeOrder.tvSubTitle.setText("查看");
        binding.includeOrder.ivIcon.setBackgroundResource(R.mipmap.icon_my_orders);

        binding.includeAddress.tvTitle.setText("收货地址");
        binding.includeAddress.ivIcon.setBackgroundResource(R.mipmap.icon_my_address);

        binding.includeFav.tvTitle.setText("我的收藏");
        binding.includeFav.ivIcon.setBackgroundResource(R.mipmap.icon_my_fav);

        binding.includeComment.tvTitle.setText("我的评价");
        binding.includeComment.ivIcon.setBackgroundResource(R.mipmap.icon_my_comment);

        binding.includeService.tvTitle.setText("客服中心");
        binding.includeService.tvSubTitle.setText("咨询客服");
        binding.includeService.ivIcon.setBackgroundResource(R.mipmap.icon_my_service);

        binding.includeSetting.tvTitle.setText("设置");
        binding.includeSetting.ivIcon.setBackgroundResource(R.mipmap.icon_my_setting);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        binding.ivAvatarBg.setOnClickListener((v)->{
            IntentUtils.doIntent(InfoActivity.class);
        });
        binding.includeAddress.getRoot().setOnClickListener((v)->{
            IntentUtils.doIntent(AddressListActivity.class);
        });
        binding.includeFav.getRoot().setOnClickListener((v)->{
            IntentUtils.doIntent(FavActivity.class);
        });
        binding.includeOrder.getRoot().setOnClickListener((v)->{
            IntentUtils.doIntent(MyOrderActivity.class);
        });
        binding.includeOrderTypes.getRoot().setOnClickListener((v)->{
            IntentUtils.doIntent(MyOrderActivity.class);
        });
        binding.includeSetting.getRoot().setOnClickListener((v)->{
            IntentUtils.doIntent(SettingActivity.class);
        });
        binding.includeComment.getRoot().setOnClickListener((v)->{
            IntentUtils.doIntent(CommentActivity.class);
        });
    }
}
