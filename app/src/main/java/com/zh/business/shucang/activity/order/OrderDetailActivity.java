package com.zh.business.shucang.activity.order;

import android.content.DialogInterface;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.AddressBean;
import com.waw.hr.mutils.bean.GoodsBean;
import com.waw.hr.mutils.bean.ImmediatelyBean;
import com.waw.hr.mutils.bean.OrderInfoBean;
import com.waw.hr.mutils.bean.OrderSubBean;
import com.waw.hr.mutils.event.UserEvent;
import com.waw.hr.mutils.model.ProvinceModel;
import com.zh.business.shucang.R;
import com.zh.business.shucang.activity.goods.GoodsDetailActivity;
import com.zh.business.shucang.activity.user.FavActivity;
import com.zh.business.shucang.adapter.order.OrderGoodsAdapter;
import com.zh.business.shucang.adapter.user.FavAdapter;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.common.AddressListType;
import com.zh.business.shucang.common.OrderDetailType;
import com.zh.business.shucang.common.OrderType;
import com.zh.business.shucang.databinding.ActivityOrderDetailBinding;
import com.zh.business.shucang.http.HttpObserver;
import com.zh.business.shucang.service.UserService;
import com.zh.business.shucang.utils.ImageUtils;
import com.zh.business.shucang.utils.IntentUtils;
import com.zh.business.shucang.utils.SignUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.mayubao.pay_library.AliPayReq;
import io.github.mayubao.pay_library.AliPayReq2;
import io.github.mayubao.pay_library.PayAPI;
import io.github.mayubao.pay_library.WechatPayReq;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OrderDetailActivity extends BaseActivity<ActivityOrderDetailBinding> {

    private OrderGoodsAdapter orderGoodsAdapter;

    private boolean isOpen = true;

    private OrderDetailType orderDetailType;

    private OrderSubBean orderSubBean;

    private AddressBean addressBean;

    private OrderInfoBean orderInfoBean;

    private String orderId;

    private double totalPrice;

    private int goodsNum = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("订单详情");
        binding.rvList.setFocusable(false);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        binding.rvList.setFocusable(false);
    }

    @Override
    public void initData() {
        orderDetailType = (OrderDetailType) getIntent().getExtras().get(MKey.TYPE);
        if (orderDetailType == OrderDetailType.BUY) {
            orderSubBean = (OrderSubBean) getIntent().getExtras().get(MKey.DATA);
            orderSubBean.getGoods().get(0).setNum(1);
            orderGoodsAdapter = new OrderGoodsAdapter(orderSubBean.getGoods(), orderDetailType, new WeakReference<>(this));
            binding.rvList.setAdapter(orderGoodsAdapter);
            binding.tvStatusTip.setText("等待付款");
            binding.includeProgress.setStatus(1);
            addressBean = orderSubBean.getAdress();
            calculateSelectedGoodsPrice(1);
            showAddr();
        } else if (orderDetailType == OrderDetailType.SHOP_CART) {
            orderSubBean = (OrderSubBean) getIntent().getExtras().get(MKey.DATA);
            orderGoodsAdapter = new OrderGoodsAdapter(orderSubBean.getGoods(), orderDetailType, new WeakReference<>(this));
            binding.rvList.setAdapter(orderGoodsAdapter);
            binding.tvStatusTip.setText("等待付款");
            binding.includeProgress.setStatus(1);
            addressBean = orderSubBean.getAdress();
            calculateSelectedGoodsPrice();
            showAddr();
        } else if (orderDetailType == OrderDetailType.MY_ORDER) {
            binding.tvAlterAddress.setEnabled(false);
            binding.tvAlterAddress.setVisibility(View.GONE);
            orderId = getIntent().getStringExtra(MKey.ID);
            orderInfo();
        }
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
                    showAddr();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        binding.tvGoodsList.setOnClickListener((v) -> {
            if (isOpen) {
                isOpen = false;
                binding.rvList.setVisibility(View.GONE);
                binding.vArrow.setBackgroundResource(R.mipmap.icon_arrow_down);
            } else {
                isOpen = true;
                binding.rvList.setVisibility(View.VISIBLE);
                binding.vArrow.setBackgroundResource(R.mipmap.icon_arrow_up);
            }
        });
        binding.tvAlterAddress.setOnClickListener((v) -> {
            IntentUtils.toAddressListActivity(AddressListType.CHOOSE);
        });
        binding.tvPay.setOnClickListener((v) -> {
            if (orderDetailType == OrderDetailType.BUY) {
                buy();
            } else if (orderDetailType == OrderDetailType.SHOP_CART) {
                buyCart();
            } else if (orderDetailType == OrderDetailType.MY_ORDER) {
                if (orderInfoBean.getStatus() == OrderType.PROGRESS) {
                    confirmOrder();
                } else if (orderInfoBean.getStatus() == OrderType.WAIT) {
                    buyByOrder();
                }

            }
        });
    }


    public void confirmOrder() {
        params = SignUtils.getNormalParams();
        HttpClient.Builder.getServer().affirmOrder(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(OrderDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        finish();
                    }
                });
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(OrderDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    private void buyByOrder() {

        params.put(MKey.ID, orderInfoBean.getId());
//            params.put(MKey.TOTAL_PRICE, orderSubBean.getGoods().get(0).getPrice());
//            params.put(MKey.NUM, goodsNum);
        params.put(MKey.MODE, binding.includePaytype.getPayType());
//        params.put(MKey.ADRID, addressBean.getId());
        HttpClient.Builder.getServer().payBtn(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                if (binding.includePaytype.getPayType() == 1) {
                    //3.发送支付宝支付请求
                    AliPayReq2 aliPayReq = new AliPayReq2.Builder()
                            .with(OrderDetailActivity.this)//Activity实例
//                    .setRawAliPayOrderInfo(rawAliOrderInfo)//支付宝支付订单信息
                            .setSignedAliPayOrderInfo((String) baseBean.getData()) //设置 商户私钥RSA加密后的支付宝支付订单信息
                            .create()//
                            .setOnAliPayListener(null);//
                    PayAPI.getInstance().sendPayRequest(aliPayReq);
                } else {
                    //1.创建微信支付请求
                    WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                            .with(OrderDetailActivity.this) //activity实例
                            .setAppId((String) ((Map) baseBean.getData()).get("appId")) //微信支付AppID
//                            .setPartnerId((String) ((Map)baseBean.getData()).get("nonceStr"))//微信支付商户号
                            .setPrepayId((String) ((Map) baseBean.getData()).get("package"))//预支付码
//								.setPackageValue(wechatPayReq.get)//"Sign=WXPay"
                            .setNonceStr((String) ((Map) baseBean.getData()).get("nonceStr"))
                            .setTimeStamp((String) ((Map) baseBean.getData()).get("timeStamp"))//时间戳
                            .setSign((String) ((Map) baseBean.getData()).get("paySign"))//签名
                            .create();
                    //2.发送微信支付请求
                    PayAPI.getInstance().sendPayRequest(wechatPayReq);
                }
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(OrderDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void buy() {
        params = SignUtils.getNormalParams();
        if (orderDetailType == OrderDetailType.BUY) {
            params.put(MKey.ID, orderSubBean.getGoods().get(0).getId());
            params.put(MKey.TOTAL_PRICE, orderSubBean.getGoods().get(0).getPrice());
            params.put(MKey.NUM, goodsNum);

        }
        params.put(MKey.MODE, binding.includePaytype.getPayType());
        params.put(MKey.ADRID, addressBean.getId());
        HttpClient.Builder.getServer().immediPay(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                if (binding.includePaytype.getPayType() == 1) {
                    //3.发送支付宝支付请求
                    AliPayReq2 aliPayReq = new AliPayReq2.Builder()
                            .with(OrderDetailActivity.this)//Activity实例
//                    .setRawAliPayOrderInfo(rawAliOrderInfo)//支付宝支付订单信息
                            .setSignedAliPayOrderInfo((String) baseBean.getData()) //设置 商户私钥RSA加密后的支付宝支付订单信息
                            .create()//
                            .setOnAliPayListener(null);//
                    PayAPI.getInstance().sendPayRequest(aliPayReq);
                } else {
                    //1.创建微信支付请求
                    WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                            .with(OrderDetailActivity.this) //activity实例
                            .setAppId((String) ((Map) baseBean.getData()).get("appId")) //微信支付AppID
//                            .setPartnerId((String) ((Map)baseBean.getData()).get("nonceStr"))//微信支付商户号
                            .setPrepayId((String) ((Map) baseBean.getData()).get("package"))//预支付码
//								.setPackageValue(wechatPayReq.get)//"Sign=WXPay"
                            .setNonceStr((String) ((Map) baseBean.getData()).get("nonceStr"))
                            .setTimeStamp((String) ((Map) baseBean.getData()).get("timeStamp"))//时间戳
                            .setSign((String) ((Map) baseBean.getData()).get("paySign"))//签名
                            .create();
                    //2.发送微信支付请求
                    PayAPI.getInstance().sendPayRequest(wechatPayReq);
                }
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(OrderDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    private void buyCart() {
        String ids = "";
        for (GoodsBean goodsBean : orderSubBean.getGoods()) {
            ids = ids + goodsBean.getId() + ",";
        }
        params = SignUtils.getNormalParams();
        params.put(MKey.ID, ids.substring(0, ids.length() - 1));
        params.put(MKey.TOTALPRICE, totalPrice);
        params.put(MKey.MODE, binding.includePaytype.getPayType());
        params.put(MKey.ADRID, addressBean.getId());
        HttpClient.Builder.getServer().cartPay(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                if (binding.includePaytype.getPayType() == 1) {
                    //3.发送支付宝支付请求
                    AliPayReq2 aliPayReq = new AliPayReq2.Builder()
                            .with(OrderDetailActivity.this)//Activity实例
                            .setRawAliPayOrderInfo((String) baseBean.getData())//支付宝支付订单信息
//                            .setSignedAliPayOrderInfo((String) baseBean.getData()) //设置 商户私钥RSA加密后的支付宝支付订单信息
                            .create()//
                            .setOnAliPayListener(new AliPayReq2.OnAliPayListener() {
                                @Override
                                public void onPaySuccess(String resultInfo) {
                                    LogUtil.e(TAG + "onPaySuccess", resultInfo);
                                }

                                @Override
                                public void onPayFailure(String resultInfo) {
                                    LogUtil.e(TAG + "onPayFailure", resultInfo);
                                }

                                @Override
                                public void onPayConfirmimg(String resultInfo) {
                                    LogUtil.e(TAG + "onPayConfirmimg", resultInfo);
                                }

                                @Override
                                public void onPayCheck(String status) {
                                    LogUtil.e(TAG + "onPayCheck", status);
                                }
                            });//
                    PayAPI.getInstance().sendPayRequest(aliPayReq);
                } else {
                    //1.创建微信支付请求
                    WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                            .with(OrderDetailActivity.this) //activity实例
                            .setAppId((String) ((Map) baseBean.getData()).get("appId")) //微信支付AppID
//                            .setPartnerId((String) ((Map)baseBean.getData()).get("nonceStr"))//微信支付商户号
                            .setPrepayId((String) ((Map) baseBean.getData()).get("package"))//预支付码
//								.setPackageValue(wechatPayReq.get)//"Sign=WXPay"
                            .setNonceStr((String) ((Map) baseBean.getData()).get("nonceStr"))
                            .setTimeStamp((String) ((Map) baseBean.getData()).get("timeStamp"))//时间戳
                            .setSign((String) ((Map) baseBean.getData()).get("paySign"))//签名
                            .create();
                    //2.发送微信支付请求
                    PayAPI.getInstance().sendPayRequest(wechatPayReq);
                }
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(OrderDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    /**
     * 订单详情
     */
    public void orderInfo() {
        params = SignUtils.getNormalParams();
        params.put(MKey.ID, orderId);
        HttpClient.Builder.getServer().orderInfo(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<OrderInfoBean>() {
            @Override
            public void onSuccess(BaseBean<OrderInfoBean> baseBean) {
                orderInfoBean = baseBean.getData();
                if (!StringUtils.isEmpty(orderInfoBean.getOrdernum())) {
                    binding.tvOrderNo.setText("订单编号:" + orderInfoBean.getOrdernum());
                    binding.tvOrderNo.setVisibility(View.VISIBLE);
                }
                if (!StringUtils.isEmpty(orderInfoBean.getPay_time())) {
                    binding.tvOrderPayTime.setText("付款时间:" + orderInfoBean.getPay_time());
                    binding.tvOrderPayTime.setVisibility(View.VISIBLE);
                    binding.includeProgress.setPayTime(orderInfoBean.getPay_time_mm());
                }
                if (!StringUtils.isEmpty(orderInfoBean.getEmit_time())) {
                    binding.tvOrderEmitTime.setText("发货时间:" + orderInfoBean.getEmit_time());
                    binding.tvOrderEmitTime.setVisibility(View.VISIBLE);
                    binding.includeProgress.setEmitTime(orderInfoBean.getEmit_time_mm());
                }
                if (!StringUtils.isEmpty(orderInfoBean.getSign_time())) {
                    binding.tvOrderSignTime.setText("签收时间:" + orderInfoBean.getSign_time());
                    binding.tvOrderSignTime.setVisibility(View.VISIBLE);
                    binding.includeProgress.setSignTime(orderInfoBean.getSign_time_mm());
                }
                orderGoodsAdapter = new OrderGoodsAdapter(baseBean.getData().getDeal(), orderDetailType, new WeakReference(this));
                binding.rvList.setAdapter(orderGoodsAdapter);
                switch (baseBean.getData().getStatus()) {
                    case OrderType
                            .WAIT:
                        binding.tvStatusTip.setText("等待付款");
                        binding.includeProgress.setStatus(1);
                        break;
                    case OrderType
                            .PROGRESS:
                        binding.includePaytype.setVisibility(View.GONE);
                        binding.tvStatusTip.setText("配送中");
                        binding.includeProgress.setStatus(2);
                        binding.tvPay.setText("确认收货");
                        break;
                    case OrderType
                            .FINISH:
                        binding.includePaytype.setVisibility(View.GONE);
                        binding.tvStatusTip.setText("签收完成");
                        binding.tvPay.setVisibility(View.GONE);
                        binding.includeProgress.setStatus(3);
                        break;
                }
                addressBean = baseBean.getData().getAddr();
                calculateOrderGoodsPrice();
                showAddr();

            }

            @Override
            public void onError(BaseBean<OrderInfoBean> baseBean) {
                tipDialog = DialogUtils.getFailDialog(OrderDetailActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


    /**
     * 计算单独购买的商品价格
     *
     * @param num
     */
    public void calculateSelectedGoodsPrice(int num) {
        goodsNum = num;
        totalPrice = 0;
        totalPrice = totalPrice + Double.parseDouble(orderSubBean.getGoods().get(0).getPrice()) * num;
        binding.tvPrice.setText(totalPrice + "");
    }

    /**
     * 计算我的订单过来的商品价格
     */
    public void calculateOrderGoodsPrice() {
//        totalPrice = 0;
//        for (GoodsBean goodsBean : orderInfoBean.getDeal()) {
//            totalPrice = totalPrice + Double.parseDouble(goodsBean.getPrice()) * goodsBean.getNum();
//        }
//        binding.tvPrice.setText(totalPrice + "");
    }

    /**
     * 计算购物车过来的商品价格
     */
    public void calculateSelectedGoodsPrice() {
        totalPrice = 0;
        for (GoodsBean goodsBean : orderSubBean.getGoods()) {
            totalPrice = totalPrice + Double.parseDouble(goodsBean.getPrice()) * goodsBean.getNum();
        }
        binding.tvPrice.setText(totalPrice + "");
    }

    /**
     * 显示地址
     */
    private void showAddr() {
        if (addressBean == null) {
            binding.tvName.setText("请先添加收货地址");
            binding.tvAddr.setText("");
            return;
        }
        binding.tvName.setText(addressBean.getName() + "        " + addressBean.getPhone());
        binding.tvAddr.setText(addressBean.getAddress());
    }


}
