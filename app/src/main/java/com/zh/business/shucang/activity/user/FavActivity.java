package com.zh.business.shucang.activity.user;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.GoodsBean;
import com.zh.business.shucang.R;
import com.zh.business.shucang.activity.goods.GoodsListActivity;
import com.zh.business.shucang.adapter.index.IndexGoodsAdapter;
import com.zh.business.shucang.adapter.user.AddressAdapter;
import com.zh.business.shucang.adapter.user.FavAdapter;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityFavBinding;
import com.zh.business.shucang.http.HttpObserver;
import com.zh.business.shucang.service.UserService;
import com.zh.business.shucang.utils.SignUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    public void initData() {
        getData();
    }

    @Override
    public void initListener() {

    }

    public void getData() {
        params = SignUtils.getNormalParams();
        HttpClient.Builder.getServer().myCollect(UserService.getInstance().getToken(),params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<GoodsBean>>() {
            @Override
            public void onSuccess(BaseBean<List<GoodsBean>> baseBean) {
                if (favAdapter == null) {
                    favAdapter = new FavAdapter((List<GoodsBean>)baseBean.getData());
                    binding.rvList.setAdapter(favAdapter);
                } else {
                    favAdapter.getData().clear();
                    favAdapter.notifyDataSetChanged();
                    favAdapter.addData((List<GoodsBean>)baseBean.getData());
                    favAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(BaseBean<List<GoodsBean>> baseBean) {
                tipDialog = DialogUtils.getFailDialog(FavActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}
