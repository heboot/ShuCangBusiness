package com.zh.business.shucang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.classify.ClassifyAdapter;
import com.zh.business.shucang.adapter.classify.ClassifyTopAdapter;
import com.zh.business.shucang.adapter.classify.TypeGoodsAdapter;
import com.zh.business.shucang.base.BaseFragment;
import com.zh.business.shucang.databinding.FragmentClassifyBinding;
import com.zh.business.shucang.databinding.HeadClassifyTopBinding;
import com.zh.business.shucang.databinding.ItemClassifyTopBinding;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

public class ClassifyFragment extends BaseFragment<FragmentClassifyBinding> {

    private ClassifyAdapter classifyAdapter;

    private TypeGoodsAdapter typeGoodsAdapter;

    public static ClassifyFragment newInstance() {
        Bundle args = new Bundle();
        ClassifyFragment fragment = new ClassifyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_classify;
    }

    @Override
    public void initUI() {
        binding.rvListLeft.setLayoutManager(new LinearLayoutManager(_mActivity, RecyclerView.VERTICAL, false));
        binding.rvListRight.setLayoutManager(new LinearLayoutManager(_mActivity, RecyclerView.VERTICAL, false));
    }

    @Override
    public void initData() {
        List<String> datas = new ArrayList<>();
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        classifyAdapter = new ClassifyAdapter(R.layout.item_classify, datas);
        typeGoodsAdapter = new TypeGoodsAdapter(R.layout.item_classify_type, datas);
        initTopView();
        binding.rvListLeft.setAdapter(classifyAdapter);
        binding.rvListRight.setAdapter(typeGoodsAdapter);
    }

    private void initTopView() {
        List<String> datas = new ArrayList<>();
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        HeadClassifyTopBinding topBinding = DataBindingUtil.inflate(LayoutInflater.from(MAPP.mapp), R.layout.head_classify_top, null, false);
        topBinding.rvTop.setLayoutManager(new GridLayoutManager(_mActivity, 4));
        ClassifyTopAdapter classifyTopAdapter = new ClassifyTopAdapter(R.layout.item_classify_top, datas);
        topBinding.rvTop.setAdapter(classifyTopAdapter);
        typeGoodsAdapter.addHeaderView(topBinding.getRoot());
    }

    @Override
    public void initListener() {
        binding.etKey.setOnClickListener((v)->{
            IntentUtils.toGoodsListActivity();
        });
    }
}
