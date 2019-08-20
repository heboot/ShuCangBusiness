package com.zh.business.shucang.adapter.classify;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.GoodsBean;
import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.adapter.index.IndexGoodsAdapter;
import com.zh.business.shucang.databinding.ItemClassifyGoodsBinding;
import com.zh.business.shucang.databinding.ItemClassifyTypeBinding;
import com.zh.business.shucang.utils.ImageUtils;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.List;

public class TypeGoodsChildAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

    private final String TAG  = TypeGoodsChildAdapter.class.getName();

    public TypeGoodsChildAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean goodsBean) {
        ItemClassifyGoodsBinding binding = DataBindingUtil.bind(helper.itemView);

        ImageUtils.showImage(goodsBean.getCover_image(),binding.ivImg);
        binding.tvPrice.setText(goodsBean.getPrice());
        binding.tvTitle.setText(goodsBean.getName());
        binding.tvInventory.setText(goodsBean.getInven()+"");

        binding.getRoot().setOnClickListener((v)->{
            IntentUtils.toGoodsDetailActivity(goodsBean.getId());
        });
    }
}
