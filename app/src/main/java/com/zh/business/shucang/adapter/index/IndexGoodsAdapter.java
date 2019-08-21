package com.zh.business.shucang.adapter.index;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.GoodsBean;
import com.zh.business.shucang.databinding.ItemMainHotBinding;
import com.zh.business.shucang.service.GoodsService;
import com.zh.business.shucang.utils.ImageUtils;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.List;

public class IndexGoodsAdapter  extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

    private final String TAG  = IndexGoodsAdapter.class.getName();

    private GoodsService goodsService;

    public IndexGoodsAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean goodsBean) {
        ItemMainHotBinding binding = DataBindingUtil.bind(helper.itemView);
        ImageUtils.showImage(goodsBean.getCover_image(),binding.ivImg);
        binding.tvPrice.setText(goodsBean.getPrice());
        binding.tvTitle.setText(goodsBean.getName());
        binding.tvSale.setText(goodsBean.getSales()+"");
        binding.tvInventory.setText(goodsBean.getInven()+"");
        binding.getRoot().setOnClickListener((v)->{
            IntentUtils.toGoodsDetailActivity(goodsBean.getId());
        });
        binding.vAddCart.setOnClickListener((v)->{
            if(goodsService == null){
                goodsService = new GoodsService();
            }
            goodsService.addShopCart(String.valueOf(goodsBean.getId()));
        });
    }
}
