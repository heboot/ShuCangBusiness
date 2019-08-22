package com.zh.business.shucang.activity.common;

import com.example.http.HttpClient;
import com.waw.hr.mutils.DialogUtils;
import com.waw.hr.mutils.base.BaseBean;
import com.zh.business.shucang.R;
import com.zh.business.shucang.activity.user.InfoActivity;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityAboutBinding;
import com.zh.business.shucang.http.HttpObserver;
import com.zh.business.shucang.service.UserService;
import com.zh.business.shucang.utils.ImageUtils;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AboutActivity extends BaseActivity<ActivityAboutBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initUI() {
        binding.includeToolbar.tvTitle.setText("关于我们");
    }

    @Override
    public void initData() {
        about();
    }

    @Override
    public void initListener() {

    }
    public void about() {
        HttpClient.Builder.getServer().info().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new HttpObserver<String>() {
            @Override
            public void onSuccess(BaseBean<String> baseBean) {
                binding.wv.loadDataWithBaseURL(null, baseBean.getData(),
                        "text/html", "UTF-8", null);
            }

            @Override
            public void onError(BaseBean<String> baseBean) {
                tipDialog = DialogUtils.getFailDialog(AboutActivity.this, baseBean.getMsg(), true);
                tipDialog.show();
            }
        });
    }

}
