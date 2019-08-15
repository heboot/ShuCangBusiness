package com.zh.business.shucang.activity.login;

import android.text.InputType;
import android.view.View;

import com.zh.business.shucang.R;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityRegisterBinding;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    private boolean showPwd = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("注册");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        binding.switchShowpwd.setOnClickListener((v) -> {

            if (!showPwd) {
                binding.switchShowpwd.setBackgroundResource(R.mipmap.icon_show_pwd_blue);
                binding.etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                showPwd = true;
            } else {
                showPwd = false;
                binding.switchShowpwd.setBackgroundResource(R.mipmap.icon_show_pwd_red);
//                binding.etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            binding.etPwd.setSelection(binding.etPwd.getText().length());//将光标移至文字末尾
        });

        binding.llytProtocol.setOnClickListener((v) -> {
            binding.cbCheck.setChecked(binding.cbCheck.isChecked() ? false : true);
        });


    }
}
