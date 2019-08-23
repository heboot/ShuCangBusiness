package com.zh.business.shucang.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.http.HttpClient;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.GoodsBean;
import com.waw.hr.mutils.bean.OrderListBean;
import com.zh.business.shucang.R;
import com.zh.business.shucang.activity.goods.GoodsDetailActivity;
import com.zh.business.shucang.activity.goods.GoodsListActivity;
import com.zh.business.shucang.adapter.index.IndexGoodsAdapter;
import com.zh.business.shucang.adapter.order.OrderAdapter;
import com.zh.business.shucang.base.BaseFragment;
import com.zh.business.shucang.databinding.FragmentMyOrderBinding;
import com.zh.business.shucang.http.HttpObserver;
import com.zh.business.shucang.service.UserService;
import com.zh.business.shucang.utils.SignUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderFragment extends BaseFragment<FragmentMyOrderBinding> {

    private OrderAdapter orderAdapter;

    private int type;

    private QMUIDialog qmuiDialog;

    public static OrderFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(MKey.TYPE, type);
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_order;
    }

    @Override
    public void initUI() {
        binding.rvList.setLayoutManager(new LinearLayoutManager(_mActivity, RecyclerView.VERTICAL, false));
    }

    @Override
    public void initData() {
        type = (int) getArguments().get(MKey.TYPE);
        getData();
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
                getData();
            }
        });
    }

    public void showCancelDialog(String orderId) {
        if (qmuiDialog == null) {
            qmuiDialog = new QMUIDialog.MessageDialogBuilder(_mActivity)
                    .setMessage("确定要取消订单?")
                    .addAction("取消", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            qmuiDialog.dismiss();
                        }
                    })
                    .addAction("确定", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            qmuiDialog.dismiss();
                            cancel(orderId);
                        }
                    })
                    .create();
        }
        qmuiDialog.show();
        return;
    }

    private void cancel(String orderId) {


        params = SignUtils.getNormalParams();
        params.put(MKey.ID, orderId);
        HttpClient.Builder.getServer().cancelOrder(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(_mActivity, baseBean.getMsg(), true);
                tipDialog.show();
                getData();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(_mActivity, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    public void getData() {
        params = SignUtils.getNormalParams();
        params.put(MKey.TYPE, type);
        HttpClient.Builder.getServer().myOrder(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<OrderListBean>>() {
            @Override
            public void onSuccess(BaseBean<List<OrderListBean>> baseBean) {  binding.mrv.finishRefresh();
                if (orderAdapter == null) {
                    orderAdapter = new OrderAdapter(baseBean.getData(), new WeakReference<>(OrderFragment.this));
                    binding.rvList.setAdapter(orderAdapter);
                } else {
                    orderAdapter.getData().clear();
                    orderAdapter.notifyDataSetChanged();
                    orderAdapter.addData(baseBean.getData());
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(BaseBean<List<OrderListBean>> baseBean) {  binding.mrv.finishRefresh();
                tipDialog = DialogUtils.getFailDialog(_mActivity, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }


}
