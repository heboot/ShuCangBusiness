package com.zh.business.shucang.activity.user;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.bean.AddressBean;
import com.waw.hr.mutils.event.UserEvent;
import com.waw.hr.mutils.rxbus.RxBus;
import com.zh.business.shucang.R;
import com.zh.business.shucang.activity.goods.GoodsDetailActivity;
import com.zh.business.shucang.adapter.user.AddressAdapter;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.common.AddressListType;
import com.zh.business.shucang.common.NewAddressType;
import com.zh.business.shucang.databinding.ActivityAddressListBinding;
import com.zh.business.shucang.http.HttpObserver;
import com.zh.business.shucang.service.UserService;
import com.zh.business.shucang.utils.IntentUtils;
import com.zh.business.shucang.utils.SignUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddressListActivity extends BaseActivity<ActivityAddressListBinding> {

    private AddressAdapter addressAdapter;

    private AddressListType type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.tvTitle.setText("收货地址");
        setBackVisibility(View.VISIBLE);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    public void initData() {
//        type = (AddressListType) getIntent().getExtras().get(MKey.TYPE);
//        if (type == AddressListType.CHOOSE) {
//            binding.tvSave.setVisibility(View.GONE);
//        } else {
        binding.tvSave.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void initListener() {
        binding.tvSave.setOnClickListener((v) -> {
            IntentUtils.toNewAddressActivity(NewAddressType.NEW);
        });
    }

    public void getData() {
        HttpClient.Builder.getServer().address(UserService.getInstance().getToken()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<List<AddressBean>>() {
            @Override
            public void onSuccess(BaseBean<List<AddressBean>> baseBean) {
                if (addressAdapter == null) {
                    addressAdapter = new AddressAdapter(baseBean.getData(), new WeakReference<>(AddressListActivity.this));
                    binding.rvList.setAdapter(addressAdapter);
                } else {
                    addressAdapter.getData().clear();
                    addressAdapter.notifyDataSetChanged();
                    addressAdapter.addData(baseBean.getData());
                    addressAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(BaseBean<List<AddressBean>> baseBean) {
                tipDialog = DialogUtils.getFailDialog(AddressListActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    public void setDefault(String addressId) {
        params = SignUtils.getNormalParams();
        params.put(MKey.ID, addressId);
        HttpClient.Builder.getServer().defultaddr(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(AddressListActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
                getData();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(AddressListActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    public void del(String addressId) {
        params = SignUtils.getNormalParams();
        params.put(MKey.ID, addressId);
        HttpClient.Builder.getServer().delAddress(UserService.getInstance().getToken(), params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<Object>() {
            @Override
            public void onSuccess(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getSuclDialog(AddressListActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
                getData();
            }

            @Override
            public void onError(BaseBean<Object> baseBean) {
                tipDialog = DialogUtils.getFailDialog(AddressListActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    public void doChoose(AddressBean addressBean) {
        RxBus.getInstance().post(new UserEvent.ChooseAddressEvent(addressBean));
        finish();
    }

}
