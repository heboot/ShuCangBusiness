package com.zh.business.shucang.adapter.index;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.ClassifyBean;
import com.waw.hr.mutils.bean.IndexBean;
import com.zh.business.shucang.R;
import com.zh.business.shucang.databinding.ItemAdBinding;
import com.zh.business.shucang.databinding.ItemOrderBinding;
import com.zh.business.shucang.utils.ImageUtils;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.List;

public class ADAdapter extends BaseQuickAdapter<ClassifyBean.ClassBean, BaseViewHolder> {

    private final String TAG = ADAdapter.class.getName();


    public ADAdapter(List data) {
        super(R.layout.item_ad, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,ClassifyBean.ClassBean bean) {
        ItemAdBinding binding = DataBindingUtil.bind(helper.itemView);
        ImageUtils.showImage(bean.getImage(),binding.ivImg);
        binding.getRoot().setOnClickListener((v)->{
            IntentUtils.toGoodsListActivity();
        });
    }
}
