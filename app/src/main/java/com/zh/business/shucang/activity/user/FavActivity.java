package com.zh.business.shucang.activity.user;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.user.AddressAdapter;
import com.zh.business.shucang.adapter.user.FavAdapter;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityFavBinding;

import java.util.ArrayList;
import java.util.List;

public class FavActivity extends BaseActivity<ActivityFavBinding> {

    private FavAdapter favAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fav;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.tvTitle.setText("我的收藏");
        setBackVisibility(View.VISIBLE);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
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
        favAdapter = new FavAdapter( datas);
        binding.rvList.setAdapter(favAdapter);
    }

    @Override
    public void initListener() {

    }
}
