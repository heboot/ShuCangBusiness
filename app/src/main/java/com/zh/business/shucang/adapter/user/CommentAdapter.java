package com.zh.business.shucang.adapter.user;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.shopcart.ShopCartAdapter;
import com.zh.business.shucang.databinding.ItemCommentBinding;
import com.zh.business.shucang.databinding.ItemFavBinding;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.List;

public class CommentAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private final String TAG = ShopCartAdapter.class.getName();


    public CommentAdapter(List data) {
        super(R.layout.item_comment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String s) {

    }
}
