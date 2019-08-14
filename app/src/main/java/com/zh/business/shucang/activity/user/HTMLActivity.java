package com.zh.business.shucang.activity.user;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zh.business.shucang.R;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityHhBinding;

public class HTMLActivity extends BaseActivity<ActivityHhBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_hh;
    }

    @Override
    public void initUI() {


//        binding.wv.setWebChromeClient(new WebChromeClient() {
//
//        });


        binding.wv.getSettings().setJavaScriptEnabled(true);
        binding.wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        binding.wv.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        binding.wv.loadUrl("https://dwz.cn/IkBojfH8");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
