package com.zh.business.shucang.activity.order;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.LogUtil;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.AddressBean;
import com.waw.hr.mutils.bean.GoodsBean;
import com.waw.hr.mutils.bean.OrderInfoBean;
import com.waw.hr.mutils.bean.OrderSubBean;
import com.waw.hr.mutils.event.UserEvent;
import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.order.OrderGoodsAdapter;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.common.AddressListType;
import com.zh.business.shucang.common.OrderDetailType;
import com.zh.business.shucang.common.OrderType;
import com.zh.business.shucang.databinding.ActivityOrderDetailBinding;
import com.zh.business.shucang.http.HttpObserver;
import com.zh.business.shucang.service.UserService;
import com.zh.business.shucang.utils.IntentUtils;
import com.zh.business.shucang.utils.PayUtils;
import com.zh.business.shucang.utils.SignUtils;

import java.lang.ref.WeakReference;
import java.util.Map;

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

    private Handler aliPayObserver;


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

        aliPayObserver = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 555) {
                    Map<String, String> map = (Map) msg.obj;
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(map.get("resultStatus"), "9000")) {
//                        该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        tipDialog = DialogUtils.getSuclDialog(OrderDetailActivity.this, "支付成功", true);
                        tipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                finish();
                            }
                        });
                        tipDialog.show();
                    } else if (TextUtils.equals(map.get("resultStatus"), "6001")) {
//                        该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        tipDialog = DialogUtils.getInfolDialog(OrderDetailActivity.this, "支付取消", true);
                        tipDialog.show();
                    } else {
//                         该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        tipDialog = DialogUtils.getFailDialog(OrderDetailActivity.this, "支付失败", true);
                        tipDialog.show();
                    }
                }
            }
        };


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
                } else if (o instanceof UserEvent.WxPayEvent) {
                    switch (((UserEvent.WxPayEvent) o).getErrorCode()) {
                        case 0:
                            tipDialog = DialogUtils.getSuclDialog(OrderDetailActivity.this,"支付成功",true);
                            tipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    finish();
                                }
                            });
                            tipDialog.show();
                            break;
                        case -1:
                            tipDialog = DialogUtils.getFailDialog(OrderDetailActivity.this,"支付失败",true);
                            tipDialog.show();
                            break;
                        case -2:
                            tipDialog = DialogUtils.getFailDialog(OrderDetailActivity.this,"取消支付",true);
                            tipDialog.show();
                            break;
                        default:
                            tipDialog = DialogUtils.getFailDialog(OrderDetailActivity.this,"支付失败",true);
                            tipDialog.show();
                            break;
                    }
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

    private void aliPay(String orderInfo) {
        PayUtils.getInstance().aliPay(orderInfo, aliPayObserver);
    }

    private void wxPay(Map map) {
        PayUtils.getInstance().wxPay(map);
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
                    aliPay((String) baseBean.getData());

                } else {
                    wxPay((Map) baseBean.getData());
//                    //1.创建微信支付请求
//                    WechatPayReq wechatPayReq = new WechatPayReq.Builder()
//                            .with(OrderDetailActivity.this) //activity实例
//                            .setAppId((String) ((Map) baseBean.getData()).get("appId")) //微信支付AppID
////                            .setPartnerId((String) ((Map)baseBean.getData()).get("nonceStr"))//微信支付商户号
//                            .setPrepayId((String) ((Map) baseBean.getData()).get("package"))//预支付码
////								.setPackageValue(wechatPayReq.get)//"Sign=WXPay"
//                            .setNonceStr((String) ((Map) baseBean.getData()).get("nonceStr"))
//                            .setTimeStamp((String) ((Map) baseBean.getData()).get("timeStamp"))//时间戳
//                            .setSign((String) ((Map) baseBean.getData()).get("paySign"))//签名
//                            .create();
//                    //2.发送微信支付请求
//                    PayAPI.getInstance().sendPayRequest(wechatPayReq);
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
                    aliPay((String) baseBean.getData());
                } else {
                    wxPay((Map) baseBean.getData());
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
                    aliPay((String) baseBean.getData());
                } else {
                    wxPay((Map) baseBean.getData());
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
