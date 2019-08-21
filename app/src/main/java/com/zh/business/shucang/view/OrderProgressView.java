package com.zh.business.shucang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;

import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.R;
import com.zh.business.shucang.common.PayType;
import com.zh.business.shucang.databinding.LayoutOrderProgressBinding;

public class OrderProgressView extends LinearLayout {

    private LayoutOrderProgressBinding binding;

    public OrderProgressView(Context context) {
        super(context);
        initView();
    }

    public OrderProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OrderProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public OrderProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(MAPP.mapp), R.layout.layout_order_progress, this, true);
        binding.includeProgress1.tvStatus.setText("待支付");
        binding.includeProgress2.tvStatus.setText("配送中");
        binding.includeProgress3.tvStatus.setText("已签收");
    }


    public void setStatus(int status){
        switch (status){
            case 1:
                binding.includeProgress1.vOval.setBackgroundResource(R.drawable.bg_oval_d71b43_border3);
                binding.includeProgress2.vOval.setBackgroundResource(R.drawable.bg_oval_d6d6d6_border3);
                binding.includeProgress3.vOval.setBackgroundResource(R.drawable.bg_oval_d6d6d6_border3);
                break;
            case 2:
                binding.includeProgress2.vOval.setBackgroundResource(R.drawable.bg_oval_d71b43_border3);
                binding.includeProgress1.vOval.setBackgroundResource(R.drawable.bg_oval_d6d6d6_border3);
                binding.includeProgress3.vOval.setBackgroundResource(R.drawable.bg_oval_d6d6d6_border3);
                break;
            case 3:
                binding.includeProgress3.vOval.setBackgroundResource(R.drawable.bg_oval_d71b43_border3);
                binding.includeProgress2.vOval.setBackgroundResource(R.drawable.bg_oval_d6d6d6_border3);
                binding.includeProgress1.vOval.setBackgroundResource(R.drawable.bg_oval_d6d6d6_border3);
                break;
        }
    }

}
