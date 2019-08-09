package com.zh.business.shucang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.waw.hr.mutils.LogUtil;
import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.index.IndexGoodsAdapter;
import com.zh.business.shucang.base.BaseFragment;
import com.zh.business.shucang.databinding.ActivityIndexBinding;
import com.zh.business.shucang.view.IndexGridItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IndexFragment extends BaseFragment<ActivityIndexBinding> {

    private IndexGoodsAdapter indexGoodsAdapter;


    public static IndexFragment newInstance() {
        Bundle args = new Bundle();
        IndexFragment fragment = new IndexFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index;
    }

    @Override
    public void initUI() {
        binding.rvList.setLayoutManager(new GridLayoutManager(_mActivity, 2));
//        binding.rvList.addItemDecoration(new IndexGridItemDecoration(MAPP.mapp));
        binding.includeToolbar.tvTitle.setText("蜀仓建材");
    }

    @Override
    public void initData() {
        List<String> datas = new ArrayList();
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
//        int imgWidth = (QMUIDisplayHelper.getScreenWidth(MAPP.mapp)  - MAPP.mapp.getResources().getDimensionPixelSize(R.dimen.x45))/2;
//        LogUtil.e(TAG, imgWidth + "");
        indexGoodsAdapter = new IndexGoodsAdapter(R.layout.item_main_hot, datas);
        indexGoodsAdapter.setEnableLoadMore(true);
        indexGoodsAdapter.addHeaderView(DataBindingUtil.inflate(LayoutInflater.from(_mActivity), R.layout.head_index, null, false).getRoot());
        binding.rvList.setAdapter(indexGoodsAdapter);
    }

    @Override
    public void initListener() {
        indexGoodsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

            }
        }, binding.rvList);
    }
}
