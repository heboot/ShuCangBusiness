package com.zh.business.shucang.adapter.classify;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.index.IndexGoodsAdapter;
import com.zh.business.shucang.databinding.ItemClassifyTypeBinding;
import com.zh.business.shucang.databinding.ItemMainHotBinding;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

public class TypeGoodsAdapter  extends BaseQuickAdapter<String, BaseViewHolder> {

    private final String TAG  = IndexGoodsAdapter.class.getName();

    private int imgWidth = 0 ;

    public TypeGoodsAdapter(int layoutResId, List data) {
        super(layoutResId, data);
//        imgWidth = QMUIDisplayHelper.getScreenWidth(MAPP.mapp) - MAPP.mapp.getResources().getDimensionPixelOffset(R.dimen.x45);
//        LogUtil.e(TAG,imgWidth+"");
        this.imgWidth = imgWidth;
    }

    @Override
    protected void convert(BaseViewHolder helper, String s) {
        ItemClassifyTypeBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.rvList.setLayoutManager(new GridLayoutManager(MAPP.mapp,2));
        List<String> datas  = new ArrayList<>();
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        binding.tvMore.setOnClickListener((v)->{
            IntentUtils.toGoodsListActivity();
        });
        TypeGoodsChildAdapter typeGoodsChildAdapter = new TypeGoodsChildAdapter(R.layout.item_classify_goods,datas);
        binding.rvList.setAdapter(typeGoodsChildAdapter);

    }
}
