package com.zh.business.shucang.adapter.user;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.GoodsBean;
import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.shopcart.ShopCartAdapter;
import com.zh.business.shucang.databinding.ItemAddressBinding;
import com.zh.business.shucang.databinding.ItemFavBinding;
import com.zh.business.shucang.utils.ImageUtils;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.List;

public class FavAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

    private final String TAG = ShopCartAdapter.class.getName();


    public FavAdapter(List data) {
        super(R.layout.item_fav, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean goodsBean) {
        ItemFavBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvTitle.setText(goodsBean.getName());
        binding.tvPrice.setText(goodsBean.getPrice());
        ImageUtils.showImage(goodsBean.getCover_image(), binding.ivImg);
        binding.tvNumTip.setText("x" + String.valueOf(goodsBean.getNum()));
        binding.getRoot().setOnClickListener((v)->{
            IntentUtils.toGoodsDetailActivity();
        });
    }
}
