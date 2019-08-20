package com.zh.business.shucang.adapter.index;

import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.bean.GoodsBean;
import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.R;
import com.zh.business.shucang.databinding.ItemMainHotBinding;
import com.zh.business.shucang.utils.ImageUtils;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.List;

public class IndexGoodsAdapter  extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

    private final String TAG  = IndexGoodsAdapter.class.getName();

    private int imgWidth = 0 ;

    public IndexGoodsAdapter(int layoutResId, List data) {
        super(layoutResId, data);
//        imgWidth = QMUIDisplayHelper.getScreenWidth(MAPP.mapp) - MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.x45);
//        LogUtil.e(TAG,imgWidth+"");
        this.imgWidth = imgWidth;
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
    }
}
