package com.zh.business.shucang.adapter.classify;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.ClassifyBean;
import com.zh.business.shucang.adapter.index.IndexGoodsAdapter;
import com.zh.business.shucang.databinding.ItemClassifyBinding;
import com.zh.business.shucang.databinding.ItemClassifyTopBinding;
import com.zh.business.shucang.utils.ImageUtils;

import java.util.List;

public class ClassifyTopAdapter extends BaseQuickAdapter<ClassifyBean.ClassBean, BaseViewHolder> {

    private final String TAG  = IndexGoodsAdapter.class.getName();

    private int imgWidth = 0 ;

    public ClassifyTopAdapter(int layoutResId, List data) {
        super(layoutResId, data);
//        imgWidth = QMUIDisplayHelper.getScreenWidth(MAPP.mapp) - MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.x45);
//        LogUtil.e(TAG,imgWidth+"");
        this.imgWidth = imgWidth;
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassifyBean.ClassBean s) {

        ItemClassifyTopBinding binding =  DataBindingUtil.bind(helper.itemView);
        ImageUtils.showImage(s.getImage(),binding.ivImg);




    }
}
