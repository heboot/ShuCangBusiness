package com.zh.business.shucang.adapter.user;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.waw.hr.mutils.bean.AddressBean;
import com.zh.business.shucang.R;
import com.zh.business.shucang.activity.user.AddressListActivity;
import com.zh.business.shucang.adapter.shopcart.ShopCartAdapter;
import com.zh.business.shucang.common.NewAddressType;
import com.zh.business.shucang.databinding.ItemAddressBinding;
import com.zh.business.shucang.utils.IntentUtils;

import java.lang.ref.WeakReference;
import java.nio.file.WatchEvent;
import java.util.List;

public class AddressAdapter extends BaseQuickAdapter<AddressBean, BaseViewHolder> {

    private final String TAG = AddressAdapter.class.getName();

    private WeakReference<AddressListActivity> weakReference;

    public AddressAdapter(List data, WeakReference<AddressListActivity> weakReference) {
        super(R.layout.item_address, data);
        this.weakReference = weakReference;
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean s) {
        ItemAddressBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.tvName.setText(s.getName());
        binding.tvPhone.setText(s.getPhone());
        binding.tvAddr.setText(s.getAddress() + s.getInfo());
        if (s.getFlag() == 1) {
            binding.cbCheck.setChecked(true);
        } else {
            binding.cbCheck.setChecked(false);
        }

        binding.tvNormal.setOnClickListener((v)->{
            weakReference.get().setDefault(String.valueOf(s.getId()));
        });
        binding.tvDelete.setOnClickListener((v)->{
            weakReference.get().del(String.valueOf(s.getId()));
        });
        binding.tvAlter.setOnClickListener((v)->{
            IntentUtils.toNewAddressActivity(NewAddressType.EDIT,s);
        });
        binding.getRoot().setOnClickListener((v)->{
            weakReference.get().doChoose(s);
        });
    }
}
