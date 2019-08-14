package com.zh.business.shucang.activity.user;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.user.CommentAdapter;
import com.zh.business.shucang.base.BaseActivity;
import com.zh.business.shucang.databinding.ActivityCommentBinding;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends BaseActivity<ActivityCommentBinding> {

    private CommentAdapter commentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    public void initUI() {
        setBackVisibility(View.VISIBLE);
        binding.includeToolbar.tvTitle.setText("我的评价");
        binding.rvList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
    }

    @Override
    public void initData() {
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
        commentAdapter = new CommentAdapter(datas);
        binding.rvList.setAdapter(commentAdapter);
    }

    @Override
    public void initListener() {

    }
}
