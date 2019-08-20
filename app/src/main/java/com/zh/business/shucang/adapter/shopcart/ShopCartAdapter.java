package com.zh.business.shucang.adapter.shopcart;

import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.waw.hr.mutils.bean.GoodsBean;
import com.zh.business.shucang.MAPP;
import com.zh.business.shucang.R;
import com.zh.business.shucang.adapter.index.IndexGoodsAdapter;
import com.zh.business.shucang.databinding.ItemMainHotBinding;
import com.zh.business.shucang.databinding.ItemShopCartBinding;
import com.zh.business.shucang.utils.IntentUtils;

import java.util.List;

public class ShopCartAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

    private final String TAG = ShopCartAdapter.class.getName();


    private QMUIDialog qmuiDialog;

    private int deleteIndex;

    public ShopCartAdapter(List data) {
        super(R.layout.item_shop_cart, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean goodsBean) {
        ItemShopCartBinding binding = DataBindingUtil.bind(helper.itemView);

        binding.getRoot().setOnClickListener((v) -> {
            IntentUtils.toGoodsDetailActivity();
        });

        binding.vJia.setOnClickListener((v) -> {

        });

        binding.vJian.setOnClickListener((v) -> {

        });

        binding.vDelete.setOnClickListener((v) -> {
            deleteIndex = helper.getLayoutPosition();
            if (qmuiDialog == null) {
                initDialog();
            }
            qmuiDialog.show();
        });

    }

    private void initDialog() {
        qmuiDialog = new QMUIDialog.MessageDialogBuilder(MAPP.mapp.getCurrentActivity())
                .setMessage("确定要删除商品?")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        qmuiDialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        qmuiDialog.dismiss();
                        remove(deleteIndex);
                        notifyDataSetChanged();
                    }
                })
                .create();
    }
}
