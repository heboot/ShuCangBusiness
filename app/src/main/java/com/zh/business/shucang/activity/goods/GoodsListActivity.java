package com.zh.business.shucang.activity.goods;

import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.index.IndexGoodsAdapter;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityClassifyMoreResultBinding;

import java.util.ArrayList;
import java.util.List;

public class GoodsListActivity extends BaseActivity<ActivityClassifyMoreResultBinding> {

    private IndexGoodsAdapter indexGoodsAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_classify_more_result;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.rvList.setLayoutManager(new GridLayoutManager(this,2));
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
        binding.rvList.setAdapter(indexGoodsAdapter);
    }

    @Override
    public void initListener() {

    }
}
