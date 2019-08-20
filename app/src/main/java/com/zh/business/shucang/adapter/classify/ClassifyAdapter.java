package com.zh.business.shucang.adapter.classify;

import android.graphics.Color;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.ClassifyBean;
import com.zh.business.shucang.adapter.index.IndexGoodsAdapter;
import com.zh.business.shucang.databinding.ItemClassifyBinding;
import com.zh.business.shucang.databinding.ItemClassifyTypeBinding;
import com.zh.business.shucang.databinding.ItemMainHotBinding;
import com.zh.business.shucang.fragment.ClassifyFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class ClassifyAdapter extends BaseQuickAdapter<ClassifyBean.ClassBean, BaseViewHolder> {

    private final String TAG = ClassifyAdapter.class.getName();


    private int checkPosition;

   private WeakReference<ClassifyFragment> classifyFragment;

    public ClassifyAdapter(int layoutResId, List data, ClassifyFragment classifyFragment) {
        super(layoutResId, data);
        this.classifyFragment = new WeakReference<ClassifyFragment>(classifyFragment);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassifyBean.ClassBean s) {
        ItemClassifyBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvTitle.setText(s.getName());
        binding.getRoot().setOnClickListener((v) -> {
            checkPosition = helper.getLayoutPosition();
            binding.getRoot().setBackgroundColor(0xfffffff);
            notifyDataSetChanged();
            classifyFragment.get().getClassGoodsData(s.getId());
        });
        if (checkPosition == helper.getLayoutPosition()) {
            binding.getRoot().setBackgroundColor(0xfffffff);
        } else {
            binding.getRoot().setBackgroundColor(Color.parseColor("#d3d3d3"));
        }


    }
}
