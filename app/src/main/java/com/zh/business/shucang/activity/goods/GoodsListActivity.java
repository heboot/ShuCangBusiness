package com.zh.business.shucang.activity.goods;

import android.print.PageRange;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.GoodsBean;
import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.index.IndexGoodsAdapter;
import com.zh.business.shucang.adapter.shopcart.ShopCartAdapter;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityClassifyMoreResultBinding;
import com.zh.business.shucang.fragment.ShopCartFragment;
import com.zh.business.shucang.http.HttpObserver;
import com.zh.business.shucang.service.UserService;
import com.zh.business.shucang.utils.SignUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    }

    @Override
    public void initListener() {
        binding.etKey.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                    if(StringUtils.isEmpty(binding.etKey.getText().toString())){
                        return false;
                    }
                    getData(binding.etKey.getText().toString());
                }
                return false;
            }
        });
    }

    public void getData(String str) {
        params = SignUtils.getNormalParams();
        params.put(MKey.STR,str);
        HttpClient.Builder.getServer().search(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<GoodsBean>>() {
            @Override
            public void onSuccess(BaseBean<List<GoodsBean>> baseBean) {
                if (indexGoodsAdapter == null) {
                    indexGoodsAdapter = new IndexGoodsAdapter(R.layout.item_main_hot,baseBean.getData());
                    binding.rvList.setAdapter(indexGoodsAdapter);
                } else {
                    indexGoodsAdapter.getData().clear();
                    indexGoodsAdapter.notifyDataSetChanged();
                    indexGoodsAdapter.addData(baseBean.getData());
                    indexGoodsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(BaseBean<List<GoodsBean>> baseBean) {
                tipDialog = DialogUtils.getFailDialog(GoodsListActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }



}
