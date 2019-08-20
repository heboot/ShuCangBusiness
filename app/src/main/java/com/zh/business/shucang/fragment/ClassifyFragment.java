package com.zh.business.shucang.fragment;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.ClassifyBean;
import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.classify.ClassifyAdapter;
import com.zh.business.shucang.adapter.classify.ClassifyTopAdapter;
import com.zh.business.shucang.adapter.classify.TypeGoodsAdapter;
import com.zh.business.shucang.base.BaseFragment;
import com.zh.business.shucang.databinding.FragmentClassifyBinding;
import com.zh.business.shucang.databinding.HeadClassifyTopBinding;
import com.zh.business.shucang.http.HttpObserver;
import com.zh.business.shucang.utils.IntentUtils;
import com.zh.business.shucang.utils.SignUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ClassifyFragment extends BaseFragment<FragmentClassifyBinding> {

    private ClassifyAdapter classifyAdapter;

    private TypeGoodsAdapter typeGoodsAdapter;

    private List<ClassifyBean.ClassBean> classBeanList = new ArrayList<>();

    private List<ClassifyBean> classifyBeanList = new ArrayList<>();

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
        getClassData();

    }

    private void initTopView(List<ClassifyBean.ClassBean> classBeans) {
        typeGoodsAdapter.removeAllHeaderView();
        HeadClassifyTopBinding topBinding = DataBindingUtil.inflate(LayoutInflater.from(MAPP.mapp), R.layout.head_classify_top, null, false);
        topBinding.rvTop.setLayoutManager(new GridLayoutManager(_mActivity, 4));
        ClassifyTopAdapter classifyTopAdapter = new ClassifyTopAdapter(R.layout.item_classify_top, classBeans);
        topBinding.rvTop.setAdapter(classifyTopAdapter);
        typeGoodsAdapter.addHeaderView(topBinding.getRoot());
        binding.rvListRight.setAdapter(typeGoodsAdapter);
    }

    @Override
    public void initListener() {
        binding.etKey.setOnClickListener((v) -> {
            IntentUtils.toGoodsListActivity();
        });
    }


    public void getClassGoodsData(int id) {
        params = SignUtils.getNormalParams();
        params.put(MKey.ID,id);
        HttpClient.Builder.getServer().classInfo(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<ClassifyBean>() {
            @Override
            public void onSuccess(BaseBean<ClassifyBean> baseBean) {
                if (typeGoodsAdapter == null) {
                    typeGoodsAdapter = new TypeGoodsAdapter(R.layout.item_classify_type, baseBean.getData().getInfo());
                }else{
                    typeGoodsAdapter.getData().clear();
                    typeGoodsAdapter.notifyDataSetChanged();
                    typeGoodsAdapter.addData(baseBean.getData().getInfo());
                }
                initTopView(baseBean.getData().getClasz());

            }

            @Override
            public void onError(BaseBean<ClassifyBean> baseBean) {
                tipDialog = DialogUtils.getFailDialog(_mActivity, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    private void getClassData() {
        HttpClient.Builder.getServer().classList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<ClassifyBean.ClassBean>>() {
            @Override
            public void onSuccess(BaseBean<List<ClassifyBean.ClassBean>> baseBean) {
                classBeanList = baseBean.getData();
                if (classifyAdapter == null) {
                    classifyAdapter = new ClassifyAdapter(R.layout.item_classify, classBeanList,ClassifyFragment.this);
                }
                binding.rvListLeft.setAdapter(classifyAdapter);
                getClassGoodsData(baseBean.getData().get(0).getId());
            }

            @Override
            public void onError(BaseBean<List<ClassifyBean.ClassBean>> baseBean) {
                tipDialog = DialogUtils.getFailDialog(_mActivity, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }



}
