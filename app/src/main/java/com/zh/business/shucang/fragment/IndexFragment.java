package com.zh.business.shucang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.ClassifyBean;
import com.waw.hr.mutils.bean.GoodsBean;
import com.waw.hr.mutils.bean.IndexBean;
import com.zh.business.shucang.R;
import com.zh.business.shucang.activity.MainActivity;
import com.zh.business.shucang.adapter.index.ADAdapter;
import com.zh.business.shucang.adapter.index.IndexGoodsAdapter;
import com.zh.business.shucang.base.BaseFragment;
import com.zh.business.shucang.common.AdvertType;
import com.zh.business.shucang.databinding.ActivityIndexBinding;
import com.zh.business.shucang.databinding.HeadIndexBinding;
import com.zh.business.shucang.http.HttpObserver;
import com.zh.business.shucang.utils.BannerImageLoader;
import com.zh.business.shucang.utils.ImageUtils;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class IndexFragment extends BaseFragment<ActivityIndexBinding> {

    private IndexGoodsAdapter indexGoodsAdapter;

    private List<IndexBean.AdvertBean> bannerList = new ArrayList<>();

    private List<ClassifyBean.ClassBean> classBeanList = new ArrayList<>();

    private List<IndexBean.AdvertBean> classLargeList = new ArrayList<>();

    private List<IndexBean.AdvertBean> classSmallList = new ArrayList<>();

    private ADAdapter adAdapter;

    private List<GoodsBean> goodsBeanList = new ArrayList<>();

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
        binding.includeToolbar.tvTitle.setText("首页");
    }

    @Override
    public void initData() {
        getIndexData();

    }

    @Override
    public void initListener() {
        binding.mrv.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                getIndexData();
            }
        });
    }


    /**
     * 获取banner
     *
     * @param indexBean
     * @return
     */
    private List<IndexBean.AdvertBean> getBanners(IndexBean indexBean) {
        bannerList.clear();
        for (IndexBean.AdvertBean advertBean : indexBean.getAdvert()) {
            if (advertBean.getType() == AdvertType.BANNER) {
                bannerList.add(advertBean);
            }
        }
        return bannerList;
    }


    private void getIndexData() {
        classBeanList.clear();
        goodsBeanList.clear();
        HttpClient.Builder.getServer().index().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<IndexBean>() {
            @Override
            public void onSuccess(BaseBean<IndexBean> baseBean) {
                binding.mrv.finishRefresh();
                getBanners(baseBean.getData());
                classBeanList = baseBean.getData().getClasz();
                goodsBeanList = baseBean.getData().getGoods();
                showData(baseBean.getData());
            }

            @Override
            public void onError(BaseBean<IndexBean> baseBean) {
                binding.mrv.finishRefresh();
                tipDialog = DialogUtils.getFailDialog(_mActivity, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void showData(IndexBean indexBean) {
        HeadIndexBinding headIndexBinding = DataBindingUtil.inflate(LayoutInflater.from(_mActivity), R.layout.head_index, null, false);
        if (indexGoodsAdapter == null) {
            indexGoodsAdapter = new IndexGoodsAdapter(R.layout.item_main_hot, goodsBeanList);
            indexGoodsAdapter.setEnableLoadMore(false);
        } else {
            indexGoodsAdapter.removeAllHeaderView();
            indexGoodsAdapter.getData().clear();
            indexGoodsAdapter.notifyDataSetChanged();
            indexGoodsAdapter.addData(indexBean.getGoods());
        }

        headIndexBinding.banner.setImages(bannerList);
        headIndexBinding.banner.setImageLoader(new BannerImageLoader());
        headIndexBinding.banner.start();
        headIndexBinding.rvAd.setLayoutManager(new LinearLayoutManager(_mActivity, RecyclerView.HORIZONTAL, false));
        if(adAdapter == null){
            adAdapter = new ADAdapter(classBeanList);
        }else{
            adAdapter.getData().clear();
            adAdapter.notifyDataSetChanged();
            adAdapter.addData(classBeanList);
        }
        headIndexBinding.rvAd.setAdapter(adAdapter);

        initClaszData(indexBean,headIndexBinding);
        indexGoodsAdapter.addHeaderView(headIndexBinding.getRoot());
        binding.rvList.setAdapter(indexGoodsAdapter);
    }

    private void initClaszData(IndexBean indexBean, HeadIndexBinding binding) {

        for (IndexBean.AdvertBean advertBean : indexBean.getAdvert()) {
            if (advertBean.getType() == AdvertType.AD_LARGER) {
                classLargeList.add(advertBean);
            } else if (advertBean.getType() == AdvertType.AD_SMALL) {
                classSmallList.add(advertBean);
            }
        }

        ImageUtils.showImage(classLargeList.get(0).getAd_image(), binding.includeNew.includeNew1.iv1);


        ImageUtils.showImage(classSmallList.get(0).getAd_image(), binding.includeNew.includeNew1.iv2);
        ImageUtils.showImage(classSmallList.get(1).getAd_image(), binding.includeNew.includeNew1.iv3);
        ImageUtils.showImage(classSmallList.get(2).getAd_image(), binding.includeNew.includeNew1.iv4);

        ImageUtils.showImage(classLargeList.get(1).getAd_image(), binding.includeNew.includeNew2.iv1);
        ImageUtils.showImage(classSmallList.get(3).getAd_image(), binding.includeNew.includeNew2.iv2);
        ImageUtils.showImage(classSmallList.get(4).getAd_image(), binding.includeNew.includeNew2.iv3);
        ImageUtils.showImage(classSmallList.get(5).getAd_image(), binding.includeNew.includeNew2.iv4);


    }


}
