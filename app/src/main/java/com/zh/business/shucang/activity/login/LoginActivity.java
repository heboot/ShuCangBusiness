package com.zh.business.shucang.activity.login;

import android.text.InputType;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zh.business.shucang.R;
import com.zh.business.shucang.activity.MainActivity;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityLoginBinding;
import com.zh.business.shucang.http.HttpObserver;
import com.zh.business.shucang.service.UserService;
import com.zh.business.shucang.utils.IntentUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private boolean showPwd = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.GONE);
        binding.includeToolbar.tvTitle.setText("登录");
    }

    @Override
    public void initData() {
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
    }

    @Override
    public void initListener() {
        binding.tvRegister.setOnClickListener((v) -> {
            IntentUtils.doIntent(RegisterActivity.class);
        });
        binding.tvForget.setOnClickListener((v) -> {
            IntentUtils.doIntent(ForgetActivity.class);
        });
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
        binding.tvLogin.setOnClickListener((v)->{
            login();
        });
    }

    private void login() {

        if (StringUtils.isEmpty(binding.etPhone.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入手机号码", true);
            tipDialog.show();
            return;
        }
        if (StringUtils.isEmpty(binding.etPwd.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入密码", true);
            tipDialog.show();
            return;
        }

        params.put(MKey.ACCOUNT, binding.etPhone.getText());
        params.put(MKey.PASSWORD, binding.etPwd.getText());

        loadingDialog.show();
        HttpClient.Builder.getServer().mobilelogin(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<String>() {
            @Override
            public void onSuccess(BaseBean<String> baseBean) {
                dismissLoadingDialog();
                UserService.getInstance().setToken(baseBean.getData());
                IntentUtils.doIntent(MainActivity.class);
                finish();
            }

            @Override
            public void onError(BaseBean<String> baseBean) {
                dismissLoadingDialog();
                tipDialog = DialogUtils.getFailDialog(LoginActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
