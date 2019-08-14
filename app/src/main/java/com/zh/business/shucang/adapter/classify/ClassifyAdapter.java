package com.zh.business.shucang.adapter.classify;

import android.graphics.Color;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zh.business.shucang.adapter.index.IndexGoodsAdapter;
import com.zh.business.shucang.databinding.ItemClassifyBinding;
import com.zh.business.shucang.databinding.ItemClassifyTypeBinding;
import com.zh.business.shucang.databinding.ItemMainHotBinding;

import java.util.List;

public class ClassifyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private final String TAG = IndexGoodsAdapter.class.getName();

    private int imgWidth = 0;

    private int checkPosition;

    public ClassifyAdapter(int layoutResId, List data) {
        super(layoutResId, data);
//        imgWidth = QMUIDisplayHelper.getScreenWidth(MAPP.mapp) - MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.x45);
//        LogUtil.e(TAG,imgWidth+"");
        this.imgWidth = imgWidth;
    }

    @Override
    protected void convert(BaseViewHolder helper, String s) {
        ItemClassifyBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.getRoot().setOnClickListener((v) -> {
            checkPosition = helper.getLayoutPosition();
            binding.getRoot().setBackgroundColor(0xfffffff);
            notifyDataSetChanged();
        });
        if (checkPosition == helper.getLayoutPosition()) {
            binding.getRoot().setBackgroundColor(0xfffffff);
        } else {
            binding.getRoot().setBackgroundColor(Color.parseColor("#d3d3d3"));
        }


    }
}
