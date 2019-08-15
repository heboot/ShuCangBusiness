package com.zh.business.shucang.activity.user;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.waw.hr.mutils.MKey;
import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.shopcart.ShopCartAdapter;
import com.zh.business.shucang.adapter.user.AddressAdapter;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.common.AddressListType;
import com.zh.business.shucang.databinding.ActivityAddressListBinding;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

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
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
    }

    @Override
    public void initData() {
        type  = (AddressListType) getIntent().getExtras().get(MKey.TYPE);
        if(type == AddressListType.CHOOSE){
            binding.tvSave.setVisibility(View.GONE);
        }else{
            binding.tvSave.setVisibility(View.VISIBLE);
        }
        List<String> datas = new ArrayList();
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
        datas.add("1");
//        int imgWidth = (QMUIDisplayHelper.getScreenWidth(MAPP.mapp)  - MAPP.mapp.getResources().getDimensionPixelSize(R.dimen.x45))/2;
//        LogUtil.e(TAG, imgWidth + "");
        addressAdapter = new AddressAdapter( datas);
        binding.rvList.setAdapter(addressAdapter);
    }

    @Override
    public void initListener() {
        binding.tvSave.setOnClickListener((v)->{
            IntentUtils.doIntent( AddAddressActivity.class);
        });
    }

    public void doChoose(){
        Intent intent = new Intent();
        intent.putExtra(MKey.ADDRESS,"");
        setResult(RESULT_OK);
    }

}
