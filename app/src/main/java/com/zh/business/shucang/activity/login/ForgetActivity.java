package com.zh.business.shucang.activity.login;

import android.text.InputType;
import android.view.View;

import com.zh.business.shucang.R;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityForgetBinding;

public class ForgetActivity extends BaseActivity<ActivityForgetBinding> {

    private boolean showPwd = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("找回密码");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        binding.switchShowpwd.setOnClickListener((v) -> {

            if (!showPwd) {
                binding.switchShowpwd.setBackgroundResource(R.mipmap.icon_show_pwd_blue);
                binding.etConfirmPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                showPwd = true;
            } else {
                showPwd = false;
                binding.switchShowpwd.setBackgroundResource(R.mipmap.icon_show_pwd_red);
//                binding.etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.etConfirmPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            binding.etConfirmPwd.setSelection(binding.etConfirmPwd.getText().length());//将光标移至文字末尾
        });
    }
}
