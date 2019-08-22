package com.zh.business.shucang.activity.goods;

import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.AddressBean;
import com.waw.hr.mutils.bean.GoodsDetailBean;
import com.waw.hr.mutils.bean.ImmediatelyBean;
import com.waw.hr.mutils.bean.OrderSubBean;
import com.waw.hr.mutils.event.UserEvent;
import com.zh.business.shucang.R;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.common.AddressListType;
import com.zh.business.shucang.common.OrderDetailType;
import com.zh.business.shucang.databinding.ActivityGoodsDetailBinding;
import com.zh.business.shucang.http.HttpObserver;
import com.zh.business.shucang.service.GoodsService;
import com.zh.business.shucang.service.UserService;
import com.zh.business.shucang.utils.GoodsDetailImageLoader;
import com.zh.business.shucang.utils.IntentUtils;
import com.zh.business.shucang.utils.SignUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GoodsDetailActivity extends BaseActivity<ActivityGoodsDetailBinding> {

    private String goodsID;

    private GoodsDetailBean goodsDetailBean;

    private GoodsService goodsService;

    private AddressBean addressBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.tvTitle.setText("商品详情");
        setBackVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
        goodsID = (String) getIntent().getExtras().get(MKey.ID);
        getDetailData();
    }

    @Override
    public void initListener() {
        rxObservable.subscribe(new Observer<Object>() {

            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                addDisposable(d);
                this.disposable = d;
            }

            @Override
            public void onNext(Object o) {
                if (o instanceof UserEvent.ChooseAddressEvent) {
                    addressBean = ((UserEvent.ChooseAddressEvent) o).getAddressBean();
                    binding.tvAddress.setText(addressBean.getAddress() + addressBean.getInfo());
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        binding.tvBuy.setOnClickListener((v) -> {
            if(!UserService.getInstance().isLogin()){
                return;
            }
            buy();
        });
        binding.vFav.setOnClickListener((v) -> {
            if(!UserService.getInstance().isLogin()){
                return;
            }
            fav();
        });
        binding.vShopcart.setOnClickListener((v) -> {
            if(!UserService.getInstance().isLogin()){
                return;
            }
            if (goodsService == null) {
                goodsService = new GoodsService();
            }
            goodsService.addShopCart(goodsID);
        });
        binding.tvAddress.setOnClickListener((v)->{
            if(!UserService.getInstance().isLogin()){
                return;
            }
            IntentUtils.toAddressListActivity(AddressListType.CHOOSE);
        });
    }


    private void buy() {
        params = SignUtils.getNormalParams();
        params.put(MKey.ID, goodsID);
        HttpClient.Builder.getServer().immediately(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<OrderSubBean>() {
            @Override
            public void onSuccess(BaseBean<OrderSubBean> baseBean) {
                IntentUtils.toOrderDetailActivity(OrderDetailType.BUY, baseBean.getData());
            }

            @Override
            public void onError(BaseBean<OrderSubBean> baseBean) {
                tipDialog = DialogUtils.getFailDialog(GoodsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    private void getDetailData() {
        params = SignUtils.getNormalParams();
        params.put(MKey.ID, goodsID);
        HttpClient.Builder.getServer().goodsInfo(UserService.getInstance().getToken(),params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<GoodsDetailBean>() {
            @Override
            public void onSuccess(BaseBean<GoodsDetailBean> baseBean) {
                goodsDetailBean = baseBean.getData();
                showData();
            }

            @Override
            public void onError(BaseBean<GoodsDetailBean> baseBean) {
                tipDialog = DialogUtils.getFailDialog(GoodsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void fav() {
        params = SignUtils.getNormalParams();
        params.put(MKey.ID, goodsID);
        HttpClient.Builder.getServer().collect(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(GoodsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
                if (goodsDetailBean.getSign() == 0) {
                    goodsDetailBean.setSign(1);
                    binding.ivFav.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                } else {
                    goodsDetailBean.setSign(0);
                    binding.ivFav.setBackgroundResource(R.mipmap.icon_goods_fav);
                }

            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(GoodsDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void showData() {
        binding.tvNo.setText(goodsDetailBean.getCode() + "");
        binding.tvTitle.setText(goodsDetailBean.getName());
        binding.tvOldPrice.setText(goodsDetailBean.getOriginal_price());
        binding.tvPrice.setText(goodsDetailBean.getPrice());
        binding.tvDetailContent.setText(goodsDetailBean.getInfo_content());
        binding.tvInventory.setText(goodsDetailBean.getInven() + "");
        binding.tvSale.setText(goodsDetailBean.getSales() + "");

        binding.banner.setImages(goodsDetailBean.getInfo_images());
        binding.banner.setImageLoader(new GoodsDetailImageLoader());
        binding.banner.start();

        if (goodsDetailBean.getSign() == 1) {
            binding.ivFav.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
        } else {
            binding.ivFav.setBackgroundResource(R.mipmap.icon_goods_fav);
        }

        if (!UserService.getInstance().isLoginValue()) {
            binding.tvAddress.setText("请添加收货地址");
        }else{
            if(goodsDetailBean.getAddress() == null){
                binding.tvAddress.setText("请添加收货地址");
            }else{
                binding.tvAddress.setText(goodsDetailBean.getAddress());
            }
        }


    }

}
