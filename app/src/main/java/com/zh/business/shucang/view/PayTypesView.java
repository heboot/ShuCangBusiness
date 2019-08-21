package com.zh.business.shucang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.R;
import com.zh.business.shucang.common.PayType;
import com.zh.business.shucang.databinding.LayoutPayTypesBinding;

public class PayTypesView extends ConstraintLayout {

    private LayoutPayTypesBinding binding;

    private OnClickListener onClickListener;

    private PayType payType = PayType.ALI;


    public PayTypesView(Context context) {
        super(context);
        initView();
    }

    public PayTypesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PayTypesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

        binding = DataBindingUtil.inflate(LayoutInflater.from(MAPP.mapp), R.layout.layout_pay_types, this, true);

        onClickListener = (v) -> {
            if (v.getId() == R.id.cb_alipay || v.getId() == R.id.tv_alipay ) {
                payType = PayType.ALI;
                binding.cbAlipay.setChecked(true);
                binding.cbWx.setChecked(false);
                binding.cbHuodao.setChecked(false);
            } else if (v.getId() == R.id.cb_wx|| v.getId() == R.id.tv_wx) {
                payType = PayType.WX;
                binding.cbAlipay.setChecked(false);
                binding.cbWx.setChecked(true);
                binding.cbHuodao.setChecked(false);
            } else if (v.getId() == R.id.cb_huodao|| v.getId() == R.id.tv_huodao) {
                payType = PayType.HUODAO;
                binding.cbAlipay.setChecked(false);
                binding.cbWx.setChecked(false);
                binding.cbHuodao.setChecked(true);
            }
        };

        binding.tvAlipay.setOnClickListener(onClickListener);
        binding.tvWx.setOnClickListener(onClickListener);
        binding.tvHuodao.setOnClickListener(onClickListener);
        binding.cbAlipay.setOnClickListener(onClickListener);
        binding.cbWx.setOnClickListener(onClickListener);
        binding.cbHuodao.setOnClickListener(onClickListener);

    }

    public int getPayType() {
        switch (payType){
            case WX:
                return 2;
            case ALI:
                return 1;
        }
        return 0;
    }
}
