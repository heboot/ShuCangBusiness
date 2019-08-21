package com.zh.business.shucang.fragment;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.GoodsBean;
import com.waw.hr.mutils.bean.GoodsDetailBean;
import com.waw.hr.mutils.bean.IndexBean;
import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.shopcart.ShopCartAdapter;
import com.zh.business.shucang.base.BaseFragment;
import com.zh.business.shucang.databinding.ActivityShopCartBinding;
import com.zh.business.shucang.http.HttpObserver;
import com.zh.business.shucang.service.GoodsService;
import com.zh.business.shucang.service.UserService;

import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShopCartFragment extends BaseFragment<ActivityShopCartBinding> {

    private ShopCartAdapter shopCartAdapter;

    private double totalPrice;

    private String deleteIds="";

    private GoodsService goodsService;

    public static ShopCartFragment newInstance() {
        Bundle args = new Bundle();
        ShopCartFragment fragment = new ShopCartFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_cart;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.tvTitle.setText("购物车");
        binding.rvList.setLayoutManager(new LinearLayoutManager(_mActivity, RecyclerView.VERTICAL, false));
    }

    @Override
    public void initData() {
        getShopcartData();
    }

    @Override
    public void initListener() {
        binding.tvCheckAll.setOnClickListener((v) -> {
            if (shopCartAdapter != null) {
                if (shopCartAdapter.isAllCheck()) {
                    binding.tvCheckAll.setText("全选");
                    shopCartAdapter.setAllCheck(false);
                    shopCartAdapter.getSelectedGoodsBean().clear();
                    shopCartAdapter.notifyDataSetChanged();
                } else {
                    binding.tvCheckAll.setText("取消");
                    shopCartAdapter.getSelectedGoodsBean().clear();
                    shopCartAdapter.setAllCheck(true);
                    shopCartAdapter.notifyDataSetChanged();
                }
            }
        });
        binding.tvDeleteAll.setOnClickListener((v) -> {
            if (shopCartAdapter != null) {
                if (shopCartAdapter.getSelectedGoodsBean() != null) {
                    for (Integer beanId : shopCartAdapter.getSelectedGoodsBean().keySet()) {
                        for( int i = 0 ; i < shopCartAdapter.getData().size();i++){
                            if(shopCartAdapter.getData().get(i).getId() == beanId){
                                deleteIds = deleteIds + beanId + ",";
                                shopCartAdapter.getData().remove(i);
                            }
                        }
//                        for (GoodsBean goodsBean : shopCartAdapter.getData()) {
//                            if (goodsBean.getId() == beanId) {
//                                deleteIds = deleteIds + beanId + ",";
//                                shopCartAdapter.getData().remove(goodsBean);
//
//                            }
//                        }
                    }

                    if(!StringUtils.isEmpty(deleteIds)){
                        shopCartAdapter.notifyDataSetChanged();
                        if(goodsService == null){
                            goodsService = new GoodsService();
                        }
                        goodsService.removeShopCart(deleteIds);
                        getShopcartData();
                        deleteIds = "";
                    }

                }
            }
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        getShopcartData();
    }

    public void getShopcartData() {
        HttpClient.Builder.getServer().cartList(UserService.getInstance().getToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<GoodsBean>>() {
            @Override
            public void onSuccess(BaseBean<List<GoodsBean>> baseBean) {
                totalPrice = 0;
                if (shopCartAdapter == null) {
                    shopCartAdapter = new ShopCartAdapter(baseBean.getData(), new WeakReference<>(ShopCartFragment.this));
                    binding.rvList.setAdapter(shopCartAdapter);
                } else {
                    shopCartAdapter.getData().clear();
                    shopCartAdapter.notifyDataSetChanged();
                    shopCartAdapter.addData(baseBean.getData());
                    shopCartAdapter.notifyDataSetChanged();
                }
                shopCartAdapter.setAllCheck(false);
                shopCartAdapter.getSelectedGoodsBean().clear();
                binding.tvPrice.setText(totalPrice + "");
                binding.tvCheckAll.setText("全选");
            }

            @Override
            public void onError(BaseBean<List<GoodsBean>> baseBean) {
                tipDialog = DialogUtils.getFailDialog(_mActivity, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    public void calculateSelectedGoodsPrice(Map<Integer, GoodsBean> goodsBeans) {
        totalPrice = 0;
        if(goodsBeans == null){
            binding.tvPrice.setText(totalPrice + "");
            return;
        }
        for (Integer id : goodsBeans.keySet()) {
            totalPrice = totalPrice + Double.parseDouble(goodsBeans.get(id).getPrice()) * goodsBeans.get(id).getNum();
        }
        binding.tvPrice.setText(totalPrice + "");
    }

}
