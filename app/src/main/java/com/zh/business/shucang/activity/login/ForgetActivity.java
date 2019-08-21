package com.zh.business.shucang.activity.login;

import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.MKey;
import com.waw.hr.mutils.ObserableUtils;
import com.waw.hr.mutils.StringUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zh.business.shucang.R;
import com.zh.business.shucang.activity.MainActivity;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityForgetBinding;
import com.zh.business.shucang.http.HttpObserver;
import com.zh.business.shucang.service.UserService;
import com.zh.business.shucang.utils.IntentUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForgetActivity extends BaseActivity<ActivityForgetBinding> {

    private boolean showPwd = false;

    private Observer countDownObserver;

    private Observable observable;

    private String code;

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
        loadingDialog = DialogUtils.getLoadingDialog(this, "", false);
        countDownObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                if (integer <= 0) {
                    binding.tvCode.setEnabled(true);
                    binding.tvCode.setText("获取验证码");
                } else {
                    binding.tvCode.setText(integer + "");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
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

        binding.tvCode.setOnClickListener((v) -> {
            sendCode();
        });

        binding.tvNext.setOnClickListener((v)->{
            forget();
        });

    }

    private void forget() {

        if (StringUtils.isEmpty(binding.etPhone.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入手机号码", true);
            tipDialog.show();
            return;
        }
        if (StringUtils.isEmpty(binding.etCode.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入验证码", true);
            tipDialog.show();
            return;
        }
        if (StringUtils.isEmpty(binding.etPwd.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入密码", true);
            tipDialog.show();
            return;
        }
        if (StringUtils.isEmpty(binding.etConfirmPwd.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入确认密码", true);
            tipDialog.show();
            return;
        }
        if (!StringUtils.isEmpty(code) && !code.equals(binding.etCode.getText().toString().trim())) {
            tipDialog = DialogUtils.getFailDialog(this, "验证码不正确", true);
            tipDialog.show();
            return;
        }
        if (!binding.etPwd.getText().toString().equals(binding.etConfirmPwd.getText().toString())) {
            tipDialog = DialogUtils.getFailDialog(this, "两次密码输入不一致", true);
            tipDialog.show();
            return;
        }
        loadingDialog.show();

        params.put(MKey.MOBILE, binding.etPhone.getText());
        params.put(MKey.CAPTCHA, StringUtils.isEmpty(binding.etCode.getText()) ? "" : binding.etCode.getText());
        params.put(MKey.NEWPASSWORD, binding.etPwd.getText());

        HttpClient.Builder.getServer().resetpwd(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<String>() {
            @Override
            public void onSuccess(BaseBean<String> baseBean) {
                dismissLoadingDialog();
                UserService.getInstance().setToken(baseBean.getData());
                tipDialog = DialogUtils.getSuclDialog(ForgetActivity.this, baseBean.getMsg(), true);
                tipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        IntentUtils.doIntent(LoginActivity.class);
                        finish();
                    }
                });
                tipDialog.show();
            }

            @Override
            public void onError(BaseBean<String> baseBean) {
                dismissLoadingDialog();
                tipDialog = DialogUtils.getFailDialog(ForgetActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });


    }


    private void sendCode() {
        if (StringUtils.isEmpty(binding.etPhone.getText())) {
            tipDialog = DialogUtils.getFailDialog(this, "请输入手机号码", true);
            tipDialog.show();
            return;
        }
        binding.tvCode.setEnabled(false);
        params.put(MKey.PHONE, binding.etPhone.getText());

        HttpClient.Builder.getServer().code(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<String>() {
            @Override
            public void onSuccess(BaseBean<String> baseBean) {
                code = baseBean.getData();
                observable = ObserableUtils.countdown(60);
                observable.subscribe(countDownObserver);
            }

            @Override
            public void onError(BaseBean<String> baseBean) {
                binding.tvCode.setEnabled(true);
                tipDialog = DialogUtils.getFailDialog(ForgetActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}
